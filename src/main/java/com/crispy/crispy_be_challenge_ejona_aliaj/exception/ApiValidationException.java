package com.crispy.crispy_be_challenge_ejona_aliaj.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ApiValidationException extends ApiException {
    private static final long serialVersionUID = 1L;
    private static final String DEFAULT_TITLE = "Validation Error";

    private final List<ValidationMessage> invalidParams;

    public ApiValidationException(List<ValidationMessage> invalidParams) {
        super(DEFAULT_TITLE);
        this.invalidParams = invalidParams;
    }

    public ApiValidationException(String message, List<ValidationMessage> invalidParams) {
        super(message);
        this.invalidParams = invalidParams;
    }

}
