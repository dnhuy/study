package com.itsol.smartoffice.model;


import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
@Entity(name="COMMENTISSUES")
public class CommentIssues {
	@Id
	@Column(name = "COMMENT_ID", length = 10)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer commentId;
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ISSUES_ID", nullable = false)
	@JsonIgnore
	private Issues issues;
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
	private Users users;
	@Column(name="DATE_COMMENT", nullable=false)
	private Date dateComment;
	@Column(name="CONTENT_COMMENT", length=1000, nullable=true)
	private String comment;
	public Integer getCommentId() {
		return commentId;
	}
	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}
	public Issues getIssues() {
		return issues;
	}
	public void setIssues(Issues issues) {
		this.issues = issues;
	}
	
	public Users getUsers() {
		return users;
	}
	public void setUsers(Users users) {
		this.users = users;
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
	
	public CommentIssues(Integer commentId, Issues issues, Users users, Date dateComment, String comment) {
		super();
		this.commentId = commentId;
		this.issues = issues;
		this.users = users;
		this.dateComment = dateComment;
		this.comment = comment;
	}
	public CommentIssues() {
		super();
	}
	
}
