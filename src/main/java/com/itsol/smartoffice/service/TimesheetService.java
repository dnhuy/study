package com.itsol.smartoffice.service;
import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.itsol.smartoffice.model.TimeSheet;
import com.itsol.smartoffice.repository.TimesheetRepository;
import com.itsol.smartoffice.utils.Constants;
@Service
public class TimesheetService {
	
	@Autowired
	private TimesheetRepository timesheetRepository;
	
	public List<TimeSheet> getTimeSheetByIdAndDate(Integer userId, Date timeSheetDate){
		return timesheetRepository.findTimeSheetByUsersUserIdAndTimeSheetDate(userId, timeSheetDate);
	}
	
	public String createTimeSheet(TimeSheet timeSheet) {
		try {
			timesheetRepository.save(timeSheet);
			return Constants.SUCCESS;
		} catch (IllegalArgumentException e) {
			return Constants.FAIL;
		}
	}
	
	public TimeSheet getTimeSheetById(Integer timeSheetId) {
		return timesheetRepository.findByTimeSheetId(timeSheetId);
	}
	
	public String updateTimeSheet(TimeSheet timeSheet) {
		try {
			timesheetRepository.save(timeSheet);
			return Constants.SUCCESS;
		} catch (IllegalArgumentException e) {
			return Constants.FAIL;
		}
	}
	
	public String deleteTimeSheet(Integer timeSheetId) {
		try {
			timesheetRepository.deleteById(timeSheetId);
			return Constants.SUCCESS;
		} catch (IllegalArgumentException e) {
			return Constants.FAIL;
		}
	}
	
	public Page<TimeSheet> getTimeSheetUserPeriod(Integer userId, 
			Date startDate, Date endDate, Integer page){
		Sort sortable = Sort.by("timeSheetId").descending();
		Pageable pageable = PageRequest.of(page, 10, sortable);
		return timesheetRepository.findTimeSheetUserPeriod(userId, startDate, endDate, pageable);
	}
}