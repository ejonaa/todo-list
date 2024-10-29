package com.crispy.crispy_be_challenge_ejona_aliaj.service.impl;

import com.crispy.crispy_be_challenge_ejona_aliaj.converter.ProjectMapper;
import com.crispy.crispy_be_challenge_ejona_aliaj.dto.ProjectDTO;
import com.crispy.crispy_be_challenge_ejona_aliaj.entity.ProjectEntity;
import com.crispy.crispy_be_challenge_ejona_aliaj.repository.ProjectRepository;
import com.crispy.crispy_be_challenge_ejona_aliaj.service.ProjectService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    private final ProjectMapper projectMapper;


    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }

    @Override
    public Page<ProjectDTO> findAll(Pageable pageable) {
        return projectRepository.findAll(PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSortOr(Sort.by(Sort.Direction.ASC, "title"))
        )).map(projectMapper::toDto);
    }

    @Override
    public ProjectDTO createProject(ProjectDTO projectDTO) {
        ProjectEntity project = projectMapper.fromDto(projectDTO);
        project.setCreatedBy(1L);
        return projectMapper.toDto(projectRepository.save(project));
    }
}
