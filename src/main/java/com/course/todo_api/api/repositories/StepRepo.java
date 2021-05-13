package com.course.todo_api.api.repositories;

import com.course.todo_api.api.models.TodoItem;
import com.course.todo_api.api.models.TodoStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StepRepo extends JpaRepository<TodoStep, Long> {
    List<TodoStep> findAllByTodoItem(TodoItem todoItem);
}
