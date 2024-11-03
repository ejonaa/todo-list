package com.crispy.crispy_be_challenge_ejona_aliaj.controller.request;

import com.crispy.crispy_be_challenge_ejona_aliaj.controller.request.validation.Validatable;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class TaskRequest implements Validatable, Serializable {

    @NotBlank
    @Size(max = 50)
    private String name;

    @Size(max = 3000)
    private String note;

    private LocalDate dueDate;

    private Boolean completed;

    private Boolean important;

}
