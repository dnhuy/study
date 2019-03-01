package com.itsol.smartoffice.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itsol.smartoffice.model.Roles;
import com.itsol.smartoffice.model.Users;
import com.itsol.smartoffice.repository.UserRepository;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user = userRepository.findByUserName(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}

		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		List<Roles> roles = user.getListRoles();
		for (Roles role : roles) {
			grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleName()));
		}

		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassWord(),
				grantedAuthorities);
	}
}
