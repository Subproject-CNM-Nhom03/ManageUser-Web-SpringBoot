package com.example.chat.service;

import java.util.List;

import com.example.chat.entity.Authentication;
import com.example.chat.entity.User;

public interface AuthenticationService {
	List<Authentication> getListAuthenticationByUser(User user);
	
	
	//---DAT---------
	Authentication saveAuthentication(Authentication authentication);
}
