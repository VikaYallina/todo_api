package com.course.todo_api.api.req_body_wrapper;

import java.util.Date;

public class StepRequest {
    private Long todoItemId;
    private String stepText;
    private Date createdOn;
    private Boolean isCompleted;

    public StepRequest( Long todoItemId, String stepText, Date createdOn, Boolean isCompleted) {
        this.todoItemId = todoItemId;
        this.stepText = stepText;
        this.createdOn = createdOn;
        this.isCompleted = isCompleted;
    }


    public Long getTodoItemId() {
        return todoItemId;
    }

    public void setTodoItemId(Long todoItemId) {
        this.todoItemId = todoItemId;
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
}
