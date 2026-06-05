package com.musicapi.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ApiKeyFilter extends OncePerRequestFilter {

    @Value("${api.key}")
    private String apiKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String method = request.getMethod();
        String path = request.getRequestURI();

        // Permitir acceso libre a H2 console y peticiones de lectura
        if (path.startsWith("/h2-console") || method.equals("GET")
                || method.equals("HEAD") || method.equals("OPTIONS")) {
            filterChain.doFilter(request, response);
            return;
        }

        // POST, PUT, DELETE, PATCH requieren API Key
        String providedKey = request.getHeader("X-API-KEY");
        if (providedKey == null || !providedKey.equals(apiKey)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(
                "{\"status\": 403, \"error\": \"Forbidden\", " +
                "\"mensaje\": \"Autenticación requerida. Incluye el header X-API-KEY.\"}"
            );
            return;
        }

        filterChain.doFilter(request, response);
    }
}
