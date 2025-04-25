package com.example.pwm.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info("-----------security config------------");
        // http
        // .cors(httpSecurityCorsConfigurer -> {
        // httpSecurityCorsConfigurer. configurationSource(corsConfigurationSource());
        // });

        // http.sessionManagement(sessionConfig ->
        // sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // http.csrf(config -> config.disable());

        // http.formLogin(config -> {
        // config.loginPage("/api/host/login");
        // config.successHandler(new APILoginSuccessHandler());
        // });

        // --------------------------------------
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions(frame -> frame.disable())) // X-Frame-Options 비활성화
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(config -> {
                    config.loginPage("/api/host/login"); 
                    config.successHandler(new APILoginSuccessHandler());
                    config.failureHandler(new APILoginFailHandler());
                });
            http.addFilterBefore(new JWTCheckFilter(), UsernamePasswordAuthenticationFilter.class);     //JWT체크
            
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
