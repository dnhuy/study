package com.itsol.smartoffice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.itsol.smartoffice.model.News;
import com.itsol.smartoffice.service.NewsService;

@RequestMapping(value = "/newscontroller")
@RestController
public class NewsController {
    @Autowired
    private NewsService newsService;
    @PostMapping("/createnews")
    public ResponseEntity<Object> createNews(@RequestBody News news) {
        return newsService.createNews(news);
    }

    @GetMapping("/findnewspage")
    public ResponseEntity<Object> findNewsPage(@RequestParam("page") int page) {
        return newsService.findPageAndSorting(page, 10);
    }
    @GetMapping("/findallnews")
    public ResponseEntity<Object> findAllNews() {
        return newsService.findAllNews();
    }
    @RequestMapping(value="news/{newsId}",
			method=RequestMethod.GET,
			produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ResponseBody
	public ResponseEntity<News> getNewsById(@PathVariable("newsId") Integer newsId) {
		News news = newsService.getNewsById(newsId);
		if (news == null) {
			return new ResponseEntity<News>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<News>(news, HttpStatus.OK);
	}

}