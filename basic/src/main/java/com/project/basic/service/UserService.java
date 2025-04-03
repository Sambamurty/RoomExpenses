package com.project.basic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.basic.model.UserInfo;
import com.project.basic.model.UserPrinciple;
import com.project.basic.repository.UserInfoRepo;

@Service
public class UserService implements UserDetailsService{
	@Autowired
	private UserInfoRepo userinfoRepo;
	@Override
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		UserInfo userInfo = userinfoRepo.findByUsername(usernameOrEmail);
		if(userInfo == null){
			userInfo = userinfoRepo.findByEmail(usernameOrEmail);
		}
		if(userInfo == null)
			 throw new UsernameNotFoundException("User not Found.");
		return new UserPrinciple(userInfo);
	}

}
