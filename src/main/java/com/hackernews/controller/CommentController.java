package com.hackernews.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hackernews.entity.Comment;
import com.hackernews.entity.Content;
import com.hackernews.entity.User;
import com.hackernews.repository.CommentRepositroy;
import com.hackernews.repository.ContentRepositroy;
import com.hackernews.repository.UserRepository;

@Controller
public class CommentController {
	
	@Autowired 
	CommentRepositroy commentRepositroy;
	@Autowired
	ContentRepositroy contentRepositroy;
	@Autowired
	UserRepository userRepository;
	
	public User getUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User author = userRepository.getUserByUserName(username);
        return author;
	}
	
	@PostMapping("/comment")
	public String addcomment(@RequestParam(name = "id", required = false) int id,
			@RequestParam(name = "comment", required = false ) String comment ,Model model) {
		Content content = contentRepositroy.findById(id).get();
		Comment newComment = new Comment();
		newComment.setComment(comment);
		newComment.setSubmitTime(new Timestamp(new Date().getTime()));
		newComment.setContent(content);
		newComment.setUser(getUser());
		
		commentRepositroy.save(newComment);
		List<Comment> allComments = content.getComments();
		if(allComments==null) {
			allComments = new ArrayList<>();
		}
		allComments.add(newComment);
		contentRepositroy.save(content);
		
		
		return "redirect:/showcontent?id="+id;
	}
	
	@GetMapping("/newcomments")
	public String allComments(Model model) {
		List<Comment> allComments = commentRepositroy.findAll(Sort.by(Sort.Direction.DESC, "submitTime"));
		User user = getUser();
		
		model.addAttribute("user",user);
		model.addAttribute("comments",allComments);
		
		return "allComments";
	}
	@GetMapping("/threads")
	public String hidePage(@RequestParam("id") String id, Model model) {
		
		User author = userRepository.findById(Integer.parseInt(id)).get();
		List<Comment> comments = author.getComments();
		
		User user = getUser();
		model.addAttribute("comments",comments);
		model.addAttribute("user",user);
		
		return "allComments";
	}
	
	@GetMapping("/upvotecomment")
	public String upVoteComment(@RequestParam("id") String id, Model model) {
		Comment comment = commentRepositroy.findById(Integer.parseInt(id)).get();
		User user = getUser();
		List<Comment> upVoteComment = user.getUpVoteComment();
		if(upVoteComment==null) {
			upVoteComment = new ArrayList<>();
		}
		upVoteComment.add(comment);
		userRepository.save(user);
		
		return "redirect:/newcomments";
	}
	@GetMapping("/unvotecomment")
	public String unVote(@RequestParam("id") String id, Model model) {
		Comment comment = commentRepositroy.findById(Integer.parseInt(id)).get();
		User user = getUser();
		List<Comment> upVoteComment = user.getUpVoteComment();
		upVoteComment.remove(comment);
		userRepository.save(user);
		
		return "redirect:/newcomments";
	}
	
	@GetMapping("/upvotedcomment")
	public String upVotedContent(@RequestParam("id") String id, Model model) {
		
		User user = userRepository.findById(Integer.parseInt(id)).get();
		
		List<Comment> upVoteComment = user.getUpVoteComment();
		
		
		model.addAttribute("comments", upVoteComment);
		model.addAttribute("user",user);
		
		return "upvotecomment";
	}
	
	@GetMapping("/unvotedcomment")
	public String unVotedPage(@RequestParam("id") String id, Model model) {
		
		Comment comment = commentRepositroy.findById(Integer.parseInt(id)).get();
		User user = getUser();
		
		List<Comment> upVoteComment = user.getUpVoteComment();
		upVoteComment.remove(comment);
		
		user.setUpVoteComment(upVoteComment);
		userRepository.save(user);
		
		return "redirect:/upvotedcomment?id="+user.getId();
	}
}
