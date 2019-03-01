package com.itsol.smartoffice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itsol.smartoffice.model.LogIssues;

import java.util.List;

public interface LogissuesRepository extends JpaRepository<LogIssues, Integer> {
	LogIssues findByLogId(Integer logId);

	List<LogIssues> findAll();

	List<LogIssues> findLogIssuesByIssuesIssuesId(Integer issuesId);
}