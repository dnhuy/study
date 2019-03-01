package com.itsol.smartoffice.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.itsol.smartoffice.model.TimeSheet;
import com.itsol.smartoffice.model.Users;
import com.itsol.smartoffice.repository.ProjectRepository;
import com.itsol.smartoffice.repository.TimesheetRepository;
import com.itsol.smartoffice.repository.UserRepository;
import com.itsol.smartoffice.utils.Constants;
import com.itsol.smartoffice.utils.DateManager;

@Service
public class UsersService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private TimesheetRepository timesheetRepository;
	@Autowired
	private DateManager dateManager;

	public List<Users> getAllUsers() {
		return userRepository.findAll();
	};

	public List<Users> getAllUsersNoRole() {
		return userRepository.findAllByListRolesNull();
	}

	public List<Users> getAllTeamLead() {
		return userRepository.findAllUsersByListRolesRoleName("ROLE_TEAMLEAD");
	}

	public List<Users> getAllMember() {
		return userRepository.findAllUsersByListRolesRoleName("ROLE_MEMBER");
	}

	public List<Users> getAllUserFree(Date date1, Date date2) {
		return userRepository
				.findAllUsersByProjectsTimeStartGreaterThanEqualOrProjectsTimeEndLessThanEqualOrProjectsNull(date1,
						date2);
	}

	public Users getUsersByUserName(String username) {
		return userRepository.findByUserName(username);
	}

	public boolean deleteUser(Integer userId) {
		try {
			userRepository.deleteById(userId);
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}

	}

	public Users getUserById(Integer id) {
		return userRepository.findByUserId(id);
	}

	public void sendActiveMail(Users users, String linkActive) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(users.getEmail());
		message.setSubject("active account smart office");
		message.setText(linkActive);
		mailSender.send(message);
	}

	public void sendWelcomeMail(Users users) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(users.getEmail());
		message.setSubject("active account smart office");
		message.setText("congratulation, your account is actived\nnow you can use your account");
		mailSender.send(message);
	}

	public void sendPassword(Users users, String password) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(users.getEmail());
		message.setSubject("New password");
		message.setText("Here is new password: " + password);
		mailSender.send(message);
	}

	public String saveUser(Users users) {
		if (userRepository.findByUserName(users.getUserName()) != null) {
			return Constants.USER_NAME_WAS_USED;
		}
		if (userRepository.findByEmail(users.getEmail()) != null) {
			return Constants.EMAIL_WAS_USED;
		}
		users.setStatus(false);
		userRepository.save(users);
		return Constants.SUCCESS;
	};

	public void updateUser(Users users) {
		userRepository.save(users);
	};

	public Users getProjectWorkingByUserInDay(Integer userId, Date dayWork) {
		return userRepository.findUsersByUserIdAndProjectsTimeStartLessThanEqualAndProjectsTimeEndGreaterThanEqual(
				userId, dayWork, dayWork);
	}

	public Integer getCountUserMissTimeSheet(Integer projectId, String month) {
		List<Users> users = projectRepository.getOne(projectId).getUsers();
		int count = 0;
		int numberday = dateManager.countDayofMonth(month);
		for (Users user : users) {
			Integer userId = user.getUserId();
			for (int i = 1; i <= numberday; i++) {
				String dateStr = i + "-" + month;
				Date timeSheetDate = dateManager.convertStringToDate(dateStr);
				List<TimeSheet> timeSheets = timesheetRepository.findTimeSheetByUserIdAndDate(userId, timeSheetDate);
				if (timeSheets == null || timeSheets.isEmpty()) {
					count++;
					break;
				}
			}
		}
		return count;
	}

	
}
