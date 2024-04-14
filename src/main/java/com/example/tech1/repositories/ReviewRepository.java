package com.example.tech1.repositories;

import com.example.tech1.Review;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for managing reviews.
 */
@Repository
public interface ReviewRepository  extends CrudRepository<Review, Integer>  {
}
