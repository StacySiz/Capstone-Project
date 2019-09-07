package ru.stacy.capstone.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.stacy.capstone.dto.EventDTO;
import ru.stacy.capstone.model.Event;
import ru.stacy.capstone.service.EventService;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    private ModelMapper modelMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void givenEvents_whenGetEvents_thenReturnJsonArray() throws Exception {

        Event mockedEvent = new Event();
        mockedEvent.setName("TestName");

        List<Event> allEvents = Collections.singletonList(mockedEvent);

        EventDTO eventDTO = new EventDTO();
        eventDTO.setName("TestName");

        given(eventService.findAllEvents()).willReturn(allEvents);
        given(modelMapper.map(mockedEvent, EventDTO.class)).willReturn(eventDTO);

        this.mockMvc.perform(get("/event/all")).andDo(print())
                .andExpect(jsonPath("$[0].name", is(mockedEvent.getName())))
                .andExpect(status().isOk());
    }
}
