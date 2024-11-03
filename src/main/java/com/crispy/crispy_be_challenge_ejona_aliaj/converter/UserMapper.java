package com.crispy.crispy_be_challenge_ejona_aliaj.converter;

import com.crispy.crispy_be_challenge_ejona_aliaj.controller.request.security.NewUserRequest;
import com.crispy.crispy_be_challenge_ejona_aliaj.dto.UserDTO;
import com.crispy.crispy_be_challenge_ejona_aliaj.entity.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    
    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserDTO toDto(UserEntity user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public UserEntity fromDto(UserDTO userDTO) {
        return modelMapper.map(userDTO, UserEntity.class);
    }

    public UserDTO toDto(NewUserRequest user) {
        return modelMapper.map(user, UserDTO.class);
    }

}
