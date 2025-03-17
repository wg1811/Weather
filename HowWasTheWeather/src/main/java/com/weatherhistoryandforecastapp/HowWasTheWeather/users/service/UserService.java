package com.weatherhistoryandforecastapp.HowWasTheWeather.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weatherhistoryandforecastapp.HowWasTheWeather.users.model.User;
import com.weatherhistoryandforecastapp.HowWasTheWeather.users.repository.UserRepository;

import reactor.core.publisher.Mono;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Mono<User> findByEmail(String userEmail) {
        return userRepository.findByEmail(userEmail);
    }

    public Mono<User> save(User user) {
        user.setPassword(user.getPassword()); // Encrypt password before saving
        return userRepository.save(user);
    }
}



// import java.util.Collections;
// import java.util.Optional;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Service;

// import com.weatherhistoryandforecastapp.HowWasTheWeather.users.model.User;
// import com.weatherhistoryandforecastapp.HowWasTheWeather.users.repository.UserRepository;

// @Service
// public class AuthService {

//     @Autowired
//     private UserRepository userRepository;

//     @Autowired
//     private PasswordEncoder passwordEncoder;

//     public User regUserEntity(String email, String password) {

//         // Check if email already exists

//         if (userRepository.existsByEmail(email)) {
//             throw new RuntimeException("Email already exists");
//         }

//         // Create new user
//         User user = new User(email, passwordEncoder.encode(password));
//         user.setRoles(Collections.singleton("ROLE_USER"));
//         return userRepository.save(user);
//     }

//     public Optional<User> getUserByEmail(String email) {
//         return userRepository.findByEmail(email);
//     }
// }
