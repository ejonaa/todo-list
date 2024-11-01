package com.crispy.crispy_be_challenge_ejona_aliaj.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDTO implements Serializable {

    private String login;

    private String firstName;

    private String lastName;

}
