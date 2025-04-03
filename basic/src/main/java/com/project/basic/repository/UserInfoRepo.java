package com.project.basic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.basic.model.UserInfo;

@Repository
public interface UserInfoRepo extends JpaRepository<UserInfo, String>{
	UserInfo findByUsername(String username);

	UserInfo findByEmail(String usernameOrEmail);
}
