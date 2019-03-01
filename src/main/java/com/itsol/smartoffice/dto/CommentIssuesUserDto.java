package com.itsol.smartoffice.dto;

import com.itsol.smartoffice.model.Issues;
import com.itsol.smartoffice.model.Users;

import java.io.Serializable;
import java.sql.Date;

public class CommentIssuesUserDto implements Serializable {

    private Integer issuesId;
    private Integer commentId;
    private Integer userId;
    private String userName;
    private String fullName;
    private Date dateComment;
    private String comment;
    private Users users;
    private Issues issues;

    public CommentIssuesUserDto(Integer commentId, Date dateComment, String comment, Users users, Issues issues) {
        this.commentId = commentId;
        this.dateComment = dateComment;
        this.comment = comment;
        this.users = users;
        this.issues = issues;
    }

    public CommentIssuesUserDto() {
    }

    public Integer getIssuesId() {
        return issues.getIssuesId();
    }

    public Integer getCommentId() {
        return commentId;
    }

    public Integer getUserId() {
        return users.getUserId();
    }

    public String getUserName() {
        return users.getUserName();
    }

    public String getFullName() {
        return users.getFullName();
    }

    public Date getDateComment() {
        return dateComment;
    }

    public String getComment() {
        return comment;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public void setDateComment(Date dateComment) {
        this.dateComment = dateComment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public void setIssues(Issues issues) {
        this.issues = issues;
    }
}
