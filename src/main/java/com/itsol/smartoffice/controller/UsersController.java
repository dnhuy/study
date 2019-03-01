package com.itsol.smartoffice.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.itsol.smartoffice.model.Project;
import com.itsol.smartoffice.model.Roles;
import com.itsol.smartoffice.model.Users;
import com.itsol.smartoffice.repository.ProjectRepository;
import com.itsol.smartoffice.repository.RolesRepository;
import com.itsol.smartoffice.repository.UserRepository;
import com.itsol.smartoffice.service.Jwtservice;
import com.itsol.smartoffice.service.UsersService;
import com.itsol.smartoffice.utils.Constants;
import com.itsol.smartoffice.utils.DateManager;
import com.itsol.smartoffice.utils.Validations;

@RequestMapping(value = "/usercontroller")
@RestController
public class UsersController {

	@Autowired
	private UsersService service;
	@Autowired
	private Jwtservice jwtservice;
	@Autowired
	private RolesRepository rolesRepository;
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private DateManager dateManager;

	/**
	 * @param users
	 * @return
	 * @throws UserExistException
	 * @throws EmailUsedException
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<String> register(@RequestBody Users users, HttpServletRequest request) {
		String message = service.saveUser(users);
		if (message.equals(Constants.SUCCESS)) {
			String token = jwtservice.generateTokenLogin(users.getUserName());
			String linkActive = request.getRequestURL() + request.getContextPath() + "/active/" + token
					+ "\n\tClick this link to active your account";
			service.sendActiveMail(users, linkActive);
			return new ResponseEntity<String>(message, HttpStatus.OK);
		}
		return new ResponseEntity<String>(message, HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<Object> login(@RequestBody Users users) {
		try {
			List<Users> allUsers = service.getAllUsers();
			if (Validations.isValidLogin(users, allUsers)) {
				users = service.getUsersByUserName(users.getUserName());
				if (!users.getStatus()) {
					return new ResponseEntity<Object>(Constants.REQUIRE_ACTIVE, HttpStatus.OK); // chưa active thì trả
				}
				if (users.getListRoles().isEmpty() || users.getListRoles() == null) {
					String token = jwtservice.generateTokenLogin(users.getUserName());
					token = "\"" + token + "\"";
					return new ResponseEntity<Object>(token, HttpStatus.OK);
				}
				if (!users.getListRoles().isEmpty()) {
					String token = jwtservice.generateTokenLogin(users.getUserName(), users.getUserId().toString(),
							users.getListRoles().get(0).getRoleName());
					token = "\"" + token + "\"";
					return new ResponseEntity<Object>(token, HttpStatus.OK);
				}
			}
			return new ResponseEntity<Object>(Constants.LOGIN_FAILURE, HttpStatus.BAD_REQUEST); // truong hợp khác thì
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(Constants.FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/register/active/{token}", method = RequestMethod.GET)
	public ResponseEntity<String> activeAccount(@PathVariable String token) {
		String userName = jwtservice.getUsernameFromToken(token);
		Users userToActive = service.getUsersByUserName(userName);
		if (!userToActive.getStatus()) {
			userToActive.setStatus(true);
			service.updateUser(userToActive);
			service.sendWelcomeMail(userToActive); // send email notify active success
			return new ResponseEntity<>(Constants.ACTIVE_SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<>(Constants.FAIL, HttpStatus.BAD_REQUEST); // if account is not active
	}

	@RequestMapping(value = "/setrole", method = RequestMethod.POST)
	public ResponseEntity<Object> setRoleForUser(@RequestParam("user_id") Integer user_id,
			@RequestParam("role_id") Integer role_id) {
		try {
			Users user = service.getUserById(user_id);
			List<Roles> roles = new ArrayList<Roles>();
			roles.add(rolesRepository.findByRoleId(role_id));
			user.setListRoles(roles);
			service.updateUser(user);
			return new ResponseEntity<Object>("\"Grant role succes!\"", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(Constants.FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/setrole", method = RequestMethod.GET)
	public ResponseEntity<Object> getAllUsersNoRole() {
		List<Users> users = service.getAllUsersNoRole();
		return new ResponseEntity<Object>(users, HttpStatus.OK);
	}

	@RequestMapping(value = "/forgotpassword", method = RequestMethod.POST)
	public ResponseEntity<Object> forgotPassword(@RequestParam("userName") String userName) {
		try {
			Users user = userRepository.findByUserName(userName);
			if (user != null) {
				String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
				String password = RandomStringUtils.random(10, characters);
				service.sendPassword(user, password);
				user.setPassWord(password);
				service.updateUser(user);
				return new ResponseEntity<Object>("\"Reset password success, please check your email!\"", HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>("\"User not exist\"", HttpStatus.INTERNAL_SERVER_ERROR);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(Constants.FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/userdetail", method = RequestMethod.GET)
	public ResponseEntity<Object> getUserDetail(@RequestParam("user_id") Integer user_id) {
		try {
			Users user = service.getUserById(user_id);
			return new ResponseEntity<Object>(user, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(Constants.FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/userdetail", method = RequestMethod.POST)
	public ResponseEntity<Object> editUserDetail(@RequestBody Users user) {
		try {
			String username = user.getUserName();
			Users existUser = service.getUsersByUserName(username);
			if (user.getEmail() != null) {
				existUser.setEmail(user.getEmail());
			}
			if (user.getFullName() != null) {
				existUser.setFullName(user.getFullName());
			}
			if (user.getLinkImage() != null) {
				existUser.setLinkImage(user.getLinkImage());
			}
			if (user.getPassWord() != null) {
				existUser.setPassWord(user.getPassWord());
			}
			if (user.getPhone() != null) {
				existUser.setPhone(user.getPhone());
			}
			service.updateUser(existUser);
			return new ResponseEntity<Object>("\"Edit success!\"", HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(Constants.FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/userprofile", method = RequestMethod.GET)
	public ResponseEntity<Object> getUserProfileFromToken() {
		Authentication au = SecurityContextHolder.getContext().getAuthentication();
		String username = au.getName();
		Users user = service.getUsersByUserName(username);
		if (user != null) {
			return new ResponseEntity<Object>(user, HttpStatus.OK);
		}
		return new ResponseEntity<Object>("\"Fail to load user profile\"", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/teamleadfree", method = RequestMethod.GET)
	public ResponseEntity<Object> getListLeader() {
		Date now = dateManager.getCurrentDate();
		List<Project> projects = projectRepository.findAllProjectByTimeStartLessThanEqualAndTimeEndGreaterThanEqual(now,
				now);
		Collection<Integer> teamLeadIdInProject = new ArrayList<Integer>();
		if (projects != null) {
			for (Project p : projects) {
				teamLeadIdInProject.add(p.getTeamlead().getUserId());
			}
		}
		List<Users> leaders = service.getAllTeamLead();
		List<Users> listLeaderFree = new ArrayList<Users>();
		Collection<Integer> allLeaderId = new ArrayList<Integer>();
		if (leaders != null) {
			for (Users u : leaders) {
				allLeaderId.add(u.getUserId());
			}
			allLeaderId.removeAll(teamLeadIdInProject);
			for (Integer i : allLeaderId) {
				listLeaderFree.add(service.getUserById(i));
			}
			return new ResponseEntity<Object>(listLeaderFree, HttpStatus.OK);
		}
		return new ResponseEntity<Object>("\"Fail to load leader\"", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/memberfree", method = RequestMethod.GET)
	public ResponseEntity<Object> getListMember() {
		try {
			List<Users> usersList = userRepository.findUsersByListRolesRoleName("ROLE_MEMBER");
			List<Users> dataResponse = new ArrayList<>();
			for (Users user : usersList) {
				List<Project> projectList = user.getProjects();
				if (null == projectList || projectList.size() == 0) {
					dataResponse.add(user);
				} else {
					for (Project project : projectList) {
						Date startDate = project.getTimeStart();
						Date endDate = project.getTimeEnd();
						java.util.Date dateNow = new java.util.Date();
						if (startDate.getTime() > dateNow.getTime() || endDate.getTime() < dateNow.getTime()) {
							dataResponse.add(user);
						}
					}
				}
			}
			return new ResponseEntity<Object>(dataResponse, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>("\"Fail to load member free\"", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/deleteuser/{userId}", method = RequestMethod.POST)
	public ResponseEntity<Object> deleteUser(@PathVariable("userId") Integer userId) {
		if (service.deleteUser(userId)) {
			return new ResponseEntity<Object>("\"Delete success\"", HttpStatus.OK);
		}
		return new ResponseEntity<Object>("\"Delete fail\"", HttpStatus.OK);
	}
}
