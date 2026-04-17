package io.github.arkaman.taskmanager.controller;

import io.github.arkaman.taskmanager.domain.dto.*;
import io.github.arkaman.taskmanager.domain.entity.AppUser;
import io.github.arkaman.taskmanager.service.AuthService;
import io.github.arkaman.taskmanager.security.SecurityService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService service;
    private final SecurityService securityService;

    public AuthController(AuthService service, SecurityService securityService) {
        this.service = service;
        this.securityService = securityService;
    }

    @PostMapping("/register")
    public ResponseEntity<MessageResponse> register(@Valid @RequestBody RegisterRequest request) {

        service.register(request);

        return ResponseEntity.ok(new MessageResponse("User registered successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(service.login(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestBody RefreshRequest request) {
        return ResponseEntity.ok(
                service.refresh(request.refreshToken())
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<MessageResponse> logout() {

        AppUser user = securityService.getCurrentUser();

        service.logout(user);

        return ResponseEntity.ok(new MessageResponse("Logged out successfully"));
    }
}