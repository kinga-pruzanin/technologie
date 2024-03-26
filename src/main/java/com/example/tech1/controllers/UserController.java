package com.example.tech1.controllers;

import com.example.tech1.User;
import com.example.tech1.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Secured("ROLE_LIBRARIAN")
    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public @ResponseBody User addUser(@RequestBody User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepo.save(user);
    }

    @Secured("ROLE_LIBRARIAN")
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Integer id) {
        userRepo.deleteById(id);
    }
}
