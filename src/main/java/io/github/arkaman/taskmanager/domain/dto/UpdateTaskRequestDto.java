package io.github.arkaman.taskmanager.domain.dto;

import io.github.arkaman.taskmanager.domain.entity.TaskPriority;
import io.github.arkaman.taskmanager.domain.entity.TaskStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

public record UpdateTaskRequestDto(

        @NotBlank(message = TITLE_REQUIRED_MESSAGE)
        @Length(max = 255, message = TITLE_LENGTH_MESSAGE)
        String title,

        @Length(max = 1000, message = DESCRIPTION_LENGTH_MESSAGE)
        String description,

        @FutureOrPresent(message = DUE_DATE_VALIDATION_MESSAGE)
        LocalDate dueDate,

        @NotNull(message = PRIORITY_REQUIRED_MESSAGE)
        TaskPriority priority,

        @NotNull(message = STATUS_REQUIRED_MESSAGE)
        TaskStatus status

) {

    private static final String TITLE_REQUIRED_MESSAGE =
            "Title is required.";

    private static final String TITLE_LENGTH_MESSAGE =
            "Title must not exceed 255 characters.";

    private static final String DESCRIPTION_LENGTH_MESSAGE =
            "Description must not exceed 1000 characters.";

    private static final String DUE_DATE_VALIDATION_MESSAGE =
            "Due date must be today or a future date.";

    private static final String PRIORITY_REQUIRED_MESSAGE =
            "Priority is required.";

    private static final String STATUS_REQUIRED_MESSAGE =
            "Status is required.";
}