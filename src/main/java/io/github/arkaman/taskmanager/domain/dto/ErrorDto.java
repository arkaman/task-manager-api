package io.github.arkaman.taskmanager.domain.dto;

public record ErrorDto(
        String message,
        int status,
        long timestamp
) {}
