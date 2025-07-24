package com.beyond.basic.b2_board.Common;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
// PreAuthorize 어노테이션 사용하기 위한 설정 -> filter chain 만들 때 @EnableMethodSecurity 사용
@EnableMethodSecurity
public class SecurityConfig {
    private final JwtTokenFilter jwtTokenFilter;
    private final JwtAuthenticationHandler jwtAuthenticationHandler;
    private final JwtAuthorizationHandler jwtAuthorizationHandler;

//    내가 만든 객체는 @Component, 외부 라이브러리를 활용한 객체는 @Bean + @Configuration
//    @Bean은 메서드 위에 붙여 Return 되는 객체를 싱글톤 객체로 생성한다
//    @Component는 클래스 위에 붙여 클래스자체를 싱글톤 객체로 생성한다
//    filter 계층에서 filter 로직을 커스텀한다.
    @Bean // @Component와 유사, 싱글톤객체 만드는 어노테이션, 둘의 차이는 무엇일까? 메서드에 붙어 있음
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception { // Single Tone
        return httpSecurity
//                cors : 특정도메인에 대한 허용정책, 사전에 약속 되어 있는 특정 도메인의 요청만 받겠다 / postman은 웹브라우저가 아니므로 cors 정책에 적용 X
                .cors(c -> c.configurationSource(corsConfiguration()))
//                csrf : (보안공격 중 하나로서 타 사이트의 쿠키값을 꺼내서 탈취 하는 공격)에 대해서 비활성화
//                세션기반 로그인(mvc 패턴, ssr)에서는 csrf 별도 설정하는 것이 일반적이나
//                토큰기반 로그인(rest api서버, csr)에서는 csrf 설정 않는 것이 일반적
                .csrf(AbstractHttpConfigurer::disable)
//                httpBasic : 인증 방법 중 하나, email/pw를 인코딩하여 인증(전송)하는 방식, 간단한 인증의 경우에만 사용한다 / email/pw를 인코딩하여 전송하는 것이 굉장히 위험함
                .httpBasic(AbstractHttpConfigurer::disable)
//                세션 로그인 방식 비활성화 (세션 : stateful인데(인증값을 서버에서 가지고 있음) -> STATELESS로 설정했으니 토큰로그인 방식으로 하겠다)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                 token을 검증하고, token 검증을 통해 Authentication 객체 생성
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(e ->
                        e.authenticationEntryPoint(jwtAuthenticationHandler) // 401의 경우 (token이 null일 때 로그인필요 등)
                                .accessDeniedHandler(jwtAuthorizationHandler) // 403의 경우
                )
//                예외 api 정책 설정**
//                athenticated() : 예외를 제외한 모든 요청에 대해서 Authentication객체가 생성 되기를 요구
                .authorizeHttpRequests(a -> a.requestMatchers("/author/create", "/author/doLogin").permitAll().anyRequest().authenticated())
                .build();
    }

//    도메인 정책에 대해 약속하는 것?
    private CorsConfigurationSource corsConfiguration(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000")); // 원하는 도메인 커스텀
        configuration.setAllowedMethods(Arrays.asList("*")); // 모든 HTTP(get, post, patch 등) 메서드 허용
        configuration.setAllowedHeaders(Arrays.asList("*")); // 모든 헤더요소(Authorization 등) 허용
        configuration.setAllowCredentials(true); // 자격 증명 허용
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); //모든 url패턴에 대해 cors설정 적용, ** : 모든계층구조
        return source; // 모든 정책이 들어가 있음
    }

    @Bean // 메서드가 리턴해주는 객체(passwordEncoder를 싱글톤으로)가 있을 때 그 객체를 싱글톤으로 만들어줌,
         // authorService에서 PasswordEncoder 객체를 주입
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


}
