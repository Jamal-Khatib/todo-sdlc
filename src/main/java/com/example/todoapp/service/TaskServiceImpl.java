package com.example.todoapp.service;

import com.example.todoapp.dto.CreateTaskRequestDto;
import com.example.todoapp.dto.TaskDto;
import com.example.todoapp.dto.TaskMapper;
import com.example.todoapp.dto.UpdateTaskRequestDto;
import com.example.todoapp.entity.Task;
import com.example.todoapp.exception.TaskNotFoundException;
import com.example.todoapp.repository.TaskRepository;
import com.example.todoapp.entity.Priority;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.HashSet;
import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final TaskSpecification taskSpecification;

    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper, TaskSpecification taskSpecification) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.taskSpecification = taskSpecification;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TaskDto> getAllTasks(Pageable pageable, String search, Priority priority, String tag) {
        Specification<Task> spec = Specification.where(null);
        if (search != null && !search.isBlank()) {
            spec = spec.and(taskSpecification.titleContains(search).or(taskSpecification.descriptionContains(search)));
        }
        if (priority != null) {
            spec = spec.and(taskSpecification.hasPriority(priority));
        }
        if (tag != null && !tag.isBlank()) {
            spec = spec.and(taskSpecification.hasTag(tag));
        }
        return taskRepository.findAll(spec, pageable).map(taskMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public TaskDto getTaskById(UUID id) {
        return taskRepository.findById(id)
            .map(taskMapper::toDto)
            .orElseThrow(() -> new TaskNotFoundException(id));
    }

    @Override
    @Transactional
    public TaskDto createTask(CreateTaskRequestDto taskDto) {
        Task task = taskMapper.toEntity(taskDto);
        Task savedTask = taskRepository.save(task);
        return taskMapper.toDto(savedTask);
    }

    @Override
    @Transactional
    public TaskDto updateTask(UUID id, UpdateTaskRequestDto taskDto) {
        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new TaskNotFoundException(id));

        if (taskDto.title() != null) {
            task.setTitle(taskDto.title());
        }
        if (taskDto.description() != null) {
            task.setDescription(taskDto.description());
        }
        if (taskDto.priority() != null) {
            task.setPriority(taskDto.priority());
        }
        if (taskDto.dueDate() != null) {
            task.setDueDate(taskDto.dueDate());
        }
        if (taskDto.completed() != null) {
            task.setCompleted(taskDto.completed());
        }
        if (taskDto.tags() != null) {
            task.setTags(taskDto.tags());
        }

        Task updatedTask = taskRepository.save(task);
        return taskMapper.toDto(updatedTask);
    }

    @Override
    @Transactional
    public void deleteTask(UUID id) {
        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException(id);
        }
        taskRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void importTasksFromCsv(MultipartFile file) {
        try (CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            String[] line;
            reader.readNext(); // Skip header
            while ((line = reader.readNext()) != null) {
                Task task = new Task();
                task.setTitle(line[0]);
                task.setDescription(line[1]);
                task.setPriority(Priority.valueOf(line[2]));
                if (line[3] != null && !line[3].isEmpty()) {
                    task.setDueDate(LocalDate.parse(line[3]));
                }
                if (line.length > 4 && line[4] != null && !line[4].isEmpty()) {
                    task.setTags(new HashSet<>(Arrays.asList(line[4].split(","))));
                }
                taskRepository.save(task);
            }
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException("Failed to parse CSV file: " + e.getMessage());
        }
    }
}
