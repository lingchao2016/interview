package com.interview.service;

import com.interview.model.Account;
import com.interview.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Page<Account> getAllAccounts(Pageable pageable) {
        return accountRepository.findAll(pageable);
    }

    public Optional<Account> getAccountById(UUID id) {
        return accountRepository.findById(id);
    }

    public Optional<Account> getAccountByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    @Transactional
    public Account createAccount(Account account) {
        // Check if account with email already exists
        if (accountRepository.findByEmail(account.getEmail()).isPresent()) {
            throw new RuntimeException("Account with email " + account.getEmail() + " already exists");
        }
        // Encode password before saving
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return accountRepository.save(account);
    }

    @Transactional
    public Account updateAccount(UUID id, Account accountDetails) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + id));

        // Check if email is being changed and if it's already taken by another account
        if (!account.getEmail().equals(accountDetails.getEmail())) {
            Optional<Account> existingAccount = accountRepository.findByEmail(accountDetails.getEmail());
            if (existingAccount.isPresent() && !existingAccount.get().getId().equals(id)) {
                throw new RuntimeException("Email " + accountDetails.getEmail() + " is already taken");
            }
        }

        account.setFirstName(accountDetails.getFirstName());
        account.setLastName(accountDetails.getLastName());
        account.setEmail(accountDetails.getEmail());
        // Encode password if it's being updated
        if (accountDetails.getPassword() != null && !accountDetails.getPassword().isEmpty()) {
            account.setPassword(passwordEncoder.encode(accountDetails.getPassword()));
        }
        account.setPhone(accountDetails.getPhone());

        return accountRepository.save(account);
    }

    @Transactional
    public void deleteAccount(UUID id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + id));
        accountRepository.delete(account);
    }

    public List<Account> searchAccountsByName(String name) {
        return accountRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name, name);
    }
}