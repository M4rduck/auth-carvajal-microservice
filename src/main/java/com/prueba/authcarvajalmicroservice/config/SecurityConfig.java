package com.prueba.authcarvajalmicroservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import com.prueba.authcarvajalmicroservice.config.security.AuthenticationManager;
import com.prueba.authcarvajalmicroservice.config.security.SecurityContextRepository;

import reactor.core.publisher.Mono;

@EnableWebFluxSecurity
public class SecurityConfig {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
    private SecurityContextRepository securityContextRepository;

    @Bean
    public SecurityWebFilterChain securitygWebFilterChain(ServerHttpSecurity http) {
        http
                .exceptionHandling((exceptionHandling) -> {
                	exceptionHandling.authenticationEntryPoint((exchage, change) -> {
                		return Mono.fromRunnable(() -> exchage.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED));
                	});
                	exceptionHandling.accessDeniedHandler((exchage, change) -> {
                		return Mono.fromRunnable(() -> exchage.getResponse().setStatusCode(HttpStatus.FORBIDDEN));
                	});
                })
                .csrf(csrf -> {
                	csrf.disable();
                })
        		.formLogin(formLogin -> {
        			formLogin.disable();
        		})
        		.httpBasic(httpBasic -> {
        			httpBasic.disable();
        		})
        		.authenticationManager(authenticationManager)
        		.securityContextRepository(securityContextRepository)
                .authorizeExchange((exchanges) -> {
                	exchanges.pathMatchers(HttpMethod.OPTIONS).permitAll();
                	exchanges.pathMatchers("/auth/login", "/auth/register").permitAll();
                	exchanges.anyExchange().authenticated().and().csrf().disable();
                });
        return http.build();
    }

}
