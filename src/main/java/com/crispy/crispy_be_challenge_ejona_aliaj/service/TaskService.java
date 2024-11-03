package com.crispy.crispy_be_challenge_ejona_aliaj.service;

import com.crispy.crispy_be_challenge_ejona_aliaj.dto.TaskDTO;

public interface TaskService {

    TaskDTO createTask(TaskDTO taskDTO);

    TaskDTO updateTask(TaskDTO taskDTO);
}
