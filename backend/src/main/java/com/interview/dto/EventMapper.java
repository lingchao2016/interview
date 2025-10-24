package com.interview.dto;

import com.interview.model.Event;
import com.interview.model.Performer;
import com.interview.model.Venue;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class EventMapper {

    public EventResponse toResponse(Event event) {
        if (event == null) {
            return null;
        }

        EventResponse response = new EventResponse();
        response.setId(event.getId());
        response.setName(event.getName());
        response.setDescription(event.getDescription());
        response.setEventDate(event.getEventDate());
        response.setLocation(event.getLocation());
        response.setCreatedAt(event.getCreatedAt());
        response.setUpdatedAt(event.getUpdatedAt());

        // Map venue
        if (event.getVenue() != null) {
            response.setVenue(toVenueInfo(event.getVenue()));
        }

        // Map performers
        if (event.getPerformers() != null) {
            Set<EventResponse.PerformerInfo> performerInfos = event.getPerformers().stream()
                    .map(this::toPerformerInfo)
                    .collect(Collectors.toSet());
            response.setPerformers(performerInfos);
        }

        return response;
    }

    public Event toEntity(EventRequest request) {
        if (request == null) {
            return null;
        }

        Event event = new Event();
        event.setName(request.getName());
        event.setDescription(request.getDescription());
        event.setEventDate(request.getEventDate());
        event.setLocation(request.getLocation());

        return event;
    }

    public void updateEntityFromRequest(Event event, EventRequest request) {
        if (event == null || request == null) {
            return;
        }

        event.setName(request.getName());
        event.setDescription(request.getDescription());
        event.setEventDate(request.getEventDate());
        event.setLocation(request.getLocation());
    }

    private EventResponse.VenueInfo toVenueInfo(Venue venue) {
        if (venue == null) {
            return null;
        }

        return new EventResponse.VenueInfo(
                venue.getId(),
                venue.getName(),
                venue.getAddress(),
                venue.getCity(),
                venue.getState(),
                venue.getZipCode()
        );
    }

    private EventResponse.PerformerInfo toPerformerInfo(Performer performer) {
        if (performer == null) {
            return null;
        }

        return new EventResponse.PerformerInfo(
                performer.getId(),
                performer.getName(),
                performer.getGenre(),
                performer.getBio()
        );
    }
}