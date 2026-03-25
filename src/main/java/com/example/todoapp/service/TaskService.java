package com.example.todoapp.service;

import com.example.todoapp.dto.CreateTaskRequestDto;
import com.example.todoapp.dto.TaskDto;
import com.example.todoapp.dto.UpdateTaskRequestDto;
import com.example.todoapp.entity.Priority;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface TaskService {
    Page<TaskDto> getAllTasks(Pageable pageable, String search, Priority priority, String tag);
    TaskDto getTaskById(UUID id);
    TaskDto createTask(CreateTaskRequestDto taskDto);
    TaskDto updateTask(UUID id, UpdateTaskRequestDto taskDto);
    void deleteTask(UUID id);

    void importTasksFromCsv(MultipartFile file);
}
