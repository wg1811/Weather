package com.weatherhistoryandforecastapp.HowWasTheWeather.users.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.weatherhistoryandforecastapp.HowWasTheWeather.users.model.User;
import com.weatherhistoryandforecastapp.HowWasTheWeather.users.security.jwt.JwtAuthenticationManager;
import com.weatherhistoryandforecastapp.HowWasTheWeather.users.security.jwt.JwtTokenProvider;
import com.weatherhistoryandforecastapp.HowWasTheWeather.users.security.model.AuthRequest;
import com.weatherhistoryandforecastapp.HowWasTheWeather.users.security.model.AuthResponse;
import com.weatherhistoryandforecastapp.HowWasTheWeather.users.service.UserService;

import jakarta.validation.Valid;
import reactor.core.publisher.Mono;

@RestController
public class AuthController {

    private final JwtTokenProvider tokenProvider;
    private final UserService userService;
    private final JwtAuthenticationManager authManager;

    public AuthController(JwtTokenProvider tokenProvider, UserService userService,
            JwtAuthenticationManager authManager) {
        this.tokenProvider = tokenProvider;
        this.userService = userService;
        this.authManager = authManager;
    }

    // added /api to the path to differentiate from frontend server /login? I don't
    // understand this stuff yet.
    @PostMapping("/api/login")
    public Mono<ResponseEntity<AuthResponse>> login(@Valid @RequestBody AuthRequest request) {
        return authManager.authenticateWithCredentials(request.getEmail(), request.getPassword())
                .map(authentication -> {
                    String token = tokenProvider.generateToken(authentication);
                    return ResponseEntity.ok(new AuthResponse(token));
                })
                .onErrorResume(BadCredentialsException.class, e -> 
                Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse(null, "Invalid credentials"))))
            .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse(null, "Invalid credentials"))));    }

    @PostMapping("/api/signup")
    public Mono<ResponseEntity<AuthResponse>> signup(@Valid @RequestBody AuthRequest request) {
        return userService.findByEmail(request.getEmail())
                .flatMap(existingUser -> Mono.error(new RuntimeException("Email already in use")))
                .switchIfEmpty(Mono.defer(() -> {
                    User newUser = new User(request.getEmail(), request.getPassword());
                    return userService.save(newUser);
                }))
                .flatMap(
                        savedUser -> authManager.authenticateWithCredentials(request.getEmail(), request.getPassword()))
                .map(authentication -> {
                    String token = tokenProvider.generateToken(authentication);
                    return ResponseEntity.status(HttpStatus.CREATED).body(new AuthResponse(token));
                });
    }
}