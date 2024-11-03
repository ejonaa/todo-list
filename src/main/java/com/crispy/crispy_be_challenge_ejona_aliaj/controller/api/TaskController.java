package com.crispy.crispy_be_challenge_ejona_aliaj.controller.api;

import com.crispy.crispy_be_challenge_ejona_aliaj.controller.request.TaskRequest;
import com.crispy.crispy_be_challenge_ejona_aliaj.controller.request.validation.ValidateInputRequest;
import com.crispy.crispy_be_challenge_ejona_aliaj.dto.TaskDTO;
import com.crispy.crispy_be_challenge_ejona_aliaj.dto.converter.TaskDtoConverter;
import com.crispy.crispy_be_challenge_ejona_aliaj.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Objects;

/**
 * The type Task controller.
 *
 * @author ejoaliaj
 */
@RestController
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "TaskController", description = "API to manage tasks")
@RequestMapping("/api/1.0/projects")
public class TaskController {

    private final TaskDtoConverter taskDtoConverter;

    private final TaskService taskService;

    public TaskController(TaskDtoConverter taskDtoConverter, TaskService taskService) {
        this.taskDtoConverter = taskDtoConverter;
        this.taskService = taskService;
    }

    /**
     * Create task
     *
     * @param projectId      id of project where task is being created
     * @param newTaskRequest task details
     */
    @Operation(summary = "Create a task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful creation"),
            @ApiResponse(responseCode = "400", description = "Validation errors"),
            @ApiResponse(responseCode = "404", description = "Resource not found")})
    @ValidateInputRequest
    @PostMapping(path = "/{projectId}/tasks", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createTask(@PathVariable Long projectId, @RequestBody TaskRequest newTaskRequest, UriComponentsBuilder ucb) {
        TaskDTO taskDTO = taskDtoConverter.convert(newTaskRequest);
        if (Objects.nonNull(taskDTO)) {
            taskDTO.setProjectId(projectId);
        }
        TaskDTO savedTask = taskService.createTask(taskDTO);
        if (Objects.nonNull(savedTask)) {
            URI locationOfNewProject = ucb
                    .path("/api/1.0/projects/{projectId}/tasks/{id}")
                    .buildAndExpand(projectId, savedTask.getId())
                    .toUri();
            return ResponseEntity.created(locationOfNewProject).build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Partially update a task.
     *
     * @param projectId   id of project task belongs to
     * @param taskId      id of task that is being updated
     * @param taskRequest task details
     */
    @Operation(summary = "Partially update a task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful update"),
            @ApiResponse(responseCode = "400", description = "Validation errors"),
            @ApiResponse(responseCode = "404", description = "Resource not found")})
    @PatchMapping(path = "/{projectId}/tasks/{taskId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateTask(@PathVariable Long projectId, @PathVariable Long taskId,
                                           @RequestBody TaskRequest taskRequest) {
        TaskDTO taskDTO = taskDtoConverter.convert(taskRequest);
        if (Objects.nonNull(taskDTO)) {
            taskDTO.setId(taskId);
            taskDTO.setProjectId(projectId);
        }
        TaskDTO updatedTask = taskService.updateTask(taskDTO);

        if (Objects.nonNull(updatedTask)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
