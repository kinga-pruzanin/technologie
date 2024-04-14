package com.example.tech1.repositories;

import com.example.tech1.BookDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for managing book's details.
 */
@Repository
public interface BookDetailsRepository extends CrudRepository<BookDetails, Integer> {
}