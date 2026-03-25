package com.example.todoapp.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.todoapp.dto.CreateTaskRequestDto;
import com.example.todoapp.dto.TaskDto;
import com.example.todoapp.dto.UpdateTaskRequestDto;
import com.example.todoapp.entity.Priority;
import com.example.todoapp.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(TaskController.class)
@ActiveProfiles("test")
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllTasks_shouldReturnTasksPage() throws Exception {
        TaskDto taskDto = new TaskDto(UUID.randomUUID(), "Test Task", "Description", Priority.LOW, null, false, Collections.emptySet(), LocalDateTime.now(), LocalDateTime.now());
        given(taskService.getAllTasks(any(), any(), any(), any())).willReturn(new PageImpl<>(Collections.singletonList(taskDto)));

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    void getTaskById_whenTaskExists_shouldReturnTask() throws Exception {
        UUID taskId = UUID.randomUUID();
        TaskDto taskDto = new TaskDto(taskId, "Test Task", "Description", Priority.LOW, null, false, Collections.emptySet(), LocalDateTime.now(), LocalDateTime.now());
        given(taskService.getTaskById(taskId)).willReturn(taskDto);

        mockMvc.perform(get("/api/tasks/{id}", taskId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(taskId.toString()));
    }

    @Test
    void createTask_withValidData_shouldReturnCreatedTask() throws Exception {
        CreateTaskRequestDto requestDto = new CreateTaskRequestDto("New Task", "Description", Priority.HIGH, LocalDate.now(), Set.of("test"));
        TaskDto responseDto = new TaskDto(UUID.randomUUID(), "New Task", "Description", Priority.HIGH, LocalDate.now(), false, Set.of("test"), LocalDateTime.now(), LocalDateTime.now());
        given(taskService.createTask(any())).willReturn(responseDto);

        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("New Task"));
    }

    @Test
    void updateTask_withValidData_shouldReturnUpdatedTask() throws Exception {
        UUID taskId = UUID.randomUUID();
        UpdateTaskRequestDto requestDto = new UpdateTaskRequestDto("Updated Task", null, null, null, null, null);
        TaskDto responseDto = new TaskDto(taskId, "Updated Task", "Description", Priority.LOW, null, false, Collections.emptySet(), LocalDateTime.now(), LocalDateTime.now());
        given(taskService.updateTask(any(), any())).willReturn(responseDto);

        mockMvc.perform(patch("/api/tasks/{id}", taskId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Task"));
    }

    @Test
    void deleteTask_shouldReturnNoContent() throws Exception {
        UUID taskId = UUID.randomUUID();
        mockMvc.perform(delete("/api/tasks/{id}", taskId))
                .andExpect(status().isNoContent());
    }
}
