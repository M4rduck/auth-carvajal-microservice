package com.prueba.authcarvajalmicroservice.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.prueba.authcarvajalmicroservice.config.security.PBKDF2Encoder;
import com.prueba.authcarvajalmicroservice.config.utils.JwtUtil;
import com.prueba.authcarvajalmicroservice.repository.UserRepository;
import com.prueba.carvajal.model.User;

@RestController("auth")
public class AuthenticationREST {
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
    private PBKDF2Encoder passwordEncoder;
    
    @Autowired
    private UserRepository userRepository;
    
    @PostMapping("/register")
    @CrossOrigin
    public User register(@RequestBody User user)
    {
    	String passEncode = passwordEncoder.encode(user.getPassword());
    	user.setPassword(passEncode);
        return userRepository.save(user);
    }

}
