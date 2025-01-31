package edu.labs.events.controllers;

import edu.labs.events.entities.Event;
import edu.labs.events.services.EventsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Event save(@RequestBody Event event) {
        return eventsService.save(event);
    }
    
    @PutMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> update(@RequestBody Event event) {
    	try {
    		eventsService.updateEvent(event);
    	}catch(RuntimeException exception) {
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    	}
        
        return ResponseEntity.ok("Success");
    }
    
    

    @GetMapping("/{id}")
    public Event getById(@PathVariable long id) {
        return eventsService.getById(id);
    }

}
