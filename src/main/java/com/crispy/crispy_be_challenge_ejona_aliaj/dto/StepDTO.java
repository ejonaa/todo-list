package com.crispy.crispy_be_challenge_ejona_aliaj.dto;

import lombok.Data;

@Data
public class StepDTO {

    private Long id;

    private String name;

    private Long taskId;

    private Long projectId;

    private Long userId;
}
