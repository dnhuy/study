package com.itsol.smartoffice.model;

import java.sql.Date;
import java.util.ArrayList;
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
import javax.persistence.OneToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity(name = "USERS")
public class Users {
	@Id
	@Column(name = "USER_ID", length = 6)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;
	@Column(name = "USER_NAME", length = 50, nullable = false)
	private String userName;
	@Column(name = "PASS_WORD", length = 50, nullable = false)
	private String passWord;
	@Column(name = "FULL_NAME", length = 50, nullable = false)
	private String fullName;
	@Column(name = "EMAIL", length = 50, nullable = false)
	private String email;
	@Column(name = "PHONE", length = 11, nullable = true)
	private String phone;
	@Column(name = "STATUS", nullable = true)
	private Boolean status;
	@ManyToMany(fetch=FetchType.EAGER)
	@JsonIgnore
	@JoinTable(name = "USERROLE", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = {
			@JoinColumn(name = "ROLE_ID") })
	private List<Roles> listRoles;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DEPARTMENT_ID")
	@JsonIgnore
	private Department department;
	@Column(name = "LAST_ACCESS")
	private Date lastAccess;
	@Column(name = "LINK_IMAGE", length = 50, nullable = true)
	private String linkImage;
	@ManyToMany(mappedBy = "users")
	@JsonIgnore
	private List<Project> projects;
	@OneToMany(mappedBy = "teamlead")
	private List<Project> projectOfTeamLead;
	@OneToMany(mappedBy = "users")
	@JsonIgnore
	private List<News> news;
	@OneToMany(mappedBy = "users")
	private List<TimeSheet> timeSheets;
	@OneToMany(mappedBy = "users")
	private List<DayOff> dayOff;
	@OneToMany(mappedBy = "users")
	@JsonIgnore
	private List<Issues> issues;
	@OneToMany(mappedBy = "users")
	@JsonIgnore
	private List<CommentIssues> commentIssues;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public List<Roles> getListRoles() {
		return listRoles;
	}

	public void setListRoles(List<Roles> listRoles) {
		this.listRoles = listRoles;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Date getLastAccess() {
		return lastAccess;
	}

	public void setLastAccess(Date lastAccess) {
		this.lastAccess = lastAccess;
	}

	public String getLinkImage() {
		return linkImage;
	}

	public void setLinkImage(String linkImage) {
		this.linkImage = linkImage;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public List<News> getNews() {
		return news;
	}

	public void setNews(List<News> news) {
		this.news = news;
	}

	public List<TimeSheet> getTimeSheets() {
		return timeSheets;
	}

	public void setTimeSheets(List<TimeSheet> timeSheets) {
		this.timeSheets = timeSheets;
	}

	public List<DayOff> getDayOffs() {
		return dayOff;
	}

	public void setDayOffs(List<DayOff> dayOff) {
		this.dayOff = dayOff;
	}

	public List<Issues> getIssues() {
		return issues;
	}

	public void setIssues(List<Issues> issues) {
		this.issues = issues;
	}

	public List<CommentIssues> getCommentIssues() {
		return commentIssues;
	}

	public void setCommentIssues(List<CommentIssues> commentIssues) {
		this.commentIssues = commentIssues;
	}

	public Users(Integer userId, String userName, String passWord, String fullName, String email, String phone,
			Boolean status, List<Roles> listRoles, Department department, Date lastAccess, String linkImage,
			List<Project> projects, List<News> news, List<TimeSheet> timeSheets, List<DayOff> dayOff,
			List<Issues> issues, List<CommentIssues> commentIssues) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.passWord = passWord;
		this.fullName = fullName;
		this.email = email;
		this.phone = phone;
		this.status = status;
		this.listRoles = listRoles;
		this.department = department;
		this.lastAccess = lastAccess;
		this.linkImage = linkImage;
		this.projects = projects;
		this.news = news;
		this.timeSheets = timeSheets;
		this.dayOff = dayOff;
		this.issues = issues;
		this.commentIssues = commentIssues;
	}

	public Users() {
		super();
	}

	public List<GrantedAuthority> loadAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (Roles role : listRoles) {
			authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
		}
		return authorities;
	}
}