package com.weatherhistoryandforecastapp.HowWasTheWeather.users.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.weatherhistoryandforecastapp.HowWasTheWeather.users.model.User;
import com.weatherhistoryandforecastapp.HowWasTheWeather.users.repository.UserRepository;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

class UserServiceTest {

    private UserService userService;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    void findByEmail_returnsUser() {
        User user = new User("test@example.com", "encoded-password");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Mono.just(user));

        Mono<User> result = userService.findByEmail("test@example.com");

        StepVerifier.create(result)
            .assertNext(foundUser -> {
                assertThat(foundUser.getEmail()).isEqualTo("test@example.com");
                assertThat(foundUser.getPassword()).isEqualTo("encoded-password");
            })
            .verifyComplete();
    }

    @Test
    void findByEmail_whenNotFound_returnsEmpty() {
        // Arrange
        when(userRepository.findByEmail("unknown@example.com")).thenReturn(Mono.empty());

        // Act
        Mono<User> result = userService.findByEmail("unknown@example.com");

        // Assert
        StepVerifier.create(result)
            .verifyComplete(); // No emission, just completion
    }

    @Test
    void save_encodesPasswordAndSavesUser() {
        User user = new User("new@example.com", "raw-password");
        User savedUser = new User("new@example.com", "encoded-password");
        when(passwordEncoder.encode("raw-password")).thenReturn("encoded-password");
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(savedUser));

        Mono<User> result = userService.save(user);

        StepVerifier.create(result)
            .assertNext(saved -> {
                assertThat(saved.getEmail()).isEqualTo("new@example.com");
                assertThat(saved.getPassword()).isEqualTo("encoded-password");
            })
            .verifyComplete();

        verify(passwordEncoder).encode("raw-password");
        verify(userRepository).save(argThat(u -> 
            u.getEmail().equals("new@example.com") && 
            u.getPassword().equals("encoded-password")));
    }
}