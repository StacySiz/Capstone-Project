package ru.stacy.capstone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {
    private String name;

    private String description;

    private List<PlaceDTO> potentialPlaces;

    private Set<EventParticipantDTO> participants;

    private boolean isFree;

    private Integer price;
}
