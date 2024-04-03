package com.example.tech1.repositories;

import com.example.tech1.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>  {

    @Query("SELECT u.password FROM User u WHERE u.username = :username")
    String findPassword(String username);

    @Query("SELECT u.id FROM User u WHERE u.username = :username")
    String findId(String username);

    @Query("SELECT u.role FROM User u WHERE u.username = :username")
    String findRole(String username);

    @Query("SELECT u.username FROM User u WHERE u.username = :username")
    String findUsername(String username);

    @Query("SELECT u.id FROM User u WHERE u.id = :id")
    Integer checkIfExists(Integer id);

    @Query("SELECT u.email FROM User u WHERE u.email = :email")
    String findEmail(String username);
}
