package com.example.pwm.security.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.pwm.controller.dto.HostDTO;
import com.example.pwm.util.JWTUtil;
import com.google.gson.Gson;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JWTCheckFilter extends OncePerRequestFilter {

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        log.info("check uri............{}", path);

        //로그인 처리 경로 예외
        if (path.startsWith("/api/host/")) {
            return true;
        }        
                //로그인 처리 경로 예외
        if (path.startsWith("/api/sign")) {
            return true;
        }        
        //DB 경로 예외
        if (path.startsWith("/h2-console")) {
            return true;
        }
        if (path.startsWith("/favicon.ico")) {
            return true;
        }
        /*
         * 첨부파일 이미지를 사용하는 경로 등 예외
         * if(request.getMethod().equals("OPTIONS")){
         * return true;
         * }
         * 
         * if(path.startsWith("/api/host/view/")){
         * return true;
         * }
         */
        return false;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        log.info("--------------JWTCheckFilter---------------");
        String authHeaderStr = request.getHeader("Authorization");
        try {
            String accessToken = authHeaderStr.substring(7);
            Map<String, Object> claims = JWTUtil.validateToken(accessToken);

            log.info("JWT Claims: {}", claims);
            // filterChain.doFilter(request, response);

            String email = (String) claims.get("email");
            String passwd = (String) claims.get("passwd");
            String name = (String) claims.get("name");
            List<String> roleNames = (List<String>) claims.get("roleNames");

            HostDTO hostDTO = new HostDTO(email, passwd, name, roleNames);

            log.info("-----------------------------------");
            log.info("hostDTO : {}", hostDTO);
            log.info("{}", hostDTO.getAuthorities());

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(hostDTO,
                    passwd, hostDTO.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        } catch (Exception e) {

            log.error("JWT Check Error..................");
            log.error(e.getMessage());

            Gson gson = new Gson();
            String msg = gson.toJson(Map.of("error", "ERROR_ACCESS_TOKEN"));

            response.setContentType("application/json");
            PrintWriter printWriter = response.getWriter();
            printWriter.println(msg);
            printWriter.close();
        }
    }
}
