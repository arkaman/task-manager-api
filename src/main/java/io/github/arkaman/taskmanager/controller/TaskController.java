package io.github.arkaman.taskmanager.controller;

import io.github.arkaman.taskmanager.domain.dto.CreateTaskRequestDto;
import io.github.arkaman.taskmanager.domain.dto.TaskDto;
import io.github.arkaman.taskmanager.domain.dto.UpdateTaskRequestDto;
import io.github.arkaman.taskmanager.domain.entity.Task;
import io.github.arkaman.taskmanager.mapper.TaskMapper;
import io.github.arkaman.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    public TaskController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @PostMapping
    public ResponseEntity<TaskDto> createTask(
            @Valid @RequestBody CreateTaskRequestDto dto
    ) {
        Task task = taskService.createTask(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(taskMapper.toDto(task));
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> listTasks() {
        List<TaskDto> tasks = taskService.listTasks()
                .stream()
                .map(taskMapper::toDto)
                .toList();

        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDto> getTask(@PathVariable UUID taskId) {
        Task task = taskService.getTask(taskId);
        return ResponseEntity.ok(taskMapper.toDto(task));
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskDto> updateTask(
            @PathVariable UUID taskId,
            @Valid @RequestBody UpdateTaskRequestDto dto
    ) {
        Task task = taskService.updateTask(taskId, dto);
        return ResponseEntity.ok(taskMapper.toDto(task));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable UUID taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }
}