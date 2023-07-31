package com.prueba.authcarvajalmicroservice.config.security;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.prueba.authcarvajalmicroservice.config.utils.JwtUtil;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {
	
	private JwtUtil jwtUtil;
	
	@Override
	public Mono<Authentication> authenticate(Authentication authentication) {
		String authToken = authentication.getCredentials().toString();
		String username = jwtUtil.getUsernameFromToken(authToken);
		return Mono.just(jwtUtil.validateToken(authToken))
				.filter(valid -> valid)
				.switchIfEmpty(Mono.empty())
				.map(valid -> {
					return new UsernamePasswordAuthenticationToken(username, null);
				});
	}

}
