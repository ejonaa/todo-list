package com.crispy.crispy_be_challenge_ejona_aliaj.dto.converter;

import com.crispy.crispy_be_challenge_ejona_aliaj.controller.request.TaskRequest;
import com.crispy.crispy_be_challenge_ejona_aliaj.dto.TaskDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TaskDtoConverter implements Converter<TaskRequest, TaskDTO> {

    @Override
    public TaskDTO convert(TaskRequest source) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setName(source.getName());
        taskDTO.setNote(source.getNote());
        taskDTO.setDueDate(source.getDueDate());
        taskDTO.setImportant(source.getImportant());
        taskDTO.setCompleted(source.getCompleted());
        return taskDTO;
    }
}
