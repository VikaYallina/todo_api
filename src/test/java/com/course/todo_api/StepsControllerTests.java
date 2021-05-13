package com.course.todo_api;

import com.course.todo_api.api.controllers.StepController;
import com.course.todo_api.api.models.TodoStep;
import com.course.todo_api.api.services.StepService;
import com.course.todo_api.security.WebSecurityConfig;
import com.course.todo_api.security.jwt.AuthTokenFilter;
import com.course.todo_api.security.services.UserDetailsServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = StepController.class, excludeAutoConfiguration = {WebSecurityConfig.class})
//@AutoConfigureMockMvc
public class StepsControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private StepService stepService;



    @Test
    @WithMockUser
    public void testGet() throws Exception {
        TodoStep step = new TodoStep();
        Date now = new Date();
        step.setCreatedOn(now);
        step.setStepText("Test text");

        // Входные данные
        List<TodoStep> steps = Arrays.asList(step);

        // Метод-заглушка, возвращает указанные данные
        given(stepService.findAll()).willReturn(steps);

        // Создание запроса типа GET
        mvc.perform(get("/api/resource/step/")
                // Определяем тип ответа
                .contentType(MediaType.APPLICATION_JSON))
                // Ответ должен быть без успешным
                .andExpect(status().isOk())
                // Полученный список должен состоять из одного элемента
                .andExpect(jsonPath("$", hasSize(1)))
                // Текст результата должен совпадать с входными данными
                .andExpect(jsonPath("$[0].stepText", is(step.getStepText())));
    }
}
