package com.example.todoapp.service;

import com.example.todoapp.entity.Priority;
import com.example.todoapp.entity.Task;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class TaskSpecification {

    public Specification<Task> titleContains(String keyword) {
        return (root, query, criteriaBuilder) ->
            keyword == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + keyword.toLowerCase() + "%");
    }

    public Specification<Task> descriptionContains(String keyword) {
        return (root, query, criteriaBuilder) ->
            keyword == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + keyword.toLowerCase() + "%");
    }

    public Specification<Task> hasPriority(Priority priority) {
        return (root, query, criteriaBuilder) ->
            priority == null ? null : criteriaBuilder.equal(root.get("priority"), priority);
    }

    public Specification<Task> hasTag(String tag) {
        return (root, query, criteriaBuilder) ->
            tag == null ? null : criteriaBuilder.isMember(tag, root.get("tags"));
    }
}
