package com.example.chat.service;

import java.util.List;

import com.example.chat.entity.User;

public interface UserService {
	User saveUser(User user);

	List<User> getAllUsers();

	User getUserById(int id);

	User getUserByPhoneNumber(String phoneNumber);

	User updateUser(User user);

	String checkLogin(User user);

	User checkUserJWT(String token);

	List<User> searchUser(String search);

	// Lam them
	String checkUpdateProfile(User user);

	String checkPassword(User user);

	Boolean checkPasswordRegex(String pass);

	Boolean checkUserName(String name);

	Boolean checkEmail(String email);
	//Lâm thêm kiểm tra email trùng ngày 25/10/2021
	Boolean checkPhone(String phone);
	
	Boolean checkSameEmail(String email, int userid);
	
	Boolean checkSamePhone(String phone);
	
	Boolean checkSameEmailSignup(String email);


	//Hào
	String checkAccount(User user);
	String checkAccountUpdate(User user);
	Boolean resetPassword(String phoneNumber,String password);
	void voHieuHoaSocket(int userId);

	//12/11Lam them
	Boolean updateUserProfile(int userId, String userName, String email, String gender);
}
