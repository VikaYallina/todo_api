package com.course.todo_api.api.repositories;

import com.course.todo_api.api.models.TodoGroup;
import com.course.todo_api.api.models.TodoItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoItemRepo extends JpaRepository<TodoItem, Long> {
    public List<TodoItem> findAllByTodoGroup(TodoGroup todoGroup);
}
