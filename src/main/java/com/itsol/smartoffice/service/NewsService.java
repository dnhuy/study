package com.itsol.smartoffice.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.itsol.smartoffice.dto.NewsDto;
import com.itsol.smartoffice.model.News;
import com.itsol.smartoffice.model.Users;
import com.itsol.smartoffice.repository.NewsRepository;
import com.itsol.smartoffice.utils.DtoManager;

@Service
public class NewsService {
	@Autowired
	private NewsRepository newsRepository;

	@Autowired
	private UsersService usersService;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private DtoManager dtoManager;

	public ResponseEntity<Object> createNews(News news) {
		try {

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userName = auth.getName();
			Users user = usersService.getUsersByUserName(userName);
			news.setUsers(user);
			newsRepository.save(news);
			NewsDto newsDto = modelMapper.map(news, NewsDto.class);
			return new ResponseEntity<>(newsDto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<Object> findPage(Integer page, Integer size) {
		try {
			Sort sortable = Sort.by("newsId").descending();
			Pageable pageable = PageRequest.of(page, size, sortable);
			List<News> listNews = newsRepository.findNews(pageable);
			List<NewsDto> listNewsDto = new ArrayList<>();
			dtoManager.convertToListDto(listNews, listNewsDto, NewsDto.class);
			return new ResponseEntity<>(listNewsDto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<Object> findAllNews() {
		try {
			List<News> listNews = newsRepository.findAll();
			List<NewsDto> listNewsDto = new ArrayList<>();
			dtoManager.convertToListDto(listNews, listNewsDto, NewsDto.class);
			return new ResponseEntity<>(listNewsDto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public News getNewsById(Integer newsId) {
		return newsRepository.findByNewsId(newsId);
	}

	public ResponseEntity<Object> findPageAndSorting(int page, int size) {
		try {
			Sort sortable = Sort.by("newsId").descending();
			Pageable pageable = PageRequest.of(page, size, sortable);
			Page<News> newsPage = newsRepository.findAll(pageable);
			List<News> newsList = newsPage.getContent();
			List<NewsDto> newsDtos = new ArrayList<>();
			dtoManager.convertToListDto(newsList, newsDtos, NewsDto.class);
			Page<NewsDto> newsDtoPage = new PageImpl<NewsDto>(newsDtos, pageable, newsPage.getTotalElements());
			return new ResponseEntity<>(newsDtoPage, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}