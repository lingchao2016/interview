package com.interview.service;

import com.interview.dto.CursorPageResponse;
import com.interview.model.Event;
import com.interview.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Cacheable(value = "events", key = "'all'")
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Cacheable(value = "events", key = "#id")
    public Optional<Event> getEventById(UUID id) {
        return eventRepository.findById(id);
    }

    @CacheEvict(value = "events", allEntries = true)
    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    @CacheEvict(value = "events", allEntries = true)
    public Event updateEvent(UUID id, Event eventDetails) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));

        event.setName(eventDetails.getName());
        event.setDescription(eventDetails.getDescription());
        event.setEventDate(eventDetails.getEventDate());
        event.setLocation(eventDetails.getLocation());

        return eventRepository.save(event);
    }

    @CacheEvict(value = "events", allEntries = true)
    public void deleteEvent(UUID id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));
        eventRepository.delete(event);
    }

    @Cacheable(value = "events", key = "'search:' + #name")
    public List<Event> searchEventsByName(String name) {
        return eventRepository.findByNameContainingIgnoreCase(name);
    }

    @Cacheable(value = "events", key = "'location:' + #location")
    public List<Event> getEventsByLocation(String location) {
        return eventRepository.findByLocation(location);
    }

    @Cacheable(value = "events", key = "'upcoming'")
    public List<Event> getUpcomingEvents() {
        return eventRepository.findByEventDateAfter(LocalDateTime.now());
    }

    @Cacheable(value = "events", key = "'between:' + #startDate + ':' + #endDate")
    public List<Event> getEventsBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        return eventRepository.findByEventDateBetween(startDate, endDate);
    }

    public CursorPageResponse<Event> getEventsCursorPaginated(UUID cursor, int pageSize) {
        // Fetch one extra to determine if there are more results
        Pageable pageable = PageRequest.of(0, pageSize + 1);
        List<Event> events;

        if (cursor == null) {
            // First page
            events = eventRepository.findFirstPage(pageable);
        } else {
            // Get the cursor event to extract its createdAt timestamp
            Event cursorEvent = eventRepository.findById(cursor)
                    .orElseThrow(() -> new RuntimeException("Invalid cursor: event not found with id " + cursor));

            events = eventRepository.findEventsAfterCursor(
                    cursorEvent.getCreatedAt(),
                    cursor,
                    pageable
            );
        }

        // Determine if there are more results
        boolean hasMore = events.size() > pageSize;

        // If we have more results, remove the extra one
        if (hasMore) {
            events = events.subList(0, pageSize);
        }

        // Determine the next cursor (ID of the last event in this page)
        String nextCursor = null;
        if (hasMore && !events.isEmpty()) {
            nextCursor = events.get(events.size() - 1).getId().toString();
        }

        return new CursorPageResponse<>(events, nextCursor, hasMore, pageSize);
    }
}