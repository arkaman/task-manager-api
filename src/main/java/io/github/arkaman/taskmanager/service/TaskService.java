package io.github.arkaman.taskmanager.service;

import io.github.arkaman.taskmanager.domain.dto.CreateTaskRequestDto;
import io.github.arkaman.taskmanager.domain.dto.UpdateTaskRequestDto;
import io.github.arkaman.taskmanager.domain.entity.Task;

import java.util.List;
import java.util.UUID;

public interface TaskService {

    Task createTask(CreateTaskRequestDto dto);

    List<Task> listTasks();

    Task getTask(UUID taskId);

    Task updateTask(UUID taskId, UpdateTaskRequestDto dto);

    void deleteTask(UUID taskId);
}