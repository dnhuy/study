package com.itsol.smartoffice.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.itsol.smartoffice.dto.IssuesDto;
import com.itsol.smartoffice.model.Project;
import com.itsol.smartoffice.model.Users;
import com.itsol.smartoffice.repository.ProjectRepository;
import com.itsol.smartoffice.utils.Constants;

@Service
public class ProjectService {
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private UsersService usersService;
	public List<Project> getAllProject() {
		return projectRepository.findAll();
	}

	public ResponseEntity<Object> getUsersByProjectId(Integer projectId) {
		List<Users> pro = projectRepository.getOne(projectId).getUsers();
		if (pro != null) {
			return new ResponseEntity<Object>( pro, HttpStatus.OK);
		}
		return new ResponseEntity<Object>( Constants.FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	public Page<Project> getTenProject(Pageable pageable) {
		return projectRepository.getTenProject(pageable);
	}

	public ResponseEntity<Object> getProjectById(Integer projectId) {
		Project project = projectRepository.findByProjectId(projectId);
		if (project != null) {
			return new ResponseEntity<Object>( project, HttpStatus.OK);
		}
		return new ResponseEntity<Object>( Constants.FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	public ResponseEntity<Object> createProject(Project project) {
		try {
			projectRepository.save(project);
			return new ResponseEntity<Object>( Constants.SUCCESS, HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<Object>( Constants.FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<Object> updateProject(Project project) {
		try {
			projectRepository.save(project);
			return new ResponseEntity<Object>( Constants.SUCCESS, HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<Object>( Constants.FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public String deleteProject(Integer projId) {
		try {
			projectRepository.deleteById(projId);
			return Constants.SUCCESS;
		} catch (IllegalArgumentException e) {
			return Constants.FAIL;
		}
		
	}

	public void exportToExcel(String path) {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("list project");
		List<Project> projects = projectRepository.findAll();
		int rownum = 0;
		Row row;
		Cell cell;
		XSSFCellStyle headerStyle = createStyleForTitle(workbook);
		CellStyle dateStyle = createDateStyle(workbook);
		row = sheet.createRow(0);
		cell = row.createCell(0);
		cell.setCellValue("PROJECT ID");
		cell.setCellStyle(headerStyle);
		cell = row.createCell(1, CellType.STRING);
		cell.setCellValue("PROJECT NAME");
		cell.setCellStyle(headerStyle);
		cell = row.createCell(2);
		cell.setCellValue("TEAM LEADER ID");
		cell.setCellStyle(headerStyle);
		cell = row.createCell(3);
		cell.setCellValue("TIME START");
		cell.setCellStyle(headerStyle);
		cell = row.createCell(4);
		cell.setCellValue("TIME END");
		cell.setCellStyle(headerStyle);
		for (Project p : projects) {
			rownum++;
			row = sheet.createRow(rownum);
			cell = row.createCell(0);
			cell.setCellValue(p.getProjectId());
			cell = row.createCell(1);
			cell.setCellValue(p.getProjectName());
			cell = row.createCell(2);
			cell.setCellValue(p.getTeamlead().getFullName());
			cell = row.createCell(3);
			cell.setCellValue(p.getTimeStart());
			cell.setCellStyle(dateStyle);
			cell = row.createCell(4);
			cell.setCellValue(p.getTimeEnd());
			cell.setCellStyle(dateStyle);
		}
		File file = new File(path);
		file.getParentFile().mkdirs();
		FileOutputStream outFile = null;
		try {
			outFile = new FileOutputStream(file);
			workbook.write(outFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				outFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				workbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static XSSFCellStyle createStyleForTitle(XSSFWorkbook workbook) {
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		XSSFCellStyle style = workbook.createCellStyle();
		style.setFont(font);
		return style;
	}

	private static CellStyle createDateStyle(XSSFWorkbook workbook) {
		CellStyle cellStyle = workbook.createCellStyle();
		CreationHelper createHelper = workbook.getCreationHelper();
		short dateFormat = createHelper.createDataFormat().getFormat("yyyy-dd-MM");
		cellStyle.setDataFormat(dateFormat);
		return cellStyle;
	}
	public ResponseEntity<Object> checkUserWorkingProject(IssuesDto issuesDto) {
		Users users = usersService.getProjectWorkingByUserInDay(issuesDto.getUserId(), issuesDto.getLastDate());
		if (null == users || users.getProjects().size() < 1) {
			return new ResponseEntity<>(false, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(true, HttpStatus.OK);
		}
	}
}