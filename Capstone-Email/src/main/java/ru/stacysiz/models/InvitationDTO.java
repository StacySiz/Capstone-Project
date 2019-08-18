package ru.stacysiz.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InvitationDTO {
    private String name;
    private String description;
    private boolean isFree;
    private Integer price;
    private List<String> guests;
}
