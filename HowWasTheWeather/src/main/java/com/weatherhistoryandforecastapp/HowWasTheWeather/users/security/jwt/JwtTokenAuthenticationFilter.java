
package com.weatherhistoryandforecastapp.HowWasTheWeather.users.security.jwt;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import lombok.NonNull;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Component
public class JwtTokenAuthenticationFilter implements WebFilter {
    
    private final JwtTokenProvider tokenProvider;

    public JwtTokenAuthenticationFilter(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
                .filter(header -> header.startsWith("Bearer "))
                .map(header -> header.substring(7))
                .filter(token -> tokenProvider.validateToken(token))
                .map(token -> {
                    String username = tokenProvider.getUsernameFromToken(token);
                    Authentication auth = new UsernamePasswordAuthenticationToken(
                            username, 
                            null, 
                            Collections.emptyList() // Replace with actual authorities if needed
                    );
                    return auth;
                })
                .flatMap(authentication -> 
                    chain.filter(exchange)
                        .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication))
                )
                .switchIfEmpty(chain.filter(exchange));
    }
}

// ==============================

// package com.weatherhistoryandforecastapp.HowWasTheWeather.users.security.jwt;

// import java.util.Collections;

// import org.springframework.http.HttpHeaders;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.web.server.WebFilterExchange;
// import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
// import org.springframework.stereotype.Component;
// import org.springframework.web.server.ServerWebExchange;

// import reactor.core.publisher.Mono;

// @Component
// public class JwtTokenAuthenticationFilter extends AbstractAuthenticationWebFilter {
//     private final JwtTokenProvider tokenProvider;

//     public JwtTokenAuthenticationFilter(JwtTokenProvider tokenProvider) {
//         super(new BearerTokenAuthenticationConverter());
//         this.tokenProvider = tokenProvider;
//         setAuthenticationManager(authentication -> {
//             String token = (String) authentication.getCredentials();
//             if (tokenProvider.validateToken(token)) {
//                 String username = tokenProvider.getUsernameFromToken(token);
//                 return Mono.just(new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList()));
//             }
//             return Mono.empty();
//         });
//     }

//     @Override
//     protected Mono<Void> onAuthenticationSuccess(Authentication authentication, WebFilterExchange exchange) {
//         return this.successHandler.onAuthenticationSuccess(exchange, authentication);
//     }

//     private static class BearerTokenAuthenticationConverter implements ServerAuthenticationConverter {
//         @Override
//         public Mono<Authentication> convert(ServerWebExchange exchange) {
//             return Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
//                     .filter(header -> header.startsWith("Bearer "))
//                     .map(header -> header.substring(7))
//                     .map(token -> new UsernamePasswordAuthenticationToken(token, token));
//         }
//     }
// }

// {

// private final JwtTokenProvider jwtUtils;

// public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain)
// {
// return
// Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst("Authorization"))
// .filter(header -> header.startsWith("Bearer "))
// .flatMap(token -> {
// if (jwtUtils.validateToken(token)) {
// Authentication auth = new
// UsernamePasswordAuthenticationToken(jwtUtils.getEmailFromToken(token), token,
// jwtUtils.getAuthoritiesFromToken(token));
// return chain.filter(exchange)
// .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth));
// }
// return chain.filter(exchange);
// })
// .switchIfEmpty(chain.filter(exchange));
// }
// }
