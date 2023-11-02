package com.hackernews.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String email;
	private String password;
	@Column(length=2000)
	private String about;

	@OneToMany(mappedBy = "user")
	private List<Content> contents;

	@OneToMany(mappedBy = "user")
	private List<Comment> comments;

	@ElementCollection
	private List<Integer> hiddenConnentIds;

	@ManyToMany
	@JoinTable(
			name = "user_contents",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "content_id")
			)
	private List<Content> upVoteContent;
	
	@ManyToMany
	@JoinTable(
			name = "user_comment",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "comment_id")
			)
	private List<Comment> upVoteComment;
	@Column(name="roles")
    private String roles = "ROLE_AUTHOR";
	

	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}

	public User() {
		super();
	}

	public User(int id, String name, String email, String password, List<Content> contents, List<Comment> comments,
			List<Integer> hiddenConnentIds, List<Content> upVoteContent, List<Comment> upVoteComment) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.contents = contents;
		this.comments = comments;
		this.hiddenConnentIds = hiddenConnentIds;
		this.upVoteContent = upVoteContent;
		this.upVoteComment = upVoteComment;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Content> getContents() {
		return contents;
	}

	public void setContents(List<Content> contents) {
		this.contents = contents;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<Integer> getHiddenConnentIds() {
		return hiddenConnentIds;
	}

	public void setHiddenConnentIds(List<Integer> hiddenConnentIds) {
		this.hiddenConnentIds = hiddenConnentIds;
	}

	public List<Content> getUpVoteContent() {
		return upVoteContent;
	}

	public void setUpVoteContent(List<Content> upVoteContent) {
		this.upVoteContent = upVoteContent;
	}

	public List<Comment> getUpVoteComment() {
		return upVoteComment;
	}

	public void setUpVoteComment(List<Comment> upVoteComment) {
		this.upVoteComment = upVoteComment;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}
	





}
