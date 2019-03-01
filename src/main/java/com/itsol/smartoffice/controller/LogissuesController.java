package com.itsol.smartoffice.controller;

import com.itsol.smartoffice.service.LogissuesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logissuescontroller")
public class LogissuesController {
	@Autowired
	private LogissuesService logissuesService;

	@GetMapping("/history-edit-issues")
	public ResponseEntity<Object> findLogIssuesByIssuesId(@RequestParam Integer issuesId) {
		return logissuesService.findLogIssuesByIssuesId(issuesId);
	}
}