package com.crispy.crispy_be_challenge_ejona_aliaj.dto.converter;

import com.crispy.crispy_be_challenge_ejona_aliaj.controller.request.security.NewUserRequest;
import com.crispy.crispy_be_challenge_ejona_aliaj.dto.UserDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConverter implements Converter<NewUserRequest, UserDTO> {
    @Override
    public UserDTO convert(NewUserRequest source) {
        UserDTO userDTO = new UserDTO();
        userDTO.setLogin(source.getLogin());
        userDTO.setFirstName(source.getFirstName());
        userDTO.setLastName(source.getLastName());
        return userDTO;
    }
}
