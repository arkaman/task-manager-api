package io.github.arkaman.taskmanager.service;

import io.github.arkaman.taskmanager.domain.dto.RegisterRequest;
import io.github.arkaman.taskmanager.domain.entity.AppUser;
import io.github.arkaman.taskmanager.domain.entity.Role;
import io.github.arkaman.taskmanager.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public AuthService(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    @Transactional
    public void register(RegisterRequest request) {

        String email = request.getEmail().trim().toLowerCase();
        String username = request.getUsername().trim();
        String password = request.getPassword();

        if (repo.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username already taken");
        }
        if (repo.findByEmailIgnoreCase(email).isPresent()) {
            throw new IllegalArgumentException("Email already registered");
        }

        AppUser user = AppUser.builder()
                .username(username)
                .email(email)
                .password(encoder.encode(password))
                .role(Role.USER)
                .build();

        repo.save(user);
    }
}