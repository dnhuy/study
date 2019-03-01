package com.itsol.smartoffice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.itsol.smartoffice.model.CommentIssues;
import org.springframework.data.repository.PagingAndSortingRepository;
import java.util.List;

public interface CommentissuesRepository
		extends JpaRepository<CommentIssues, Integer>, PagingAndSortingRepository<CommentIssues, Integer> {
	List<CommentIssues> findAllByIssuesIssuesId(Integer issuesId);

	CommentIssues findCommentIssuesByCommentId(Integer commentId);

	List<CommentIssues> findAllByIssuesIssuesIdAndUsersUserId(Integer issuesId, Integer userId);

	Page<CommentIssues> findAll(Pageable pageable);

	Page<CommentIssues> findAllByIssuesIssuesId(Integer issuesId, Pageable pageable);
}