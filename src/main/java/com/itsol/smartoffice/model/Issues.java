package com.itsol.smartoffice.model;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity(name="ISSUES")
public class Issues {
	@Id@Column(name = "ISSUES_ID", length = 10)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer issuesId;
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
	private Users users;
	@Column(name="PROJECT_ID", length=6, nullable=false)
	private Integer projectId;
	@Column(name="ISSUES_CONTENT", length=1000, nullable=false)
	private String issuesContent;
	@Column(name="LAST_DATE", nullable=false)
	private Date lastDate;
	@OneToMany(mappedBy="issues")
	private List<LogIssues> listLog;
	@OneToMany(mappedBy="issues")
	private List<CommentIssues> listComment;
	public Integer getIssuesId() {
		return issuesId;
	}
	public void setIssuesId(Integer issuesId) {
		this.issuesId = issuesId;
	}
	public Users getUsers() {
		return users;
	}
	public void setUsers(Users users) {
		this.users = users;
	}
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
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
	public List<LogIssues> getListLog() {
		return listLog;
	}
	public void setListLog(List<LogIssues> listLog) {
		this.listLog = listLog;
	}
	public List<CommentIssues> getListComment() {
		return listComment;
	}
	public void setListComment(List<CommentIssues> listComment) {
		this.listComment = listComment;
	}
	public Issues(Integer issuesId, Users users, Integer projectId, String issuesContent, Date lastDate,
			List<LogIssues> listLog, List<CommentIssues> listComment) {
		super();
		this.issuesId = issuesId;
		this.users = users;
		this.projectId = projectId;
		this.issuesContent = issuesContent;
		this.lastDate = lastDate;
		this.listLog = listLog;
		this.listComment = listComment;
	}
	public Issues() {
		super();
	}
	
}
