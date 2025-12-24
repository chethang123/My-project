package com.microservices.api_gateway;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements GlobalFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                             GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        String method = request.getMethod().name();

        /* =========================
           1️⃣ ALLOW CORS PREFLIGHT
           ========================= */
        if ("OPTIONS".equalsIgnoreCase(method)) {
            exchange.getResponse().setStatusCode(HttpStatus.OK);
            return exchange.getResponse().setComplete();
        }

        /* =========================
           2️⃣ PUBLIC ENDPOINTS
           ========================= */
        if (path.startsWith("/auth")) {
            return chain.filter(exchange);
        }

        /* =========================
           3️⃣ READ AUTH HEADER
           ========================= */
        String authHeader = request.getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        /* =========================
           4️⃣ VALIDATE TOKEN
           ========================= */
        String token = authHeader.substring(7);

        try {
            jwtUtil.validateToken(token);
        } catch (Exception ex) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        /* =========================
           5️⃣ FORWARD REQUEST
           ========================= */
        return chain.filter(exchange);
    }
}
