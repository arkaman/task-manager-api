package io.github.arkaman.taskmanager.domain.dto;

import io.github.arkaman.taskmanager.domain.entity.TaskPriority;
import io.github.arkaman.taskmanager.domain.entity.TaskStatus;

import java.time.LocalDate;
import java.util.UUID;

public record TaskDto(
        UUID id,
        String title,
        String description,
        LocalDate dueDate,
        TaskPriority priority,
        TaskStatus status
) {
}
