package com.course.todo_api;

import com.course.todo_api.api.models.TodoItem;
import com.course.todo_api.api.models.TodoStep;
import com.course.todo_api.api.repositories.StepRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class StepsRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private StepRepo stepRepo;

    @Test
    public void testRepo(){
        TodoItem item = new TodoItem();
        item.setItemText("text");
        item.setCompleted(false);
        entityManager.persist(item);

        TodoItem other_item = new TodoItem();
        other_item.setItemText("text");
        other_item.setCompleted(false);
        entityManager.persist(other_item);

        TodoStep step1 = new TodoStep();
        step1.setStepText("step1");
        step1.setTodoItem(item);
        step1.setCompleted(false);
        entityManager.persist(step1);

        TodoStep step2 = new TodoStep();
        step2.setStepText("step2");
        step2.setTodoItem(other_item);
        step2.setCompleted(false);
        entityManager.persist(step2);

        // Создание сущности
        TodoStep step3 = new TodoStep();
        step3.setStepText("step3");
        step3.setTodoItem(item);
        step3.setCompleted(false);
        // Добавление сущности в базц данных
        entityManager.persist(step3);

        // Запись всех изменений в базе данных до транзакции
        entityManager.flush();

        // Список всех этапов, для задачи item
        List<TodoStep> list = Arrays.asList(step1,step3);

        // Вызов метода репозитория
        List<TodoStep> steps = stepRepo.findAllByTodoItem(item);
        // Определение соответсивия результата метода входным данным
        assertThat(steps.containsAll(list)).isTrue();

    }
}
