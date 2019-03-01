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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity(name = "PROJECTS")
public class Project {
	@Id
	@Column(name = "PROJECT_ID", length = 6)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer projectId;
	@Column(name = "PROJECT_NAME", length = 50, nullable = false)
	private String projectName;
	@Column(name = "USER_CREATE_ID", length = 6, nullable = false)
	private Integer userCreateId;
//	@Column(name = "TEAM_LEAD_ID", length = 6, nullable=false)
//	private Integer teamLeadId;
	@Column(name = "TIME_START", nullable = true)
	private Date timeStart;
	@Column(name = "TIME_END", nullable = true)
	private Date timeEnd;
	@ManyToMany
	@JoinTable(name = "USERPROJECT", joinColumns = { @JoinColumn(name = "PROJECT_ID") }, inverseJoinColumns = {
			@JoinColumn(name = "USER_ID") })
	private List<Users> users;
	@ManyToOne
	@JoinColumn(name = "TEAM_LEAD_ID")
	private Users teamlead;

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Date getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(Date timeStart) {
		this.timeStart = timeStart;
	}

	public Date getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(Date timeEnd) {
		this.timeEnd = timeEnd;
	}

	public List<Users> getUsers() {
		return users;
	}

	public Integer getUserCreateId() {
		return userCreateId;
	}

	public void setUserCreateId(Integer userCreateId) {
		this.userCreateId = userCreateId;
	}

	public Users getTeamlead() {
		return teamlead;
	}

	public void setTeamlead(Users teamlead) {
		this.teamlead = teamlead;
	}

	public void setUsers(List<Users> users) {
		this.users = users;
	}

	public Project(Integer projectId, String projectName, Integer userCreateId, Date timeStart, Date timeEnd,
			List<Users> users, Users teamlead) {
		super();
		this.projectId = projectId;
		this.projectName = projectName;
		this.userCreateId = userCreateId;
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
		this.users = users;
		this.teamlead = teamlead;
	}

	public Project() {
		super();
	}

}