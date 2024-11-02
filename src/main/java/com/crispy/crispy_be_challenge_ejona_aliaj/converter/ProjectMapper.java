package com.crispy.crispy_be_challenge_ejona_aliaj.converter;

import com.crispy.crispy_be_challenge_ejona_aliaj.controller.response.ProjectResponse;
import com.crispy.crispy_be_challenge_ejona_aliaj.controller.response.TaskResponse;
import com.crispy.crispy_be_challenge_ejona_aliaj.dto.ProjectDTO;
import com.crispy.crispy_be_challenge_ejona_aliaj.dto.StepDTO;
import com.crispy.crispy_be_challenge_ejona_aliaj.entity.ProjectEntity;
import com.crispy.crispy_be_challenge_ejona_aliaj.entity.TaskEntity;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProjectMapper {

    private final ModelMapper modelMapper;

    public ProjectMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    public void init() {
        TypeMap<ProjectEntity, ProjectResponse> propertyMapper = this.modelMapper.createTypeMap(ProjectEntity.class, ProjectResponse.class);

        propertyMapper.addMappings(
                mapper -> {
                    mapper.map(ProjectEntity::getId, ProjectResponse::setId);
                    mapper.map(ProjectEntity::getTitle, ProjectResponse::setTitle);
                    mapper.map(ProjectEntity::getUserId, ProjectResponse::setUserId);
                    mapper.using(convertTasks).map(ProjectEntity::getTasks, ProjectResponse::setTasks);
                }
        );
    }

    public ProjectDTO toDto(ProjectEntity product) {
        return modelMapper.map(product, ProjectDTO.class);
    }

    public List<ProjectDTO> toDtoList(List<ProjectEntity> products) {
        return products.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public ProjectEntity fromDto(ProjectDTO productDto) {
        return modelMapper.map(productDto, ProjectEntity.class);
    }

    public ProjectResponse toResponse(ProjectEntity product) {
        return modelMapper.map(product, ProjectResponse.class);
    }

    Converter<List<TaskEntity>, List<TaskResponse>> convertTasks = new AbstractConverter<>() {
        protected List<TaskResponse> convert(List<TaskEntity> tasks) {
            return tasks.stream().map(task -> {
                TaskResponse taskResponse = new TaskResponse();
                taskResponse.setId(task.getId());
                taskResponse.setName(task.getName());
                taskResponse.setNote(task.getNote());
                taskResponse.setDueDate(task.getDueDate());
                taskResponse.setImportant(task.getImportant());
                taskResponse.setCompleted(task.getCompleted());
                taskResponse.setProjectId(task.getProject().getId());
                taskResponse.setUserId(task.getUserId());
                taskResponse.setSteps(task.getSteps().stream().map(step -> modelMapper.map(step, StepDTO.class)).collect(Collectors.toList()));
                return taskResponse;
            }).collect(Collectors.toList());
        }
    };
}
