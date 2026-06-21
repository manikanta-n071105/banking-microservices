package com.bank.apigateway.filter;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

@Component
public class RoleValidator {

    public boolean isAdminOnly(
            ServerWebExchange exchange) {

        String path =
                exchange.getRequest()
                        .getURI()
                        .getPath();

        return path.startsWith("/customers")
                || path.startsWith("/accounts");
    }
}