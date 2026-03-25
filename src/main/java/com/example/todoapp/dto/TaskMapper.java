package com.example.todoapp.dto;

import com.example.todoapp.entity.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public TaskDto toDto(Task task) {
        if (task == null) {
            return null;
        }
        return new TaskDto(
            task.getId(),
            task.getTitle(),
            task.getDescription(),
            task.getPriority(),
            task.getDueDate(),
            task.isCompleted(),
            task.getTags(),
            task.getCreatedAt(),
            task.getUpdatedAt()
        );
    }

    public Task toEntity(CreateTaskRequestDto dto) {
        if (dto == null) {
            return null;
        }
        Task task = new Task();
        task.setTitle(dto.title());
        task.setDescription(dto.description());
        task.setPriority(dto.priority());
        task.setDueDate(dto.dueDate());
        task.setTags(dto.tags());
        return task;
    }
}
