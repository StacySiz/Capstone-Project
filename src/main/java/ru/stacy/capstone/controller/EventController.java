package ru.stacy.capstone.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.stacy.capstone.dto.EventDTO;
import ru.stacy.capstone.model.Event;
import ru.stacy.capstone.service.EventService;
import ru.stacy.capstone.service.UserService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/event")
public class EventController {

    private EventService eventService;

    private UserService userService;

    private ModelMapper modelMapper;

    @Autowired
    public EventController(EventService eventService, UserService userService, ModelMapper modelMapper) {
        this.eventService = eventService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/create")
    public ResponseEntity createEvent(@RequestBody EventDTO eventDTO, HttpServletRequest request) {
        //TODO uncoment
//        User user = userService.getLoggedInUser(request);
//        Event event = eventService.createEvent(eventDTO, user.getId());
        Event event = eventService.createEvent(eventDTO, 1L);
        return new ResponseEntity<>(modelMapper.map(event, EventDTO.class), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity getEvent(@PathVariable Long id) {
        if (eventService.findEvent(id) != null) {
            return new ResponseEntity<>(modelMapper.map(eventService.findEvent(id), EventDTO.class), HttpStatus.OK);
        } else return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity updateEvent(@RequestBody EventDTO eventDTO, @PathVariable Long id) {
        if (eventService.findEvent(id) != null) {
            Event event = eventService.updateEvent(eventDTO, id);
            return new ResponseEntity<>(modelMapper.map(event, EventDTO.class), HttpStatus.OK);
        } else return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteEvent(@PathVariable Long id) {
        if (eventService.findEvent(id) != null) {
            eventService.deleteEvent(id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
