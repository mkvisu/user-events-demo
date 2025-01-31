package edu.labs.events.services;

import java.util.Optional;

import edu.labs.events.entities.Event;

public interface EventsService {
	public Event save(Event event);
	public Event getById(long id);
	public Optional<Event> updateEvent(Event event);
}
