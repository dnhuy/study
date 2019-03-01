package com.itsol.smartoffice.rest;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import com.itsol.smartoffice.model.Users;
import com.itsol.smartoffice.service.Jwtservice;
import com.itsol.smartoffice.service.UsersService;

public class JwtAuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {
	private final static String TOKEN_HEADER = "Authorization";
	@Autowired
	private Jwtservice jwtService;
	@Autowired
	private UsersService userService;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String authToken = httpRequest.getHeader(TOKEN_HEADER);
		if (jwtService.validateTokenLogin(authToken)) {
			String username = jwtService.getUsernameFromToken(authToken);
			Users user = userService.getUsersByUserName(username);
			if (user != null) {
				boolean enabled = true;
				boolean accountNonExpired = true;
				boolean credentialsNonExpired = true;
				boolean accountNonLocked = true;
				UserDetails userDetail = new User(username, user.getPassWord(), enabled, accountNonExpired,
						credentialsNonExpired, accountNonLocked, user.loadAuthorities());
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail,
						null, userDetail.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}
		chain.doFilter(request, response);
	}
}
