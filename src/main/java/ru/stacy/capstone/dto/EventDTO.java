package ru.stacy.capstone.dto;

import lombok.Data;
import ru.stacy.capstone.model.User;

import java.util.Date;
import java.util.Set;

@Data
public class EventDTO {
    private Long id;

    private String name;

    private String description;

    private Date date;

    private String address;

    private User planner;

    private Set<User> participants;
}
