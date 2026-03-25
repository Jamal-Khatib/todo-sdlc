package com.example.todoapp.service;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.example.todoapp.dto.CreateTaskRequestDto;
import com.example.todoapp.dto.TaskDto;
import com.example.todoapp.dto.TaskMapper;
import com.example.todoapp.dto.UpdateTaskRequestDto;
import com.example.todoapp.entity.Task;
import com.example.todoapp.exception.TaskNotFoundException;
import com.example.todoapp.repository.TaskRepository;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @Mock
    private TaskSpecification taskSpecification;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    void getAllTasks_shouldReturnPageOfTasks() {
        Page<Task> page = new PageImpl<>(Collections.singletonList(new Task()));
        when(taskRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);
        when(taskMapper.toDto(any(Task.class))).thenReturn(mock(TaskDto.class));

        Page<TaskDto> result = taskService.getAllTasks(Pageable.unpaged(), null, null, null);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
    }

    @Test
    void getTaskById_whenTaskExists_shouldReturnTask() {
        UUID taskId = UUID.randomUUID();
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(new Task()));
        when(taskMapper.toDto(any(Task.class))).thenReturn(mock(TaskDto.class));

        TaskDto result = taskService.getTaskById(taskId);

        assertThat(result).isNotNull();
    }

    @Test
    void getTaskById_whenTaskNotFound_shouldThrowException() {
        UUID taskId = UUID.randomUUID();
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.getTaskById(taskId));
    }

    @Test
    void createTask_shouldSaveAndReturnTask() {
        CreateTaskRequestDto requestDto = mock(CreateTaskRequestDto.class);
        Task task = new Task();
        Task savedTask = new Task();
        TaskDto taskDto = mock(TaskDto.class);

        when(taskMapper.toEntity(requestDto)).thenReturn(task);
        when(taskRepository.save(task)).thenReturn(savedTask);
        when(taskMapper.toDto(savedTask)).thenReturn(taskDto);

        TaskDto result = taskService.createTask(requestDto);

        assertThat(result).isEqualTo(taskDto);
        verify(taskRepository).save(task);
    }

    @Test
    void updateTask_whenTaskExists_shouldUpdateAndReturnTask() {
        UUID taskId = UUID.randomUUID();
        UpdateTaskRequestDto requestDto = new UpdateTaskRequestDto("Updated Title", null, null, null, null, null);
        Task existingTask = new Task();
        existingTask.setTitle("Original Title");

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(taskMapper.toDto(any(Task.class))).thenReturn(mock(TaskDto.class));

        taskService.updateTask(taskId, requestDto);

        ArgumentCaptor<Task> taskCaptor = ArgumentCaptor.forClass(Task.class);
        verify(taskRepository).save(taskCaptor.capture());
        assertThat(taskCaptor.getValue().getTitle()).isEqualTo("Updated Title");
    }

    @Test
    void deleteTask_whenTaskExists_shouldDeleteTask() {
        UUID taskId = UUID.randomUUID();
        when(taskRepository.existsById(taskId)).thenReturn(true);
        doNothing().when(taskRepository).deleteById(taskId);

        taskService.deleteTask(taskId);

        verify(taskRepository).deleteById(taskId);
    }

    @Test
    void deleteTask_whenTaskNotFound_shouldThrowException() {
        UUID taskId = UUID.randomUUID();
        when(taskRepository.existsById(taskId)).thenReturn(false);

        assertThrows(TaskNotFoundException.class, () -> taskService.deleteTask(taskId));
    }
}
