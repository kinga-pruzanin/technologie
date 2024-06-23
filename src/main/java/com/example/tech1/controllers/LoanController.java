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

/**
 * Controller for managing loans.
 */
@RestController
@RequestMapping("/loan")
public class LoanController {

    private final LoanRepository loanRepo;
    private final BookRepository bookRepo;
    private final UserRepository userRepo;

    /**
     * Constructs a new LoanController.
     * @param loanRepo The repository for loans.
     * @param bookRepo The repository for books.
     * @param userRepo The repository for users.
     */
    @Autowired
    public LoanController(LoanRepository loanRepo, BookRepository bookRepo, UserRepository userRepo) {
        this.loanRepo = loanRepo;
        this.bookRepo = bookRepo;
        this.userRepo = userRepo;
    }

    /**
     * Adds a new loan.
     * @param loan The loan to add.
     * @return The added loan.
     * @throws ResponseStatusException If the associated book or user does not exist.
     */
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

    /**
     * Marks a loan as returned.
     * @param id The ID of the loan to mark as returned.
     * @return The loan with the return date updated.
     * @throws ResponseStatusException If the loan with the given ID is not found.
     */
    @PostMapping("/return/{id}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public @ResponseBody Loan returnLoan(@PathVariable Integer id) {
        Loan loan = loanRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan with given ID not found"));

        loan.setReturnDate(LocalDate.now());
        return loanRepo.save(loan);
    }

    @PutMapping("/accept/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void acceptLoan(@PathVariable Integer id) {
        try {
            Loan loan = loanRepo.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan with given ID not found"));

            loan.setAccepted(true);

            loanRepo.save(loan);

        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error accepting loan", e);
        }
    }

    /**
     * Retrieves all loans.
     * @return All loans in the database.
     */
    @GetMapping("/getAll")
    public @ResponseBody Iterable<Loan> getAllLoans() {
        return loanRepo.findAll();
    }
}
