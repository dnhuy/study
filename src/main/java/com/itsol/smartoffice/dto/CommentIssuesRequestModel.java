package com.itsol.smartoffice.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;

public class CommentIssuesRequestModel implements Serializable {
    @NotNull
    private Integer userId;

    @NotNull
    private Integer issuesId;
    private Date dateComment;
    private String comment;

    public CommentIssuesRequestModel(@NotNull Integer userId, @NotNull Integer issuesId, Date dateComment, String comment) {
        this.userId = userId;
        this.issuesId = issuesId;
        this.dateComment = dateComment;
        this.comment = comment;
    }

    public CommentIssuesRequestModel() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getIssuesId() {
        return issuesId;
    }

    public void setIssuesId(Integer issuesId) {
        this.issuesId = issuesId;
    }

    public Date getDateComment() {
        return dateComment;
    }

    public void setDateComment(Date dateComment) {
        this.dateComment = dateComment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
