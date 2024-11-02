package com.crispy.crispy_be_challenge_ejona_aliaj.controller.response;

import com.crispy.crispy_be_challenge_ejona_aliaj.dto.TaskDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ProjectResponse implements Serializable {

    private Long id;

    private String title;

    private List<TaskDTO> tasks;

    private Long userId;

}
