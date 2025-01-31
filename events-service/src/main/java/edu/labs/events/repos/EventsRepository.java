package edu.labs.events.repos;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.labs.events.entities.Event;

@Repository
public interface EventsRepository extends JpaRepository<Event, Long> {
}

