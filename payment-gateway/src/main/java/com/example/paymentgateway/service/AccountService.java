package com.example.paymentgateway.service;

import com.example.paymentgateway.dao.AccountDao;
import com.example.paymentgateway.dto.AccountDto;
import com.example.paymentgateway.entity.Account;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountDao accountDao;

    public AccountDto createAccount(AccountDto accountDto) {
        return toDto(accountDao.save(toEntity(accountDto)));
    }

    public Account toEntity(AccountDto accountDto) {
        Account account = new Account();
        account.setAccountNumber(generateAccountNumber());
        account.setName(accountDto.getName());
        return account;
    }

    public AccountDto toDto(Account account) {
        return new AccountDto(
                account.getId(),
                account.getName(),
                account.getAccountNumber(),
                account.getAmount()
        );
    }

    public String generateAccountNumber() {
        SecureRandom random = new SecureRandom();
        return "HUGO" + (random.nextInt(100000) + 100000);
    }

    @Transactional
    public double deposit(String accountNumber, double amount) {
        Account account = findAccountByAccountNumber(accountNumber);
        account.setAmount(account.getAmount() + amount);
        return account.getAmount();
    }

    public Account findAccountByAccountNumber(String accountNumber) {
        return accountDao.findAccountByAccountNumber(accountNumber)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public double withdraw(String accountNumber, double amount) {
        if (amount > checkBalance(accountNumber)) {
            throw new RuntimeException(amount + " is insufficient!");
        }

        double updateAmount = checkBalance(accountNumber) - amount;
        Account account = findAccountByAccountNumber(accountNumber);
        account.setAmount(updateAmount);

        return updateAmount;
    }

    private double checkBalance(String accountNumber) {
        return accountDao.checkBalance(accountNumber);
    }

    @Transactional
    public void makePayment(String ownerAccountNumber,
                            String customerAccountNumber,
                            double amount) {
        withdraw(customerAccountNumber, amount);
        deposit(ownerAccountNumber, amount);
    }
}
