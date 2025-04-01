package com.weatherhistoryandforecastapp.HowWasTheWeather.users.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.weatherhistoryandforecastapp.HowWasTheWeather.users.model.User;
import com.weatherhistoryandforecastapp.HowWasTheWeather.users.repository.UserRepository;

import reactor.core.publisher.Mono;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Mono<User> findByEmail(String userEmail) {
        return userRepository.findByEmail(userEmail);
    }

    public Mono<User> save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Encode password before saving
        return userRepository.save(user);
    }
}