package ru.stacy.capstone.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.stacy.capstone.dto.PlaceDTO;
import ru.stacy.capstone.model.Place;
import ru.stacy.capstone.model.Role;
import ru.stacy.capstone.model.User;
import ru.stacy.capstone.repository.UserRepository;
import ru.stacy.capstone.security.JwtTokenProvider;
import ru.stacy.capstone.service.PlaceService;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider tokenProvider;

    private Place mockedPlace;
    private PlaceDTO placeDTO;
    private List<Place> places;
    private User user;
    private String token = null;


    private static final ObjectMapper om = new ObjectMapper();

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
        when(placeService.findPlace(1L)).thenReturn(mockedPlace);
        when(placeService.updatePlace(placeDTO, 1L)).thenReturn(mockedPlace);

        given(placeService.findAllPlaces()).willReturn(places);
        given(placeService.addPlace(placeDTO)).willReturn(mockedPlace);

        user = new User();
        user.setEmail("testEmail");
        user.setUsername("test");
        user.setPassword("testPassword");
        user.setRoles(Collections.singletonList(Role.ROLE_ADMIN));

        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);
        token = tokenProvider.createToken(user.getUsername(), Role.ROLE_ADMIN);
        given(modelMapper.map(mockedPlace, PlaceDTO.class)).willReturn(placeDTO);
    }

    @Test
    public void givenPlaces_whenGetPlaces_thenReturnJsonArray() throws Exception {
        mockMvc.perform(get("/places/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is(mockedPlace.getName())))
                .andExpect(jsonPath("$[0].address", is(mockedPlace.getAddress())))
                .andExpect(jsonPath("$[0].description", is(mockedPlace.getDescription())))
                .andExpect(jsonPath("$[0].email", is(mockedPlace.getEmail())))
                .andExpect(jsonPath("$[0].image", is(mockedPlace.getImage())));
    }

    @Test
    public void givenPlace_whenGetPlaceById_thenReturnJson() throws Exception {
        mockMvc.perform(get("/places/{id}", 1L)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(mockedPlace.getName())))
                .andExpect(jsonPath("$.address", is(mockedPlace.getAddress())))
                .andExpect(jsonPath("$.description", is(mockedPlace.getDescription())))
                .andExpect(jsonPath("$.email", is(mockedPlace.getEmail())))
                .andExpect(jsonPath("$.image", is(mockedPlace.getImage())));
    }

    @Test
    public void savePlace_thenReturnJson() throws Exception {
        mockMvc.perform(post("/places/add")
                .header("Authorization", "Bearer " + token)
                .content(om.writeValueAsString(placeDTO))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(mockedPlace.getName())))
                .andExpect(jsonPath("$.address", is(mockedPlace.getAddress())))
                .andExpect(jsonPath("$.description", is(mockedPlace.getDescription())))
                .andExpect(jsonPath("$.email", is(mockedPlace.getEmail())))
                .andExpect(jsonPath("$.image", is(mockedPlace.getImage())))
                .andExpect(status().isOk());
    }

    @Test
    public void updatePlace_thenReturnJson() throws Exception {
        mockMvc.perform(put("/places/{id}", 1L)
                .content(om.writeValueAsString(placeDTO))
                .header("Authorization", "Bearer " + token)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(mockedPlace.getName())))
                .andExpect(jsonPath("$.address", is(mockedPlace.getAddress())))
                .andExpect(jsonPath("$.description", is(mockedPlace.getDescription())))
                .andExpect(jsonPath("$.email", is(mockedPlace.getEmail())))
                .andExpect(jsonPath("$.image", is(mockedPlace.getImage())))
                .andExpect(status().isOk());
    }

    @Test
    public void deletePlace() throws Exception {
        doNothing().when(placeService).deletePlace(1L);

        mockMvc.perform(delete("/places/{id}", 1L)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
    }
}
