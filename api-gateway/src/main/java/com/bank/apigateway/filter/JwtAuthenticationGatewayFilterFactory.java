package com.bank.apigateway.filter;

import com.bank.apigateway.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationGatewayFilterFactory
        extends AbstractGatewayFilterFactory<Object> {

    private final JwtUtil jwtUtil;



    public JwtAuthenticationGatewayFilterFactory(
            JwtUtil jwtUtil) {

        super(Object.class);
        this.jwtUtil = jwtUtil;
    }

    @Override
    public GatewayFilter apply(
            Object config) {

        return (exchange, chain) -> {

            String path =
                    exchange.getRequest()
                            .getURI()
                            .getPath();

            if(path.startsWith("/auth")) {

                return chain.filter(exchange);
            }

            String authHeader =
                    exchange.getRequest()
                            .getHeaders()
                            .getFirst("Authorization");

            if(authHeader == null ||
                    !authHeader.startsWith(
                            "Bearer ")) {

                exchange.getResponse()
                        .setStatusCode(
                                HttpStatus.UNAUTHORIZED);

                return exchange.getResponse()
                        .setComplete();
            }

            String token =
                    authHeader.substring(7);

            if(!jwtUtil.validateToken(token)) {

                exchange.getResponse()
                        .setStatusCode(
                                HttpStatus.UNAUTHORIZED);

                return exchange.getResponse()
                        .setComplete();
            }

            String username =
                    jwtUtil.extractUsername(token);

            String role =
                    jwtUtil.extractRole(token);

            System.out.println("USERNAME = " + username);
            System.out.println("ROLE = " + role);

            exchange = exchange.mutate()
                    .request(r -> r.headers(headers -> {
                        headers.add("X-User", username);
                        headers.add("X-Role", role);
                    }))
                    .build();

            if(path.startsWith("/customers")
                    || path.startsWith("/accounts")) {

                if(!role.equals("ADMIN")) {

                    exchange.getResponse()
                            .setStatusCode(
                                    HttpStatus.FORBIDDEN);

                    return exchange
                            .getResponse()
                            .setComplete();
                }
            }

            return chain.filter(exchange);
        };
    }
}