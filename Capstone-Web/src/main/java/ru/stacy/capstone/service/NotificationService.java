package ru.stacy.capstone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.stacy.capstone.dto.InvitationEventInfoDTO;
import ru.stacy.capstone.model.Event;
import ru.stacy.capstone.repository.EventRepository;

import java.util.List;

@Service
public class NotificationService {

    @Value("${services.email}")
    private String emailNotificationURL;

    private EventRepository eventRepository;

    @Autowired
    public NotificationService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public ResponseEntity inviteFriendsToEvent(Long eventId, List<String> emailList) {

        Event event = eventRepository.findOne(eventId);

        InvitationEventInfoDTO invitation = new InvitationEventInfoDTO();
        invitation.setName(event.getName());
        invitation.setDescription(event.getDescription());
        invitation.setFree(event.isFree());
        invitation.setPrice(event.getPrice());
        invitation.setGuests(emailList);

        //TODO email people and invite
        RestTemplate restTemplate = new RestTemplate();
//        HttpEntity<Event> request = new HttpEntity<>(map);

        ResponseEntity responseEntity = restTemplate.postForObject(emailNotificationURL + "/email", invitation, ResponseEntity.class);

return responseEntity;
//        ResponseEntity<Event> response = restTemplate
//                .exchange(emailNotificationURL, HttpMethod.POST, request, Event.class);
//        return null;
    }
}
