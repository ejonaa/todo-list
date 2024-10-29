package com.crispy.crispy_be_challenge_ejona_aliaj.controller.api;

import com.crispy.crispy_be_challenge_ejona_aliaj.controller.request.ProjectRequest;
import com.crispy.crispy_be_challenge_ejona_aliaj.dto.ProjectDTO;
import com.crispy.crispy_be_challenge_ejona_aliaj.dto.converter.ProjectDtoConverter;
import com.crispy.crispy_be_challenge_ejona_aliaj.service.ProjectService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/1.0/projects")
public class ProjectController {

    private final ProjectDtoConverter projectDtoConverter;

    private final ProjectService projectService;

    public ProjectController(ProjectDtoConverter projectDtoConverter, ProjectService projectService) {
        this.projectDtoConverter = projectDtoConverter;
        this.projectService = projectService;
    }

    @GetMapping
    private ResponseEntity<Page<ProjectDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(projectService.findAll(pageable));
    }

    @PostMapping
    private ResponseEntity<Void> createProject(@RequestBody ProjectRequest newProjectRequest, UriComponentsBuilder ucb) {
        ProjectDTO projectDTO = projectDtoConverter.convert(newProjectRequest);
        ProjectDTO savedProject = projectService.createProject(projectDTO);
        URI locationOfNewProject = ucb
                .path("1.0/projects/{id}")
                .buildAndExpand(savedProject.getId())
                .toUri();
        return ResponseEntity.created(locationOfNewProject).build();
    }
}
