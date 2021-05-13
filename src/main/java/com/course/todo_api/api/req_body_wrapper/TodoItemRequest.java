package com.course.todo_api.api.req_body_wrapper;

import java.util.Date;

public class TodoItemRequest {
    private Long groupId;
    private String itemText;
    private Date createdOn;
    private Boolean isCompleted;
    private Date completedOn;
    private String importanceLvl;
    private Date completeBy;

    public TodoItemRequest(Long groupId, String itemText, Date createdOn, Boolean isCompleted, Date completedOn, String importanceLvl, Date completeBy) {
        this.groupId = groupId;
        this.itemText = itemText;
        this.createdOn = createdOn;
        this.isCompleted = isCompleted;
        this.completedOn = completedOn;
        this.importanceLvl = importanceLvl;
        this.completeBy = completeBy;
    }


    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getItemText() {
        return itemText;
    }

    public void setItemText(String itemText) {
        this.itemText = itemText;
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

    public Date getCompletedOn() {
        return completedOn;
    }

    public void setCompletedOn(Date completedOn) {
        this.completedOn = completedOn;
    }

    public String getImportanceLvl() {
        return importanceLvl;
    }

    public void setImportanceLvl(String importanceLvl) {
        this.importanceLvl = importanceLvl;
    }

    public Date getCompleteBy() {
        return completeBy;
    }

    public void setCompleteBy(Date completeBy) {
        this.completeBy = completeBy;
    }
}
