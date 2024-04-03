package com.example.tech1.controllers;

import com.example.tech1.Book;
import com.example.tech1.BookDetails;
import com.example.tech1.repositories.BookDetailsRepository;
import com.example.tech1.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/bookDetails")
public class BookDetailsController {

    private final BookDetailsRepository bookDetailsRepo;
    private final BookRepository bookRepo;

    @Autowired
    public BookDetailsController(BookDetailsRepository bookDetailsRepo, BookRepository bookRepo) {
        this.bookDetailsRepo = bookDetailsRepo;
        this.bookRepo = bookRepo;
    }

    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public @ResponseBody BookDetails addBookDetails(@RequestBody BookDetails bookDetails) {
        Book book = bookRepo.findById(bookDetails.getBook().getId()).orElse(null);
        if (book == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Book with given ID doesn't exist");
        }
        return bookDetailsRepo.save(bookDetails);
    }

    @GetMapping("/getAll")
    public @ResponseBody Iterable<BookDetails> getAllBooks() {
        return bookDetailsRepo.findAll();
    }

}
