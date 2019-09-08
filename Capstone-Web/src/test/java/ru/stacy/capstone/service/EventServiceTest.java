package ru.stacy.capstone.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.stacy.capstone.dto.EventDTO;
import ru.stacy.capstone.dto.UserDTO;
import ru.stacy.capstone.model.Event;
import ru.stacy.capstone.model.User;
import ru.stacy.capstone.repository.EventRepository;
import ru.stacy.capstone.repository.PlaceRepository;
import ru.stacy.capstone.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EventServiceTest {

    @MockBean
    private EventRepository eventRepository;

    @MockBean
    private PlaceRepository placeRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private EventService eventService;

    private Event mockedEvent;
    private EventDTO eventDTO;
    private List<Event> allEvents;
    private User mockedUser;
    private UserDTO userDTO;

    @Before
    public void init() {
        mockedEvent = new Event();
        mockedEvent.setName("TestName");
        mockedEvent.setDescription("TestDescription");
        mockedEvent.setPrice(100);
        mockedEvent.setFree(true);

        mockedUser = new User();
        mockedUser.setUsername("TestUsername");
        mockedUser.setEmail("testEmail");

        mockedEvent.setPlanner(mockedUser);
        mockedEvent.setPotentialPlaces(new ArrayList<>());

        eventDTO = new EventDTO();
        eventDTO.setName(mockedEvent.getName());
        eventDTO.setDescription(mockedEvent.getDescription());
        eventDTO.setPrice(mockedEvent.getPrice());
        eventDTO.setFree(mockedEvent.isFree());

        eventDTO.setPotentialPlaces(new ArrayList<>());
        allEvents = Collections.singletonList(mockedEvent);
    }

    @Test
    public void whenSaveEvent_thenReturnEvent() {
        when(userRepository.findOne(1L)).thenReturn(mockedUser);
        Event createdEvent = eventService.createEvent(eventDTO, 1L);

        assertThat(createdEvent.getName()).isEqualTo(mockedEvent.getName());
        assertThat(createdEvent.getDescription()).isEqualTo(mockedEvent.getDescription());
        assertThat(createdEvent.getPrice()).isEqualTo(mockedEvent.getPrice());
        assertThat(createdEvent.isFree()).isEqualTo(mockedEvent.isFree());
        assertThat(createdEvent.getParticipants()).isEqualTo(mockedEvent.getParticipants());
        assertThat(createdEvent.getPotentialPlaces()).isEqualTo(mockedEvent.getPotentialPlaces());
    }
}
