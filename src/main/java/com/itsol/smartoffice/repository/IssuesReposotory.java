package com.itsol.smartoffice.repository;

import com.itsol.smartoffice.dto.IssuesUserDto;
import com.itsol.smartoffice.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.itsol.smartoffice.model.Issues;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface IssuesReposotory extends JpaRepository<Issues, Integer>, PagingAndSortingRepository<Issues, Integer> {

Issues findByIssuesId(Integer issuesId);

List<Issues> findAllByUsersUserId(Integer userId);

@Query("SELECT i FROM ISSUES i")
List<Issues> findIssues(Pageable pageable);

Page<Issues> findAll(Pageable pageable);
}