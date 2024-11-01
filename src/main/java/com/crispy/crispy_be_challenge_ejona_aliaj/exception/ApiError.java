package com.crispy.crispy_be_challenge_ejona_aliaj.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiError {

    private int status;

    private String instance;

    private String title;

    private String detail;

    public ApiError() {
    }

    public ApiError(int status, String title, String url) {
        this.status = status;
        this.title = title;
        this.instance = url;
    }

}
