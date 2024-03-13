package com.example.tech1.controllers;

import com.example.tech1.Review;
import com.example.tech1.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/review")
public class ReviewController {

    private final ReviewRepository reviewRepo;

    @Autowired
    public ReviewController(ReviewRepository reviewRepo) {
        this.reviewRepo = reviewRepo;
    }

    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public @ResponseBody Review addReview(@RequestBody Review review) {
        return reviewRepo.save(review);
    }

    @GetMapping("/getAll")
    public @ResponseBody Iterable<Review> getAllReviews() {
        return reviewRepo.findAll();
    }


}
