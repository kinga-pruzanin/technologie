package com.example.tech1.controllers;

import com.example.tech1.Loan;
import com.example.tech1.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loan")
public class LoanController {

    private final LoanRepository loanRepo;

    @Autowired
    public LoanController(LoanRepository loanRepo) {
        this.loanRepo = loanRepo;
    }

    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public @ResponseBody Loan addLoan(@RequestBody Loan loan) {
        return loanRepo.save(loan);
    }

    @GetMapping("/getAll")
    public @ResponseBody Iterable<Loan> getAllLoans() {
        return loanRepo.findAll();
    }

}
