package ru.stacy.capstone.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.stacy.capstone.dto.EventDTO;
import ru.stacy.capstone.model.Event;
import ru.stacy.capstone.model.Place;
import ru.stacy.capstone.repository.EventRepository;
import ru.stacy.capstone.repository.PlaceRepository;
import ru.stacy.capstone.repository.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    private UserRepository userRepository;

    private EventRepository eventRepository;

    private PlaceRepository placeRepository;

    private ModelMapper modelMapper;

    @Autowired
    public EventService(UserRepository userRepository, EventRepository eventRepository, PlaceRepository placeRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.placeRepository = placeRepository;
        this.modelMapper = modelMapper;
    }


    public Event createEvent(EventDTO eventDTO, Long userPlannerId) {
        List<Place> places = eventDTO.getPotentialPlaces()
                .stream()
                .map(PlaceDTO -> modelMapper.map(PlaceDTO, Place.class))
                .map(place -> placeRepository.save(place))
                .collect(Collectors.toList());

        Event event = Event.builder()
                .name(eventDTO.getName())
                .date(new Date())
                .description(eventDTO.getDescription())
                .planner(userRepository.findOne(userPlannerId))
                .potentialPlaces(places)
                .price(eventDTO.getPrice())
                .isFree(eventDTO.isFree())
                .build();
        if (!event.isFree()) {
            event.setPrice(eventDTO.getPrice());
        }
        eventRepository.save(event);
        return event;
    }

    public void deleteEvent(Long id) {
        eventRepository.delete(id);
    }

    public Event updateEvent(EventDTO eventDTO, Long id) {
        Event event = modelMapper.map(eventDTO, Event.class);
        if (eventDTO.getPotentialPlaces() != null) {
            List<Place> places = eventDTO.getPotentialPlaces()
                    .stream()
                    .map(PlaceDTO -> modelMapper.map(PlaceDTO, Place.class))
                    .map(place -> placeRepository.save(place))
                    .collect(Collectors.toList());
            event.setPotentialPlaces(places);
        }
        Event oldEvent = eventRepository.findOne(id);
        event.setParticipants(oldEvent.getParticipants());
        event.setId(id);
        eventRepository.save(event);
        return event;
    }

    public Event findEvent(Long id) {
        return eventRepository.findOne(id);
    }

    public List<Event> findAllEvents() {
        return eventRepository.findAll();
    }
}
