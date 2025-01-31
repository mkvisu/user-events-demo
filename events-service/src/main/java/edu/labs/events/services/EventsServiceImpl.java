package edu.labs.events.services;


import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.labs.events.entities.Event;
import edu.labs.events.repos.EventsRepository;

@Service
@Slf4j
public class EventsServiceImpl implements EventsService {

    private final EventsRepository repository;

    @Autowired
    public EventsServiceImpl(EventsRepository repository) {
        this.repository = repository;
    }

    public Event save(Event event) {
        return this.repository.save(event);
    }
    
    public Optional<Event> updateEvent(Event event) {
    	Optional<Event> existingEvent = repository.findById(event.getId());
    	if(existingEvent.isPresent()) {
    		Event tempEvent = existingEvent.get();
    		tempEvent = Event.builder().name(event.getName())
    				.address(event.getAddress())
    				.code(event.getCode())
    				.eventDate(event.getEventDate())
    				.id(event.getId())
    				.build();
    		return Optional.of(this.save(tempEvent));
    	}
    	return Optional.ofNullable(null);
    }

    public Event getById(long id) {
        return this.repository.findById(id)
                .orElse(null);
    }
}
