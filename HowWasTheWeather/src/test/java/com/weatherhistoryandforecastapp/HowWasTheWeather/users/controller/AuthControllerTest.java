package com.weatherhistoryandforecastapp.HowWasTheWeather.users.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.weatherhistoryandforecastapp.HowWasTheWeather.users.model.User;
import com.weatherhistoryandforecastapp.HowWasTheWeather.users.security.jwt.JwtAuthenticationManager;
import com.weatherhistoryandforecastapp.HowWasTheWeather.users.security.jwt.JwtTokenProvider;
import com.weatherhistoryandforecastapp.HowWasTheWeather.users.security.model.AuthRequest;
import com.weatherhistoryandforecastapp.HowWasTheWeather.users.security.model.AuthResponse;
import com.weatherhistoryandforecastapp.HowWasTheWeather.users.service.UserService;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

class AuthControllerTest {

    private AuthController authController;
    private JwtAuthenticationManager authManager;
    private JwtTokenProvider tokenProvider;
    private UserService userService;

    @BeforeEach
    void setUp() {
        // Mock dependencies
        authManager = mock(JwtAuthenticationManager.class);
        tokenProvider = mock(JwtTokenProvider.class);
        userService = mock(UserService.class);
        authController = new AuthController(tokenProvider, userService, authManager);
    }

    @Test
    void login_withValidCredentials_returnsToken() {
        // Arrange
        AuthRequest request = new AuthRequest("user@example.com", "password");
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken("user@example.com", null);
        when(authManager.authenticateWithCredentials("user@example.com", "password"))
            .thenReturn(Mono.just(auth));
        when(tokenProvider.generateToken(auth)).thenReturn("mocked-jwt-token");

        // Act
        Mono<ResponseEntity<AuthResponse>> result = authController.login(request);

        // Assert
        StepVerifier.create(result)
            .assertNext(response -> {
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(response.getBody().getToken()).isEqualTo("mocked-jwt-token");
                assertThat(response.getBody().getError()).isNull();
            })
            .verifyComplete();
    }

    @Test
    void login_withInvalidCredentials_returnsUnauthorized() {
        // Arrange
        AuthRequest request = new AuthRequest("user@example.com", "wrong-password");
        when(authManager.authenticateWithCredentials("user@example.com", "wrong-password"))
            .thenReturn(Mono.empty()); // Simulates authentication failure

        // Act
        Mono<ResponseEntity<AuthResponse>> result = authController.login(request);

        // Assert
        StepVerifier.create(result)
            .assertNext(response -> {
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
                assertThat(response.getBody().getToken()).isNull();
                assertThat(response.getBody().getError()).isEqualTo("Invalid credentials");
            })
            .verifyComplete();
    }

    @Test
    void signup_withNewEmail_createsUserAndReturnsToken() {
        AuthRequest request = new AuthRequest("newuser@example.com", "password");
        User newUser = new User("newuser@example.com", "password");
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken("newuser@example.com", null);

        // Mock UserService: email doesn't exist, then save succeeds
        when(userService.findByEmail("newuser@example.com")).thenReturn(Mono.empty());
        when(userService.save(any(User.class))).thenReturn(Mono.just(newUser));
        // Mock authManager and tokenProvider
        when(authManager.authenticateWithCredentials("newuser@example.com", "password"))
            .thenReturn(Mono.just(auth));
        when(tokenProvider.generateToken(auth)).thenReturn("mocked-jwt-token");

        Mono<ResponseEntity<AuthResponse>> result = authController.signup(request);

        StepVerifier.create(result)
            .assertNext(response -> {
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
                assertThat(response.getBody().getToken()).isEqualTo("mocked-jwt-token");
                assertThat(response.getBody().getError()).isNull();
            })
            .verifyComplete();

        verify(userService).save(any(User.class)); // Ensure save was called
    }

    @Test
    void signup_withExistingEmail_returnsError() {
        AuthRequest request = new AuthRequest("existing@example.com", "password");
        User existingUser = new User("existing@example.com", "password");

        // Mock UserService: email already exists
        when(userService.findByEmail("existing@example.com")).thenReturn(Mono.just(existingUser));

        Mono<ResponseEntity<AuthResponse>> result = authController.signup(request);

        StepVerifier.create(result)
            .expectErrorMatches(throwable -> 
                throwable instanceof RuntimeException && 
                "Email already in use".equals(throwable.getMessage()))
            .verify();

        verify(userService, never()).save(any(User.class)); // Ensure save wasn't called
    }
}