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
import ru.stacy.capstone.dto.PlaceDTO;
import ru.stacy.capstone.model.Place;
import ru.stacy.capstone.service.PlaceService;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PlaceRestControllerIntegrationTest {
    @MockBean
    private PlaceService placeService;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void givenPlaces_whenGetPlaces_thenReturnJsonArray() throws Exception {
        Place mockedPlace = new Place();
        mockedPlace.setName("TestName");
        PlaceDTO placeDTO = new PlaceDTO();
        placeDTO.setName(mockedPlace.getName());

        List<Place> places = Collections.singletonList(mockedPlace);

        given(placeService.findAllPlaces()).willReturn(places);
        given(modelMapper.map(mockedPlace, PlaceDTO.class)).willReturn(placeDTO);

        mockMvc.perform(get("/places/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is(mockedPlace.getName())));
    }
}
