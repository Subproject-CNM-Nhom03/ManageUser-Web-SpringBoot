package com.example.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.chat.service.SecurityService;
import com.example.chat.service.UserService;

@Controller("/register")
public class ResgisterController {
	@Autowired
	private UserService userService;
	
//	@GetMapping({ "/", "/welcome" })
//	public String welcome(Model model) {
//
//		model.addAttribute("listUser",userService.getAllUsers());
//		return "welcome";
//	}
}
