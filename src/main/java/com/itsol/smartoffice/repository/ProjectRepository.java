package com.itsol.smartoffice.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.itsol.smartoffice.model.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

	List<Project> findAll();
	Project findByProjectId(Integer projectId);
	@Query("SELECT P FROM PROJECTS P")
	Page<Project> getTenProject(Pageable pageable);
	List<Project> findAllProjectByTimeStartLessThanEqualAndTimeEndGreaterThanEqual(Date now1, Date now2);
}