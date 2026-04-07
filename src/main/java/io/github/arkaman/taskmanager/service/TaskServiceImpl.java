package io.github.arkaman.taskmanager.service;

import io.github.arkaman.taskmanager.domain.dto.CreateTaskRequestDto;
import io.github.arkaman.taskmanager.domain.dto.UpdateTaskRequestDto;
import io.github.arkaman.taskmanager.domain.entity.Task;
import io.github.arkaman.taskmanager.domain.entity.TaskPriority;
import io.github.arkaman.taskmanager.domain.entity.TaskStatus;
import io.github.arkaman.taskmanager.exception.TaskNotFoundException;
import io.github.arkaman.taskmanager.repository.TaskRepository;
import io.github.arkaman.taskmanager.repository.spec.TaskSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task createTask(CreateTaskRequestDto dto) {
        Task task = Task.builder()
                .title(dto.title())
                .description(dto.description())
                .dueDate(dto.dueDate())
                .priority(dto.priority())
                .build();

        return taskRepository.save(task);
    }

    @Override
    public Page<Task> listTasks(
            TaskStatus status,
            TaskPriority priority,
            String keyword,
            Pageable pageable
    ) {

        Specification<Task> spec = Specification
                .where(TaskSpecification.hasStatus(status))
                .and(TaskSpecification.hasPriority(priority))
                .and(TaskSpecification.titleOrDescriptionContains(keyword));

        return taskRepository.findAll(spec, pageable);
    }

    @Override
    public Task getTask(UUID taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));
    }

    @Override
    public Task updateTask(UUID taskId, UpdateTaskRequestDto dto) {
        Task task = getTask(taskId);

        task.updateDetails(
                dto.title(),
                dto.description(),
                dto.dueDate(),
                dto.priority()
        );

        task.updateStatus(dto.status());

        return taskRepository.save(task);
    }

    @Override
    public void deleteTask(UUID taskId) {
        if (!taskRepository.existsById(taskId)) {
            throw new TaskNotFoundException(taskId);
        }
        taskRepository.deleteById(taskId);
    }
}