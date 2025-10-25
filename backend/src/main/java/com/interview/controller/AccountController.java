package com.interview.controller;

import com.interview.dto.AccountMapper;
import com.interview.dto.AccountRequest;
import com.interview.dto.AccountResponse;
import com.interview.model.Account;
import com.interview.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Tag(name = "Accounts", description = "Account management APIs")
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;
    private final AccountMapper accountMapper;

    @Autowired
    public AccountController(AccountService accountService, AccountMapper accountMapper) {
        this.accountService = accountService;
        this.accountMapper = accountMapper;
    }

    @Operation(summary = "Get all accounts", description = "Retrieve a list of all accounts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of accounts")
    })
    @GetMapping
    public ResponseEntity<List<AccountResponse>> getAllAccounts() {
        List<Account> accounts = accountService.getAllAccounts();
        List<AccountResponse> responses = accounts.stream()
                .map(accountMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Get account by ID", description = "Retrieve a specific account by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account found"),
            @ApiResponse(responseCode = "404", description = "Account not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getAccountById(
            @Parameter(description = "ID of the account to retrieve") @PathVariable UUID id) {
        return accountService.getAccountById(id)
                .map(accountMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get account by email", description = "Retrieve a specific account by their email address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account found"),
            @ApiResponse(responseCode = "404", description = "Account not found")
    })
    @GetMapping("/email/{email}")
    public ResponseEntity<AccountResponse> getAccountByEmail(
            @Parameter(description = "Email of the account to retrieve") @PathVariable String email) {
        return accountService.getAccountByEmail(email)
                .map(accountMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create new account", description = "Create a new account account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Account created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input or email already exists")
    })
    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Account request object")
            @Valid @RequestBody AccountRequest request) {
        try {
            Account account = accountMapper.toEntity(request);
            Account createdAccount = accountService.createAccount(account);
            return ResponseEntity.status(HttpStatus.CREATED).body(accountMapper.toResponse(createdAccount));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(summary = "Update account", description = "Update an existing account by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account updated successfully"),
            @ApiResponse(responseCode = "404", description = "Account not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input or email already taken")
    })
    @PutMapping("/{id}")
    public ResponseEntity<AccountResponse> updateAccount(
            @Parameter(description = "ID of the account to update") @PathVariable UUID id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated account request object")
            @Valid @RequestBody AccountRequest request) {
        try {
            Account accountDetails = accountMapper.toEntity(request);
            Account updatedAccount = accountService.updateAccount(id, accountDetails);
            return ResponseEntity.ok(accountMapper.toResponse(updatedAccount));
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(summary = "Delete account", description = "Delete a account by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Account deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Account not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(
            @Parameter(description = "ID of the account to delete") @PathVariable UUID id) {
        try {
            accountService.deleteAccount(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Search accounts by name", description = "Search for accounts by first or last name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of accounts")
    })
    @GetMapping("/search")
    public ResponseEntity<List<AccountResponse>> searchAccountsByName(@RequestParam String name) {
        List<Account> accounts = accountService.searchAccountsByName(name);
        List<AccountResponse> responses = accounts.stream()
                .map(accountMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Get accounts by first name", description = "Get all accounts with matching first name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of accounts")
    })
    @GetMapping("/firstname/{firstName}")
    public ResponseEntity<List<AccountResponse>> getAccountsByFirstName(@PathVariable String firstName) {
        List<Account> accounts = accountService.getAccountsByFirstName(firstName);
        List<AccountResponse> responses = accounts.stream()
                .map(accountMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Get accounts by last name", description = "Get all accounts with matching last name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of accounts")
    })
    @GetMapping("/lastname/{lastName}")
    public ResponseEntity<List<AccountResponse>> getAccountsByLastName(@PathVariable String lastName) {
        List<Account> accounts = accountService.getAccountsByLastName(lastName);
        List<AccountResponse> responses = accounts.stream()
                .map(accountMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
}