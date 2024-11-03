package com.crispy.crispy_be_challenge_ejona_aliaj.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskDTO {

    private Long id;

    private String name;

    private String note;

    private LocalDate dueDate;

    private Boolean completed;

    private Boolean important;

    private Long projectId;

    private Long userId;

}
