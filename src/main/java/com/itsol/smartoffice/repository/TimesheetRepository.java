package com.itsol.smartoffice.repository;
import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.itsol.smartoffice.model.TimeSheet;
public interface TimesheetRepository extends JpaRepository<TimeSheet, Integer>, PagingAndSortingRepository<TimeSheet, Integer>{
	
	@Query("SELECT ts FROM TimeSheet ts "
			+ "WHERE ts.users.userId = ?1 AND ts.timeSheetDate = ?2")
	List<TimeSheet> findTimeSheetByUserIdAndDate(Integer userId, Date timeSheetDate);
	
	@Query("SELECT ts FROM TimeSheet ts WHERE ts.timeSheetId = :timeSheetId")
	TimeSheet findByTimeSheetId(@Param("timeSheetId") Integer timeSheetId);
	
	@Query("SELECT ts FROM TimeSheet ts "
			+ "WHERE (ts.users.userId = ?1) AND (ts.timeSheetDate BETWEEN ?2 AND ?3)")
	Page<TimeSheet> findTimeSheetUserPeriod(Integer userId, Date startDate, Date endDate, Pageable pageable);
	
	List<TimeSheet> findTimeSheetByUsersUserIdAndTimeSheetDate(Integer userId, Date timeSheetDate);
}