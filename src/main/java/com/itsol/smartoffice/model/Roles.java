package com.itsol.smartoffice.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity(name = "ROLES")
public class Roles {
	@Id
	@Column(name = "ROLES_ID", length = 2)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer roleId;
	@Column(name = "ROLES_NAME", length = 20, nullable=false)
	private String roleName;
	@ManyToMany(mappedBy = "listRoles")
	private List<Users> users;
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public List<Users> getUsers() {
		return users;
	}
	public void setUsers(List<Users> users) {
		this.users = users;
	}
	public Roles(Integer roleId, String roleName, List<Users> users) {
		super();
		this.roleId = roleId;
		this.roleName = roleName;
		this.users = users;
	}
	public Roles() {
		super();
	}
	
}
