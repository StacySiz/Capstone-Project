package ru.stacy.capstone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.stacy.capstone.dto.InvitationEventInfoDTO;
import ru.stacy.capstone.model.Event;
import ru.stacy.capstone.model.User;
import ru.stacy.capstone.repository.EventRepository;
import ru.stacy.capstone.repository.UserRepository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class NotificationService {

    @Value("${services.email}")
    private String emailNotificationURL;

    private EventRepository eventRepository;

    private UserRepository userRepository;

    @Autowired
    public NotificationService(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity inviteFriendsToEvent(Long eventId, List<String> emailList) {

        Event event = eventRepository.findOne(eventId);

        InvitationEventInfoDTO invitation = createInvitation(event, emailList);

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.postForObject(emailNotificationURL + "/email", invitation, ResponseEntity.class);
    }

    public ResponseEntity verifyRegistration(Long userId, String verificationToken) {
        Map<String, String> map = new LinkedHashMap<>();
        User user = userRepository.findOne(userId);
        String email = user.getEmail();
        map.put(email, verificationToken);

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(emailNotificationURL + "/verify", map, ResponseEntity.class);
    }

    private InvitationEventInfoDTO createInvitation(Event event, List<String> emailList) {
        InvitationEventInfoDTO invitation = new InvitationEventInfoDTO();
        invitation.setName(event.getName());
        invitation.setDescription(event.getDescription());
        invitation.setFree(event.isFree());
        invitation.setPrice(event.getPrice());
        invitation.setGuests(emailList);

        return invitation;
    }
}
