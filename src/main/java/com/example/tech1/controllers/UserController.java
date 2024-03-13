package com.example.tech1.controllers;

import com.example.tech1.User;
import com.example.tech1.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepo;

    @Autowired
    public UserController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public @ResponseBody User addUser(@RequestBody User user) {
        return userRepo.save(user);
    }

    @GetMapping("/getAll")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepo.findAll();
    }
}
