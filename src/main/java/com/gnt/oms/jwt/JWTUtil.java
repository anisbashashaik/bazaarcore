package com.gnt.oms.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTUtil {

    //private String secret = "btechdays";
    private String secret = "ANISBASHASHAIKANISBASHASHAIKANISBASHASHAIKANISBASHASHAIKANISBASHASHAIKANISBASHASHAIKANISBASHASHAIKANISBASHASHAIKANISBASHASHAIKANISBASHASHAIKANISBASHASHAIKANISBASHASHAIKANISBASHASHAIKANISBASHASHAIKANISBASHASHAIKANISBASHASHAIK";

    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);

    }

    public String generateToken(String username, String role) {
        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("role", role);
        claims.put("username", username);
        return createToken(claims, username);
    }

    public String generateToken(String userName) {
        return generateToken(userName, "user");
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
