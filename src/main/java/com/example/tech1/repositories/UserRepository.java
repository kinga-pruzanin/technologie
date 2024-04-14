package com.example.tech1.repositories;

import com.example.tech1.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for managing users.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Integer>  {

    /**
     * Finds the password of a user by username.
     * @param username The username of the user.
     * @return The password of the user if found, otherwise null.
     */
    @Query("SELECT u.password FROM User u WHERE u.username = :username")
    String findPassword(String username);

    /**
     * Finds the ID of a user by username.
     * @param username The username of the user.
     * @return The ID of the user if found, otherwise null.
     */
    @Query("SELECT u.id FROM User u WHERE u.username = :username")
    String findId(String username);

    /**
     * Finds the role of a user by username.
     * @param username The username of the user.
     * @return The role of the user if found, otherwise null.
     */
    @Query("SELECT u.role FROM User u WHERE u.username = :username")
    String findRole(String username);

    /**
     * Finds the username of a user by username.
     * @param username The username of the user.
     * @return The username of the user if found, otherwise null.
     */
    @Query("SELECT u.username FROM User u WHERE u.username = :username")
    String findUsername(String username);

    /**
     * Checks if a user with the given ID exists.
     * @param id The ID of the user.
     * @return The ID of the user if exists, otherwise null.
     */
    @Query("SELECT u.id FROM User u WHERE u.id = :id")
    Integer checkIfExists(Integer id);

    /**
     * Finds the email of a user by email.
     * @param email The email of the user.
     * @return The email of the user if found, otherwise null.
     */
    @Query("SELECT u.email FROM User u WHERE u.email = ?1")
    String findEmail(String email);
}
