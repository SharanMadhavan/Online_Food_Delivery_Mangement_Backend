
package com.fooddelivery.authservice.config;

import com.fooddelivery.authservice.entity.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtUtil {
  //  private final String SECRET_KEY = "my_super_secret_key_1234567890"; // Use a long random string in productio
	private final String SECRET_KEY = "my_super_secret_key_1234567890_ab"; // 32+ chars

    private final long EXPIRATION_TIME = 86400000; // 1 day

    public String generateToken(com.fooddelivery.authservice.entity.User user) {
        return Jwts.builder()
        		   .setSubject(user.getEmail())
                   .claim("role", user.getRole())
                   .claim("name", user.getName())
                   .setIssuedAt(new Date())
                   .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                   .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
                   .compact();
    }

    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    public boolean validateToken(String token, com.fooddelivery.authservice.entity.User user) {
        final String username = extractUsername(token);
        return (username.equals(user.getEmail()) && !isTokenExpired(token));
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isTokenExpired(String token) {
        final Date expiration = getClaims(token).getExpiration();
        return expiration.before(new Date());
    }
}
