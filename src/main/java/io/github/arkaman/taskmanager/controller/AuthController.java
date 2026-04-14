package io.github.arkaman.taskmanager.controller;

import io.github.arkaman.taskmanager.domain.dto.RegisterRequest;
import io.github.arkaman.taskmanager.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@Valid @RequestBody RegisterRequest request) {

        service.register(request);

        return ResponseEntity.ok(Map.of("message", "User registered successfully"));
    }
}