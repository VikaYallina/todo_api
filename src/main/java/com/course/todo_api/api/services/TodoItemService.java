package com.course.todo_api.api.services;

import com.course.todo_api.api.models.TodoGroup;
import com.course.todo_api.api.models.TodoItem;
import com.course.todo_api.api.models.TodoStep;
import com.course.todo_api.api.repositories.GroupRepo;
import com.course.todo_api.api.repositories.TodoItemRepo;
import com.course.todo_api.api.req_body_wrapper.TodoItemRequest;
import com.course.todo_api.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TodoItemService {
    @Autowired
    private TodoItemRepo todoItemRepo;

    @Autowired
    private GroupRepo groupRepo;

    @Autowired
    private GroupService groupService;

    @Autowired
    private StepService stepService;

    public List<TodoItem> findAll(){
        return todoItemRepo.findAll();
    }

    public List<TodoItem> findAllByGroupId(Long id){
        Optional<TodoGroup> optGroup =groupRepo.findById(id);
        TodoGroup group = optGroup.orElse(null);

        if (group != null){
            return todoItemRepo.findAllByTodoGroup(group);
        }else{
            return null;
        }
    }

    public Optional<TodoItem> findById(Long id){
        return todoItemRepo.findById(id);
    }

    public List<TodoItem> findByUserId(Long id){
        List<TodoGroup> groups = groupService.findByUserId(id);
        List<TodoItem> items = new ArrayList<>();
        for (TodoGroup group: groups){
            items.addAll(todoItemRepo.findAllByTodoGroup(group));
        }
        return items;
    }

    public void save(TodoItem todoItem){
        todoItemRepo.save(todoItem);
    }

    public TodoItem update(Long id, TodoItemRequest todoItemReq){
        Optional<TodoItem> oldItemOpt = todoItemRepo.findById(id);
        TodoItem oldItem = oldItemOpt.orElse(null);

        TodoItem newItem = req2obj(todoItemReq);

        if (oldItem != null){
            oldItem.setItemText(newItem.getItemText());
            oldItem.setCompleted(newItem.getCompleted());
            oldItem.setCompletedOn(newItem.getCompletedOn());
            oldItem.setCreatedOn(newItem.getCreatedOn());
            oldItem.setImportanceLvl(newItem.getImportanceLvl());
            oldItem.setTodoGroup(newItem.getTodoGroup());
            oldItem.setCompleteBy(newItem.getCompleteBy());

            todoItemRepo.save(oldItem);
            return oldItem;
        }
        else{
            return null;
        }

    }

    public void delete(Long id){
        List<TodoStep> steps = stepService.findByTodoItemId(id);

        if (!steps.isEmpty()){
            for (TodoStep step:steps){
                stepService.delete(step.getId());
            }
        }
        todoItemRepo.deleteById(id);
    }

    public TodoItem req2obj(TodoItemRequest request){
        TodoItem item = new TodoItem();
        if (request != null){
            Optional<TodoGroup> group = groupRepo.findById(request.getGroupId());
            item.setTodoGroup(group.orElse(null));

            item.setItemText(request.getItemText());
            item.setCompleted(request.getCompleted());
            item.setCompletedOn(request.getCompletedOn());
            item.setCreatedOn(request.getCreatedOn());
            item.setImportanceLvl(request.getImportanceLvl());
            item.setCompleteBy(request.getCompleteBy());
        }
        return  item;
    }
}
