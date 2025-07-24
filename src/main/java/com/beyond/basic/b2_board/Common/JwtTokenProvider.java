package com.beyond.basic.b2_board.Common;

import com.beyond.basic.b2_board.Author.Domain.Author;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.Signature;
import java.util.Date;

@Component // 싱글톤으로 만들고, controller에 주입
public class JwtTokenProvider { // token은 payload + secret key를 조합해서 만든다
    @Value("${jwt.expirationAt}") // "${}" : yml에 있는 파일 전체를 지칭
    private int expirationAt;
    @Value("${jwt.secretKeyAt}")
    private String secretKeyAt;

    private Key secret_at_key; // secret key의 객체 타입은 Key

//    @PostConstruct : 스프링빈이 만들어지는 시점에 빈이 만들어진 직후에 아래 메서드가 바로 실행, 즉 호출을 안해도 바로 실행된다, createAtToken 갖다 쓰면 바로 이게 사용됨.
//    모든 사용자가 똑같은 secretKey를 가지므로 딱 한번만 초기화해서 쓰자
    @PostConstruct
    public void init() { // 메서드로 한 번 만들어서 JwtTokenProvider를 싱글톤 객체로 주입을 해서 사용
        secret_at_key = new SecretKeySpec(java.util.Base64.getDecoder().decode(secretKeyAt), SignatureAlgorithm.HS512.getJcaName()); // decode + algorithm 세팅
    }

    public String createAtToken(Author author) {
        String email = author.getEmail();
        String role = author.getRole().toString();
//        claims는 Payload(사용자정보) 부분으로 사용된다, subJect : 주된 키값을 의미, 딱 하나만 넣을 수 있다, 유일한 구분자
        Claims claims = Jwts.claims().setSubject(email); // filter에서 claims.getSubject와 싱크가 맞음
//        주된 key 값을 제외한 나머지 사용자정보는 put 사용하여 key:value 세팅
        claims.put("role", role);
//        claims.put("role", role); // 추가도 가능
        Date now = new Date();
//        토큰 : Header + Payload + Signature
        String token = Jwts.builder() // 라이브러리의 도움, 토큰을 제작
                .setClaims(claims)
                .setIssuedAt(now) // 발행시간
                .setExpiration(new Date(now.getTime() + expirationAt*60*1000L)) // 만료시간, 유효기간 지난 토큰은 의미없음, 30분을 밀리초 단위로 세팅(30*60*1000L), 시간은 중요값, 만료 되었을 때 검증하는 것이 필요(라이브러리 존재)
//                signWith : 'secret key'를 통해 마지막 signature 생성
                .signWith(secret_at_key)
                .compact();

        return token;
    }
}
