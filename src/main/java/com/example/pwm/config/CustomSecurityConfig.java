package com.example.pwm.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.pwm.security.filter.JWTCheckFilter;
import com.example.pwm.security.handler.APILoginFailHandler;
import com.example.pwm.security.handler.APILoginSuccessHandler;
import com.example.pwm.security.handler.CustomAccesssDeniedHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
@RequiredArgsConstructor
@EnableWebSecurity // @PreAuthorize("hasAnyRole('ROLE_USER''ROLE_ADMIN')") 권한설정
public class CustomSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info("-----------security config------------");
        http
                .csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화 (테스트용)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // 모든 요청 허용
                )
                .formLogin(login -> login.disable()) // 폼 로그인 비활성화 (선택사항)
                .httpBasic(basic -> basic.disable()); // HTTP Basic 비활성화 (선택사항)

        // .authorizeHttpRequests((auth) -> auth
        // .requestMatchers("/error").permitAll()
        // .requestMatchers("/swagger-ui.html", "/swagger-ui/**",
        // "/v3/api-docs/**").permitAll()
        // .anyRequest().authenticated())

        // .cors(cors -> cors.configurationSource(corsConfigurationSource()))

        // .csrf(csrf -> csrf.disable())
        // .headers(headers -> headers.frameOptions(frame -> frame.disable())) //
        // X-Frame-Options 비활성화

        // .sessionManagement(session ->
        // session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

        // .formLogin(config -> {
        // config.loginPage("/api/signin");
        // config.successHandler(new APILoginSuccessHandler());
        // config.failureHandler(new APILoginFailHandler());
        // })

        // .addFilterBefore(new JWTCheckFilter(),
        // UsernamePasswordAuthenticationFilter.class) // JWT체크

        // .exceptionHandling(config -> {
        // config.accessDeniedHandler(new CustomAccesssDeniedHandler());
        // });

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    // 비밀번호 암호화
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
