package com.crispy.crispy_be_challenge_ejona_aliaj.controller.response;

import com.crispy.crispy_be_challenge_ejona_aliaj.dto.StepDTO;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
public class TaskResponse implements Serializable {

    private Long id;

    private String name;

    private String note;

    private LocalDate dueDate;

    private Boolean completed;

    private Boolean important;

    private List<StepDTO> steps;

    private Long projectId;

    private Long userId;

}
