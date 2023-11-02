package com.hackernews.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hackernews.entity.User;
import com.hackernews.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	UserRepository userRepository;
	public void addUser(String name,String email,String password) {
		User newUser = new User();
		newUser.setName(name);
		newUser.setEmail(email);
		newUser.setPassword(password);
		userRepository.save(newUser);
	}
}
