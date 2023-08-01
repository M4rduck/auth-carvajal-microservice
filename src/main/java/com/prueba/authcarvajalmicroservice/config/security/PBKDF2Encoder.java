package com.prueba.authcarvajalmicroservice.config.security;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PBKDF2Encoder implements PasswordEncoder {

    @Value("${spring.application.password.encoder.secret}")
    private String secret;

    @Value("${spring.application.password.encoder.iteration}")
    private Integer iteration;

    @Value("${spring.application.password.encoder.keylength}")
    private Integer keylength;

	@Override
	public String encode(CharSequence rawPassword) {
		try {
            byte[] result = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512")
                                            .generateSecret(new PBEKeySpec(rawPassword.toString().toCharArray(), secret.getBytes(), iteration, keylength))
                                            .getEncoded();
            return Base64.getEncoder().encodeToString(result);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            throw new RuntimeException(ex);
        }
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return encode(rawPassword).equals(encodedPassword);
	}

}
