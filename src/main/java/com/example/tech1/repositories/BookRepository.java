package com.example.tech1.repositories;

import com.example.tech1.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {
    @Query("SELECT b.isbn FROM Book b WHERE b.isbn = :isbn")
    String findByIsbn(String isbn);

}
