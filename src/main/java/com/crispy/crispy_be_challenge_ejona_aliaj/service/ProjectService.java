package com.crispy.crispy_be_challenge_ejona_aliaj.service;

import com.crispy.crispy_be_challenge_ejona_aliaj.dto.ProjectDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectService {

    Page<ProjectDTO> findAll(Pageable pageable);

    ProjectDTO createProject(ProjectDTO projectDTO);

    ProjectDTO updateProject(ProjectDTO projectDTO);

    boolean deleteProject(Long projectId);
}
