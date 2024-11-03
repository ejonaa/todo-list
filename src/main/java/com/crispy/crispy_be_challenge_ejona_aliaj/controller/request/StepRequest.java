package com.crispy.crispy_be_challenge_ejona_aliaj.controller.request;

import com.crispy.crispy_be_challenge_ejona_aliaj.controller.request.validation.Validatable;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class StepRequest implements Validatable, Serializable {

    @NotBlank
    private String name;

}
