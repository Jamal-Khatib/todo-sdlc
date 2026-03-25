package com.example.todoapp.repository;

import com.example.todoapp.entity.Priority;
import com.example.todoapp.entity.Task;
import com.example.todoapp.service.TaskSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Import(TaskSpecification.class)
class TaskRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskSpecification taskSpecification;

    @BeforeEach
    void setUp() {
        Task task1 = new Task();
        task1.setTitle("First test task");
        task1.setDescription("A task for testing search");
        task1.setPriority(Priority.HIGH);
        task1.setTags(Set.of("java", "spring"));
        entityManager.persist(task1);

        Task task2 = new Task();
        task2.setTitle("Second test task");
        task2.setDescription("Another task for testing");
        task2.setPriority(Priority.LOW);
        task2.setTags(Set.of("testing", "spring"));
        entityManager.persist(task2);
    }

    @Test
    void whenTitleContains_thenCorrectTasksReturned() {
        Specification<Task> spec = taskSpecification.titleContains("first");
        List<Task> tasks = taskRepository.findAll(spec);
        assertThat(tasks).hasSize(1).extracting(Task::getTitle).contains("First test task");
    }

    @Test
    void whenDescriptionContains_thenCorrectTasksReturned() {
        Specification<Task> spec = taskSpecification.descriptionContains("another");
        List<Task> tasks = taskRepository.findAll(spec);
        assertThat(tasks).hasSize(1).extracting(Task::getTitle).contains("Second test task");
    }

    @Test
    void whenHasPriority_thenCorrectTasksReturned() {
        Specification<Task> spec = taskSpecification.hasPriority(Priority.HIGH);
        List<Task> tasks = taskRepository.findAll(spec);
        assertThat(tasks).hasSize(1).extracting(Task::getPriority).contains(Priority.HIGH);
    }

    @Test
    void whenHasTag_thenCorrectTasksReturned() {
        Specification<Task> spec = taskSpecification.hasTag("spring");
        List<Task> tasks = taskRepository.findAll(spec);
        assertThat(tasks).hasSize(2);

        spec = taskSpecification.hasTag("java");
        tasks = taskRepository.findAll(spec);
        assertThat(tasks).hasSize(1);
    }
}
