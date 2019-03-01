package com.itsol.smartoffice.dto;

import java.io.Serializable;
import java.sql.Date;

public class IssuesDto implements Serializable {
    private Integer issuesId;
    private Integer userId;
    private Date lastDate;
    private String issuesContent;

    public IssuesDto(Integer issuesId, Integer userId, Date lastDate, String issuesContent) {
        this.issuesId = issuesId;
        this.userId = userId;
        this.lastDate = lastDate;
        this.issuesContent = issuesContent;
    }

    public IssuesDto() {
    }

    public Integer getIssuesId() {
        return issuesId;
    }

    public void setIssuesId(Integer issuesId) {
        this.issuesId = issuesId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    public String getIssuesContent() {
        return issuesContent;
    }

    public void setIssuesContent(String issuesContent) {
        this.issuesContent = issuesContent;
    }
}
