package com.project.basic.controller;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.basic.model.UserInfo;
import com.project.basic.service.UserInfoService;

@Controller
public class HomeController {
	
	@Autowired	
	UserInfoService userinfoService;
	@GetMapping("/")
	public String home(Model model)
	{
		return "home";
	}
	@GetMapping("/login")
	public String login()
	{
		return "login";
	}
	@GetMapping("/register")
	public String register()
	{
		return "register";
	}
	
	@PostMapping("/register")
	public String registerPost(@ModelAttribute("user") UserInfo user,
			@RequestParam(value = "subscribe", required = false) boolean subscribe,Model model)
	{
		Collection<String> roles = new ArrayList<>();	
		roles.add("ROLE_USER");
		if(subscribe)
		{
			roles.add("ROLE_SUBSCRIBER");
		}
		user.setRole(roles);
		String res = userinfoService.registerUser(user);
		if(res.equals("home"))
		{
			return "redirect:/login";
		}
		else
		{
			model.addAttribute("error",res);
			return "register";
		}
	}
	@GetMapping("/books")
	public String books()
	{
		return "books";
	}
	@GetMapping("/magazine")
	public String magazine()
	{
		return "magazine";
	}
}
