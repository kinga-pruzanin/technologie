package com.example.tech1.controllers;

import com.example.tech1.Book;
import com.example.tech1.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookRepository bookRepo;

    @Autowired
    public BookController(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public @ResponseBody Book addBook(@RequestBody Book book) {
        if (bookRepo.findByIsbn(book.getIsbn()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This book was already added to database");
        }
        return bookRepo.save(book);
    }

    @GetMapping("/getAll")
    public @ResponseBody Iterable<Book> getAllBooks() {
        return bookRepo.findAll();
    }

}
