package com.example.chat.serviceImpl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.chat.entity.User;
import com.example.chat.service.UserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
//    @Autowired
//    private UserRepository userRepository;

	@Autowired
	private UserService userService;
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String phoneNumber) {
    	
    	User user=userService.getUserByPhoneNumber(phoneNumber);
    	
        
//        if (user == null) throw new UsernameNotFoundException(username);
//
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        //for (Role role : user.getRoles()){
            grantedAuthorities.add(new SimpleGrantedAuthority("ADMIN"));
        //}
        if(user!=null) {
        	return new org.springframework.security.core.userdetails.User("#", user.getPassword(), grantedAuthorities);
        }
        return new org.springframework.security.core.userdetails.User("@", "{noop}123", grantedAuthorities);
        //return null;
    }
}
