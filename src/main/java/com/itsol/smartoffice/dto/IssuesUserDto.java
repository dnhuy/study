package com.itsol.smartoffice.dto;

import com.itsol.smartoffice.model.Project;
import com.itsol.smartoffice.model.Users;

import java.io.Serializable;
import java.sql.Date;

public class IssuesUserDto implements Serializable {
    private Integer issuesId;
    private Integer projectId;
    private Integer userId;
    private String userName;
    private String fullName;
    private String issuesContent;
    private Date lastDate;
    private Users users;

    public IssuesUserDto(Integer issuesId, Integer projectId, Integer userId, String userName, String fullName, String issuesContent, Date lastDate, Users users) {
        this.issuesId = issuesId;
        this.projectId = projectId;
        this.userId = userId;
        this.userName = userName;
        this.fullName = fullName;
        this.issuesContent = issuesContent;
        this.lastDate = lastDate;
        this.users = users;
    }

    public IssuesUserDto() {
    }

    public Integer getIssuesId() {
        return issuesId;
    }

    public void setIssuesId(Integer issuesId) {
        this.issuesId = issuesId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getUserId() {
        return users.getUserId();
    }

    public void setUserId(Integer userId) {
        this.userId = users.getUserId();
    }

    public String getUserName() {
        return users.getUserName();
    }

    public void setUserName(String userName) {
        this.userName = users.getUserName();
    }

    public String getIssuesContent() {
        return issuesContent;
    }

    public void setIssuesContent(String issuesContent) {
        this.issuesContent = issuesContent;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public String getFullName() {
        return users.getFullName();
    }

    public void setFullName(String fullName) {
        this.fullName = users.getFullName();
    }
}
