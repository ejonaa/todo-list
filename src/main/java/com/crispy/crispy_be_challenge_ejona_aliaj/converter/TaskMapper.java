package com.crispy.crispy_be_challenge_ejona_aliaj.converter;

import com.crispy.crispy_be_challenge_ejona_aliaj.controller.request.TaskRequest;
import com.crispy.crispy_be_challenge_ejona_aliaj.dto.TaskDTO;
import com.crispy.crispy_be_challenge_ejona_aliaj.entity.TaskEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskMapper {

    private final ModelMapper modelMapper;

    public TaskMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public TaskDTO toDto(TaskEntity task) {
        return modelMapper.map(task, TaskDTO.class);
    }

    public List<TaskDTO> toDtoList(List<TaskEntity> tasks) {
        return tasks.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public TaskEntity fromDto(TaskDTO taskDTO) {
        return modelMapper.map(taskDTO, TaskEntity.class);
    }

    public TaskDTO toDto(TaskRequest request) {
        return modelMapper.map(request, TaskDTO.class);
    }

}
