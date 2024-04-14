package com.example.tech1.repositories;

import com.example.tech1.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for managing books.
 */
@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {

    /**
     * Custom query to find a book by its ISBN.
     * @param isbn The ISBN of the book to find.
     * @return The ISBN of the book if found, otherwise null.
     */
    @Query("SELECT b.isbn FROM Book b WHERE b.isbn = :isbn")
    String findByIsbn(String isbn);

}
