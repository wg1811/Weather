package com.weatherhistoryandforecastapp.HowWasTheWeather.users.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.weatherhistoryandforecastapp.HowWasTheWeather.users.payload.request.LoginRequest;
import com.weatherhistoryandforecastapp.HowWasTheWeather.users.payload.response.JwtResponse;
import com.weatherhistoryandforecastapp.HowWasTheWeather.users.payload.response.MessageResponse;
import com.weatherhistoryandforecastapp.HowWasTheWeather.users.security.jwt.JwtTokenProvider;
import com.weatherhistoryandforecastapp.HowWasTheWeather.users.security.services.UserDetailsImpl;
import com.weatherhistoryandforecastapp.HowWasTheWeather.users.service.AuthService;

import jakarta.validation.Valid;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private ReactiveAuthenticationManager authenticationManager;

    @Autowired
    private AuthService userService;

    @Autowired
    private JwtTokenProvider jwtUtils;

    @PostMapping("/signin")
    public Mono<ResponseEntity<?>> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()))
                .map(authentication -> {
                    ReactiveSecurityContextHolder.withAuthentication(authentication);
                    String jwt = jwtUtils.generateJwtToken(authentication);
                    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
                    List<String> roles = userDetails.getAuthorities().stream()
                            .map(item -> item.getAuthority())
                            .collect(Collectors.toList());

                    return ResponseEntity.ok(new JwtResponse(
                            jwt,
                            userDetails.getId(),
                            userDetails.getEmail(),
                            roles));
                })
                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest()
                        .body(new MessageResponse("Invalid email or password"))));
    }
}