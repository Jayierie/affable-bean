package com.example.paymentgateway.controller;

import com.example.paymentgateway.dto.AccountDto;
import com.example.paymentgateway.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/create")
    public AccountDto createAccount(@RequestBody AccountDto accountDto) {
        return accountService.createAccount(accountDto);
    }

    @PostMapping("/deposit")
    public ResponseEntity<Double> deposit(@RequestParam("accountNumber") String accountNumber,
                                          @RequestParam("amount") double amount) {
        return ResponseEntity.ok(accountService.deposit(accountNumber, amount));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Double> withdraw(@RequestParam("accountNumber") String accountNumber,
                                           @RequestParam("amount") double amount) {
        return ResponseEntity.ok(accountService.withdraw(accountNumber, amount));
    }

    @PostMapping("/make-payment")
    public ResponseEntity<String> makePayment(@RequestParam("ownerAccountNumber") String ownerAccountNumber,
                                            @RequestParam("customerAccountNumber") String customerAccountNumber,
                                            @RequestParam("amount") double amount) {
        accountService.makePayment(ownerAccountNumber, customerAccountNumber, amount);
        return ResponseEntity.ok("Successful transaction!");
    }

}
