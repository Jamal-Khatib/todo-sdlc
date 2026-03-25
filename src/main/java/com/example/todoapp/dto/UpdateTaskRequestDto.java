package com.example.todoapp.dto;

import com.example.todoapp.entity.Priority;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.Set;

public record UpdateTaskRequestDto(
    @Size(min = 1, max = 200)
    String title,

    @Size(max = 1000)
    String description,

    Priority priority,

    LocalDate dueDate,

    Boolean completed,

    Set<@Size(max = 30) String> tags
) {}
