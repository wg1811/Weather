package com.weatherhistoryandforecastapp.HowWasTheWeather.users.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.weatherhistoryandforecastapp.HowWasTheWeather.users.config.JwtUtil;
import com.weatherhistoryandforecastapp.HowWasTheWeather.users.model.AuthRequest;
import com.weatherhistoryandforecastapp.HowWasTheWeather.users.model.AuthResponse;
import com.weatherhistoryandforecastapp.HowWasTheWeather.users.model.User;
import com.weatherhistoryandforecastapp.HowWasTheWeather.users.service.UserService;

import reactor.core.publisher.Mono;

@RestController
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Mono<ResponseEntity<AuthResponse>> login(@RequestBody AuthRequest authRequest) {
        return userService.findByEmail(authRequest.getEmail())
                .map(userDetails -> {
                    if (userDetails.getPassword().equals(authRequest.getPassword())) {
                        return ResponseEntity.ok(new AuthResponse(jwtUtil.generateToken(authRequest.getEmail())));
                    } else {
                        throw new BadCredentialsException("Invalid username or password");
                    }
                }).switchIfEmpty(Mono.error(new BadCredentialsException("Invalid username or password")));
    }
    @PostMapping("/signup")
    public Mono<ResponseEntity<String>> signup(@RequestBody User user) {
    // Encrypt password before saving
    user.setPassword(user.getPassword());
    return userService.save(user)
            .map(savedUser -> ResponseEntity.ok("User signed up successfully"));
    }

    @GetMapping("/protected")
    public Mono<ResponseEntity<String>> protectedEndpoint() {
        return Mono.just(ResponseEntity.ok("You have accessed a protected endpoint!"));
    }
}



// ===================

// import java.util.List;
// import java.util.stream.Collectors;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.authentication.ReactiveAuthenticationManager;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.ReactiveSecurityContextHolder;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;


// import com.weatherhistoryandforecastapp.HowWasTheWeather.users.payload.request.LoginRequest;
// import com.weatherhistoryandforecastapp.HowWasTheWeather.users.payload.response.JwtResponse;
// import com.weatherhistoryandforecastapp.HowWasTheWeather.users.payload.response.MessageResponse;
// import com.weatherhistoryandforecastapp.HowWasTheWeather.users.security.services.UserDetailsImpl;

// import com.weatherhistoryandforecastapp.HowWasTheWeather.users.security.jwt.JwtTokenProvider;
// import com.weatherhistoryandforecastapp.HowWasTheWeather.users.service.AuthService;

// import jakarta.validation.Valid;
// import reactor.core.publisher.Mono;

// @RestController
// @RequestMapping("/api/auth")
// public class AuthController {

//     @Autowired
//     private ReactiveAuthenticationManager authenticationManager;

//     @Autowired
//     private AuthService userService;

//     @Autowired
//     private JwtTokenProvider jwtUtils;

//     @PostMapping("/signin")
//     public Mono<ResponseEntity<?>> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
//         return authenticationManager
//                 .authenticate(
//                         new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()))
//                 .flatMap(authentication -> {
//                     // Store the authentication in the security context
//                     return Mono.just(authentication)
//                             .flatMap(auth -> {
//                                 String jwt = jwtUtils.generateJwtToken(auth);
//                                 UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
//                                 List<String> roles = userDetails.getAuthorities().stream()
//                                         .map(item -> item.getAuthority())
//                                         .collect(Collectors.toList());

//                                 return Mono.just(ResponseEntity.ok(new JwtResponse(
//                                         jwt,
//                                         userDetails.getId(),
//                                         userDetails.getEmail(),
//                                         roles)));
//                             })
//                             .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
//                 })
//                 .onErrorResume(e -> {
//                     // Log the error for debugging
//                     System.err.println("Authentication error: " + e.getMessage());
//                     return Mono.just(ResponseEntity.badRequest()
//                             .body(new MessageResponse("Invalid email or password")));
//                 });
//     }
// }

// =========================

// package com.weatherhistoryandforecastapp.HowWasTheWeather.users.controller;

// import java.util.List;
// import java.util.stream.Collectors;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.authentication.ReactiveAuthenticationManager;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.context.ReactiveSecurityContextHolder;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.weatherhistoryandforecastapp.HowWasTheWeather.users.payload.request.LoginRequest;
// import com.weatherhistoryandforecastapp.HowWasTheWeather.users.payload.response.JwtResponse;
// import com.weatherhistoryandforecastapp.HowWasTheWeather.users.payload.response.MessageResponse;
// import com.weatherhistoryandforecastapp.HowWasTheWeather.users.security.jwt.JwtTokenProvider;
// import com.weatherhistoryandforecastapp.HowWasTheWeather.users.security.services.UserDetailsImpl;
// import com.weatherhistoryandforecastapp.HowWasTheWeather.users.service.AuthService;

// import jakarta.validation.Valid;
// import reactor.core.publisher.Mono;

// @RestController
// @RequestMapping("/api/auth")
// public class AuthController {

//     @Autowired
//     private ReactiveAuthenticationManager authenticationManager;

//     @Autowired
//     private AuthService userService;

//     @Autowired
//     private JwtTokenProvider jwtUtils;

//     @PostMapping("/signin")
//     public Mono<ResponseEntity<?>> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
//         return authenticationManager
//                 .authenticate(
//                         new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()))
//                 .map(authentication -> {
//                     ReactiveSecurityContextHolder.withAuthentication(authentication);
//                     String jwt = jwtUtils.generateJwtToken(authentication);
//                     UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//                     List<String> roles = userDetails.getAuthorities().stream()
//                             .map(item -> item.getAuthority())
//                             .collect(Collectors.toList());

//                     return ResponseEntity.ok(new JwtResponse(
//                             jwt,
//                             userDetails.getId(),
//                             userDetails.getEmail(),
//                             roles));
//                 })
//                 .onErrorResume(e -> Mono.just(ResponseEntity.badRequest()
//                         .body(new MessageResponse("Invalid email or password"))));
//     }
// }