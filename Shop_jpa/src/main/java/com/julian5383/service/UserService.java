package com.julian5383.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.julian5383.entity.User;
import com.julian5383.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	public User create(String username, String email, String password) {
		User user = new User();
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(encoder.encode(password));
		this.userRepository.save(user);
		return user;
	}
	
	public User getUser(String username) {
		Optional<User> user = this.userRepository.findByUsername(username);
		return user.get();
	}
	
}
