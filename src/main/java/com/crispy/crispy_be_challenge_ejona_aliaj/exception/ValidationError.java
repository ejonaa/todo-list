package com.crispy.crispy_be_challenge_ejona_aliaj.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ValidationError extends ApiError {

    @JsonProperty("invalid-params")
    private List<ValidationMessage> invalidParams;

    public ValidationError() {
    }
}
