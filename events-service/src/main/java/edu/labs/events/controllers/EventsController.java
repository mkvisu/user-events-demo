package edu.labs.events.controllers;

import edu.labs.events.entities.Event;
import edu.labs.events.exception.EventNotFoundException;
import edu.labs.events.exception.InvalidDateRangeException;
import edu.labs.events.services.EventsService;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
@Slf4j
public class EventsController {

    @Autowired
    private EventsService eventsService;


    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public Event save(@RequestBody Event event) {
        return eventsService.save(event);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> update(@RequestBody Event event, @PathVariable Long eventId) {
    	try {
    		eventsService.updateEvent(event, eventId);
    	}catch(RuntimeException exception) {
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    	}
        
        return ResponseEntity.ok("Success");
    }
    
    @DeleteMapping("/{eventId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> deleteEvent(@PathVariable Long eventId) throws EventNotFoundException{
    	try {
    		eventsService.deleteEvent(eventId);
    	}catch(EventNotFoundException exception) {
    		throw exception;
    	}
    	catch(RuntimeException exception) {
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    	}
        
        return ResponseEntity.ok("Success");
    }

    @GetMapping("/{id}")
    public Event getById(@PathVariable long id) {
        return eventsService.getById(id);
    }
    
    @GetMapping
    public List<Event> getAllEvents(){
    	return this.eventsService.getAllEvents();
    }
    
    @GetMapping("/startDate/{startDate}/endDate/{endDate}")
    public List<Event> getEventsBetween(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate, 
    		@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate){

    	if(startDate.before(endDate)) {
    		return this.eventsService.getEventsBetween(startDate, endDate);
    	}
    	
    	throw new InvalidDateRangeException(startDate, endDate);
    }
    
    @GetMapping("/name/{eventName}")
    public List<Event> getEventsContaining(@PathVariable @NotEmpty String eventName){
    	return this.eventsService.getEventsContaining(eventName);
    }

}
