package com.example.todoapp.dto;

import com.example.todoapp.entity.Priority;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record TaskDto(
    UUID id,
    String title,
    String description,
    Priority priority,
    LocalDate dueDate,
    boolean completed,
    Set<String> tags,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}
