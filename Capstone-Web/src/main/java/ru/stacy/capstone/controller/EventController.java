package ru.stacy.capstone.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.stacy.capstone.dto.EventDTO;
import ru.stacy.capstone.model.Event;
import ru.stacy.capstone.service.EventService;
import ru.stacy.capstone.service.NotificationService;
import ru.stacy.capstone.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/event")
public class EventController {

    private EventService eventService;

    private UserService userService;

    private ModelMapper modelMapper;

    private NotificationService notificationService;

    @Autowired
    public EventController(EventService eventService, UserService userService, ModelMapper modelMapper, NotificationService notificationService) {
        this.eventService = eventService;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.notificationService = notificationService;
    }

    @PostMapping("/create")
    public ResponseEntity createEvent(@RequestBody EventDTO eventDTO, HttpServletRequest request) {
        //TODO uncoment
//        User user = userService.getLoggedInUser(request);
//        Event event = eventService.createEvent(eventDTO, user.getId());
        Event event = eventService.createEvent(eventDTO, 1L);
        return ResponseEntity.ok(modelMapper.map(event, EventDTO.class));
    }

    @GetMapping("/all")
    public ResponseEntity getAllEvents() {
        List<Event> allEvents = eventService.findAllEvents();
        List<EventDTO> eventDTOS = allEvents.stream()
                .map(event -> modelMapper.map(event, EventDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(eventDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity getEvent(@PathVariable Long id) {
        if (eventService.findEvent(id) != null) {
            return ResponseEntity.ok(modelMapper.map(eventService.findEvent(id), EventDTO.class));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("{id}/update")
    public ResponseEntity updateEvent(@RequestBody EventDTO eventDTO, @PathVariable Long id) {
        if (eventService.findEvent(id) != null) {
            Event event = eventService.updateEvent(eventDTO, id);
            return ResponseEntity.ok(modelMapper.map(event, EventDTO.class));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}/delete")
    public ResponseEntity deleteEvent(@PathVariable Long id) {
        if (eventService.findEvent(id) != null) {
            eventService.deleteEvent(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("{id}/invite")
    public ResponseEntity inviteToEvent(@PathVariable Long id, @RequestBody List<String> emails) {
        ResponseEntity responseEntity = notificationService.inviteFriendsToEvent(id, emails);
        return ResponseEntity.ok(responseEntity);
    }
}
