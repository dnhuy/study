package com.itsol.smartoffice.service;

import com.itsol.smartoffice.dto.LogIssuesDto;
import com.itsol.smartoffice.model.Issues;
import com.itsol.smartoffice.model.LogIssues;
import com.itsol.smartoffice.repository.LogissuesRepository;
import com.itsol.smartoffice.utils.DtoManager;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LogissuesService {
	@Autowired
	private LogissuesRepository logissuesRepository;

	@Autowired
	private DtoManager dtoManager;

	public void createNewLogIssues(Issues issues) {
		logissuesRepository.save(new LogIssues(null, issues, issues.getIssuesContent(), issues.getLastDate()));
	}

	public ResponseEntity<Object> findAllLogIssues() {
		try {
			List<LogIssuesDto> logIssuesDtoList = new ArrayList<>();
			List<LogIssues> logIssuesList = logissuesRepository.findAll();
			dtoManager.convertToListDto(logIssuesList, logIssuesDtoList, LogIssuesDto.class);
			return new ResponseEntity<>(logIssuesDtoList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<Object> findLogIssuesByIssuesId(Integer issuesId) {
		try {
			List<LogIssuesDto> logIssuesDtoList = new ArrayList<>();
			List<LogIssues> logIssuesList = logissuesRepository.findLogIssuesByIssuesIssuesId(issuesId);
			dtoManager.convertToListDto(logIssuesList, logIssuesDtoList, LogIssuesDto.class);
			return new ResponseEntity<>(logIssuesDtoList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}