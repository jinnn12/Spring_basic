package com.beyond.basic.b2_board.Common;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component // (service 와 유사한 성격)
@Slf4j
public class JwtTokenFilter extends GenericFilter {

    @Value("${jwt.secretKeyAt}")
    private String secretKey;
//    token이 없는 경우 그냥 진행 시켜
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            //        doFilter에서 매개변수를 servletRequest 강제하므로 밑에서 내가 필요한 것으로 형변환 !
            HttpServletRequest req = (HttpServletRequest) servletRequest;
            String bearerToken = req.getHeader("Authorization"); // 헤더에서 Authorization값을 꺼내겠다
//            null일 경우 아까 handler에서 잡고, try catch 했으므로
            if (bearerToken == null) {
//            token 없는 경우 다시 filterchain으로 되돌아가는 로직
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
//        token이 있는 경우 토큰 검증 후 Authentication객체 생성
            String token = bearerToken.substring(7); // 'Bearer 'fdisamfoasmdf, 'Bearer ' 7자리까지 뗀다
//        token 검증 및 claims 추출 (토큰을 조작했을 때 에러가 터질 가능성이 존재)
//            굳이 try catch 한 이유는 밑엣놈 때문에, 밑엣놈은 500번대 에러가 터짐
            Claims claims = Jwts.parserBuilder() // 이것부터 아래 코드 전부가 검증하는 코드이다, 토큰이 잘못됐을 땐 여기서 에러가 남?
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token) // 토큰의 payload는 이 token 안에 있음
                    .getBody();

//        claims.getSubject(); // subject인 email을 꺼냄
//        claims.get("role");
            List<GrantedAuthority> authorities = new ArrayList<>(); // 권한이 하나만 있는 게 아니기 때문에
//        Authentication 객체를 만들 때 권한은 ROLE_' 라는 키워드를 붙여서 만드는 것이 추후 문제 발생X
            authorities.add(new SimpleGrantedAuthority("ROLE_" + claims.get("role"))); // @PreAuthorize("hasRole('ADMIN')), N개의 Role이 필요하다면 for문 돌려서 List<GrantedAuthority> list에 넣어줘야 함
//        principal(구분자) + credential() + authorize(권한)
//        사실 이메일만 알면 DB 조회하여 credential + authorize 다 알 수 있으나, 이렇게 설계해야한다.
            Authentication authentication = new UsernamePasswordAuthenticationToken(claims.getSubject(), "", authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication); // authentication이 있어야 한다 must

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        filterChain.doFilter(servletRequest, servletResponse); // filterChain으로 가라



    }
}
