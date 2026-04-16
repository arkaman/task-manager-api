package io.github.arkaman.taskmanager.service;

import io.github.arkaman.taskmanager.domain.dto.LoginRequest;
import io.github.arkaman.taskmanager.domain.dto.RegisterRequest;
import io.github.arkaman.taskmanager.domain.entity.AppUser;
import io.github.arkaman.taskmanager.domain.entity.Role;
import io.github.arkaman.taskmanager.repository.UserRepository;
import io.github.arkaman.taskmanager.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    public AuthService(UserRepository repo, PasswordEncoder encoder, JwtService jwtService) {
        this.repo = repo;
        this.encoder = encoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public void register(RegisterRequest request) {

        String email = request.email().trim().toLowerCase();
        String username = request.username().trim();
        String password = request.password();

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

    public String login(LoginRequest request) {
        String email = request.email().trim().toLowerCase();
        String password = request.password();

        AppUser user = repo.findByEmailIgnoreCase(email).orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        if (!encoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        return jwtService.generateToken(user.getEmail());
    }
}