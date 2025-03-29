package com.weatherhistoryandforecastapp.HowWasTheWeather.users.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;

import com.weatherhistoryandforecastapp.HowWasTheWeather.users.security.jwt.JwtAuthenticationConverter;
import com.weatherhistoryandforecastapp.HowWasTheWeather.users.security.jwt.JwtAuthenticationManager;
import com.weatherhistoryandforecastapp.HowWasTheWeather.users.security.jwt.JwtTokenProvider;

import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private final JwtAuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    public SecurityConfig(JwtAuthenticationManager authenticationManager, JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(csrf -> csrf.disable()
                        .httpBasic(basic -> basic.disable()
                                .formLogin(login -> login.disable()
                                        .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                                        .authenticationManager(authenticationManager)
                                        .authorizeExchange(exchange -> exchange
                                                .pathMatchers("/api/login", "/api/signup").permitAll()
                                                .anyExchange().authenticated())
                                        .exceptionHandling(spec -> spec
                                                .authenticationEntryPoint((exchange, ex) -> {
                                                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                                                    return Mono.empty();
                                                })))))
                .addFilterAt(jwtAuthenticationFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }

    @Bean
    public AuthenticationWebFilter jwtAuthenticationFilter() {
        AuthenticationWebFilter filter = new AuthenticationWebFilter(authenticationManager);
        filter.setServerAuthenticationConverter(
                new JwtAuthenticationConverter(tokenProvider));
        return filter;
    }
}
