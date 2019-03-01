package com.itsol.smartoffice.utils;

import java.util.List;

import com.itsol.smartoffice.model.Users;
import com.itsol.smartoffice.repository.UserRepository;

public class Validations {
	/**
	 * @param email
	 * @param users
	 * @return
	 */
	public static Boolean isExistedEmail(String email, List<Users> users) {
		for (int i = 0; i < users.size(); i++) {
			if (email.equals(users.get(i).getEmail())) { // duyệt list user nếu trùng với email return true
				return true;
			}
		}
		return false;// ngược lại return false
	}

	/**
	 * @param userName
	 * @param users
	 * @return
	 */
	public static Boolean isExistedUserName(String userName, List<Users> users) {
		for (int i = 0; i < users.size(); i++) {
			if (userName.equals(users.get(i).getUserName())) { // duyệt list user nếu trùng với username return true
				return true;
			}
		}
		return false;// ngược lại return false
	}

	public static Boolean isPhoneNumber(String phone) {
		return phone.matches("/d{11}");
	}

	public static Boolean isValidLogin(Users users, List<Users> list) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getUserName().equals(users.getUserName())
					&& list.get(i).getPassWord().equals(users.getPassWord())) {
				return true;
			}
		}
		return false;
	}
}
