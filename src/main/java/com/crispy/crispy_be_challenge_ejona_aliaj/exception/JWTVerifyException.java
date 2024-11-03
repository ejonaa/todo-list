package com.crispy.crispy_be_challenge_ejona_aliaj.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class JWTVerifyException extends ApiException {

    private static final long serialVersionUID = 1L;

    private static final String DEFAULT_TITLE = "JWT Verification Error";

    public JWTVerifyException(Throwable cause) {
        super(DEFAULT_TITLE, cause);
    }

    public JWTVerifyException(String message, Throwable cause) {
        super(message, cause);
    }
}
