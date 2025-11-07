package com.collabhub.backend.dto;

import lombok.Data; // ⚠️ You forgot this line

@Data
public class CreateTeamRequest {
    private String name;
    private String topic;
    private String description;
    private int capacity;
}
