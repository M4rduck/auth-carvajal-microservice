package com.prueba.authcarvajalmicroservice.config.utils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.prueba.carvajal.document.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtUtil {
	
	@Value("${spring.application.jwtSecret}")
	private String jwtSecret;

	@Value("${spring.application.jwtExpirationInMs}")
	private int jwtExpirationInMs;
	
	private Key key;
	
	@PostConstruct
	public void init() {
		this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
	}
	
	private String doGenerateToken(Map<String, Object> claims, String username) {
        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(key)
                .compact();
    }
	
	public Claims getAllClaimsFromToken(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
	}
	
	public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        return this.doGenerateToken(claims, user.getUsername());
    }
	
	public Date getExpirationDateFromToken(String token) {
		return this.getAllClaimsFromToken(token).getExpiration();
	}
	
	public String getUsernameFromToken(String token) {
		return this.getAllClaimsFromToken(token).getSubject();
	}
	
	private Boolean isTokenExpired(String token) {
		final Date expiration = this.getExpirationDateFromToken(token);
		return expiration.before(expiration);
	}
	
	public Boolean validateToken(String token) {
		return !this.isTokenExpired(token);
	}

}
