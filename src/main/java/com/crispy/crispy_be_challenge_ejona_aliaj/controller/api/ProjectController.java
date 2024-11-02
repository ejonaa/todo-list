package com.crispy.crispy_be_challenge_ejona_aliaj.controller.api;

import com.crispy.crispy_be_challenge_ejona_aliaj.controller.request.ProjectRequest;
import com.crispy.crispy_be_challenge_ejona_aliaj.controller.request.validation.ValidateInputRequest;
import com.crispy.crispy_be_challenge_ejona_aliaj.dto.ProjectDTO;
import com.crispy.crispy_be_challenge_ejona_aliaj.dto.converter.ProjectDtoConverter;
import com.crispy.crispy_be_challenge_ejona_aliaj.service.ProjectService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Objects;

@RestController
@RequestMapping("/api/1.0/projects")
public class ProjectController {

    private final ProjectDtoConverter projectDtoConverter;

    private final ProjectService projectService;

    public ProjectController(ProjectDtoConverter projectDtoConverter, ProjectService projectService) {
        this.projectDtoConverter = projectDtoConverter;
        this.projectService = projectService;
    }

    @GetMapping
    public ResponseEntity<Page<ProjectDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(projectService.findAll(pageable));
    }

    @ValidateInputRequest
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createProject(@RequestBody ProjectRequest newProjectRequest, UriComponentsBuilder ucb) {
        ProjectDTO projectDTO = projectDtoConverter.convert(newProjectRequest);
        ProjectDTO savedProject = projectService.createProject(projectDTO);
        URI locationOfNewProject = ucb
                .path("1.0/projects/{id}")
                .buildAndExpand(savedProject.getId())
                .toUri();
        return ResponseEntity.created(locationOfNewProject).build();
    }

    @ValidateInputRequest
    @PutMapping(path = "/{projectId}", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateProject(@PathVariable Long projectId, @RequestBody ProjectRequest projectRequest) {
        ProjectDTO projectDTO = projectDtoConverter.convert(projectRequest);
        if (Objects.nonNull(projectDTO)) {
            projectDTO.setId(projectId);
        }
        ProjectDTO updatedProject = projectService.updateProject(projectDTO);

        if (Objects.nonNull(updatedProject)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
