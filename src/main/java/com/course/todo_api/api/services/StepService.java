package com.course.todo_api.api.services;

import com.course.todo_api.api.models.TodoGroup;
import com.course.todo_api.api.models.TodoItem;
import com.course.todo_api.api.models.TodoStep;
import com.course.todo_api.api.repositories.StepRepo;
import com.course.todo_api.api.repositories.TodoItemRepo;
import com.course.todo_api.api.req_body_wrapper.StepRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StepService {
    @Autowired
    public StepRepo stepRepo;

    @Autowired
    private TodoItemService todoItemService;

    @Autowired
    private TodoItemRepo todoItemRepo;

    public List<TodoStep> findAll(){
        return stepRepo.findAll();
    }

    public List<TodoStep> findByTodoItemId(Long id){
        Optional<TodoItem> optItem = todoItemRepo.findById(id);
        TodoItem item = optItem.orElse(null);

        if (item != null){
            return stepRepo.findAllByTodoItem(item);
        }else{
            return null;
        }
    }

    public List<TodoStep> findAllByGroupId(Long id){
        List<TodoItem> items = todoItemService.findAllByGroupId(id);
        List<TodoStep> steps = new ArrayList<>();
        for (TodoItem item: items){
            steps.addAll(stepRepo.findAllByTodoItem(item));
        }
        return steps;
    }

    public Optional<TodoStep> findById(Long id){
        return stepRepo.findById(id);
    }

    public void save(TodoStep step){
        stepRepo.save(step);
    }

    public TodoStep update(Long id, StepRequest newStepReq){
        Optional<TodoStep> oldStepOpt = stepRepo.findById(id);
        TodoStep oldStep = oldStepOpt.orElse(null);

        TodoStep newStep = req2obj(newStepReq);
        if(oldStep != null){
            oldStep.setStepText(newStep.getStepText());
            oldStep.setCompleted(newStep.getCompleted());
            oldStep.setCreatedOn(newStep.getCreatedOn());
            oldStep.setTodoItem(newStep.getTodoItem());

            stepRepo.save(oldStep);
            return oldStep;
        }else{
            return null;
        }
    }

    public void delete(Long id){
        stepRepo.deleteById(id);
    }

    public TodoStep req2obj(StepRequest request){
        TodoStep step = new TodoStep();

        if (request!=null){
            Optional<TodoItem> itemOptional = todoItemRepo.findById(request.getTodoItemId());
            step.setTodoItem(itemOptional.orElse(null));

            step.setStepText(request.getStepText());
            step.setCompleted(request.getCompleted());
            step.setCreatedOn(request.getCreatedOn());
        }
        return step;
    }
}
