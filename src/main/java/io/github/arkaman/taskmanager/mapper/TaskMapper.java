package io.github.arkaman.taskmanager.mapper;

import io.github.arkaman.taskmanager.domain.dto.TaskDto;
import io.github.arkaman.taskmanager.domain.entity.Task;

public interface TaskMapper {

    TaskDto toDto(Task task);
}