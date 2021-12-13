package com.example.chat.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.chat.entity.Authentication;
import com.example.chat.entity.User;
import com.example.chat.service.AuthenticationService;
import com.example.chat.service.SecurityService;
import com.example.chat.service.UserService;

@Controller("/")
public class LoginController {
	@Autowired
	private SecurityService securityService;
	@Autowired
	private UserService userService;
	@Autowired
	private AuthenticationService auService;

	@GetMapping("/login")
	public String login(Model model, String error, String logout) {
		if (securityService.isAuthenticated()) {
			return "redirect:/";
		}

		if (error != null)
			model.addAttribute("error", "Your username and password is invalid.");

		if (logout != null)
			model.addAttribute("message", "You have been logged out successfully.");

		return "login";
	}

	@GetMapping({ "/", "/welcome" })
	public String welcome(Model model) {
		List<User> listU=userService.getAllUsers();
		List<User> listU2=new ArrayList<User>();
		
		for (int i=0; i<listU.size();i++) {
			for(Authentication au:listU.get(i).getListAuthentications()) {
				if(au.getRole().equals("ROLE_USER")) {
					listU2.add(listU.get(i));
					//System.out.println(listU.get(i).getUserName());
				}
			}
		}
		model.addAttribute("listUser",listU2);
		model.addAttribute("phone","");
		model.addAttribute("result","");
		return "welcome";
	}
	@GetMapping({ "/search" })
	public String search(Model model,@RequestParam("phone") String phone) {
		List<User> listU=new ArrayList<User>();
		List<User> listU2=new ArrayList<User>();
		String rs="";
		if(phone!=null&&phone!="") {
			
			User u=userService.getUserByPhoneNumber(phone);
			if(u!=null) {
				listU.add(u);
				
				for (int i=0; i<listU.size();i++) {
					for(Authentication au:listU.get(i).getListAuthentications()) {
						if(au.getRole().equals("ROLE_USER")) {
							listU2.add(listU.get(i));
							
						}else rs="User not found";
					}
				}
			}
			else {
				rs="User not found";
			}
		}
		else {
			phone="";
			return "redirect:/";
			
		}
		model.addAttribute("listUser",listU2);
		model.addAttribute("phone",phone);
		model.addAttribute("result",rs);
		return "welcome";
	}
	
	
	
	
	@GetMapping({ "/showFormForAdd"})
	public String formUser(Model model,@RequestParam(required=false,name = "error") String error) {

		User user=new User();
		user.setUserName("");
		if(model.getAttribute("x")!=null) {
			user.setEmail(model.getAttribute("x").toString());
		}
		model.addAttribute("user", user);
		model.addAttribute("error", error);
		return "user-form";
	}
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("userId") int userId,@RequestParam(required=false,name = "error") String error,
									Model theModel) {
		
		// get the employee from the service
		User u=userService.getUserById(userId);
		
		
		// set employee as a model attribute to pre-populate the form
		theModel.addAttribute("user", u);
		theModel.addAttribute("error", error);
		// send over to our form
		return "user-form";			
	}
	
	@PostMapping("user/save")
	public String saveUser(@ModelAttribute("user") User user,Model theModel) {
		
		// save the employee
		String rs=userService.checkAccount(user);
		
		if(user.getUserName().equals("")||user.getEmail().equals("")||user.getPhoneNumber().equals("")) {
			rs="Not be empty";
			if(user.getUserId()==0) {
				theModel.addAttribute("x","X");
				return "redirect:/showFormForAdd?error="+rs;
			}else return "redirect:/showFormForUpdate?userId="+user.getUserId()+"&error="+rs;
			
		}else if(rs=="true"&&user.getUserId()==0) {
			user.setAvatar("https://upload.wikimedia.org/wikipedia/commons/thumb/5/59/User-avatar.svg/1024px-User-avatar.svg.png");
			user.setPassword("123456");
			user.setGender(true);
			Authentication au=new Authentication(user, "ROLE_USER", true);
			userService.saveUser(user);
			auService.saveAuthentication(au);
			return "redirect:/";
			
		}else if(rs!="true"&&user.getUserId()==0) {
			return "redirect:/showFormForAdd?error="+rs;
		}
		else if(user.getUserId()!=0){
			String rs2=userService.checkAccountUpdate(user);
			if(rs2=="true") {
				userService.updateUser(user);
				return "redirect:/";
			}
			else 
				return "redirect:/showFormForUpdate?userId="+user.getUserId()+"&error="+rs2;
		}else {			
		}

		return "redirect:/";
	}
	
	
	@GetMapping("/vohieuhoa")
	public String delete(@RequestParam("userId") int userId) {
		
		// delete the employee
		User u=userService.getUserById(userId);
		if(u.getDisable()) {
			u.setDisable(false);
		}else {
			u.setDisable(true);
			userService.voHieuHoaSocket(userId);
		}
		
		userService.updateUser(u);


		// redirect to /employees/list
		return "redirect:/";
		
	}
}
