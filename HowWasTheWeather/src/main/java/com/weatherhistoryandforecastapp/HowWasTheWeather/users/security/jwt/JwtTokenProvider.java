package com.weatherhistoryandforecastapp.HowWasTheWeather.users.security.jwt;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration}")
    private long jwtExpirationMs; // have to set this. Should I just do it here? Make it billions of ms, or
                                  // somethign?

    public String generateToken(Authentication authentication) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .setSubject(authentication.getName())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                .compact();
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            System.out.println("Invalid JWT token");
        }
        return false;
    }
}

// private final String SECRET_KEY; // What is this secret key? Where do I get
// it from?
// private static final long JWT_EXPIRATION_MS = 360000000; // 100 hours

// public JwtTokenProvider(@Value("${jwt.secret}") String secret) { // I don't
// know what the jwt.secret is.
// // For HS256 algorithm
// this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
// }

// // Reactive token generation
// public Mono<String> generateTokenReactive(Authentication authentication) {
// return Mono.fromCallable(() -> generateJwtToken(authentication));
// }

// public List<GrantedAuthority> getAuthoritiesFromToken(String token) {
// Claims claims = getAllClaimsFromToken(token);
// return ((List<?>) claims.get("roles")).stream()
// .map(auth -> new SimpleGrantedAuthority((String) auth))
// .collect(Collectors.toList());
// }

// private Claims getAllClaimsFromToken(String token) {
// return Jwts.parserBuilder()
// .verifyWith(key)
// .build()
// .parseSignedClaims(token)
// .getBody();
// }
