package com.crispy.crispy_be_challenge_ejona_aliaj.controller.request.security;

import com.crispy.crispy_be_challenge_ejona_aliaj.controller.request.validation.Validatable;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class NewUserRequest implements Validatable, Serializable {

    @NotBlank
    private String login;

    @NotBlank
    private String password;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

}
