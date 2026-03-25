package com.example.todoapp.controller;

import com.example.todoapp.dto.CreateTaskRequestDto;
import com.example.todoapp.dto.TaskDto;
import com.example.todoapp.dto.UpdateTaskRequestDto;
import com.example.todoapp.service.TaskService;
import jakarta.validation.Valid;
import com.example.todoapp.entity.Priority;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public Page<TaskDto> getAllTasks(Pageable pageable,
                                     @RequestParam(required = false) String search,
                                     @RequestParam(required = false) Priority priority,
                                     @RequestParam(required = false) String tag) {
        return taskService.getAllTasks(pageable, search, priority, tag);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable UUID id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @PostMapping
    public ResponseEntity<TaskDto> createTask(@Valid @RequestBody CreateTaskRequestDto taskDto) {
        TaskDto createdTask = taskService.createTask(taskDto);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable UUID id, @Valid @RequestBody UpdateTaskRequestDto taskDto) {
        return ResponseEntity.ok(taskService.updateTask(id, taskDto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable UUID id) {
        taskService.deleteTask(id);
    }
}
