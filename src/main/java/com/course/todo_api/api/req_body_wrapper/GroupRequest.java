package com.course.todo_api.api.req_body_wrapper;

import java.util.Date;

public class GroupRequest {
    private Date createdOn;
    private String groupTitle;
    private Long userId;

    public GroupRequest(Date createdOn, String groupTitle, Long userId) {
        this.createdOn = createdOn;
        this.groupTitle = groupTitle;
        this.userId = userId;
    }


    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getGroupTitle() {
        return groupTitle;
    }

    public void setGroupTitle(String groupTitle) {
        this.groupTitle = groupTitle;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
