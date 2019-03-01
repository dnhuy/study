package com.itsol.smartoffice.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.itsol.smartoffice.model.DayOff;
import com.itsol.smartoffice.model.Project;
import com.itsol.smartoffice.model.Users;
import com.itsol.smartoffice.repository.DayoffRepository;
import com.itsol.smartoffice.utils.Constants;

@Service
public class DayoffService {
	// Nối vào Day Off Repository
	@Autowired
	DayoffRepository dayoffRepository;

	// Nối vào Project Service
	@Autowired
	private ProjectService projectService;

	@Autowired
	private UsersService userService;

	///////////////////////////////////////////////////////////////////////////////////

	// Hiển thị

	// Danh sách toàn bộ DayOFF trong bảng DayOff
	public Page<DayOff> getAllDayOff(Pageable pageable) {
		return dayoffRepository.findAll(pageable);
	}


	

	// Danh sách toàn bộ DayOFF của 1 user
	public List<DayOff> findByUser(Users user) {
		return dayoffRepository.findByUsers(user);
	}

	// Lấy chi tiết của 1 Day OFF cụ thể theo Id
	public DayOff findByDayOffId(Integer dayOffId) {
		return dayoffRepository.findByDayOffId(dayOffId);
	}

	// Danh sách Day Off theo trạng thái
	// dùng để phân loại xin phép theo 3 trạng thái duyệt, từ chối, chưa duyệt
	public Page<DayOff> getListStatus(Pageable pageable, String status) {
		return dayoffRepository.findByStatus(pageable, status);
	}

	// PHÂN TRANG
	public Page<DayOff> getListByPage(Pageable pageable) {
		return dayoffRepository.getThreeDayOff(pageable);
	}

	// Danh sách DayOff theo ProjectId
	public List<DayOff> findByProjectId(Integer projectId) {
		List<DayOff> listByProject = dayoffRepository.getByProjectTimeStart(projectId);
		List<DayOff> listFiltered = new ArrayList<>();
		for (DayOff dayOff : listByProject) {
			Users s = dayOff.getUsers();
			List<Project> p = s.getProjects();
			for (Project i : p) {
				if(i.getProjectId()==projectId) {
					listFiltered.add(dayOff);
				}
			}
		}
		return listFiltered;
	}



	// Thêm một xin nghỉ mới
	@Transactional
	public String setNewDayOff(DayOff dayoff) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();
		Users user = userService.getUsersByUserName(userName);
		dayoff.setUsers(user);
		dayoff.setStatus(Constants.PENDDING);
		LocalDate local = LocalDate.now();
		Date date = Date.valueOf(local);
		dayoff.setDateCreated(date);
		DayOff check = dayoffRepository.save(dayoff);
		if (check.getDateCreated().equals(dayoff.getDateCreated())) {
			return Constants.SUCCESS;
		}
		return Constants.FAIL;
	}

	// Duyệt trạng thái của xin phép
	@Transactional
	public String setStatus(Integer dayoffId, String update) {
		DayOff dayoff = dayoffRepository.getOne(dayoffId);
		dayoff.setStatus(update);
		DayOff check = dayoffRepository.save(dayoff);
		if (check.getStatus().equals(dayoff.getStatus())) {
			return Constants.SUCCESS;
		}
		return Constants.FAIL;
	}

// Kết thúc
/////////////////////////////////////////////////////////////////////////

}
