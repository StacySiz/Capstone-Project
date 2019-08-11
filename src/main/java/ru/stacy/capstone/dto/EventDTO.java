package ru.stacy.capstone.dto;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class EventDTO {
    private String name;

    private String description;

    private List<PlaceDTO> potentialPlaces;

    private Set<UserDTO> participants;
}
