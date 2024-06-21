package com.example.tech1.repositories;

import com.example.tech1.Book;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository for managing books.
 */
@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {

    Book findByIsbn(String isbn);

    /**
     * Custom query to find a book by its ISBN.
     *
     * @param isbn The ISBN of the book to find.
     * @return The ISBN of the book if found, otherwise null.
     */
    @Query("SELECT b.isbn FROM Book b WHERE b.isbn = :isbn")
    String findBookByIsbn(String isbn);

    @Query("SELECT COUNT(b) > 0 FROM Book b WHERE b.isbn = :isbn AND NOT EXISTS (SELECT 1 FROM Loan l WHERE l.book.id = b.id AND l.returnDate IS NULL)")
    boolean canDeleteBook(@Param("isbn") String isbn);

    @Query("SELECT COUNT(l) > 0 FROM Loan l WHERE l.book.isbn = :isbn")
    boolean wasBookEverBorrowed(@Param("isbn") String isbn);
}
