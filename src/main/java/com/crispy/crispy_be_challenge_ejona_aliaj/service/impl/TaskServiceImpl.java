package com.crispy.crispy_be_challenge_ejona_aliaj.service.impl;

import com.crispy.crispy_be_challenge_ejona_aliaj.converter.TaskMapper;
import com.crispy.crispy_be_challenge_ejona_aliaj.dto.TaskDTO;
import com.crispy.crispy_be_challenge_ejona_aliaj.entity.TaskEntity;
import com.crispy.crispy_be_challenge_ejona_aliaj.entity.UserEntity;
import com.crispy.crispy_be_challenge_ejona_aliaj.exception.AuthorizationException;
import com.crispy.crispy_be_challenge_ejona_aliaj.repository.TaskRepository;
import com.crispy.crispy_be_challenge_ejona_aliaj.repository.UserRepository;
import com.crispy.crispy_be_challenge_ejona_aliaj.security.SecurityUtil;
import com.crispy.crispy_be_challenge_ejona_aliaj.service.TaskService;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {

    private final UserRepository userRepository;

    private final TaskRepository taskRepository;

    private final TaskMapper taskMapper;

    public TaskServiceImpl(UserRepository userRepository, TaskRepository taskRepository, TaskMapper taskMapper) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    public TaskDTO createTask(TaskDTO taskDTO) {
        TaskEntity task = taskMapper.fromDto(taskDTO);
        task.setUserId(getUserId());
        return taskMapper.toDto(taskRepository.save(task));
    }

    private Long getUserId() {
        UserEntity user = userRepository.findByLogin(SecurityUtil.getUserName()).orElseThrow(() -> new AuthorizationException("Token Not Provided"));
        return user.getId();
    }
}
