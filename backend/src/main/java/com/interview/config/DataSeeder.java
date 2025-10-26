package com.interview.config;

import com.interview.model.Account;
import com.interview.model.Event;
import com.interview.model.Role;
import com.interview.repository.AccountRepository;
import com.interview.repository.EventRepository;
import com.interview.repository.RoleRepository;
import com.interview.service.EventService;
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
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EventService eventService;

    @Override
    public void run(String... args) throws Exception {
        // Check if data already exists
        if (accountRepository.count() > 0) {
            System.out.println("Data already seeded, skipping...");
            return;
        }

        // Create roles
        Role adminRole = new Role("ADMIN");
        adminRole = roleRepository.save(adminRole);

        Role userRole = new Role("USER");
        userRole = roleRepository.save(userRole);

        // Create default admin account
        Account admin = new Account();
        admin.setFirstName("Lingchao");
        admin.setLastName("Kong");
        admin.setEmail("admin@example.com");
        admin.setPassword(passwordEncoder.encode("password123"));
        admin.setPhone("123-456-7890");
        admin.getRoles().add(adminRole);
        accountRepository.save(admin);

        // Create 20 user accounts
        createUserAccount("John", "Smith", "john.smith@example.com", "555-0101", userRole);
        createUserAccount("Emma", "Johnson", "emma.johnson@example.com", "555-0102", userRole);
        createUserAccount("Michael", "Williams", "michael.williams@example.com", "555-0103", userRole);
        createUserAccount("Sophia", "Brown", "sophia.brown@example.com", "555-0104", userRole);
        createUserAccount("James", "Jones", "james.jones@example.com", "555-0105", userRole);
        createUserAccount("Olivia", "Garcia", "olivia.garcia@example.com", "555-0106", userRole);
        createUserAccount("William", "Martinez", "william.martinez@example.com", "555-0107", userRole);
        createUserAccount("Ava", "Rodriguez", "ava.rodriguez@example.com", "555-0108", userRole);
        createUserAccount("David", "Davis", "david.davis@example.com", "555-0109", userRole);
        createUserAccount("Isabella", "Miller", "isabella.miller@example.com", "555-0110", userRole);
        createUserAccount("Robert", "Wilson", "robert.wilson@example.com", "555-0111", userRole);
        createUserAccount("Mia", "Moore", "mia.moore@example.com", "555-0112", userRole);
        createUserAccount("Daniel", "Taylor", "daniel.taylor@example.com", "555-0113", userRole);
        createUserAccount("Charlotte", "Anderson", "charlotte.anderson@example.com", "555-0114", userRole);
        createUserAccount("Matthew", "Thomas", "matthew.thomas@example.com", "555-0115", userRole);
        createUserAccount("Amelia", "Jackson", "amelia.jackson@example.com", "555-0116", userRole);
        createUserAccount("Joseph", "White", "joseph.white@example.com", "555-0117", userRole);
        createUserAccount("Harper", "Harris", "harper.harris@example.com", "555-0118", userRole);
        createUserAccount("Christopher", "Martin", "christopher.martin@example.com", "555-0119", userRole);
        createUserAccount("Evelyn", "Thompson", "evelyn.thompson@example.com", "555-0120", userRole);

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

        // Additional events
        createEvent("NBA: Nets vs Knicks", "Brooklyn Nets vs New York Knicks - Battle for New York",
                    "2025-12-03 19:00:00", "Barclays Center, Brooklyn, NY", formatter);
        createEvent("NBA: Mavericks vs Suns", "Dallas Mavericks vs Phoenix Suns featuring Luka Doncic",
                    "2025-12-07 20:30:00", "American Airlines Center, Dallas, TX", formatter);
        createEvent("NBA: Bucks vs 76ers", "Milwaukee Bucks vs Philadelphia 76ers - Championship contenders clash",
                    "2025-12-12 19:30:00", "Fiserv Forum, Milwaukee, WI", formatter);
        createEvent("NFL: Chiefs vs Bills", "Kansas City Chiefs vs Buffalo Bills - AFC Championship rematch",
                    "2025-11-24 16:25:00", "Arrowhead Stadium, Kansas City, MO", formatter);
        createEvent("NFL: Cowboys vs Eagles", "Dallas Cowboys vs Philadelphia Eagles - NFC East rivalry",
                    "2025-12-01 13:00:00", "AT&T Stadium, Arlington, TX", formatter);
        createEvent("NFL: 49ers vs Seahawks", "San Francisco 49ers vs Seattle Seahawks - NFC West showdown",
                    "2025-12-14 17:15:00", "Levi's Stadium, Santa Clara, CA", formatter);
        createEvent("MLS Cup Final 2025", "Major League Soccer Championship Final",
                    "2025-12-09 15:00:00", "BMO Stadium, Los Angeles, CA", formatter);
        createEvent("UFC 300", "Ultimate Fighting Championship - Stacked fight card",
                    "2025-11-30 22:00:00", "T-Mobile Arena, Las Vegas, NV", formatter);
        createEvent("Drake - It's All a Blur Tour", "Drake live with special surprise guests",
                    "2025-12-15 20:00:00", "Scotiabank Arena, Toronto, ON", formatter);
        createEvent("Coldplay - Music of the Spheres", "Coldplay's spectacular world tour experience",
                    "2025-12-18 19:30:00", "Rose Bowl, Pasadena, CA", formatter);
        createEvent("Billie Eilish - Hit Me Hard and Soft Tour", "Billie Eilish performing new and classic hits",
                    "2025-12-20 20:00:00", "Chase Center, San Francisco, CA", formatter);
        createEvent("React Summit 2025", "The biggest React conference of the year",
                    "2025-11-18 09:00:00", "Austin Convention Center, Austin, TX", formatter);
        createEvent("AWS re:Invent 2025", "Amazon Web Services annual cloud computing conference",
                    "2025-12-02 08:00:00", "Venetian Convention Center, Las Vegas, NV", formatter);
        createEvent("DockerCon 2025", "Container technology conference and expo",
                    "2025-11-30 09:00:00", "Seattle Convention Center, Seattle, WA", formatter);
        createEvent("KubeCon 2025", "Cloud Native Computing Foundation flagship conference",
                    "2025-12-11 08:30:00", "McCormick Place, Chicago, IL", formatter);
        createEvent("GitHub Universe 2025", "GitHub's annual developer conference",
                    "2025-11-21 09:00:00", "Yerba Buena Center, San Francisco, CA", formatter);
        createEvent("Harry Styles - Love On Tour", "Harry Styles performing worldwide hits",
                    "2025-12-22 19:30:00", "Madison Square Garden, New York, NY", formatter);
        createEvent("Adele - Weekends with Adele", "Adele's exclusive Las Vegas residency",
                    "2025-12-28 20:00:00", "The Colosseum at Caesars Palace, Las Vegas, NV", formatter);
        createEvent("Bruno Mars - 24K Magic Tour", "Bruno Mars live in concert",
                    "2025-12-30 20:00:00", "Park MGM, Las Vegas, NV", formatter);
        createEvent("NHL: Rangers vs Islanders", "New York Rangers vs New York Islanders - Battle of New York on ice",
                    "2025-12-06 19:00:00", "Madison Square Garden, New York, NY", formatter);

        // Pre-populate Redis cache with all events
        System.out.println("Pre-populating Redis cache with events...");
        eventService.getAllEvents();
        System.out.println("Redis cache populated successfully!");

        // Index events to OpenSearch
        System.out.println("Indexing events to OpenSearch...");
        try {
            eventService.indexAllEvents();
            System.out.println("OpenSearch indexing completed successfully!");
        } catch (Exception e) {
            System.out.println("Warning: OpenSearch indexing failed - " + e.getMessage());
            System.out.println("The application will continue, but OpenSearch search may not be available.");
        }

        System.out.println("Data seeding completed successfully! Created " + roleRepository.count() +
                          " roles, " + accountRepository.count() + " accounts and " + eventRepository.count() + " events.");
    }

    private void createEvent(String name, String description, String dateStr, String location, DateTimeFormatter formatter) {
        Event event = new Event();
        event.setName(name);
        event.setDescription(description);
        event.setEventDate(LocalDateTime.parse(dateStr, formatter));
        event.setLocation(location);
        eventRepository.save(event);
    }

    private void createUserAccount(String firstName, String lastName, String email, String phone, Role role) {
        Account account = new Account();
        account.setFirstName(firstName);
        account.setLastName(lastName);
        account.setEmail(email);
        account.setPassword(passwordEncoder.encode("password123"));
        account.setPhone(phone);
        account.getRoles().add(role);
        accountRepository.save(account);
    }
}