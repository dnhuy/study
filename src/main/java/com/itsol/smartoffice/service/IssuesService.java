package com.itsol.smartoffice.service;

import com.itsol.smartoffice.dto.IssuesDetailCommentDto;
import com.itsol.smartoffice.dto.IssuesDto;
import com.itsol.smartoffice.dto.IssuesUserDto;
import com.itsol.smartoffice.model.*;
import com.itsol.smartoffice.repository.IssuesReposotory;
import com.itsol.smartoffice.repository.ProjectRepository;
import com.itsol.smartoffice.utils.Constants;
import com.itsol.smartoffice.utils.DtoManager;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IssuesService {

	@Autowired
	private IssuesReposotory issuesReposotory;

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private DtoManager dtoManager;

	@Autowired
	private LogissuesService logissuesService;

	@Autowired
	private UsersService usersService;

	public ResponseEntity<Object> findListIssuesByProjectId(Integer projectId) {
		try {
			List<IssuesUserDto> issuesUserDtos = new ArrayList<>();
			Project project = projectRepository.findByProjectId(projectId);
			List<Users> users = project.getUsers();
			List<IssuesUserDto> issuesDtoTemp = new ArrayList<>();
			for (Users user : users) {
				issuesDtoTemp.clear();
				List<Issues> issues = user.getIssues();
				dtoManager.convertToListDto(issues, issuesDtoTemp, IssuesUserDto.class);
				issuesUserDtos.addAll(issuesDtoTemp);
			}
			return new ResponseEntity<>(issuesUserDtos, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public Issues findIssuesByIssuesId(Integer issuesId) {
		return issuesReposotory.findByIssuesId(issuesId);
	}

	public ResponseEntity<Object> detailsIssuesByIssuesId(Integer issuesId) {
		try {
			Issues issues = issuesReposotory.findByIssuesId(issuesId);
			IssuesDetailCommentDto issuesDetailCommentDto = new IssuesDetailCommentDto();
			modelMapper.map(issues, issuesDetailCommentDto);
			return new ResponseEntity<>(issuesDetailCommentDto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<Object> findAllIssuesByUserId(Integer userId) {
		try {
			List<Issues> issuesList = issuesReposotory.findAllByUsersUserId(userId);
			List<IssuesUserDto> issuesUserDtoList = new ArrayList<>();
			dtoManager.convertToListDto(issuesList, issuesUserDtoList, IssuesUserDto.class);
			return new ResponseEntity<>(issuesUserDtoList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<Object> updateIssues(IssuesDto issuesDto) {
		try {
			Integer issuesId = issuesDto.getIssuesId();
			Issues issues = issuesReposotory.findByIssuesId(issuesId);
			if (null != issues) {
				issues.setIssuesContent(issuesDto.getIssuesContent());
				issues.setLastDate(issuesDto.getLastDate());
				issuesReposotory.save(issues);
				logissuesService.createNewLogIssues(issues);
				IssuesUserDto issuesUserDto = new IssuesUserDto();
				modelMapper.map(issues, issuesUserDto);
				return new ResponseEntity<>(issuesUserDto, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(Constants.NOT_FOUND_ENTITY, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<Object> createIssues(IssuesDto issuesDto) {
		try {
			Users users = usersService.getProjectWorkingByUserInDay(issuesDto.getUserId(), issuesDto.getLastDate());
			if (null == users || users.getProjects().size() < 1) {
				return new ResponseEntity<>(Constants.USER_NOT_WORKING_IN_PROJECTS, HttpStatus.BAD_REQUEST);
			} else {
				int projectId = users.getProjects().get(0).getProjectId();
				Issues issues = new Issues();
				issues.setUsers(users);
				issues.setProjectId(projectId);
				issues.setLastDate(issuesDto.getLastDate());
				issues.setIssuesContent(issuesDto.getIssuesContent());
				issuesReposotory.save(issues);
				logissuesService.createNewLogIssues(issues);
				IssuesDto issuesDtoRes = new IssuesDto();
				modelMapper.map(issues, issuesDtoRes);
				issuesDtoRes.setUserId(users.getUserId());
				return new ResponseEntity<>(issuesDtoRes, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<Object> findPage(int page, int size) {
		try {
			Sort sortable = Sort.by("issuesId").descending();
			Pageable pageable = PageRequest.of(page, size, sortable);
			List<Issues> issuesList = issuesReposotory.findIssues(pageable);
			List<IssuesUserDto> issuesUserDtoList = new ArrayList<>();
			dtoManager.convertToListDto(issuesList, issuesUserDtoList, IssuesUserDto.class);
			return new ResponseEntity<>(issuesUserDtoList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<Object> findPageAndSorting(int page, int size) {
		try {
			Sort sortable = Sort.by("issuesId").descending();
			Pageable pageable = PageRequest.of(page, size, sortable);
			Page<Issues> issuesPage = issuesReposotory.findAll(pageable);
			List<Issues> issuesList = issuesPage.getContent();
			List<IssuesUserDto> issuesUserDtoList = new ArrayList<>();
			dtoManager.convertToListDto(issuesList, issuesUserDtoList, IssuesUserDto.class);
			Page<IssuesUserDto> issuesUserDtoPage = new PageImpl<IssuesUserDto>(issuesUserDtoList, pageable,
					issuesPage.getTotalElements());
			return new ResponseEntity<>(issuesUserDtoPage, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}