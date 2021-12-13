package com.example.chat.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.chat.entity.User;
import com.example.chat.repository.UserRepository;
import com.example.chat.service.JwtService;
import com.example.chat.service.UserService;
import org.springframework.web.client.RestTemplate;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RestTemplate restTemplate;
	@Override
	public User saveUser(User user) {
		if (user.getUserId()!=0){
			return userRepository.saveAndFlush(user);
		}else{
			User u=getUserByPhoneNumber(user.getPhoneNumber());
			if(u!=null)return  user;
			else{
				user.setPassword(passwordEncoder.encode(user.getPassword()));
				return userRepository.saveAndFlush(user);
			}
		}
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAllMoiNhat();
	}

	@Override
	public User getUserById(int id) {

		return userRepository.getById(id);
	}

	@Override
	public User getUserByPhoneNumber(String phoneNumber) {

		return userRepository.getUserByPhoneNumber(phoneNumber);
	}

	@Override
	public User updateUser(User user) {
		return userRepository.save(user);

	}

	@Override
	public String checkLogin(User user) {
		User user1 = getUserByPhoneNumber(user.getPhoneNumber());
		if (user1 != null && passwordEncoder.matches(user.getPassword(),user1.getPassword())) {
			if (user1.getDisable()) {
				return "Account Disabled";
			} else
				return "True";
		}
		return "Invalid account or password";
	}

	// kiểm tra token và trả về user
	@Override
	public User checkUserJWT(String token) {
		User user = null;
		if (jwtService.validateTokenLogin(token)) {
			String sdt = jwtService.getUsernameFromToken(token);
			user = getUserByPhoneNumber(sdt);
			if (user.getDisable()) {
				return null;
			}
		}

		return user;

	}

	@Override
	public List<User> searchUser(String search) {
		return userRepository.findUserBySearch("%" + search + "%");
	}

	/// Laam them

	@Override
	public String checkUpdateProfile(User user) {

		return null;
	}

	@Override
	public String checkPassword(User user) {
		User user1 = getUserByPhoneNumber(user.getPhoneNumber());
		if (user1 != null && passwordEncoder.encode(user.getPassword()).equals(user1.getPassword())) {
			return "True";
		}
		return "Mật khẩu không chính xác";
	}

	//31/10
	@Override
	public Boolean checkPasswordRegex(String pass) {
		if(pass == null) {
			return false;
		}
		String pattern = "^[A-Za-z0-9@#$%^&]{6,}$";
		String password = pass;
		return password.matches(pattern);
	}
	// 31/10

	@Override
	public Boolean checkUserName(String name) {
		if(name == null) {
			return false;
		}
		String pattern = "^[aAàÀảẢãÃáÁạẠăĂằẰẳẲẵẴắẮặẶâÂầẦẩẨẫẪấẤậẬbBcCdDđĐeEèÈẻẺẽẼéÉẹẸêÊềỀểỂễỄếẾệỆfFgGhHiIìÌỉỈĩĨíÍịỊjJkKlLmMnNoOòÒỏỎõÕóÓọỌôÔồỒổỔỗỖốỐộỘơƠờỜởỞỡỠớỚợỢpPqQrRsStTuUùÙủỦũŨúÚụỤưƯừỪửỬữỮứỨựỰvVwWxXyYỳỲỷỶỹỸýÝỵỴzZ0-9 ]+$";
		String username = name;
		return username.matches(pattern);
	}

	//31/10
	@Override
	public Boolean checkEmail(String email) {
		if(email == null) {
			return false;
		}
		String pattern = "^[a-zA-Z0-9._]+@[a-zA-Z0-9]+.[a-zA-z]+$";
		String em = email;
		return em.matches(pattern);
	}

	// Lâm thêm ngày 25/10/2021
	@Override
	public Boolean checkSameEmail(String email, int userid) {

		User u = getUserById(userid);

		if (!u.getEmail().equals(email)) {
			if (userRepository.getUserbyEmail(email) != null) {
				return false;
			}
		}
		return true;
	}

	//31/10
	@Override
	public Boolean checkPhone(String phone) {
		if(phone == null) {
			return false;
		}
		String pattern = "^(09|01|08|03|02|07|05)[0-9]{8}$";
		String em = phone;
		return em.matches(pattern);
	}

	@Override
	public Boolean checkSameEmailSignup(String email) {
		User u = userRepository.existsEmailSignup(email);
		if (u != null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public Boolean checkSamePhone(String phone) {
		User u = userRepository.existsPhoneSignup(phone);
		if (u != null) {
			return false;
		} else {
			return true;
		}
	}

	// Hào 24/10
	@Override
	public String checkAccount(User user) {
		if (getUserByPhoneNumber(user.getPhoneNumber()) != null) {
			return "Phone number already in use";
		} else if (userRepository.getUserbyEmail(user.getEmail()) != null) {
			return "Email already in use";
		}
		return "true";
	}

	@Override
	public String checkAccountUpdate(User user) {
		User u = getUserById(user.getUserId());

		if (!user.getPhoneNumber().equals(u.getPhoneNumber())) {
			if (getUserByPhoneNumber(user.getPhoneNumber()) != null) {
				return "Phone number already in use";
			}
		} else if (!user.getEmail().equals(u.getEmail())) {
			if (userRepository.getUserbyEmail(user.getEmail()) != null) {
				return "Email already in use";
			}
		}
		return "true";

	}
	@Override
	public Boolean resetPassword(String phoneNumber, String password) {
		userRepository.resetPassword(phoneNumber, passwordEncoder.encode(password));
		return true;
	}

	@Override
	public void voHieuHoaSocket(int userId) {
		restTemplate.delete("http://18.141.177.22:3000/vohieuhoa?userId="+userId);
	}

	//	Lam 12/11
	@Override
	public Boolean updateUserProfile(int userId, String userName, String email, String gender) {
		try {
			Boolean temp=Boolean.parseBoolean(gender);
			userRepository.updateUserProfile(userId, userName, email, temp);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

}
