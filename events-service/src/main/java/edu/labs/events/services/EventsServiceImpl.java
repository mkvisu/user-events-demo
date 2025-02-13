package edu.labs.events.services;


import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.labs.events.entities.Event;
import edu.labs.events.exception.EventNotFoundException;
import edu.labs.events.repos.EventsRepository;
import edu.labs.events.utils.EventUtils;

@Service
@Slf4j
public class EventsServiceImpl implements EventsService {

    private final EventsRepository repository;

    @Autowired
    public EventsServiceImpl(EventsRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Event save(Event event) {
        return this.repository.saveAndFlush(event);
    }
    
    @Transactional
    public Optional<Event> updateEvent(Event sourceEvent, Long eventId) {
    	Optional<Event> existingEvent = repository.findById(eventId);
    	if(existingEvent.isPresent()) {
    		Event destinationEvent = existingEvent.get();
    		EventUtils.mapEventToEvent(sourceEvent, destinationEvent);	
    		return Optional.of(this.save(destinationEvent));
    	}
    	return Optional.ofNullable(null);
    }

    public Event getById(long id) {
        return this.repository.findById(id)
                .orElse(null);
    }

	@Override
	public List<Event> getAllEvents() {
		return this.repository.findAll();
	}
	
	public List<Event> getEventsBetween(Date stateDate, Date endDate){
		return this.repository.findByEventDateBetween(stateDate, endDate);
	}

	@Override
	public void deleteEvent(Long eventId) {
		try {
			this.repository.deleteById(eventId);
		}catch(EmptyResultDataAccessException notfound) {
			throw new EventNotFoundException(eventId);
		}catch(RuntimeException exception) {
			throw exception;
		}
	}

	@Override
	public List<Event> getEventsContaining(String eventName) {
		return this.repository.findByNameContainingIgnoreCase(eventName);
	}
	
	
}
