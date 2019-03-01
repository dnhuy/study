package com.itsol.smartoffice.model;


import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity@Table(name="NEWS")
public class News {
	@Id
	@Column(name = "NEWS_ID", length = 5)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer newsId;
	@Column(name="TITLE", length=100, nullable=false)
	private String title;
	@Column(name="LINK", length=100, nullable=false)
	private String link;
	@Column(name="CATEGORIES", length=50, nullable=false)
	private String categories;
	@Column(name="CONTENT", length=4000, nullable=false)
	private String content;
	@ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
	private Users users;
	@Column(name="POST_DATE", nullable=false)
	private Date postDate;
	@Column(name="THUMBNAIL", length=1000, nullable=false)
	private String thumbnail;
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
	public Users getUsers() {
		return users;
	}
	public void setUsers(Users users) {
		this.users = users;
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
	public News(Integer newsId, String title, String link, String categories, String content, Users users,
			Date postDate, String thumbnail) {
		super();
		this.newsId = newsId;
		this.title = title;
		this.link = link;
		this.categories = categories;
		this.content = content;
		this.users = users;
		this.postDate = postDate;
		this.thumbnail = thumbnail;
	}
	public News() {
		super();
	}
	
}
