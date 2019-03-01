package com.itsol.smartoffice.service;

import com.itsol.smartoffice.dto.CommentIssuesRequestModel;
import com.itsol.smartoffice.dto.CommentIssuesUserDto;
import com.itsol.smartoffice.model.CommentIssues;
import com.itsol.smartoffice.model.Issues;
import com.itsol.smartoffice.model.Users;
import com.itsol.smartoffice.repository.CommentissuesRepository;
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
public class CommentissuesService {
	@Autowired
	private CommentissuesRepository commentissuesRepository;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private UsersService usersService;
	@Autowired
	private IssuesService issuesService;
	@Autowired
	private DtoManager dtoManager;

	public ResponseEntity<Object> findListCommentByIssuesId(Integer issuesId) {
		try {
			List<CommentIssues> issuesList = commentissuesRepository.findAllByIssuesIssuesId(issuesId);
			List<CommentIssuesUserDto> commentIssuesUserDtoList = new ArrayList<>();
			dtoManager.convertToListDto(issuesList, commentIssuesUserDtoList, CommentIssuesUserDto.class);
			return new ResponseEntity<>(commentIssuesUserDtoList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<Object> createIssuesComment(CommentIssuesRequestModel commentIssuesRequestModel) {
		try {
			Users users = usersService.getUserById(commentIssuesRequestModel.getUserId());
			Issues issues = issuesService.findIssuesByIssuesId(commentIssuesRequestModel.getIssuesId());
			CommentIssues commentIssues = new CommentIssues();
			commentIssues.setUsers(users);
			commentIssues.setIssues(issues);
			commentIssues.setDateComment(commentIssuesRequestModel.getDateComment());
			commentIssues.setComment(commentIssuesRequestModel.getComment());
			commentissuesRepository.save(commentIssues);
			CommentIssuesUserDto commentIssuesUserDto = new CommentIssuesUserDto();
			modelMapper.map(commentIssues, commentIssuesUserDto);
			return new ResponseEntity<>(commentIssuesUserDto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<Object> findListCommentByIssuesIdAndUserId(Integer issuesId, Integer userId) {
		try {
			List<CommentIssues> issuesList = commentissuesRepository.findAllByIssuesIssuesIdAndUsersUserId(issuesId,
					userId);
			List<CommentIssuesUserDto> commentIssuesUserDtoList = new ArrayList<>();
			dtoManager.convertToListDto(issuesList, commentIssuesUserDtoList, CommentIssuesUserDto.class);
			return new ResponseEntity<>(commentIssuesUserDtoList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<Object> findListCommentByIssuesIdPagingAndSorting(Integer issuesId, Integer page, int size) {
		try {
			Sort sortable = Sort.by("commentId").descending();
			Pageable pageable = PageRequest.of(page, size, sortable);
			Page<CommentIssues> commentIssuesPage = commentissuesRepository.findAllByIssuesIssuesId(issuesId, pageable);
			List<CommentIssues> commentIssuesList = commentIssuesPage.getContent();
			List<CommentIssuesUserDto> commentIssuesUserDtoList = new ArrayList<>();
			dtoManager.convertToListDto(commentIssuesList, commentIssuesUserDtoList, CommentIssuesUserDto.class);
			Page<CommentIssuesUserDto> commentIssuesUserDtoPage = new PageImpl<CommentIssuesUserDto>(
					commentIssuesUserDtoList, pageable, commentIssuesPage.getTotalElements());
			return new ResponseEntity<>(commentIssuesUserDtoPage, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}