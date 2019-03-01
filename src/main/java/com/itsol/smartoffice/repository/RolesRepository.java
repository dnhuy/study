package com.itsol.smartoffice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itsol.smartoffice.model.Roles;
@Repository
public interface RolesRepository  extends JpaRepository<Roles, Integer>{
	Roles findByRoleId(Integer roleId);
	
}
