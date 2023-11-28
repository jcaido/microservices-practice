package com.jacido.gatewayservice.filter;

import com.jacido.gatewayservice.exception.CustomException;
import com.jacido.gatewayservice.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RouteValidator routeValidator;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (routeValidator.isSecured.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    //try {
                    //    throw new CustomException(HttpStatus.BAD_REQUEST, "missing authorization header");
                    //} catch (CustomException e) {
                    //    throw new RuntimeException(e);
                    //}
                    return Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "missing authorization header"));
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }

                try {
                    jwtUtil.validateToken(authHeader);
                } catch(Exception e) {
                    System.out.println("invalid access...!");
                    //try {
                    //    throw new CustomException(HttpStatus.BAD_REQUEST, "unauthorized access to application");
                    //} catch (CustomException ex) {
                    //    throw new RuntimeException(ex);
                    //}
                    return Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "unauthorized access to application"));
                }
            }
            return chain.filter(exchange);
        });
    }

    public static class Config {

    }
}
