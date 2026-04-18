package io.github.arkaman.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import io.github.arkaman.taskmanager.domain.entity.AppUser;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<AppUser, UUID> {
    Optional<AppUser> findByUsername(String username);
    Optional<AppUser> findByEmailIgnoreCase(String email);

    String username(String username);
}
