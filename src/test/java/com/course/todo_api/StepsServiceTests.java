package com.course.todo_api;

import com.course.todo_api.api.models.TodoItem;
import com.course.todo_api.api.models.TodoStep;
import com.course.todo_api.api.repositories.StepRepo;
import com.course.todo_api.api.repositories.TodoItemRepo;
import com.course.todo_api.api.req_body_wrapper.StepRequest;
import com.course.todo_api.api.services.StepService;
import com.course.todo_api.api.services.TodoItemService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class StepsServiceTests {

    @TestConfiguration
    static class StepsServiceTestContextConfig{
        @Bean
        public StepService stepService(){
            return new StepService();
        }
    }

    @Autowired
    private StepService stepService;

    @MockBean
    private StepRepo stepRepo;

    @MockBean
    private TodoItemService todoItemService;

    @MockBean
    private TodoItemRepo todoItemRepo;

    // Создание тестовых данных и определение методов-заглушек
    @Before
    public void setUp(){
        TodoStep step = new TodoStep();
        step.setId(1L);
        step.setCreatedOn(new Date());
        step.setStepText("old text");

        TodoItem item = new TodoItem();
        item.setId(1L);    // Здесь создаются объекты

        // Вместо вызова следущих методов будут возвращены определенные данные
        Mockito.when(stepRepo.findById(1L)).thenReturn(java.util.Optional.of(step));
        Mockito.when(stepRepo.save(step)).thenReturn(step);
        Mockito.when(todoItemRepo.findById(1L)).thenReturn(java.util.Optional.of(item));
    }

    @Test
    public void testUpdateService(){
        // Новая информация об объекте
        StepRequest request = new StepRequest(1L,"newText",new Date(),false);

        // Вызов тестируемого метода
        TodoStep s = stepService.update(1L,request);
        // Проерка результата
        assertThat(s.getStepText()).isEqualTo(request.getStepText());
    }
}
