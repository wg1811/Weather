
// 1. First, let's create a proper JwtAuthenticationManager
package com.weatherhistoryandforecastapp.HowWasTheWeather.users.security.jwt;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.weatherhistoryandforecastapp.HowWasTheWeather.users.service.UserService;

import reactor.core.publisher.Mono;

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
                    var authorities = Arrays.stream(user.getRoles())
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

                    return new UsernamePasswordAuthenticationToken(
                            user,
                            null,
                            authorities);
                });
    }

    // Method to authenticate with email and password
    public Mono<Authentication> authenticateWithCredentials(String email, String password) {
        return userService.findByEmail(email)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()))
                .map(user -> {
                    var authorities = Arrays.stream(user.getRoles())
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

                    return new UsernamePasswordAuthenticationToken(
                            user.getEmail(),
                            null,
                            authorities);
                });
    }
}
