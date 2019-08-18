package ru.stacy.capstone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvitationEventInfoDTO {
    private String name;
    private String description;
    private boolean isFree;
    private Integer price;
    private List<String> guests;
}
