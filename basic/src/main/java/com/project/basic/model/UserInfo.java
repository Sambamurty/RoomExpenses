package com.project.basic.model;

import java.util.Collection;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table; 

@Entity
@Table(name="userInfo")
public class UserInfo {
	@Id
	@Column(name="username")
	private String username;
	
	@Column(nullable = false)
	private String password;
	
	@Column(name="email")
	private String email; 
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "authorities", joinColumns = @JoinColumn(name = "username"))
	@Column(name = "authority")
	private Collection<String> roles;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Collection<String> getRole() {
		return roles;
	}
	public void setRole(Collection<String> roles) {
		this.roles = roles;
	}
	
	
}
