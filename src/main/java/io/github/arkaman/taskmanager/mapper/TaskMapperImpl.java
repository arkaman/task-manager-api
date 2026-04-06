package io.github.arkaman.taskmanager.mapper;

import io.github.arkaman.taskmanager.domain.dto.TaskDto;
import io.github.arkaman.taskmanager.domain.entity.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapperImpl implements TaskMapper {

    @Override
    public TaskDto toDto(Task task) {
        return new TaskDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                task.getPriority(),
                task.getStatus()
        );
    }
}