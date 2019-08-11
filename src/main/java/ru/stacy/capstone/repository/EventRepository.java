package ru.stacy.capstone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.stacy.capstone.model.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
}

