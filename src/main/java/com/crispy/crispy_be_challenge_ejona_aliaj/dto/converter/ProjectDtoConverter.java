package com.crispy.crispy_be_challenge_ejona_aliaj.dto.converter;

import com.crispy.crispy_be_challenge_ejona_aliaj.controller.request.ProjectRequest;
import com.crispy.crispy_be_challenge_ejona_aliaj.dto.ProjectDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProjectDtoConverter implements Converter<ProjectRequest, ProjectDTO> {

    @Override
    public ProjectDTO convert(ProjectRequest source) {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setTitle(source.getTitle());
        projectDTO.setUserId(source.getUserId());
        return projectDTO;
    }
}
