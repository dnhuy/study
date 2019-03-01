package com.itsol.smartoffice.controller;

import com.itsol.smartoffice.dto.IssuesDto;
import com.itsol.smartoffice.service.IssuesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/issuescontroller")
public class IssuesController {
	@Autowired
	private IssuesService issuesService;

	@GetMapping("/list-issues-project")
	public ResponseEntity<Object> findListIssuesByProjectId(@RequestParam(name = "projectId") Integer projectId) {
		return issuesService.findListIssuesByProjectId(projectId);
	}

	@GetMapping("/details")
	public ResponseEntity<Object> detailIssuesByIssuesId(@RequestParam(name = "issuesId") Integer issuesId) {
		return issuesService.detailsIssuesByIssuesId(issuesId);
	}

	@GetMapping("/list-all-issues-by-user")
	public ResponseEntity<Object> findAllIssuesByUserId(@RequestParam(name = "userId") Integer userId) {
		return issuesService.findAllIssuesByUserId(userId);
	}

	@GetMapping("/findIssuesPage")
	public ResponseEntity<Object> findIssuesPage(@RequestParam("page") int page) {
		return issuesService.findPageAndSorting(page, 10);
	}

	@PostMapping("/edit")
	public ResponseEntity<Object> updateIssues(@RequestBody IssuesDto issuesDto) {
		return issuesService.updateIssues(issuesDto);
	}

	@PostMapping("/create")
	public ResponseEntity<Object> createIssues(@RequestBody IssuesDto issuesDto) {
		return issuesService.createIssues(issuesDto);
	}
}