package com.example.tech1.controllers;

import com.example.tech1.User;
import com.example.tech1.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * Controller for managing user operations.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructs a new UserController.
     * @param userRepo The repository for users.
     * @param passwordEncoder The password encoder.
     */
    @Autowired
    public UserController(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Adds a new user.
     * @param user The user to add.
     * @return The added user.
     * @throws ResponseStatusException If a user with the same username or email already exists.
     */
    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public @ResponseBody User addUser(@RequestBody User user) {
        if (userRepo.findUsername(user.getUsername()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User with given username already exists");
        }
        if (userRepo.findEmail(user.getEmail()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User with given e-mail already exists");
        }
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepo.save(user);
    }

    /**
     * Deletes a user by ID.
     * @param id The ID of the user to delete.
     * @throws ResponseStatusException If the user with the given ID does not exist.
     */
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Integer id) {
        if (userRepo.checkIfExists(id) == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User with given ID doesn't exist");
        }
        userRepo.deleteById(id);
    }
}
