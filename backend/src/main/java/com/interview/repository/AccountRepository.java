package com.interview.repository;

import com.interview.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

    // Find account by email
    Optional<Account> findByEmail(String email);

    // Find account by first name (case-insensitive)
    List<Account> findByFirstNameContainingIgnoreCase(String firstName);

    // Find accounts by last name (case-insensitive)
    List<Account> findByLastNameContainingIgnoreCase(String lastName);

    // Find accounts by first name or last name (case-insensitive)
    List<Account> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);
}