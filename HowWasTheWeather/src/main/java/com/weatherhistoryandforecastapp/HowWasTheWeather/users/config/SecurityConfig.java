package com.weatherhistoryandforecastapp.HowWasTheWeather.users.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import com.weatherhistoryandforecastapp.HowWasTheWeather.users.service.JwtAuthenticationManager;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private final JwtAuthenticationManager authenticationManager;

    public SecurityConfig(JwtAuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf().disable()
                .authorizeExchange()
                .pathMatchers("/login","/signup").permitAll()
                .anyExchange().authenticated()
                .and()
                .authenticationManager(authenticationManager)
                .build();
    }
}



// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.http.HttpStatus;
// import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
// import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
// import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
// import org.springframework.security.config.web.server.ServerHttpSecurity;
// import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.server.SecurityWebFilterChain;

// import com.weatherhistoryandforecastapp.HowWasTheWeather.users.repository.UserRepository;
// import com.weatherhistoryandforecastapp.HowWasTheWeather.users.security.jwt.JwtTokenAuthenticationFilter;
// import com.weatherhistoryandforecastapp.HowWasTheWeather.users.security.jwt.JwtTokenProvider;

// import reactor.core.publisher.Mono;

// @Configuration
// @EnableWebFluxSecurity
// @EnableReactiveMethodSecurity
// public class SecurityConfig {

//     private final JwtTokenProvider tokenProvider; // Fixed variable name

//     public SecurityConfig(JwtTokenProvider tokenProvider) {
//         this.tokenProvider = tokenProvider;
//     }

//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }

//     @Bean
//     public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
//         return http
//                 .csrf(ServerHttpSecurity.CsrfSpec::disable)
//                 .authorizeExchange(exchanges -> exchanges
//                         .pathMatchers("/api/auth/**").permitAll()
//                         .pathMatchers("/api/weather/**").permitAll() // Allow weather endpoints
//                         .pathMatchers("/api/forecast/**").permitAll() // Allow forecast endpoints
//                         .anyExchange().authenticated())
//                 .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
//                 .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
//                 .exceptionHandling(exceptions -> exceptions
//                     .authenticationEntryPoint((exchange, ex) -> {
//                         return Mono.fromRunnable(() -> {
//                             exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//                         });
//                     })
//                     .accessDeniedHandler((exchange, denied) -> {
//                         return Mono.fromRunnable(() -> {
//                             exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
//                         });
//                     })
//                 )
//                 .addFilterAt(new JwtTokenAuthenticationFilter(tokenProvider), SecurityWebFiltersOrder.AUTHENTICATION)
//                 .build();
//     }

//     @Bean
//     public ReactiveUserDetailsService userDetailsService(UserRepository userRepository) {
//         return username -> userRepository.findByEmail(username)
//                 .map(user -> {
//                     if (user.getRoles() != null && !user.getRoles().isEmpty()) {
//                         return org.springframework.security.core.userdetails.User
//                                 .withUsername(user.getEmail())
//                                 .password(user.getPassword())
//                                 .roles(user.getRoles().toArray(new String[0]))
//                                 .build();
//                     }
//                     // Fallback with a default role
//                     return org.springframework.security.core.userdetails.User
//                             .withUsername(user.getEmail())
//                             .password(user.getPassword())
//                             .roles("USER")
//                             .build();
//                 });
//     }
// }

// =====================================

// package com.weatherhistoryandforecastapp.HowWasTheWeather.users.config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
// import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
// import org.springframework.security.config.web.server.ServerHttpSecurity;
// import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.server.SecurityWebFilterChain;

// import com.weatherhistoryandforecastapp.HowWasTheWeather.users.repository.UserRepository;

// @Configuration
// @EnableWebFluxSecurity
// @EnableReactiveMethodSecurity
// public class SecurityConfig {

//     private final JwtTokenProvider TokenProvider; // Adding this later.

//     public SecurityConfig(JwtTokenProvider tokenProvider) {
//         this.tokenProvider = tokenProvider;
//     }

//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }

//     @Bean
//     public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
//         return http
//                 .csrf(ServerHttpSecurity.CsrfSpec::disable)
//                 .authorizeExchange(exchanges -> exchanges
//                         .pathMatchers("/api/auth/**").permitAll()
//                         .anyExchange().authenticated())
//                 .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
//                 .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
//                 .exceptionHandling(exceptions -> exceptions
//                 .authenticationEntryPoint(exchange, ex) -> {
//                     return Mono<T>.fromRunnable(() -> {
//                         exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//                     });
//                     })
//                     .accessDeniedHandler((exchange, denied) -> {
//                         return Mono.fromRunnable(() -> {
//                             exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
//                         });
//                     })
//                 )
//                 .addFilterAt(new JwtAuthenticationFilter(tokenProvider), SecurityWebFiltersOrder.AUTHENTICATION)
//                 .build();
//     }

//     @Bean
//     public ReactiveUserDetailsService userDetailsService(UserRepository userRepository) {
//         return username -> userRepository.findByEmail(username)
//                 .map(user -> {
//                     if (user.getRoles() != null && !user.getRoles().isEmpty()) {
//                         return org.springframework.security.core.userdetails.User
//                                 .withUsername(user.getEmail())
//                                 .password(user.getPassword())
//                                 .roles(user.getRoles().toArray(new String[0]))
//                                 .build();
//                     }
//                     // Fallback with a default role
//                     return org.springframework.security.core.userdetails.User
//                             .withUsername(user.getEmail())
//                             .password(user.getPassword())
//                             .roles("USER")
//                             .build();
//                 });
//     }
// }
// This is previous version. Not sure if I need to add the /weather, /forecast?
// Or have the httpBasic bit.
// .csrf(csrf -> csrf.disable())
// .authorizeExchange(exchanges -> exchanges
// .pathMatchers("/api/auth/**").permitAll()
// .pathMatchers("/api/weather/**").permitAll() // I'm not sure if this is
// correct. Could be /public, or /forecast?
// .anyExchange().authenticated())
// .httpBasic(withDefaults())
// .build();
// }
// }
