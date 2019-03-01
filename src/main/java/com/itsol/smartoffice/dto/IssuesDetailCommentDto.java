package com.itsol.smartoffice.dto;

import com.itsol.smartoffice.model.Users;

import java.io.Serializable;
import java.util.List;

public class IssuesDetailCommentDto implements Serializable {
    private Integer issuesId;
    private Integer userId;
    private String userName;
    private String issuesContent;
    private Users users;
    private List<CommentIssuesUserDto> listComment;

    public IssuesDetailCommentDto(Integer issuesId, Integer userId, String userName, String issuesContent, Users users, List<CommentIssuesUserDto> listComment) {
        this.issuesId = issuesId;
        this.userId = userId;
        this.userName = userName;
        this.issuesContent = issuesContent;
        this.users = users;
        this.listComment = listComment;
    }

    public IssuesDetailCommentDto() {
    }

    public Integer getIssuesId() {
        return issuesId;
    }

    public void setIssuesId(Integer issuesId) {
        this.issuesId = issuesId;
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

    public List<CommentIssuesUserDto> getListComment() {
        return listComment;
    }

    public void setListComment(List<CommentIssuesUserDto> listComment) {
        this.listComment = listComment;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}
