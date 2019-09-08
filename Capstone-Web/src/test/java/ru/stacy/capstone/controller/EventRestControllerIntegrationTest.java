package ru.stacy.capstone.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.stacy.capstone.dto.EventDTO;
import ru.stacy.capstone.model.Event;
import ru.stacy.capstone.model.User;
import ru.stacy.capstone.service.EventService;
import ru.stacy.capstone.service.UserService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EventRestControllerIntegrationTest {

    @MockBean
    private EventService eventService;

    @MockBean
    private UserService userService;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private MockMvc mockMvc;

    private Event mockedEvent;
    private EventDTO eventDTO;
    private List<Event> allEvents;

    private static final ObjectMapper om = new ObjectMapper();

    @Before
    public void init() {
        mockedEvent = new Event();
        mockedEvent.setName("TestName");
        mockedEvent.setDescription("TestDescription");
        mockedEvent.setPrice(100);
        mockedEvent.setFree(true);
        mockedEvent.setParticipants(new HashSet<>());
        mockedEvent.setPotentialPlaces(new ArrayList<>());

        eventDTO = new EventDTO();
        eventDTO.setName(mockedEvent.getName());
        eventDTO.setDescription(mockedEvent.getDescription());
        eventDTO.setPrice(mockedEvent.getPrice());
        eventDTO.setFree(mockedEvent.isFree());
        eventDTO.setParticipants(new HashSet<>());
        eventDTO.setPotentialPlaces(new ArrayList<>());
        allEvents = Collections.singletonList(mockedEvent);

        when(userService.getLoggedInUser(Matchers.any())).thenReturn(new User());
        when(eventService.findEvent(1L)).thenReturn(mockedEvent);

        given(modelMapper.map(mockedEvent, EventDTO.class)).willReturn(eventDTO);
    }

    @Test
    public void givenEvents_whenGetEvents_thenReturnJsonArray() throws Exception {
        given(eventService.findAllEvents()).willReturn(allEvents);

        this.mockMvc.perform(get("/event/all")).andDo(print())
                .andExpect(jsonPath("$[0].name", is(mockedEvent.getName())))
                .andExpect(jsonPath("$[0].description", is(mockedEvent.getDescription())))
                .andExpect(jsonPath("$[0].price", is(mockedEvent.getPrice())))
                .andExpect(jsonPath("$[0].free", is(mockedEvent.isFree())))
                .andExpect(status().isOk());
    }

    @Test
    public void givenEvent_whenGetEventById_thenReturnJson() throws Exception {
        given(eventService.findEvent(1L)).willReturn(mockedEvent);

        this.mockMvc.perform(get("/event/{id}", 1L)).andDo(print())
                .andExpect(jsonPath("$.name", is(mockedEvent.getName())))
                .andExpect(jsonPath("$.description", is(mockedEvent.getDescription())))
                .andExpect(jsonPath("$.price", is(mockedEvent.getPrice())))
                .andExpect(jsonPath("$.free", is(mockedEvent.isFree())))
                .andExpect(status().isOk());
    }


    @Test
    public void saveEvent_thenReturnJson() throws Exception {
        given(eventService.createEvent(eventDTO, null)).willReturn(mockedEvent);

        mockMvc.perform(post("/event/create")
                .content(om.writeValueAsString(eventDTO))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(mockedEvent.getName())))
                .andExpect(jsonPath("$.description", is(mockedEvent.getDescription())))
                .andExpect(jsonPath("$.price", is(mockedEvent.getPrice())))
                .andExpect(jsonPath("$.free", is(mockedEvent.isFree())))
                .andExpect(status().isOk());
    }

    @Test
    public void updateEvent_thenReturnJson() throws Exception {
        when(eventService.updateEvent(eventDTO, 1L)).thenReturn(mockedEvent);

        mockMvc.perform(put("/event/{id}", 1L)
                .content(om.writeValueAsString(eventDTO))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(mockedEvent.getName())))
                .andExpect(jsonPath("$.description", is(mockedEvent.getDescription())))
                .andExpect(jsonPath("$.price", is(mockedEvent.getPrice())))
                .andExpect(jsonPath("$.free", is(mockedEvent.isFree())))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteEvent() throws Exception {
        doNothing().when(eventService).deleteEvent(1L);

        mockMvc.perform(delete("/event/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}
