package ru.stacy.capstone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.stacy.capstone.model.Place;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    Place findByName(String name);
}
