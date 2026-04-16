package io.github.arkaman.taskmanager.repository;

import io.github.arkaman.taskmanager.domain.entity.AppUser;
import io.github.arkaman.taskmanager.domain.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID>,
        JpaSpecificationExecutor<Task> {
    Page<Task> findByUser(AppUser user, Pageable pageable);
    Optional<Task> findByIdAndUser(UUID id, AppUser user);
}