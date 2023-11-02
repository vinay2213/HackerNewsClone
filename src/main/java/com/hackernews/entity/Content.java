package com.hackernews.entity;

import java.sql.Timestamp;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="contents")
public class Content {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String title;
	private String url;
	@Column(length = 5000)
	private String text;
	private String catagory;

	@ManyToOne
	@JoinColumn(name = "author")
	private User user;
	
	@OneToMany(mappedBy = "content")
	private List<Comment> comments;
	
	@Column(columnDefinition ="TIMESTAMP")
	private Timestamp submitTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getText() {
		return text;
	}

	public void setContent(String content) {
		this.text = content;
	}

	public String getCatagory() {
		return catagory;
	}

	public void setCatagory(String catagory) {
		this.catagory = catagory;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public Timestamp getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Timestamp timestamp) {
		this.submitTime = timestamp;
	}

	public Content(int id, String title, String url, String content, String catagory, com.hackernews.entity.User user,
			List<com.hackernews.entity.Comment> comments, Timestamp submitTime) {
		super();
		this.id = id;
		this.title = title;
		this.url = url;
		this.text = content;
		this.catagory = catagory;
		this.user = user;
		this.comments = comments;
		this.submitTime = submitTime;
	}

	public Content() {
		super();
	}

	
	
}
