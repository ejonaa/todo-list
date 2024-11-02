package com.crispy.crispy_be_challenge_ejona_aliaj.controller.api;

import com.crispy.crispy_be_challenge_ejona_aliaj.controller.request.StepRequest;
import com.crispy.crispy_be_challenge_ejona_aliaj.controller.request.validation.ValidateInputRequest;
import com.crispy.crispy_be_challenge_ejona_aliaj.converter.StepMapper;
import com.crispy.crispy_be_challenge_ejona_aliaj.dto.StepDTO;
import com.crispy.crispy_be_challenge_ejona_aliaj.service.StepService;
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
public class StepController {

    private final StepMapper stepMapper;

    private final StepService stepService;

    public StepController(StepMapper stepMapper, StepService stepService) {
        this.stepMapper = stepMapper;
        this.stepService = stepService;
    }

    @ValidateInputRequest
    @PostMapping(path = "/{projectId}/tasks/{taskId}/steps", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createStep(@PathVariable Long projectId, @PathVariable Long taskId,
                                           @RequestBody StepRequest newStepRequest, UriComponentsBuilder ucb) {
        StepDTO stepDTO = stepMapper.toDto(newStepRequest);
        if (Objects.nonNull(stepDTO)) {
            stepDTO.setProjectId(projectId);
            stepDTO.setTaskId(taskId);
        }
        StepDTO savedStep = stepService.createStep(stepDTO);
        if (Objects.nonNull(savedStep)) {
            URI locationOfNewProject = ucb.path("/api/1.0/projects/{projectId}/tasks/{taskId}/steps/{id}")
                    .buildAndExpand(projectId, taskId, savedStep.getId())
                    .toUri();
            return ResponseEntity.created(locationOfNewProject).build();
        }
        return ResponseEntity.notFound().build();
    }
}
