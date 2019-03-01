package com.itsol.smartoffice.dto;

import com.itsol.smartoffice.model.Users;
import org.modelmapper.ModelMapper;

import java.io.Serializable;
import java.sql.Date;

public class NewsDto implements Serializable {
    private Integer newsId;

    private String title;

    private String link;

    private String categories;

    private String content;

    private Date postDate;

    private String thumbnail;

    private UserDto userDto;

    private Users users;

    public NewsDto(Integer newsId, String title, String link, String categories, String content, Users users, Date postDate, String thumbnail) {
        this.newsId = newsId;
        this.title = title;
        this.link = link;
        this.categories = categories;
        this.content = content;
        this.postDate = postDate;
        this.thumbnail = thumbnail;
        this.users = users;
    }

    public NewsDto() {
    }

    public Integer getNewsId() {
        return newsId;
    }

    public void setNewsId(Integer newsId) {
        this.newsId = newsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public UserDto getUsers() {
        return new ModelMapper().map(users, UserDto.class);
    }
    public void setUsers(Users users) {
        this.users = users;
    }
}
