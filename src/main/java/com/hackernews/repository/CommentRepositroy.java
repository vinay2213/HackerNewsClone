package com.hackernews.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hackernews.entity.Comment;
import com.hackernews.entity.Content;

public interface CommentRepositroy extends JpaRepository<Comment, Integer>{

}
