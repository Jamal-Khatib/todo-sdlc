package com.example.todoapp.dto;

import com.example.todoapp.entity.Priority;
import com.example.todoapp.entity.Task;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class TaskMapperTest {

    private final TaskMapper taskMapper = new TaskMapper();

    @Test
    void toDto_whenTaskIsNotNull_shouldMapCorrectly() {
        Task task = new Task();
        task.setId(UUID.randomUUID());
        task.setTitle("Test Title");
        task.setDescription("Test Description");
        task.setPriority(Priority.MEDIUM);
        task.setDueDate(LocalDate.now());
        task.setCompleted(true);
        task.setTags(Set.of("java", "test"));
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());

        TaskDto dto = taskMapper.toDto(task);

        assertThat(dto.id()).isEqualTo(task.getId());
        assertThat(dto.title()).isEqualTo(task.getTitle());
        assertThat(dto.description()).isEqualTo(task.getDescription());
        assertThat(dto.priority()).isEqualTo(task.getPriority());
        assertThat(dto.dueDate()).isEqualTo(task.getDueDate());
        assertThat(dto.completed()).isEqualTo(task.isCompleted());
        assertThat(dto.tags()).isEqualTo(task.getTags());
        assertThat(dto.createdAt()).isEqualTo(task.getCreatedAt());
        assertThat(dto.updatedAt()).isEqualTo(task.getUpdatedAt());
    }

    @Test
    void toDto_whenTaskIsNull_shouldReturnNull() {
        assertThat(taskMapper.toDto(null)).isNull();
    }

    @Test
    void toEntity_whenDtoIsNotNull_shouldMapCorrectly() {
        CreateTaskRequestDto dto = new CreateTaskRequestDto("New Task", "Description", Priority.LOW, LocalDate.now(), Set.of("dto"));

        Task task = taskMapper.toEntity(dto);

        assertThat(task.getTitle()).isEqualTo(dto.title());
        assertThat(task.getDescription()).isEqualTo(dto.description());
        assertThat(task.getPriority()).isEqualTo(dto.priority());
        assertThat(task.getDueDate()).isEqualTo(dto.dueDate());
        assertThat(task.getTags()).isEqualTo(dto.tags());
    }

    @Test
    void toEntity_whenDtoIsNull_shouldReturnNull() {
        assertThat(taskMapper.toEntity(null)).isNull();
    }
}
