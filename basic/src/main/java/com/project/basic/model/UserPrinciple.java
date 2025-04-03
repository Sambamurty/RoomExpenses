package com.project.basic.model;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrinciple implements UserDetails {
	
	private UserInfo userInfo;

	public UserPrinciple(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		if(userInfo.getRole() == null)
			return null;
		return userInfo.getRole().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return userInfo.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return userInfo.getUsername();
	}

}
