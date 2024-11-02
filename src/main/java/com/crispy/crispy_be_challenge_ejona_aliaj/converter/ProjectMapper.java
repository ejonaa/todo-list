package com.crispy.crispy_be_challenge_ejona_aliaj.converter;

import com.crispy.crispy_be_challenge_ejona_aliaj.controller.response.ProjectResponse;
import com.crispy.crispy_be_challenge_ejona_aliaj.dto.ProjectDTO;
import com.crispy.crispy_be_challenge_ejona_aliaj.entity.ProjectEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProjectMapper {

    private final ModelMapper modelMapper;

    public ProjectMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ProjectDTO toDto(ProjectEntity product) {
        return modelMapper.map(product, ProjectDTO.class);
    }

    public List<ProjectDTO> toDtoList(List<ProjectEntity> products) {
        return products.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public ProjectEntity fromDto(ProjectDTO productDto) {
        return modelMapper.map(productDto, ProjectEntity.class);
    }

    public ProjectResponse toResponse(ProjectEntity product) {
        return modelMapper.map(product, ProjectResponse.class);
    }

}
