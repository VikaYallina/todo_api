package com.course.todo_api.api.services;

import com.course.todo_api.api.models.TodoGroup;
import com.course.todo_api.api.models.TodoItem;
import com.course.todo_api.api.repositories.GroupRepo;
import com.course.todo_api.api.repositories.TodoItemRepo;
import com.course.todo_api.api.req_body_wrapper.GroupRequest;
import com.course.todo_api.models.User;
import com.course.todo_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GroupService {
    @Autowired
    public GroupRepo groupRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private TodoItemService todoItemService;

    // Метод для получения списка всех групп
    public List<TodoGroup> findAll(){
        return groupRepo.findAll();
    }

    //Поиск группы по id
    public Optional<TodoGroup> findById(Long id){
        return groupRepo.findById(id);
    }

    //Поиск группы по id пользователя
    public List<TodoGroup> findByUserId(Long id){
        // Находим пользователя по id
        Optional<User> optionalUser = userRepo.findById(id);
        User user = optionalUser.orElse(null);

        if (user != null){
            return groupRepo.findAllByGroupUser(user);
        }else{
            return null;
        }
    }
    // Метод сохранения группы в базе данных
    public void save(TodoGroup todoGroup){
        groupRepo.save(todoGroup);
    }

    public TodoGroup update(Long id, GroupRequest newGroupReq){
        Optional<TodoGroup> oldGroupOpt = groupRepo.findById(id);
        TodoGroup oldGroup = oldGroupOpt.orElse(null);

        TodoGroup newGroup = req2obj(newGroupReq);

        if(oldGroup != null){
            oldGroup.setGroupTitle(newGroup.getGroupTitle());
            oldGroup.setGroupDateCreated(newGroup.getGroupDateCreated());
            oldGroup.setGroupUser(newGroup.getGroupUser());

            groupRepo.save(oldGroup);
            return oldGroup;
        }else{
            return null;
        }
    }

    public void delete(Long id){
        List<TodoItem> items = todoItemService.findAllByGroupId(id);
        if (!items.isEmpty()){
            for (TodoItem item:items)
                todoItemService.delete(item.getId());
        }
        groupRepo.deleteById(id);
    }

    public TodoGroup req2obj(GroupRequest request){
        TodoGroup resObj = new TodoGroup();
        if (request != null){
            resObj.setGroupTitle(request.getGroupTitle());
            resObj.setGroupDateCreated(request.getCreatedOn());

            Optional<User> userOpt = userRepo.findById(request.getUserId());
            resObj.setGroupUser(userOpt.orElse(null));
        }
        return resObj;
    }
}
