package com.example.chat.JWT;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import com.example.chat.entity.Authentication;
import com.example.chat.service.AuthenticationService;
import com.example.chat.service.JwtService;
import com.example.chat.service.UserService;

public class JwtAuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter{

	private final static String TOKEN_HEADER = "authorization";
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationService authenticationService;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String authToken = httpRequest.getHeader(TOKEN_HEADER);

		if (jwtService.validateTokenLogin(authToken)) {
			String username = jwtService.getUsernameFromToken(authToken);

			com.example.chat.entity.User user = userService.getUserByPhoneNumber(username);
			if (user != null) {
				boolean enabled = true;
				boolean accountNonExpired = true;
				boolean credentialsNonExpired = true;
				boolean accountNonLocked = true;
				
				List<GrantedAuthority> autho = new ArrayList<GrantedAuthority>();
				List<Authentication> list = authenticationService.getListAuthenticationByUser(user);
				for (Authentication authentication : list) {
					autho.add(new SimpleGrantedAuthority(authentication.getRole()));
				}
				
				
				
				UserDetails userDetail = new User(username, user.getPassword(), enabled, accountNonExpired,
						credentialsNonExpired, accountNonLocked, autho);

				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail,
						null, userDetail.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}
		
		chain.doFilter(request, response);
	}
}
