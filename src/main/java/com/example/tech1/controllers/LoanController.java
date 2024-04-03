package com.example.tech1.controllers;

import com.example.tech1.Book;
import com.example.tech1.Loan;
import com.example.tech1.repositories.BookRepository;
import com.example.tech1.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/loan")
public class LoanController {

    private final LoanRepository loanRepo;
    private final BookRepository bookRepo;

    @Autowired
    public LoanController(LoanRepository loanRepo, BookRepository bookRepo) {
        this.loanRepo = loanRepo;
        this.bookRepo = bookRepo;
    }

    @Secured("ROLE_LIBRARIAN")
    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public @ResponseBody Loan addLoan(@RequestBody Loan loan) {
        Book book = bookRepo.findById(loan.getBook().getId()).orElse(null);
        if (book == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Book with given ID doesn't exist");
        }
        return loanRepo.save(loan);
    }

    @Secured("ROLE_LIBRARIAN")
    @GetMapping("/getAll")
    public @ResponseBody Iterable<Loan> getAllLoans() {
        return loanRepo.findAll();
    }

}
