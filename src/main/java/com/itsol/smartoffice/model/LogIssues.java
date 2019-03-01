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

@Entity(name="LOGISSUES")
public class LogIssues {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer logId;
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ISSUES_ID", nullable = false)
	@JsonIgnore
	private Issues issues;
	@Column(name="ISSUES_CONTENT", length=1000, nullable = false)
	private String issuesContent;
	@Column(name="DATE_CREATED", nullable = false)
	private Date dateCreated;
	public Integer getLogId() {
		return logId;
	}
	public void setLogId(Integer logId) {
		this.logId = logId;
	}
	public Issues getIssues() {
		return issues;
	}
	public void setIssues(Issues issues) {
		this.issues = issues;
	}
	public String getIssuesContent() {
		return issuesContent;
	}
	public void setIssuesContent(String issuesContent) {
		this.issuesContent = issuesContent;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public LogIssues(Integer logId, Issues issues, String issuesContent, Date dateCreated) {
		super();
		this.logId = logId;
		this.issues = issues;
		this.issuesContent = issuesContent;
		this.dateCreated = dateCreated;
	}
	public LogIssues() {
		super();
	}
	
}
