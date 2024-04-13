package com.example.tech1.controllers;

import com.example.tech1.Book;
import com.example.tech1.Loan;
import com.example.tech1.User;
import com.example.tech1.repositories.BookRepository;
import com.example.tech1.repositories.LoanRepository;
import com.example.tech1.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@RestController
@RequestMapping("/loan")
public class LoanController {

    private final LoanRepository loanRepo;
    private final BookRepository bookRepo;
    private final UserRepository userRepo;

    @Autowired
    public LoanController(LoanRepository loanRepo, BookRepository bookRepo, UserRepository userRepo) {
        this.loanRepo = loanRepo;
        this.bookRepo = bookRepo;
        this.userRepo = userRepo;
    }

    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public @ResponseBody Loan addLoan(@RequestBody Loan loan) {

        Book book = bookRepo.findById(loan.getBook().getId()).orElse(null);
        if (book == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Book with given ID doesn't exist");
        }
        User user = userRepo.findById(loan.getUser().getId()).orElse(null);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User with given ID doesn't exist");
        }

        loan.setBook(book);
        loan.setUser(user);
        loan.setLoanDate(LocalDate.now());
        loan.setLoanEnd(LocalDate.now().plusMonths(1));
        return loanRepo.save(loan);
    }

    @PostMapping("/return/{id}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public @ResponseBody Loan returnLoan(@PathVariable Integer id) {
        Loan loan = loanRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan with given ID not found"));

        loan.setReturnDate(LocalDate.now());
        return loanRepo.save(loan);
    }

    @GetMapping("/getAll")
    public @ResponseBody Iterable<Loan> getAllLoans() {
        return loanRepo.findAll();
    }

}
