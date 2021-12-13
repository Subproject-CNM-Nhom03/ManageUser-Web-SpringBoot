package com.example.chat.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.chat.entity.Authentication;
import com.example.chat.entity.User;
import com.example.chat.repository.AuthenticationRepository;
import com.example.chat.service.AuthenticationService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService{

	@Autowired
	private AuthenticationRepository authenticationRepository;
	
	@Override
	public List<Authentication> getListAuthenticationByUser(User user) {
		
		return authenticationRepository.getListAuthenticationByUser(user);
	}

	//---DAT---------
	@Override
	public Authentication saveAuthentication(Authentication authentication) {
		return authenticationRepository.saveAndFlush(authentication);
	}

}
