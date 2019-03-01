package com.itsol.smartoffice.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itsol.smartoffice.model.DayOff;
import com.itsol.smartoffice.service.DayoffService;
import com.itsol.smartoffice.utils.Constants;

@RequestMapping(value = "/dayoffcontroller")
@RestController
public class DayoffController {
	@Autowired
	private DayoffService dayoffService;

// danh sach day off trong bang day off
	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Object> getAllDayOff(
			@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {

		Sort sort = Sort.by("dateCreated").descending();
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<DayOff> list = dayoffService.getListByPage(pageable);
		return new ResponseEntity<Object>(list, HttpStatus.OK);
	}

// xem danh sach chua duyet
	@RequestMapping(value = "/list/new", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Page<DayOff>> getListNew(
			@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
		Sort sort = Sort.by("dateCreated").descending();
		Pageable pageable = PageRequest.of(page, size, sort);
		return new ResponseEntity<Page<DayOff>>(dayoffService.getListStatus(pageable, Constants.PENDDING),
				HttpStatus.OK);
	}

// xem danh sach da duyet
	@RequestMapping(value = "/list/approved", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Page<DayOff>> getListApproved(
			@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
		Sort sort = Sort.by("dateCreated").descending();
		Pageable pageable = PageRequest.of(page, size, sort);
		return new ResponseEntity<Page<DayOff>>(dayoffService.getListStatus(pageable, Constants.APPROVED),
				HttpStatus.OK);
	}

// xem danh sach tu choi duyet
	@RequestMapping(value = "/list/notapproved", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Page<DayOff>> getListNotApproved(
			@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
		Sort sort = Sort.by("dateCreated").descending();
		Pageable pageable = PageRequest.of(page, size, sort);
		return new ResponseEntity<Page<DayOff>>(dayoffService.getListStatus(pageable, Constants.REJECTED),
				HttpStatus.OK);
	}

// xem chi tiet dayoff
	@RequestMapping(value = "/user/{dayOffId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<DayOff> getDetailByDayOffId(@PathVariable("dayOffId") Integer dayOffId) {

		DayOff detail = dayoffService.findByDayOffId(dayOffId);
		return new ResponseEntity<DayOff>(detail, HttpStatus.OK);
	}

// danh sach chi tiet day off trong 1 du an
	@RequestMapping(value = "/list/projectId/{projectId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<DayOff>> getDetailByProjectId(@PathVariable("projectId") Integer projectId) {
		return new ResponseEntity<List<DayOff>>(dayoffService.findByProjectId(projectId), HttpStatus.OK);
	}

// duyet xin phep cua nhan vien
	@RequestMapping(value = "/approved", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> setApproved(@RequestParam("dayoffId") Integer dayoffId) {
		return new ResponseEntity<String>(dayoffService.setStatus(dayoffId, Constants.APPROVED), HttpStatus.OK);
	}

// tu choi xin phep cua nhan vien
	@RequestMapping(value = "/notapproved", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> setDisapproved(@RequestParam("dayoffId") Integer dayoffId) {
		return new ResponseEntity<String>(dayoffService.setStatus(dayoffId, Constants.REJECTED), HttpStatus.OK);
	}

// them mot xin phep moi
	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> setNewDayOff(@RequestBody DayOff dayoff) {
		return new ResponseEntity<String>(dayoffService.setNewDayOff(dayoff), HttpStatus.OK);
	}

}