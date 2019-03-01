package com.itsol.smartoffice.dto;

import java.io.Serializable;
import java.sql.Date;

public class LogIssuesDto implements Serializable {
	private Integer logId;
	private IssuesUserDto issues;
	private String issuesContent;
	private Date dateCreated;

	public LogIssuesDto(Integer logId, IssuesUserDto issues, String issuesContent, Date dateCreated) {
		this.logId = logId;
		this.issues = issues;
		this.issuesContent = issuesContent;
		this.dateCreated = dateCreated;
	}

	public LogIssuesDto() {
	}

	public Integer getLogId() {
		return logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	public IssuesUserDto getIssues() {
		return issues;
	}

	public void setIssues(IssuesUserDto issues) {
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
}