package com.interview.repository;

import com.interview.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {

    // Find events by name (case-insensitive)
    List<Event> findByNameContainingIgnoreCase(String name);

    // Find events by location
    List<Event> findByLocation(String location);

    // Find events after a specific date
    List<Event> findByEventDateAfter(LocalDateTime date);

    // Find events between two dates
    List<Event> findByEventDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}