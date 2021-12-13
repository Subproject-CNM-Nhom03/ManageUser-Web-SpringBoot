package com.example.chat.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.chat.JWT.JwtAuthenticationTokenFilter;
import com.example.chat.JWT.RestAuthenticationEntryPoint;
import com.example.chat.JWT.UserAccessDeniedHandler;
import org.springframework.web.client.RestTemplate;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	//hao-24/10
	@Qualifier("userDetailsServiceImpl")
	@Autowired
    private UserDetailsService userDetailsService;


	//hao-24/10
	@Bean
	public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() throws Exception {
		JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter = new JwtAuthenticationTokenFilter();
		jwtAuthenticationTokenFilter.setAuthenticationManager(authenticationManager());
		return jwtAuthenticationTokenFilter;
	}

	@Bean
	public RestAuthenticationEntryPoint restServicesEntryPoint() {
		return new RestAuthenticationEntryPoint();
	}

	@Bean
	public UserAccessDeniedHandler userAccessDeniedHandler() {
		return new UserAccessDeniedHandler();
	}

	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().ignoringAntMatchers("/api/**");


		http.authorizeRequests().antMatchers("/api/login/**","/api/userfromtoken/**"
				,"/api/checkUsernameEmail","/api/users/password","/api/checkPassword","/api/checkPasswordRegex"
				,"/api/signup","/api/signup/checkvaliduser","/api/contact/**","/api/userphone/**","/api/resetPassword").permitAll();
	
		
		
		http
		//.authorizeRequests().antMatchers("/**").authenticated().and()
		.formLogin().loginPage("/login").permitAll()
        .and()
        .logout().permitAll()
		.and()
//		.antMatcher("/api/**").httpBasic().authenticationEntryPoint(restServicesEntryPoint()).and()
//				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
		.authorizeRequests()
		
		.antMatchers(HttpMethod.GET, "/api/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
		.antMatchers(HttpMethod.POST, "/api/users").permitAll()
		.antMatchers(HttpMethod.POST, "/api/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
		.antMatchers(HttpMethod.PUT, "/api/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')" )
		.antMatchers(HttpMethod.DELETE, "/api/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')" )
		.antMatchers("/css/**", "/js/**", "/registration").permitAll()
		.antMatchers("/**").authenticated()
        //.anyRequest().authenticated()
         .and()
     
		.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class)
		.exceptionHandling().accessDeniedHandler(userAccessDeniedHandler());

	//hao-24/10
//		 http
//         .authorizeRequests()
//             .antMatchers("/css/**", "/js/**", "/registration").permitAll()
//             .anyRequest().authenticated()
//             .and()
//         .formLogin()
//             .loginPage("/login")
//             .permitAll()
//             .and()
//         .logout()
//             .permitAll();
		 
		
	}
	
    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }
	//hao-24/10

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
}
