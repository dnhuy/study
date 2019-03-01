package com.itsol.smartoffice.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itsol.smartoffice.dto.IssuesDto;
import com.itsol.smartoffice.model.Project;
import com.itsol.smartoffice.service.ProjectService;
import com.itsol.smartoffice.service.UsersService;

@RequestMapping(value = "/projectcontroller")
@RestController
public class ProjectController {
	@Autowired
	private ProjectService service;
	@Autowired
	private UsersService userService;

	@RequestMapping(value = "/allproject")
	public ResponseEntity<List<Project>> getAllProject() {
		return new ResponseEntity<List<Project>>(service.getAllProject(), HttpStatus.OK);
	}

	@RequestMapping(value = "/createproject", method = RequestMethod.POST)
    public ResponseEntity<Object> createProject(@RequestBody Project project) {
        return service.createProject(project);
    }

	@RequestMapping(value = "/editproject", method = RequestMethod.POST)
	public ResponseEntity<Object> editProject(@RequestBody Project project) {
		return service.updateProject(project);
	}

	@RequestMapping(value = "/detail/{projectId}", method = RequestMethod.POST)
	public ResponseEntity<Object> findProjectDetail(@PathVariable("projectId") Integer projectId) {
		return service.getProjectById(projectId);
	}

	@RequestMapping(value = "/users/{projectId}", method = RequestMethod.GET)
	public ResponseEntity<Object> getListUser(@PathVariable("projectId") Integer projectId) {
		return service.getUsersByProjectId(projectId);
	}

	@RequestMapping(value = "/tenproject/{page}")
	public ResponseEntity<Page<Project>> getTenProject(@PathVariable("page") int page) {
		Sort sort = Sort.by("projectId").ascending();
		Pageable pageable = PageRequest.of(page, 10, sort);
		Page<Project> tenProject = service.getTenProject(pageable);

		return new ResponseEntity<Page<Project>>(tenProject, HttpStatus.OK);
	}

	@RequestMapping(value = "/export")
	public void exportToExcel(@RequestHeader("path") String path) {
		service.exportToExcel(path);
	}

	@RequestMapping(value = "/countusermisstimesheet", method = RequestMethod.GET)
	public ResponseEntity<Object> getCountUserMissTimeSheet(@RequestParam("month") String month,
			@RequestParam int page) {
//		List<Project> projects = projectService.getAllProject();
		Sort sort = Sort.by("projectId").ascending();
		Pageable pageable = PageRequest.of(page, 10, sort);
		Page<Project> tenProject = service.getTenProject(pageable);
		List<Project> projects = tenProject.getContent();
		List<Integer> in = new ArrayList<>();
		for (Project project : projects) {
			in.add(userService.getCountUserMissTimeSheet(project.getProjectId(), month));
		}
		return new ResponseEntity<Object>(in, HttpStatus.OK);
	}

	@PostMapping("/check-users-working-project")
	public ResponseEntity<Object> checkUserWorkingProject(@RequestBody IssuesDto issuesDto) {
		return service.checkUserWorkingProject(issuesDto);
	}
}