package com.example.tech1.controllers;

import com.example.tech1.LoginForm;
import com.example.tech1.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling user login.
 */
@RestController
public class LoginController {

    private final LoginService loginService;

    /**
     * Constructs a new LoginController.
     * @param loginService The login service.
     */
    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    /**
     * Handles user login.
     * @param loginForm The login form containing user credentials.
     * @return ResponseEntity with a token if login is successful, otherwise an error message.
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginForm loginForm) {
        String token = loginService.userLogin(loginForm);
        if (token == null) {
            return new ResponseEntity<>("Wrong login or password", HttpStatus.UNAUTHORIZED);
        }
        else {
            return new ResponseEntity<>(token, HttpStatus.OK);
        }
    }
}
