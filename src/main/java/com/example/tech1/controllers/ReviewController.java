package com.example.tech1.controllers;

import com.example.tech1.Book;
import com.example.tech1.Review;
import com.example.tech1.repositories.BookRepository;
import com.example.tech1.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/review")
public class ReviewController {

    private final ReviewRepository reviewRepo;
    private final BookRepository bookRepo;

    @Autowired
    public ReviewController(ReviewRepository reviewRepo, BookRepository bookRepo) {
        this.reviewRepo = reviewRepo;
        this.bookRepo = bookRepo;
    }

    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public @ResponseBody Review addReview(@RequestBody Review review) {
        Book book = bookRepo.findById(review.getBook().getId()).orElse(null);
        if (book == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Book with given ID doesn't exist");
        }
        return reviewRepo.save(review);
    }

    @GetMapping("/getAll")
    public @ResponseBody Iterable<Review> getAllReviews() {
        return reviewRepo.findAll();
    }


}
