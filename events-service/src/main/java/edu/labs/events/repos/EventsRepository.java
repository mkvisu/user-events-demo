package edu.labs.events.repos;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.labs.events.entities.Event;

@Repository
public interface EventsRepository extends JpaRepository<Event, Long> {
	public List<Event> findByEventDateBetween(Date startDate, Date endDate);
	public List<Event> findByNameContainingIgnoreCase(String name);
}

