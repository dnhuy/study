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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity(name="DAYOFF")
public class DayOff {
	@Id
	@Column(name = "DAYOFF_ID", length = 8)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer dayOffId;
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonIgnoreProperties(ignoreUnknown=true, value={ "passWord", "userName", "email", "phone", "status", "listRoles", "department", "lastAccess",
			"linkImage", "projects", "news", "timeSheets", "dayOff", "issues", "commentIssues","dayOffs" })
	@JoinColumn(name = "USER_ID", nullable = false)
	private Users users;
	@Column(name="CONTENT_OFF", length=1000, nullable = false)
	private String contentOff;
	@Column(name="DATE_CREATED", nullable = false)
	private Date dateCreated;
	@Column(name="DATE_START", nullable = false)
	private Date dateStart;
	@Column(name="TYPE_OFF", length=100, nullable = false)
	private String typeOff;
	@Column(name="TIME_OFF", nullable = false)
	private Integer timeOff;
	@Column(name="STATUS", length=20, nullable = false)
	private String status;
	public Integer getDayOffId() {
		return dayOffId;
	}
	public void setDayOffId(Integer dayOffId) {
		this.dayOffId = dayOffId;
	}
	public Users getUsers() {
		return users;
	}
	public void setUsers(Users users) {
		this.users = users;
	}
	public String getContentOff() {
		return contentOff;
	}
	public void setContentOff(String contentOff) {
		this.contentOff = contentOff;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public Date getDateStart() {
		return dateStart;
	}
	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}
	public String getTypeOff() {
		return typeOff;
	}
	public void setTypeOff(String typeOff) {
		this.typeOff = typeOff;
	}
	public Integer getTimeOff() {
		return timeOff;
	}
	public void setTimeOff(Integer timeOff) {
		this.timeOff = timeOff;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public DayOff(Integer dayOffId, Users users, String contentOff, Date dateCreated, Date dateStart, String typeOff,
			Integer timeOff, String status) {
		super();
		this.dayOffId = dayOffId;
		this.users = users;
		this.contentOff = contentOff;
		this.dateCreated = dateCreated;
		this.dateStart = dateStart;
		this.typeOff = typeOff;
		this.timeOff = timeOff;
		this.status = status;
	}
	public DayOff() {
		super();
	}
	
}
