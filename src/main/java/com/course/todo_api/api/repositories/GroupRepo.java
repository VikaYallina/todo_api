package com.course.todo_api.api.repositories;

import com.course.todo_api.api.models.TodoGroup;
import com.course.todo_api.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepo extends JpaRepository<TodoGroup, Long> {
    public List<TodoGroup> findAllByGroupUser(User user);
}
