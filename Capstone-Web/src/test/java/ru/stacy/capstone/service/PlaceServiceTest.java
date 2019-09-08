package ru.stacy.capstone.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.stacy.capstone.dto.PlaceDTO;
import ru.stacy.capstone.model.Place;
import ru.stacy.capstone.repository.PlaceRepository;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PlaceServiceTest {

    @Autowired
    private PlaceService placeService;

    @MockBean
    private PlaceRepository placeRepository;

    @MockBean
    private ModelMapper modelMapper;

    private Place mockedPlace;
    private PlaceDTO placeDTO;
    private List<Place> places;

    @Before
    public void init() {
        mockedPlace = new Place();
        mockedPlace.setName("TestName");
        mockedPlace.setAddress("TestAddress");
        mockedPlace.setDescription("TestDescription");
        mockedPlace.setEmail("TestEmail");
        mockedPlace.setImage("TestImage");
        placeDTO = new PlaceDTO();
        placeDTO.setName(mockedPlace.getName());
        placeDTO.setAddress(mockedPlace.getAddress());
        placeDTO.setDescription(mockedPlace.getDescription());
        placeDTO.setEmail(mockedPlace.getEmail());
        placeDTO.setImage(mockedPlace.getImage());

        places = Collections.singletonList(mockedPlace);

        given(modelMapper.map(mockedPlace, PlaceDTO.class)).willReturn(placeDTO);
        given(modelMapper.map(placeDTO, Place.class)).willReturn(mockedPlace);
    }

    @Test
    public void whenAddPlace_thenReturnPlace() {
        Place createdPlace = placeService.addPlace(placeDTO);

        assertThat(createdPlace.getName()).isEqualTo(mockedPlace.getName());
        assertThat(createdPlace.getAddress()).isEqualTo(mockedPlace.getAddress());
        assertThat(createdPlace.getDescription()).isEqualTo(mockedPlace.getDescription());
        assertThat(createdPlace.getEmail()).isEqualTo(mockedPlace.getEmail());
        assertThat(createdPlace.getImage()).isEqualTo(mockedPlace.getImage());
    }
}
