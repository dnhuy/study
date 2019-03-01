package com.itsol.smartoffice.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itsol.smartoffice.model.TimeSheet;
import com.itsol.smartoffice.model.Users;
import com.itsol.smartoffice.service.TimesheetService;
import com.itsol.smartoffice.service.UsersService;

@RequestMapping("/timesheetcontroller")
@RestController
public class TimesheetController {

	@Autowired
	private TimesheetService timesheetService;

	@Autowired
	private UsersService usersService;

	@RequestMapping(value = "/timesheetsuser/{timeSheetDate}", method = RequestMethod.GET)
	public ResponseEntity<Object> getTimeSheetByIdAndDate(@PathVariable("timeSheetDate") Date timeSheetDate) {
		Authentication au = SecurityContextHolder.getContext().getAuthentication();
		String username = au.getName();
		Users user = usersService.getUsersByUserName(username);
		List<TimeSheet> list = timesheetService.getTimeSheetByIdAndDate(user.getUserId(), timeSheetDate);
		if (list != null) {
			return new ResponseEntity<Object>(list, HttpStatus.OK);
		}
		return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@RequestMapping(value = "/timesheetdetail/{timeSheetId}", method = RequestMethod.GET)
	public ResponseEntity<Object> getTimeSheetById(@PathVariable("timeSheetId") Integer timeSheetId) {
		TimeSheet timeSheet = timesheetService.getTimeSheetById(timeSheetId);
		if (timeSheet != null) {
			return new ResponseEntity<Object>(timeSheet, HttpStatus.OK);
		}
		return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public ResponseEntity<String> createTimeSheet(@RequestBody TimeSheet timeSheet) {
		String message = timesheetService.createTimeSheet(timeSheet);
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<String> updateTimeSheet(@RequestBody TimeSheet timeSheet) {
		Authentication au = SecurityContextHolder.getContext().getAuthentication();
		String username = au.getName();
		Users user = usersService.getUsersByUserName(username);
		String message = timesheetService.updateTimeSheet(timeSheet);
		if (user != null && user.getUserId().equals(timeSheet.getUsers().getUserId())) {
			return new ResponseEntity<String>(message, HttpStatus.OK);
		}
		return new ResponseEntity<String>(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/delete/{timeSheetId}", method = RequestMethod.POST)
	public ResponseEntity<String> deleteTimeSheet(@PathVariable("timeSheetId") Integer timeSheetId) {
		Authentication au = SecurityContextHolder.getContext().getAuthentication();
		String username = au.getName();
		Users user = usersService.getUsersByUserName(username);
		TimeSheet timeSheet = timesheetService.getTimeSheetById(timeSheetId);
		if (user != null && timeSheet != null && user.getUserId().equals(timeSheet.getUsers().getUserId())) {
			String message = timesheetService.deleteTimeSheet(timeSheetId);
			return new ResponseEntity<String>(message, HttpStatus.OK);
		}
		return new ResponseEntity<String>(HttpStatus.NOT_MODIFIED);

	}

	@RequestMapping(value = "timesheets/{userId}/{startDate}/{endDate}/{page}", method = RequestMethod.GET)
	public ResponseEntity<Object> getTimeSheetUserPeriod(@PathVariable("userId") Integer userId,
			@PathVariable("startDate") Date startDate, @PathVariable("endDate") Date endDate,
			@PathVariable("page") Integer page) {
		Page<TimeSheet> list = timesheetService.getTimeSheetUserPeriod(userId, startDate, endDate, page);
		if (list != null) {
			return new ResponseEntity<Object>(list, HttpStatus.OK);
		}
		return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

}