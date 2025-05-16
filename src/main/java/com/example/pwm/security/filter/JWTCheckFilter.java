package com.example.pwm.security.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.pwm.domain.host.HostDTO;
import com.example.pwm.global.util.JWTUtil;
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

        if (path.startsWith("/api/host/") || path.startsWith("/api/sign") || path.startsWith("/swagger") || path.startsWith("/swagger-ui.html") || path.startsWith("/swagger-ui/**") ||path.startsWith("/api-docs") ||path.startsWith("/swagger") || path.startsWith("/api-docs/**") || path.startsWith("/v3/api-docs/**"))  {
            return true;
        }
        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeaderStr = request.getHeader("Authorization");

        try {
            String accessToken = authHeaderStr.substring(7);
            Map<String, Object> claims = JWTUtil.validateToken(accessToken);

            String email = (String) claims.get("email");
            String passwd = (String) claims.get("passwd");
            String name = (String) claims.get("name");
            List<String> roleNames = (List<String>) claims.get("roleNames");

            HostDTO hostDTO = new HostDTO(email, name, roleNames);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(hostDTO, passwd, hostDTO.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        } catch (Exception e) {
            log.error("JWT Check Error..................");
            log.error(e.getMessage());

            response.setContentType("application/json");
            PrintWriter printWriter = response.getWriter();
            printWriter.println(new Gson().toJson(Map.of("error", "ERROR_ACCESS_TOKEN")));
            printWriter.close();
        }
    }
}
