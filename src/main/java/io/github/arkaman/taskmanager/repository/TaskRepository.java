package io.github.arkaman.taskmanager.repository;

import io.github.arkaman.taskmanager.domain.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
}
