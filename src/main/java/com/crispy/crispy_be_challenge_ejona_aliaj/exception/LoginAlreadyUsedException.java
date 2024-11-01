package com.crispy.crispy_be_challenge_ejona_aliaj.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class LoginAlreadyUsedException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public LoginAlreadyUsedException(String message) {
        super(message);
    }
}
