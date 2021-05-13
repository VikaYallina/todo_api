package com.course.todo_api.api.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name="todo_step")
public class TodoStep {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "step")
    @SequenceGenerator(name = "step", sequenceName = "step_seq", allocationSize=1)
    private Long id;

    @ManyToOne
    private TodoItem todoItem;

    @Column(name="step_text")
    @NotNull
    private String stepText;

    @Column(name="created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @Column(name="is_completed")
    @NotNull
    private Boolean isCompleted;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStepText() {
        return stepText;
    }

    public void setStepText(String stepText) {
        this.stepText = stepText;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }

    public TodoItem getTodoItem() {
        return todoItem;
    }

    public void setTodoItem(TodoItem todoItem) {
        this.todoItem = todoItem;
    }
}
