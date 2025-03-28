package com.weatherhistoryandforecastapp.HowWasTheWeather.users.security.jwt;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.weatherhistoryandforecastapp.HowWasTheWeather.users.model.User;

import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationConverter implements ServerAuthenticationConverter {
    
    private final JwtTokenProvider tokenProvider;

    public JwtAuthenticationConverter(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        return Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
        .doOnNext(header -> System.out.println("Auth header: " + header))
        .filter(header -> header.startsWith("Bearer "))
                .map(header -> header.substring(7))
                .filter(token -> tokenProvider.validateToken(token))
                .map(token -> {
                    String username = tokenProvider.getUsernameFromToken(token);
                    User user = new User(username, null); // Password not needed here
                    System.out.println("Username: " + username);
                    return new UsernamePasswordAuthenticationToken(user, token, user.getAuthorities());
                });
    }
}
