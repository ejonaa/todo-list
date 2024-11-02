package com.crispy.crispy_be_challenge_ejona_aliaj.service.impl;

import com.crispy.crispy_be_challenge_ejona_aliaj.controller.response.ProjectResponse;
import com.crispy.crispy_be_challenge_ejona_aliaj.converter.ProjectMapper;
import com.crispy.crispy_be_challenge_ejona_aliaj.dto.ProjectDTO;
import com.crispy.crispy_be_challenge_ejona_aliaj.entity.ProjectEntity;
import com.crispy.crispy_be_challenge_ejona_aliaj.entity.UserEntity;
import com.crispy.crispy_be_challenge_ejona_aliaj.exception.AuthorizationException;
import com.crispy.crispy_be_challenge_ejona_aliaj.repository.ProjectRepository;
import com.crispy.crispy_be_challenge_ejona_aliaj.repository.UserRepository;
import com.crispy.crispy_be_challenge_ejona_aliaj.security.SecurityUtil;
import com.crispy.crispy_be_challenge_ejona_aliaj.service.ProjectService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    private final ProjectMapper projectMapper;

    private final UserRepository userRepository;


    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
        this.userRepository = userRepository;
    }

    @Override
    public Page<ProjectResponse> findAll(Pageable pageable) {
        return projectRepository.findByUserIdOrderById(getUserId(), PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSortOr(Sort.by(Sort.Direction.ASC, "title"))
        )).map(projectMapper::toResponse);
    }

    @Override
    public ProjectDTO createProject(ProjectDTO projectDTO) {
        ProjectEntity project = projectMapper.fromDto(projectDTO);
        project.setUserId(getUserId());
        return projectMapper.toDto(projectRepository.save(project));
    }

    @Override
    public ProjectDTO updateProject(ProjectDTO projectDTO) {
        return projectRepository.findByIdAndUserId(projectDTO.getId(), getUserId()).map(projectEntity -> {
            projectEntity.setTitle(projectDTO.getTitle());
            return projectMapper.toDto(projectRepository.save(projectEntity));
        }).orElse(null);
    }

    @Transactional
    @Override
    public boolean deleteProject(Long projectId) {
        Long deleted = projectRepository.deleteByIdAndUserId(projectId, getUserId());
        return !deleted.equals(0L);
    }

    private Long getUserId() {
        UserEntity user = userRepository.findByLogin(SecurityUtil.getUserName()).orElseThrow(() -> new AuthorizationException("Token Not Provided"));
        return user.getId();
    }

}
