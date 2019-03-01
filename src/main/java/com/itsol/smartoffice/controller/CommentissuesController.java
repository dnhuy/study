package com.itsol.smartoffice.controller;

import com.itsol.smartoffice.dto.CommentIssuesRequestModel;
import com.itsol.smartoffice.repository.UserRepository;
import com.itsol.smartoffice.service.CommentissuesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment-issues-controller")
public class CommentissuesController {
	@Autowired
	private CommentissuesService commentissuesService;

	@GetMapping("/list-comment")
	public ResponseEntity<Object> findListCommentByIssuesId(@RequestParam(name = "issuesId") Integer issuesId) {
		return commentissuesService.findListCommentByIssuesId(issuesId);
	}

	@PostMapping("/create-comment")
	public ResponseEntity<Object> createIssuesComment(
			@RequestBody CommentIssuesRequestModel commentIssuesRequestModel) {
		return commentissuesService.createIssuesComment(commentIssuesRequestModel);
	}

	@GetMapping("/find-page-comment")
	public ResponseEntity<Object> findPageCommentByIssuesId(@RequestParam(name = "issuesId") Integer issuesId,
			@RequestParam(name = "page") Integer page) {
		return commentissuesService.findListCommentByIssuesIdPagingAndSorting(issuesId, page, 10);
	}
}