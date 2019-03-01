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
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "TIMESHEETS")
public class TimeSheet {
	@Id
	@Column(name = "TIMESHEET_ID", length = 10)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer timeSheetId;
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonIgnoreProperties(ignoreUnknown = true, value = { "passWord", "userName", "email", "phone", "status",
			"listRoles", "department", "lastAccess", "linkImage", "projects", "news", "timeSheets", "dayOff", "issues",
			"commentIssues", "dayOffs" })
	@JoinColumn(name = "USER_ID", nullable = false)
	private Users users;
	@Column(name = "TIMESHEET_CONTENT", length = 500, nullable = false)
	private String timeSheetContent;
	@Column(name = "TIME_WORK", length = 2, nullable = false)
	private Integer timeWork;
	@Column(name = "RESULT", length = 3, nullable = false)
	private Integer result;
	@Column(name = "NOTE", length = 200, nullable = false)
	private String note;
	@Column(name = "TIMESHEET_DATE", nullable = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+7")
	private Date timeSheetDate;
	@Column(name = "STATUS", length = 50)
	private String status;

	public Integer getTimeSheetId() {
		return timeSheetId;
	}

	public void setTimeSheetId(Integer timeSheetId) {
		this.timeSheetId = timeSheetId;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public String getTimeSheetContent() {
		return timeSheetContent;
	}

	public void setTimeSheetContent(String timeSheetContent) {
		this.timeSheetContent = timeSheetContent;
	}

	public Integer getTimeWork() {
		return timeWork;
	}

	public void setTimeWork(Integer timeWork) {
		this.timeWork = timeWork;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getTimeSheetDate() {
		return timeSheetDate;
	}

	public void setTimeSheetDate(Date timeSheetDate) {
		this.timeSheetDate = timeSheetDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public TimeSheet(Integer timeSheetId, Users users, String timeSheetContent, Integer timeWork, Integer result,
			String note, Date timeSheetDate, String status) {
		super();
		this.timeSheetId = timeSheetId;
		this.users = users;
		this.timeSheetContent = timeSheetContent;
		this.timeWork = timeWork;
		this.result = result;
		this.note = note;
		this.timeSheetDate = timeSheetDate;
		this.status = status;
	}

	public TimeSheet() {
		super();
		// TODO Auto-generated constructor stub
	}

}
