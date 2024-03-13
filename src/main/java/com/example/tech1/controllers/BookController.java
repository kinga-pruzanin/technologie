package com.example.tech1.controllers;

import com.example.tech1.Book;
import com.example.tech1.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
        return bookRepo.save(book);
    }

    @GetMapping("/getAll")
    public @ResponseBody Iterable<Book> getAllBooks() {
        return bookRepo.findAll();
    }

}
