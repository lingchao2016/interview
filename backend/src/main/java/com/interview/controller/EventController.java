package com.interview.controller;

import com.interview.dto.EventMapper;
import com.interview.dto.EventRequest;
import com.interview.dto.EventResponse;
import com.interview.model.Event;
import com.interview.model.Performer;
import com.interview.model.Venue;
import com.interview.repository.PerformerRepository;
import com.interview.repository.VenueRepository;
import com.interview.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Tag(name = "Events", description = "Event management APIs for concerts, sports, and tech events")
@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;
    private final EventMapper eventMapper;
    private final VenueRepository venueRepository;
    private final PerformerRepository performerRepository;

    @Autowired
    public EventController(EventService eventService, EventMapper eventMapper,
                          VenueRepository venueRepository, PerformerRepository performerRepository) {
        this.eventService = eventService;
        this.eventMapper = eventMapper;
        this.venueRepository = venueRepository;
        this.performerRepository = performerRepository;
    }

    @Operation(summary = "Get all events", description = "Retrieve a list of all events including concerts, sports, and tech events")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of events")
    })
    @GetMapping
    public ResponseEntity<List<EventResponse>> getAllEvents() {
        List<Event> events = eventService.getAllEvents();
        List<EventResponse> responses = events.stream()
                .map(eventMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Get event by ID", description = "Retrieve a specific event by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event found"),
            @ApiResponse(responseCode = "404", description = "Event not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> getEventById(
            @Parameter(description = "ID of the event to retrieve") @PathVariable UUID id) {
        return eventService.getEventById(id)
                .map(eventMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create new event", description = "Create a new event (concert, sports game, or tech event)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Event created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<EventResponse> createEvent(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Event request object")
            @Valid @RequestBody EventRequest request) {
        try {
            Event event = eventMapper.toEntity(request);

            // Set venue if provided
            if (request.getVenueId() != null) {
                Venue venue = venueRepository.findById(request.getVenueId())
                        .orElseThrow(() -> new RuntimeException("Venue not found with id: " + request.getVenueId()));
                event.setVenue(venue);
            }

            // Set performers if provided
            if (request.getPerformerIds() != null && !request.getPerformerIds().isEmpty()) {
                Set<Performer> performers = new HashSet<>();
                for (UUID performerId : request.getPerformerIds()) {
                    Performer performer = performerRepository.findById(performerId)
                            .orElseThrow(() -> new RuntimeException("Performer not found with id: " + performerId));
                    performers.add(performer);
                }
                event.setPerformers(performers);
            }

            Event createdEvent = eventService.createEvent(event);
            return ResponseEntity.status(HttpStatus.CREATED).body(eventMapper.toResponse(createdEvent));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(summary = "Update event", description = "Update an existing event by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event updated successfully"),
            @ApiResponse(responseCode = "404", description = "Event not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EventResponse> updateEvent(
            @Parameter(description = "ID of the event to update") @PathVariable UUID id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated event request object")
            @Valid @RequestBody EventRequest request) {
        try {
            Event event = eventService.getEventById(id)
                    .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));

            // Update basic fields
            eventMapper.updateEntityFromRequest(event, request);

            // Update venue if provided
            if (request.getVenueId() != null) {
                Venue venue = venueRepository.findById(request.getVenueId())
                        .orElseThrow(() -> new RuntimeException("Venue not found with id: " + request.getVenueId()));
                event.setVenue(venue);
            } else {
                event.setVenue(null);
            }

            // Update performers if provided
            if (request.getPerformerIds() != null) {
                Set<Performer> performers = new HashSet<>();
                for (UUID performerId : request.getPerformerIds()) {
                    Performer performer = performerRepository.findById(performerId)
                            .orElseThrow(() -> new RuntimeException("Performer not found with id: " + performerId));
                    performers.add(performer);
                }
                event.setPerformers(performers);
            } else {
                event.setPerformers(new HashSet<>());
            }

            Event updatedEvent = eventService.createEvent(event);
            return ResponseEntity.ok(eventMapper.toResponse(updatedEvent));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete event", description = "Delete an event by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Event deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Event not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(
            @Parameter(description = "ID of the event to delete") @PathVariable UUID id) {
        try {
            eventService.deleteEvent(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Search events by name
    @GetMapping("/search")
    public ResponseEntity<List<EventResponse>> searchEventsByName(@RequestParam String name) {
        List<Event> events = eventService.searchEventsByName(name);
        List<EventResponse> responses = events.stream()
                .map(eventMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    // Get events by location
    @GetMapping("/location/{location}")
    public ResponseEntity<List<EventResponse>> getEventsByLocation(@PathVariable String location) {
        List<Event> events = eventService.getEventsByLocation(location);
        List<EventResponse> responses = events.stream()
                .map(eventMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    // Get upcoming events
    @GetMapping("/upcoming")
    public ResponseEntity<List<EventResponse>> getUpcomingEvents() {
        List<Event> events = eventService.getUpcomingEvents();
        List<EventResponse> responses = events.stream()
                .map(eventMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    // Get events between dates
    @GetMapping("/between")
    public ResponseEntity<List<EventResponse>> getEventsBetweenDates(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<Event> events = eventService.getEventsBetweenDates(startDate, endDate);
        List<EventResponse> responses = events.stream()
                .map(eventMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
}