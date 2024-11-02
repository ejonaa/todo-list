package com.crispy.crispy_be_challenge_ejona_aliaj.service.impl;

import com.crispy.crispy_be_challenge_ejona_aliaj.converter.StepMapper;
import com.crispy.crispy_be_challenge_ejona_aliaj.dto.StepDTO;
import com.crispy.crispy_be_challenge_ejona_aliaj.entity.StepEntity;
import com.crispy.crispy_be_challenge_ejona_aliaj.entity.TaskEntity;
import com.crispy.crispy_be_challenge_ejona_aliaj.entity.UserEntity;
import com.crispy.crispy_be_challenge_ejona_aliaj.exception.AuthorizationException;
import com.crispy.crispy_be_challenge_ejona_aliaj.repository.StepRepository;
import com.crispy.crispy_be_challenge_ejona_aliaj.repository.TaskRepository;
import com.crispy.crispy_be_challenge_ejona_aliaj.repository.UserRepository;
import com.crispy.crispy_be_challenge_ejona_aliaj.security.SecurityUtil;
import com.crispy.crispy_be_challenge_ejona_aliaj.service.StepService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StepServiceImpl implements StepService {

    private final UserRepository userRepository;

    private final TaskRepository taskRepository;

    private final StepRepository stepRepository;

    private final StepMapper stepMapper;

    public StepServiceImpl(UserRepository userRepository, TaskRepository taskRepository, StepRepository stepRepository, StepMapper stepMapper) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.stepRepository = stepRepository;
        this.stepMapper = stepMapper;
    }

    @Override
    public StepDTO createStep(StepDTO stepDTO) {
        Optional<TaskEntity> task = taskRepository.findByIdAndProjectIdAndUserId(stepDTO.getTaskId(), stepDTO.getProjectId(), getUserId());
        if (task.isPresent()) {
            StepEntity step = stepMapper.fromDto(stepDTO);
            step.setUserId(getUserId());
            return stepMapper.toDto(stepRepository.save(step));
        }
        return null;
    }

    private Long getUserId() {
        UserEntity user = userRepository.findByLogin(SecurityUtil.getUserName()).orElseThrow(() -> new AuthorizationException("Token Not Provided"));
        return user.getId();
    }
}
