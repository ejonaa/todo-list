package com.crispy.crispy_be_challenge_ejona_aliaj.controller.request.security;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class NewUserRequest {

    @NotBlank
    private String login;

    @NotBlank
    private String password;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

}
