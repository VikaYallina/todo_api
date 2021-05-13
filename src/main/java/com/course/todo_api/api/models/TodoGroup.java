package com.course.todo_api.api.models;

import com.course.todo_api.models.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name="todo_group")
public class TodoGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group")
    @SequenceGenerator(name = "group", sequenceName = "group_seq", allocationSize=1)
    private Long id;

    @ManyToOne
    private User groupUser;

    @Column(name="group_title")
    @NotBlank
    @NotNull
    @Size(max = 100)
    private String groupTitle;

    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getGroupUser() {
        return groupUser;
    }

    public void setGroupUser(User groupUser) {
        this.groupUser = groupUser;
    }

    public String getGroupTitle() {
        return groupTitle;
    }

    public void setGroupTitle(String groupTitle) {
        this.groupTitle = groupTitle;
    }

    public Date getGroupDateCreated() {
        return createdOn;
    }

    public void setGroupDateCreated(Date groupDateCreated) {
        this.createdOn = groupDateCreated;
    }
}
