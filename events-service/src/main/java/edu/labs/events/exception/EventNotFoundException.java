package edu.labs.events.exception;

public class EventNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public EventNotFoundException(Long eventId) {
		super(String.format("Could not find event with event id %s", eventId));
	}
}
