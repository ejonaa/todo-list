package com.crispy.crispy_be_challenge_ejona_aliaj.controller.api;

import com.crispy.crispy_be_challenge_ejona_aliaj.controller.request.TaskRequest;
import com.crispy.crispy_be_challenge_ejona_aliaj.controller.request.validation.ValidateInputRequest;
import com.crispy.crispy_be_challenge_ejona_aliaj.dto.TaskDTO;
import com.crispy.crispy_be_challenge_ejona_aliaj.dto.converter.TaskDtoConverter;
import com.crispy.crispy_be_challenge_ejona_aliaj.service.TaskService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Objects;

@RestController
@RequestMapping("/api/1.0/projects")
public class TaskController {

    private final TaskDtoConverter taskDtoConverter;

    private final TaskService taskService;

    public TaskController(TaskDtoConverter taskDtoConverter, TaskService taskService) {
        this.taskDtoConverter = taskDtoConverter;
        this.taskService = taskService;
    }

    @ValidateInputRequest
    @PostMapping(path = "/{projectId}/tasks", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createTask(@PathVariable Long projectId, @RequestBody TaskRequest newTaskRequest, UriComponentsBuilder ucb) {
        TaskDTO taskDTO = taskDtoConverter.convert(newTaskRequest);
        if (Objects.nonNull(taskDTO)) {
            taskDTO.setProjectId(projectId);
        }
        TaskDTO savedTask = taskService.createTask(taskDTO);
        URI locationOfNewProject = ucb
                .path("/api/1.0/projects/{projectId}/tasks/{id}")
                .buildAndExpand(projectId, savedTask.getId())
                .toUri();
        return ResponseEntity.created(locationOfNewProject).build();
    }
    
}
