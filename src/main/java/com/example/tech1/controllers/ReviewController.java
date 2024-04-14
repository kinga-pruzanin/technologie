package com.example.tech1.controllers;

import com.example.tech1.Book;
import com.example.tech1.Review;
import com.example.tech1.repositories.BookRepository;
import com.example.tech1.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

/**
 * Controller for managing book reviews.
 */
@RestController
@RequestMapping("/review")
public class ReviewController {

    private final ReviewRepository reviewRepo;
    private final BookRepository bookRepo;

    /**
     * Constructs a new ReviewController.
     * @param reviewRepo The repository for reviews.
     * @param bookRepo The repository for books.
     */
    @Autowired
    public ReviewController(ReviewRepository reviewRepo, BookRepository bookRepo) {
        this.reviewRepo = reviewRepo;
        this.bookRepo = bookRepo;
    }

    /**
     * Adds a new review for a book.
     * @param review The review to add.
     * @return The added review.
     * @throws ResponseStatusException If the associated book does not exist.
     */
    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public @ResponseBody Review addReview(@RequestBody Review review) {
        Book book = bookRepo.findById(review.getBook().getId()).orElse(null);
        if (book == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Book with given ID doesn't exist");
        }
        review.setReviewDate(LocalDate.now());
        return reviewRepo.save(review);
    }

    /**
     * Retrieves all reviews.
     * @return All reviews in the database.
     */
    @GetMapping("/getAll")
    public @ResponseBody Iterable<Review> getAllReviews() {
        return reviewRepo.findAll();
    }
}
