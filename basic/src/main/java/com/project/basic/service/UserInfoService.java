package com.project.basic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.basic.model.UserInfo;
import com.project.basic.repository.UserInfoRepo;

@Service
public class UserInfoService {
	@Autowired
	UserInfoRepo userinfoRepo;
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
	public String registerUser(UserInfo user) {
		if(userinfoRepo.findByUsername(user.getUsername()) == null ) {
			user.setPassword(encoder.encode(user.getPassword()));
			userinfoRepo.save(user);
			return "home";
		}
		return "User Already Exists";
	}
	
}
