package com.example.tech1.services;

import com.example.tech1.LoginForm;
import com.example.tech1.repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Service class for handling user login operations.
 */
@Service
public class LoginService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.token.key}")
    private String key;

    /**
     * Constructs a new instance of LoginService.
     * @param userRepository The UserRepository for accessing user data.
     * @param passwordEncoder The PasswordEncoder for encoding passwords.
     */
    @Autowired
    public LoginService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Performs user login operation and generates a JWT token upon successful authentication.
     * @param loginForm The LoginForm containing user login credentials.
     * @return A JWT token if authentication is successful, otherwise null.
     */
    public String userLogin(LoginForm loginForm) {
        String encodedPassword = userRepository.findPassword(loginForm.getLogin());
        String userId = userRepository.findId(loginForm.getLogin());
        String userRole = userRepository.findRole(loginForm.getLogin());

        if (passwordEncoder.matches(loginForm.getPassword(), encodedPassword)) {
            long timeMillis = System.currentTimeMillis();
            String token = Jwts.builder()
                    .issuedAt(new Date(timeMillis))
                    .expiration(new Date(timeMillis + 5*60*1000))
                    .claim("id", userId)
                    .claim("role", userRole)
                    .signWith(SignatureAlgorithm.HS256, key)
                    .compact();

            return token;
        }
        else {
            return null;
        }

    }
}
