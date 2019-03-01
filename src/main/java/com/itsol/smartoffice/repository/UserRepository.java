package com.itsol.smartoffice.repository;

import java.sql.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.itsol.smartoffice.model.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
	Users findByUserName(String username);

	Users findByEmail(String email);

	Users findByUserId(Integer userId);

	List<Users> findAllByListRolesNull();

	List<Users> findAllUsersByListRolesRoleName(String roleName);

	List<Users> findAllUsersByProjectsTimeStartGreaterThanEqualOrProjectsTimeEndLessThanEqualOrProjectsNull(
			Date dateEqual, Date dateEqual2);

	Users findUsersByUserIdAndProjectsTimeStartLessThanEqualAndProjectsTimeEndGreaterThanEqual(Integer userId,
			Date dateEqual, Date dateEqual2);

	List<Users> findUsersByListRolesRoleName(String roleName);
}