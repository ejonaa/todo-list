package com.crispy.crispy_be_challenge_ejona_aliaj.exception;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ValidationMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private String reason;

    public ValidationMessage() {
    }

    public ValidationMessage(String name, String reason) {
        this.name = name;
        this.reason = reason;
    }

}
