package com.itsol.smartoffice.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.itsol.smartoffice.rest.CustomAccessDeniedHandler;
import com.itsol.smartoffice.rest.JwtAuthenticationTokenFilter;
import com.itsol.smartoffice.rest.RestAuthenticationEntryPoint;
import com.itsol.smartoffice.service.UserDetailsServiceImpl;
import com.itsol.smartoffice.utils.DtoManager;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() throws Exception {
		JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter = new JwtAuthenticationTokenFilter();
		jwtAuthenticationTokenFilter.setAuthenticationManager(authenticationManager());
		return jwtAuthenticationTokenFilter;
	}

	@Bean
	@Scope("singleton")
	public DtoManager dtoManager() {
		return new DtoManager(modelMapper());
	}

	@Bean
	public RestAuthenticationEntryPoint restServicesEntryPoint() {
		return new RestAuthenticationEntryPoint();
	}

	@Bean
	public CustomAccessDeniedHandler customAccessDeniedHandler() {
		return new CustomAccessDeniedHandler();
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	protected void configure(HttpSecurity http) throws Exception {
		// Disable crsf cho đường dẫn /rest/**
		http.csrf().ignoringAntMatchers("/**");
		http.authorizeRequests().antMatchers("/usercontroller/userdetail").permitAll()
				.antMatchers(HttpMethod.POST, "/usercontroller/forgotpassword").permitAll()
				.antMatchers(HttpMethod.POST, "/usercontroller/register").permitAll()
				.antMatchers(HttpMethod.POST, "/usercontroller/login").permitAll()
				.antMatchers(HttpMethod.GET, "/newscontroller/news/**").permitAll()
				.antMatchers(HttpMethod.GET, "/newscontroller/find**").permitAll()
				.antMatchers(HttpMethod.GET, "/usercontroller/active/**").permitAll()
				.antMatchers(HttpMethod.GET, "/usercontroller/userprofile").permitAll()
				.antMatchers(HttpMethod.GET, "/usercontroller/userrole").permitAll();
		http.antMatcher("/**").httpBasic().authenticationEntryPoint(restServicesEntryPoint()).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				// issues
				.antMatchers("/issuescontroller?**")
				.access("hasRole('ROLE_MEMBER') " + "or hasRole('ROLE_TEAMLEAD')" + "or hasRole('ROLE_MANAGER')")
				// user
				.antMatchers("/usercontroller/setrole?").access("hasRole('ROLE_HR') " + "or hasRole('ROLE_MANAGER')")
				.antMatchers("/usercontroller/teamleadfree?")
				.access("hasRole('ROLE_HR') " + "or hasRole('ROLE_MANAGER')")
				
				.antMatchers("/usercontroller/memberfree?")
				.access("hasRole('ROLE_HR') " + "or hasRole('ROLE_MANAGER')")
				
				.antMatchers(HttpMethod.POST, "/usercontroller/deleteuser/**")
				.access("hasRole('ROLE_HR') " + "or hasRole('ROLE_MANAGER')")
				// timesheet
				.antMatchers("/timesheetcontroller/timesheetsuser")
				.access("hasRole('ROLE_MEMBER') " + "or hasRole('ROLE_TEAMLEAD')" + "or hasRole('ROLE_MANAGER')" + "or hasRole('ROLE_HR')")

				.antMatchers("/timesheetcontroller/insert?")
				.access("hasRole('ROLE_MEMBER') " + "or hasRole('ROLE_TEAMLEAD')" + "or hasRole('ROLE_MANAGER')" + "or hasRole('ROLE_HR')")

				.antMatchers("/timesheetcontroller/timesheetdetail")
				.access("hasRole('ROLE_MEMBER') " + "or hasRole('ROLE_TEAMLEAD')" + "or hasRole('ROLE_MANAGER')" + "or hasRole('ROLE_HR')")

				.antMatchers("/timesheetcontroller/update?")
				.access("hasRole('ROLE_MEMBER') " + "or hasRole('ROLE_TEAMLEAD')" + "or hasRole('ROLE_MANAGER')" + "or hasRole('ROLE_HR')")

				.antMatchers("/timesheetcontroller/delete?")
				.access("hasRole('ROLE_MEMBER') " + "or hasRole('ROLE_TEAMLEAD')" + "or hasRole('ROLE_MANAGER')" + "or hasRole('ROLE_HR')")

				.antMatchers("/timesheetcontroller/timesheets")
				.access("hasRole('ROLE_TEAMLEAD')" + "or hasRole('ROLE_MANAGER')" + "or hasRole('ROLE_MANAGER')")

				// dayoff
				.antMatchers(HttpMethod.GET, "/dayoffcontroller/user/**")
				.access("hasRole('ROLE_MEMBER') " + "or hasRole('ROLE_TEAMLEAD')" + "or hasRole('ROLE_MANAGER')")

				.antMatchers(HttpMethod.GET, "/dayoffcontroller/list?**")
				.access("hasRole('ROLE_TEAMLEAD') " + "or hasRole('ROLE_MANAGER')" + "or hasRole('ROLE_HR')")

				.antMatchers(HttpMethod.GET, "/dayoffcontroller/list/new?**")
				.access("hasRole('ROLE_TEAMLEAD') " + "or hasRole('ROLE_MANAGER')" + "or hasRole('ROLE_HR')")

				.antMatchers(HttpMethod.GET, "/dayoffcontroller/list/approved?**")
				.access("hasRole('ROLE_TEAMLEAD') " + "or hasRole('ROLE_MANAGER')" + "or hasRole('ROLE_HR')")

				.antMatchers(HttpMethod.GET, "/dayoffcontroller/list/notapproved?**")
				.access("hasRole('ROLE_TEAMLEAD') " + "or hasRole('ROLE_MANAGER')" + "or hasRole('ROLE_HR')")

				.antMatchers(HttpMethod.GET, "/dayoffcontroller/list/projectId/?**")
				.access("hasRole('ROLE_TEAMLEAD') " + "or hasRole('ROLE_MANAGER')" + "or hasRole('ROLE_HR')")

				.antMatchers(HttpMethod.POST, "/dayoffcontroller/approved?**")
				.access("hasRole('ROLE_TEAMLEAD') " + "or hasRole('ROLE_MANAGER')" + "or hasRole('ROLE_HR')")

				.antMatchers(HttpMethod.POST, "/dayoffcontroller/notapproved?**")
				.access("hasRole('ROLE_TEAMLEAD') " + "or hasRole('ROLE_MANAGER')" + "or hasRole('ROLE_HR')")

				.antMatchers(HttpMethod.POST, "/dayoffcontroller/add?")
				.access("hasRole('ROLE_MEMBER') " + "or hasRole('ROLE_TEAMLEAD')" + "or hasRole('ROLE_MANAGER')")

				// project
				.antMatchers(HttpMethod.GET, "/projectcontroller/detail")
				.access("hasRole('ROLE_TEAMLEAD') " + "or hasRole('ROLE_MANAGER')" + "or hasRole('ROLE_HR')")

				.antMatchers(HttpMethod.POST, "/projectcontroller/createproject")
				.access("hasRole('ROLE_MANAGER') " + "or hasRole('ROLE_HR')")

				.antMatchers(HttpMethod.POST, "/projectcontroller/editproject")
				.access("hasRole('ROLE_MANAGER') " + "or hasRole('ROLE_HR')")

				.antMatchers("/projectcontroller/tenproject")
				.access("hasRole('ROLE_MANAGER') " + "or hasRole('ROLE_HR')")

				.antMatchers("/projectcontroller/users").access("hasRole('ROLE_MANAGER') " + "or hasRole('ROLE_HR')")

				.antMatchers("/projectcontroller/allproject")
				.access("hasRole('ROLE_MANAGER') " + "or hasRole('ROLE_HR')")

				.antMatchers("/projectcontroller/export").access("hasRole('ROLE_MANAGER') " + "or hasRole('ROLE_HR')")

				.antMatchers("/projectcontroller/countusermisstimesheet?**")
				.access("hasRole('ROLE_MANAGER') " + "or hasRole('ROLE_HR')")
				// new
				.antMatchers(HttpMethod.POST, "/newscontroller/createnews").access("hasRole('ROLE_HR')")
				// comment
				.antMatchers("/comment-issues-controller")
				.access("hasRole('ROLE_MEMBER') " + "or hasRole('ROLE_TEAMLEAD')" + "or hasRole('ROLE_MANAGER')"
						+ "or hasRole('ROLE_HR')")
				// logissues
				.antMatchers("/logissuescontroller")
				.access("hasRole('ROLE_MEMBER') " + "or hasRole('ROLE_TEAMLEAD')" + "or hasRole('ROLE_MANAGER')"
						+ "or hasRole('ROLE_HR')")
				.and().addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class)

				.exceptionHandling().accessDeniedHandler(customAccessDeniedHandler());
	}
}
