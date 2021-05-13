package com.course.todo_api.api.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name="todo_item")
public class TodoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item")
    @SequenceGenerator(name = "item", sequenceName = "item_seq", allocationSize=1)
    private Long id;

    @ManyToOne
    private TodoGroup todoGroup;

    @Column(name="item_text")
    @NotNull
    private String itemText;

    @Column(name="created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @Column(name="is_completed")
    private Boolean isCompleted;

    @Column(name="completed_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date completedOn;

    @Column(name="imp_lvl")
    private String importanceLvl;

    @Column(name = "complete_by")
    @Temporal(TemporalType.TIMESTAMP)
    private Date completeBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TodoGroup getTodoGroup() {
        return todoGroup;
    }

    public void setTodoGroup(TodoGroup todoGroup) {
        this.todoGroup = todoGroup;
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
