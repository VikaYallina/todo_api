package com.course.todo_api.api.controllers;

import com.course.todo_api.api.models.TodoGroup;
import com.course.todo_api.api.req_body_wrapper.GroupRequest;
import com.course.todo_api.api.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingErrorProcessor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/resource/group")
public class GroupController {

    @Autowired
    // Класс GroupService автоматически присваивается контейнером Spring
    private GroupService groupService;

    // Посылается http-запрос на адрес localhost:8080/api/resource/group/ с действием GET
    @GetMapping("")
    public ResponseEntity<List<TodoGroup>> getAllGroups(){
        // Получаем весь список групп
        List<TodoGroup> allGroups = groupService.findAll();
        // Spring десериализует данные в JSON для передачи по запросу
        if (allGroups == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else if (allGroups.isEmpty())
            return new ResponseEntity<>(allGroups, HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity<>(allGroups, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<TodoGroup> getGroup(@PathVariable Long id) {
        return groupService.findById(id)
                .map(group -> new ResponseEntity<>(group, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/by-user-id/{userId}")
    public ResponseEntity<List<TodoGroup>> getAllGroupsByUserId(@PathVariable Long userId) {
        List<TodoGroup> groupsByUserId = groupService.findByUserId(userId);

        if (groupsByUserId.isEmpty())
            return new ResponseEntity<>(groupsByUserId, HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(groupsByUserId, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<TodoGroup> saveGroup(@RequestBody @Valid GroupRequest group){
        // TODO: 25.04.2021 Add errors
        TodoGroup todoGroup = groupService.req2obj(group);
        groupService.save(todoGroup);
        return new ResponseEntity<>(todoGroup, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoGroup> updateGroup(@PathVariable("id") Long id,
                                               @RequestBody @Valid GroupRequest groupReq,
                                               BindingResult bindingResult){
        // TODO: 25.04.2021 Error handling 
        TodoGroup newGroup = groupService.update(id, groupReq);
        if (newGroup == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(newGroup, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TodoGroup> deleteGroup(@PathVariable("id") Long id){
        Optional<TodoGroup> group = groupService.findById(id);
        if (!group.isPresent())
            return new ResponseEntity<>((HttpStatus.NOT_FOUND));
        groupService.delete(id);
        return new ResponseEntity<>(group.get(),HttpStatus.NO_CONTENT);
    }
}
