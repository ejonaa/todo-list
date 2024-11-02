package com.crispy.crispy_be_challenge_ejona_aliaj.converter;

import com.crispy.crispy_be_challenge_ejona_aliaj.controller.request.StepRequest;
import com.crispy.crispy_be_challenge_ejona_aliaj.dto.StepDTO;
import com.crispy.crispy_be_challenge_ejona_aliaj.entity.StepEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class StepMapper {

    private final ModelMapper modelMapper;

    public StepMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public StepDTO toDto(StepRequest step) {
        return modelMapper.map(step, StepDTO.class);
    }

    public StepEntity fromDto(StepDTO stepDTO) {
        return modelMapper.map(stepDTO, StepEntity.class);
    }

    public StepDTO toDto(StepEntity step) {
        return modelMapper.map(step, StepDTO.class);
    }
}
