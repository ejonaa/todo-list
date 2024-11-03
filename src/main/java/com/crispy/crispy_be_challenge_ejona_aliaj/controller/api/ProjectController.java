package com.crispy.crispy_be_challenge_ejona_aliaj.controller.api;

import com.crispy.crispy_be_challenge_ejona_aliaj.controller.request.ProjectRequest;
import com.crispy.crispy_be_challenge_ejona_aliaj.controller.request.validation.ValidateInputRequest;
import com.crispy.crispy_be_challenge_ejona_aliaj.controller.response.ProjectResponse;
import com.crispy.crispy_be_challenge_ejona_aliaj.dto.ProjectDTO;
import com.crispy.crispy_be_challenge_ejona_aliaj.dto.converter.ProjectDtoConverter;
import com.crispy.crispy_be_challenge_ejona_aliaj.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

/**
 * The type Project controller.
 *
 * @author ejoaliaj
 */
@RestController
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "ProjectController", description = "API to manage projects")
@RequestMapping("/api/1.0/projects")
public class ProjectController {

    private final ProjectDtoConverter projectDtoConverter;

    private final ProjectService projectService;

    public ProjectController(ProjectDtoConverter projectDtoConverter, ProjectService projectService) {
        this.projectDtoConverter = projectDtoConverter;
        this.projectService = projectService;
    }

    /**
     * Retrieve the list of projects that are created by the logged-in user.
     *
     * @param pageable paging options
     * @return the list of projects created by user
     */
    @Operation(summary = "Retrieve the list of projects that are created by the logged-in user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval")})
    @GetMapping
    public ResponseEntity<Page<ProjectResponse>> findAll(Pageable pageable) {
        return ResponseEntity.ok(projectService.findAll(pageable));
    }

    /**
     * Create a project
     *
     * @param newProjectRequest project details
     */
    @Operation(summary = "Create a project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful creation"),
            @ApiResponse(responseCode = "400", description = "Validation errors")})
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

    /**
     * Update a given project.
     *
     * @param projectId      id of project to update
     * @param projectRequest project data
     */
    @Operation(summary = "Update a given project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful update"),
            @ApiResponse(responseCode = "400", description = "Validation errors"),
            @ApiResponse(responseCode = "404", description = "Project not found")})
    @SecurityRequirement(name = "Bearer Authentication")
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

    /**
     * Delete a project and all its related data.
     *
     * @param projectId id of project to delete
     */
    @Operation(summary = "Delete a project and all its related data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful deletion"),
            @ApiResponse(responseCode = "404", description = "Project not found")})
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long projectId) {
        boolean deleted = projectService.deleteProject(projectId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
