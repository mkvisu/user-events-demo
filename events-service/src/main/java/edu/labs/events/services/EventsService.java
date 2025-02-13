package edu.labs.events.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import edu.labs.events.entities.Event;

public interface EventsService {
	public Event save(Event event);
	public Event getById(long id);
	public Optional<Event> updateEvent(Event event, Long eventId);
	public List<Event> getAllEvents();
	public void deleteEvent(Long eventId);
	public List<Event> getEventsBetween(Date stateDate, Date endDate);
	public List<Event> getEventsContaining(String eventName);
}
