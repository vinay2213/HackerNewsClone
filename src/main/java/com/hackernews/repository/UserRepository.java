package com.hackernews.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hackernews.entity.User;

public interface UserRepository extends JpaRepository<User,Integer>{
	@Query("select u from User u where u.email = :email")
	public User getUserByUserName(String email);
}
