package com.itsol.smartoffice.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity(name = "DEPARTMENT")
public class Department {
	@Id
	@Column(name = "DEPRTMENT_ID", length = 5)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer departmentId;
	@Column(name = "DEPARTMENT_NAME", nullable = false, length = 20)
	private String departmentName;
	@OneToMany(mappedBy = "department")
	private List<Users> listUsers;
	public Integer getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public List<Users> getListUsers() {
		return listUsers;
	}
	public void setListUsers(List<Users> listUsers) {
		this.listUsers = listUsers;
	}
	public Department(Integer departmentId, String departmentName, List<Users> listUsers) {
		super();
		this.departmentId = departmentId;
		this.departmentName = departmentName;
		this.listUsers = listUsers;
	}
	public Department() {
		super();
	}
	
	
}
