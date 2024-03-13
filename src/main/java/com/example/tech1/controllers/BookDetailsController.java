package com.example.tech1.controllers;

import com.example.tech1.BookDetails;
import com.example.tech1.repositories.BookDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookDetails")
public class BookDetailsController {

    private final BookDetailsRepository bookDetailsRepo;

    @Autowired
    public BookDetailsController(BookDetailsRepository bookRepo) {
        this.bookDetailsRepo = bookRepo;
    }

    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public @ResponseBody BookDetails addBookDetails(@RequestBody BookDetails bookDetails) {
        return bookDetailsRepo.save(bookDetails);
    }

    @GetMapping("/getAll")
    public @ResponseBody Iterable<BookDetails> getAllBooks() {
        return bookDetailsRepo.findAll();
    }

}
