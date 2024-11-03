package com.crispy.crispy_be_challenge_ejona_aliaj.service;

import com.crispy.crispy_be_challenge_ejona_aliaj.dto.UserDTO;

public interface UserService {

    boolean existsUsername(String login);

    void createUser(UserDTO user, String password);

}
