package com.example.tech1.controllers;

import com.example.tech1.Book;
import com.example.tech1.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * Controller for managing books.
 */
@RestController
@RequestMapping("/book")
public class BookController {

    private final BookRepository bookRepo;

    /**
     * Constructs a new BookController.
     * @param bookRepo The book repository.
     */
    @Autowired
    public BookController(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    /**
     * Adds a new book.
     * @param book The book to add.
     * @return The added book.
     * @throws ResponseStatusException If the book with the same ISBN already exists in the database.
     */
    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public @ResponseBody Book addBook(@RequestBody Book book) {
        if (bookRepo.findByIsbn(book.getIsbn()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This book was already added to the database");
        }
        return bookRepo.save(book);
    }

    /**
     * Retrieves all books.
     * @return All books in the database.
     */
    @GetMapping("/getAll")
    public @ResponseBody Iterable<Book> getAllBooks() {
        return bookRepo.findAll();
    }

}
