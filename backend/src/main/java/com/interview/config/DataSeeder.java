package com.interview.config;

import com.interview.model.Account;
import com.interview.model.Event;
import com.interview.repository.AccountRepository;
import com.interview.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Check if data already exists
        if (accountRepository.count() > 0) {
            System.out.println("Data already seeded, skipping...");
            return;
        }

        // Create default admin account
        Account admin = new Account();
        admin.setFirstName("Lingchao");
        admin.setLastName("Kong");
        admin.setEmail("admin@example.com");
        admin.setPassword(passwordEncoder.encode("password123"));
        admin.setPhone("123-456-7890");
        accountRepository.save(admin);

        // Create sample events
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        createEvent("Spring Boot Workshop", "Learn the fundamentals of Spring Boot framework",
                    "2025-11-15 10:00:00", "San Francisco, CA", formatter);
        createEvent("Java Conference 2025", "Annual Java developers conference with industry experts",
                    "2025-12-01 09:00:00", "New York, NY", formatter);
        createEvent("Microservices Meetup", "Discussion on microservices architecture patterns",
                    "2025-11-20 18:30:00", "Austin, TX", formatter);
        createEvent("Taylor Swift - The Eras Tour", "Experience the magic of Taylor Swift performing hits from all her albums",
                    "2025-11-28 19:30:00", "MetLife Stadium, East Rutherford, NJ", formatter);
        createEvent("Taylor Swift - The Eras Tour", "Taylor Swift live in concert with special guests",
                    "2025-12-05 19:30:00", "SoFi Stadium, Los Angeles, CA", formatter);
        createEvent("Beyoncé - Renaissance World Tour", "Queen Bey brings the Renaissance experience to life",
                    "2025-12-10 20:00:00", "AT&T Stadium, Arlington, TX", formatter);
        createEvent("Ed Sheeran - Mathematics Tour", "Ed Sheeran performs acoustic and electric hits",
                    "2025-11-25 19:00:00", "Madison Square Garden, New York, NY", formatter);
        createEvent("The Weeknd - After Hours Tour", "The Weeknd live with stunning visual effects",
                    "2025-12-08 20:00:00", "United Center, Chicago, IL", formatter);
        createEvent("NBA: Lakers vs Warriors", "Los Angeles Lakers take on Golden State Warriors in Western Conference showdown",
                    "2025-11-22 19:30:00", "Crypto.com Arena, Los Angeles, CA", formatter);
        createEvent("NBA: Celtics vs Heat", "Boston Celtics vs Miami Heat - Eastern Conference rivalry game",
                    "2025-11-26 20:00:00", "TD Garden, Boston, MA", formatter);

        System.out.println("Data seeding completed successfully! Created " + accountRepository.count() +
                          " accounts and " + eventRepository.count() + " events.");
    }

    private void createEvent(String name, String description, String dateStr, String location, DateTimeFormatter formatter) {
        Event event = new Event();
        event.setName(name);
        event.setDescription(description);
        event.setEventDate(LocalDateTime.parse(dateStr, formatter));
        event.setLocation(location);
        eventRepository.save(event);
    }
}