package io.github.arkaman.taskmanager.service;

import io.github.arkaman.taskmanager.domain.dto.CreateTaskRequestDto;
import io.github.arkaman.taskmanager.domain.dto.UpdateTaskRequestDto;
import io.github.arkaman.taskmanager.domain.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;


public interface TaskService {

    Task createTask(CreateTaskRequestDto dto);

    Page<Task> listTasks(Pageable pageable);

    Task getTask(UUID taskId);

    Task updateTask(UUID taskId, UpdateTaskRequestDto dto);

    void deleteTask(UUID taskId);
}