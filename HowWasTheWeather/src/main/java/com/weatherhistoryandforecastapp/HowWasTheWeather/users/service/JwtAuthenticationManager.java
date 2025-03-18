
// 1. First, let's create a proper JwtAuthenticationManager
package com.weatherhistoryandforecastapp.HowWasTheWeather.users.service;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.weatherhistoryandforecastapp.HowWasTheWeather.users.security.jwt.JwtTokenProvider;

import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Component
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    private final UserService userService;
    private final JwtTokenProvider tokenProvider;
    private final BCryptPasswordEncoder passwordEncoder;

    public JwtAuthenticationManager(UserService userService, JwtTokenProvider tokenProvider) {
        this.userService = userService;
        this.tokenProvider = tokenProvider;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();
        
        if (!tokenProvider.validateToken(authToken)) {
            return Mono.empty();
        }
        
        String email = tokenProvider.getUsernameFromToken(authToken);
        return userService.findByEmail(email)
            .map(user -> {
                var authorities = user.getRoles().stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
                
                return new UsernamePasswordAuthenticationToken(
                    user.getEmail(),
                    null,
                    authorities
                );
            });
    }

    // Method to authenticate with email and password
    public Mono<Authentication> authenticateWithCredentials(String email, String password) {
        return userService.findByEmail(email)
            .filter(user -> passwordEncoder.matches(password, user.getPassword()))
            .map(user -> {
                var authorities = user.getRoles().stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
                
                return new UsernamePasswordAuthenticationToken(
                    user.getEmail(),
                    null,
                    authorities
                );
            });
    }
}


// package com.weatherhistoryandforecastapp.HowWasTheWeather.users.service;


// import org.springframework.security.authentication.ReactiveAuthenticationManager;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.AuthenticationException;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
// import org.springframework.stereotype.Component;
// import org.springframework.web.server.ServerWebExchange;

// import com.weatherhistoryandforecastapp.HowWasTheWeather.users.config.JwtUtil;

// import reactor.core.publisher.Mono;

// @Component
// public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

//     private final JwtUtil jwtUtil;
//     private final UserService userService;

//     public JwtAuthenticationManager(JwtUtil jwtUtil, UserService userService) {
//         this.jwtUtil = jwtUtil;
//         this.userService = userService;
//     }

//     @Override
//     public Mono<Authentication> authenticate(Authentication authentication) throws AuthenticationException {
//         String token = authentication.getCredentials().toString();
//         String username = jwtUtil.extractUsername(token); //  Have to align with email (no username)

//         return userService.findByEmail(username)
//                 .map(userDetails -> {
//                     if (jwtUtil.validateToken(token, userDetails.getEmail())) {
//                         return authentication;
//                     } else {
//                         throw new AuthenticationException("Invalid JWT token") {};
//                     }
//                 });
//     }

//     public ServerAuthenticationConverter authenticationConverter() {
//         return new ServerAuthenticationConverter() {
//             @Override
//             public Mono<Authentication> convert(ServerWebExchange exchange) {
//                 String token = exchange.getRequest().getHeaders().getFirst("Authorization");
//                 if (token != null && token.startsWith("Bearer ")) {
//                     token = token.substring(7);
//                     return Mono.just(SecurityContextHolder.getContext().getAuthentication());
//                 }
//                 return Mono.empty();
//             }
//         };
//     }
// }
