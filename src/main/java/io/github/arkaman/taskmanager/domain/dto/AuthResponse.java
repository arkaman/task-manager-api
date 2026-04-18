package io.github.arkaman.taskmanager.domain.dto;

public record AuthResponse(
        String accessToken,
        String refreshToken
) {
}
