package com.ohgiraffers.quote_collection_spring.global.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private static final String[] CONTENT_PATHS = {
            "/quote", "/quote/**",
            "/person", "/person/**",
            "/country", "/country/**",
            "/period", "/period/**",
            "/field", "/field/**",
            "/theme", "/theme/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                // 쿠키 인증 없이 Authorization 헤더의 Bearer JWT만 사용
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())

                .authorizeHttpRequests(auth -> auth
                        // 앞으로 만들 회원가입·로그인 경로
                        .requestMatchers(
                                HttpMethod.POST,
                                "/auth/signup",
                                "/auth/login"
                        ).permitAll()

                        // 명언·인물·분류 조회는 공개
                        .requestMatchers(HttpMethod.GET, CONTENT_PATHS)
                        .permitAll()

                        // 위 경로의 등록·수정·삭제는 관리자만
                        .requestMatchers(CONTENT_PATHS)
                        .hasRole("ADMIN")

                        // 그 외 요청은 로그인 필요
                        .anyRequest().authenticated()
                )

                .oauth2ResourceServer(resourceServer -> resourceServer
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(
                                        jwtAuthenticationConverter()
                                )
                        )
                );

        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter authoritiesConverter =
                new JwtGrantedAuthoritiesConverter();

        authoritiesConverter.setAuthoritiesClaimName("role");
        authoritiesConverter.setAuthorityPrefix("ROLE_");

        JwtAuthenticationConverter converter =
                new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);

        return converter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}