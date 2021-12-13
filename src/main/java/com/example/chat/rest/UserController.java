package com.example.chat.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.chat.entity.Address;
import com.example.chat.entity.Authentication;
import com.example.chat.entity.Friend;
import com.example.chat.entity.User;
import com.example.chat.entity.DTO.ApiError;
import com.example.chat.entity.DTO.FriendDTO;
import com.example.chat.entity.DTO.Token;
import com.example.chat.entity.DTO.UserDTO;
import com.example.chat.service.AddressService;
import com.example.chat.service.FriendService;
import com.example.chat.service.JwtService;
import com.example.chat.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

	private static final String SDT_PATTERN = "0[0-9]{9}";

	@Autowired
	private UserService userService;

	@Autowired
	private FriendService friendService;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AddressService addressService;
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@GetMapping("/users/allblocks/{id}")
	public List<UserDTO> getListAllBlock(@PathVariable("id") int id) {
		List<Friend> list = new ArrayList<>();
		List<Friend> list1 = new ArrayList<>();
		List<User> listUser = new ArrayList<>();
		List<UserDTO> listUserDTO = new ArrayList<>();

		User user = userService.getUserById(id);
		list = friendService.getFriendByRecevice(user);
		list1 = friendService.getFriendBySender(user);

		list.addAll(list1);

		for (Friend friend : list) {
			if (friend.getBlock()) {
				if (friend.getSender().getUserId() == id) {
					listUser.add(friend.getRecevice());
				} else {
					listUser.add(friend.getSender());
				}
			}
		}

		for (User user1 : listUser) {
			listUserDTO.add(getUserDTO(user1));
		}
		return listUserDTO;

	}

	@PostMapping("/users")
	public ResponseEntity<Object> register(@RequestBody User user) {
//		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword())); 
		Authentication auth = new Authentication();
		auth.setEnable(true);
		auth.setRole("ROLE_USER");
		auth.setUser(user);
		List<Authentication> listAuthentications = new ArrayList<>();
		listAuthentications.add(auth);
		user.setListAuthentications(listAuthentications);
		User u = new User();
		try {
			 userService.saveUser(user);

		} catch (Exception e) {
			return new ResponseEntity<Object>(new ApiError(HttpStatus.BAD_REQUEST.toString(), "User Existed!"),
					HttpStatus.BAD_REQUEST);

		}
		return new ResponseEntity<Object>(u, HttpStatus.OK);

	}

	@GetMapping("/users")
	public List<UserDTO> getAllUsers() {
		List<User> listUsers = userService.getAllUsers();
		List<UserDTO> listUserDTO = new ArrayList<>();
		for (User user : listUsers) {
			listUserDTO.add(getUserDTO(user));
		}
		return listUserDTO;
	}

	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody User user) {

		ApiError eror;

		String status = userService.checkLogin(user);

		if (status.equals("True")) {
			user = userService.getUserByPhoneNumber(user.getPhoneNumber());
			Token result = new Token(jwtService.generateTokenLogin(user.getPhoneNumber()),
					String.valueOf(user.getUserId()), user.getUserName(), user.getAvatar());

			return new ResponseEntity<Object>(result, HttpStatus.OK);

		} else {
			eror = new ApiError("404", status);
			return new ResponseEntity<Object>(eror, HttpStatus.OK);
		}

	}

	@PostMapping("/userfromtoken")
	public ResponseEntity<Object> userfromtoken(@RequestBody Token token) {

		UserDTO user = null;
		User x = userService.checkUserJWT(token.getAccess_token());

		if (x != null) {
			user = getUserDTO(x);
			return new ResponseEntity<Object>(user, HttpStatus.OK);
		} else {
			ApiError eror = new ApiError("404", "Not found user");
			return new ResponseEntity<Object>(eror, HttpStatus.OK);
		}

	}

	@GetMapping("/user/{id}")
	public ResponseEntity<Object> getUserById(@PathVariable("id") int id) {
		User user = userService.getUserById(id);
		if (user != null) {
			UserDTO userDTO = getUserDTO(user);
			return new ResponseEntity<Object>(userDTO, HttpStatus.OK);
		}

		return new ResponseEntity<Object>(new ApiError(HttpStatus.NO_CONTENT.toString(), "Not Found User"),
				HttpStatus.NO_CONTENT);

	}

	// get list friend by sender
	@GetMapping("/users/friend_sender/{id}")
	public List<UserDTO> getListFriendBySender(@PathVariable("id") int id) {
		List<Friend> list = new ArrayList<>();
		List<User> listUser = new ArrayList<>();
		List<UserDTO> listUserDTO = new ArrayList<>();

		User user = userService.getUserById(id);
		list = friendService.getFriendBySender(user);

		for (Friend f : list) {
			if (!f.getStatus())
				if (!f.getBlock())
					listUser.add(f.getRecevice());
		}

		for (User user1 : listUser) {
			listUserDTO.add(getUserDTO(user1));
		}
		return listUserDTO;
	}

	// get list friend by recevice
	@GetMapping("/users/friend_recevice/{id}")
	public List<UserDTO> getListFriendByRecevice(@PathVariable("id") int id) {
		List<Friend> list = new ArrayList<>();
		List<User> listUser = new ArrayList<>();
		List<UserDTO> listUserDTO = new ArrayList<>();

		User user = userService.getUserById(id);
		list = friendService.getFriendByRecevice(user);

		for (Friend f : list) {
			if (!f.getStatus())
				if (!f.getBlock())
					listUser.add(f.getSender());
		}

		for (User user1 : listUser) {
			listUserDTO.add(getUserDTO(user1));
		}
		return listUserDTO;
	}

	// get list friend
	@GetMapping("/users/friend/{id}")
	public List<UserDTO> getListFriend(@PathVariable("id") int id) {
		List<Friend> list = new ArrayList<>();
		List<Friend> list1 = new ArrayList<>();
		List<User> listUser = new ArrayList<>();
		List<UserDTO> listUserDTO = new ArrayList<>();

		User user = userService.getUserById(id);
		list = friendService.getFriendByRecevice(user);
		list1 = friendService.getFriendBySender(user);

		list.addAll(list1);

		for (Friend f : list) {
			if (f.getStatus() && !f.getBlock()) {

				if (f.getSender().getUserId() == id) {

					listUser.add(f.getRecevice());

				} else if (f.getRecevice().getUserId() == id) {

					listUser.add(f.getSender());
				}

			}

		}

		for (User user1 : listUser) {
			listUserDTO.add(getUserDTO(user1));
		}
		return listUserDTO;

	}

//dat sua
	// get list block
	@GetMapping("/users/block/{id}")
	public List<UserDTO> getListBlock(@PathVariable("id") int id) {
		List<Friend> list = new ArrayList<>();
		List<Friend> list1 = new ArrayList<>();
		List<User> listUser = new ArrayList<>();
		List<UserDTO> listUserDTO = new ArrayList<>();

		User user = userService.getUserById(id);
		list = friendService.getFriendByRecevice(user);
		list1 = friendService.getFriendBySender(user);

		list.addAll(list1);

		for (Friend friend : list) {
			if (friend.getBlock()) {
				if (friend.getBlockId() == null) {
					friend.setBlockId(" ");
				}
				if (friend.getBlockId().equals(String.valueOf(id))) {
					if (friend.getSender().getUserId() == id) {
						listUser.add(friend.getRecevice());
					} else {
						listUser.add(friend.getSender());
					}
				}
			}
		}

		for (User user1 : listUser) {
			listUserDTO.add(getUserDTO(user1));
		}
		return listUserDTO;

	}

	// get list address by userId
	@GetMapping("/users/address/{id}")
	public List<Address> getListAddressByUser(@PathVariable("id") int id) {
		User user = userService.getUserById(id);
		List<Address> list = addressService.getListAddressByUser(user);
		for (Address address : list) {
			address.setUser(null);
		}
		return list;
	}

//	dat them
	// get invitation by sender, recevice
	@GetMapping("/users/invitation/{id}/{sid}")
	public FriendDTO getInvitation(@PathVariable("id") int userID, @PathVariable("sid") int senderID) {
		User user = userService.getUserById(userID);
		Friend friend = new Friend();
		FriendDTO friendDTO = new FriendDTO();
		List<Friend> list = new ArrayList<>();

		list = friendService.getFriendByRecevice(user);
		for (Friend f : list) {
			if (f.getSender().getUserId() == senderID) {
				friend = friendService.getFriendByID(f.getFriendId());
				friendDTO = getFriendDTO(friend);
			}
		}

		return friendDTO;
	}

	@GetMapping("/users/friend/{id}/{fid}")
	public FriendDTO getFriend(@PathVariable("id") int userID, @PathVariable("fid") int userFID) {
		User user = userService.getUserById(userID);

		List<Friend> list = new ArrayList<>();
		List<Friend> list1 = new ArrayList<>();
		FriendDTO friendDTO = new FriendDTO();
		Friend friend = new Friend();

		list = friendService.getFriendByRecevice(user);
		list1 = friendService.getFriendBySender(user);

		list.addAll(list1);

		for (Friend f : list) {
			if (f.getStatus()) {
				if (f.getSender().getUserId() == userID) {
					if (f.getRecevice().getUserId() == userFID) {
						friend = friendService.getFriendByID(f.getFriendId());
						friendDTO = getFriendDTO(friend);

					}
				} else if (f.getSender().getUserId() == userFID) {
					if (f.getRecevice().getUserId() == userID) {
						friend = friendService.getFriendByID(f.getFriendId());
						friendDTO = getFriendDTO(friend);
					}

				}
			}
		}

		return friendDTO;
	}

	// get list user by search
	@GetMapping("/search/{id}/{search}")
	public List<UserDTO> getListUserBySearch(@PathVariable("id") int id, @PathVariable("search") String search) {

		List<UserDTO> listFriend = getListFriend(id);

		// Kiểm tra cú pháp SDT
		Pattern pattern = Pattern.compile(SDT_PATTERN);
		Matcher matcher = pattern.matcher(search);

		List<User> listS = new ArrayList<User>();

		List<UserDTO> listDTO = new ArrayList<UserDTO>();

		List<User> list = new ArrayList<User>();

		if (matcher.matches()) {
			User user = userService.getUserByPhoneNumber(search);
			list.add(user);
			for (User u : list) {
				listDTO.add(getUserDTO(u));
			}
			return listDTO;
		} else {
			listS = userService.searchUser(search);
			for (User user : listS) {
				for (UserDTO user2 : listFriend) {
					if (user.getUserId() == user2.getUserId()) {
						listDTO.add(getUserDTO(user));
					}
				}
			}
			return listDTO;
		}

	}

//	dat them

	public FriendDTO getFriendDTO(Friend friend) {
		FriendDTO friendDTO = new FriendDTO();
		friendDTO.setFriendId(friend.getFriendId());
		friendDTO.setSender(friend.getSender().getUserId());
		friendDTO.setRecevice(friend.getRecevice().getUserId());
		friendDTO.setStatus(friend.getStatus());
		friendDTO.setBlock(friend.getBlock());
		friendDTO.setBlockId(friend.getBlockId());
		friendDTO.setDateAccept(friend.getDateAccept());

		return friendDTO;
	}

	public UserDTO getUserDTO(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setUserId(user.getUserId());
		userDTO.setUserName(user.getUserName());
		userDTO.setPassword(user.getPassword());
		userDTO.setFirstName(user.getFirstName());
		userDTO.setLastName(user.getLastName());
		userDTO.setPhoneNumber(user.getPhoneNumber());
		userDTO.setEmail(user.getEmail());
		userDTO.setGender(user.getGender());
		userDTO.setAvatar(user.getAvatar());
		userDTO.setStatus(user.getStatus());
		userDTO.setDisable(user.getDisable());

		return userDTO;
	}

	// Lâm sửa update 12/11
	@PutMapping("/users")
	public ResponseEntity<Object> updatUser(@RequestBody User user) {
		ApiError eror;
		String status;
		Map<String, String> o = new HashMap<String, String>();

		boolean rs=	userService.updateUserProfile(user.getUserId(), user.getUserName(), user.getEmail(), String.valueOf(user.getGender()));
		if(rs) {
			status = "True";
			o.put("status", status);
			return new ResponseEntity<Object>(o, HttpStatus.OK);
		}else {
			eror = new ApiError("404", "Update Fail");
			return new ResponseEntity<Object>(eror,HttpStatus.OK);
		}
	}

		// Cập nhật avata_url
		@PutMapping("/users/avatar")
		public ResponseEntity<Object> updateAvatar(@RequestBody User user) {
			ApiError eror;
			String status;
			Map<String, String> o = new HashMap<String, String>();
			
			User u = userService.getUserById(user.getUserId());
			u.setAvatar(user.getAvatar());
		
			try {
				userService.updateUser(u);
			} catch (Exception e) {
				eror = new ApiError("404", "Update Fail");
				return new ResponseEntity<Object>(eror,HttpStatus.OK);
			}
			
			status = "True";
			o.put("status", status);
			return new ResponseEntity<Object>(o, HttpStatus.OK);
		}


	// Kiemr tra matj khau

	@PostMapping("/checkPassword")
	public ResponseEntity<Object> checkPassword(@RequestBody User user) {

		ApiError eror;

		String status = userService.checkPassword(user);
		Map<String, String> o = new HashMap<String, String>();

		if (status.equals("True")) {
			o.put("status", status);
			return new ResponseEntity<Object>(o, HttpStatus.OK);

		} else {
			eror = new ApiError("404", status);
			return new ResponseEntity<Object>(eror, HttpStatus.OK);
		}

	}

	@PostMapping("/checkPasswordRegex")
	public ResponseEntity<Object> checkPasswordRegex(@RequestBody String pass) {
		String status = "";
		Map<String, String> o = new HashMap<String, String>();
		ApiError eror;
	
		String ps = pass.substring(1, pass.length()-1);
		
		if(ps.equals("")) {
			status = "Chưa nhập mật khẩu mới";
			eror = new ApiError("404", status);
			return new ResponseEntity<Object>(eror, HttpStatus.OK);
		}else {
			boolean rs = userService.checkPasswordRegex(ps);
			
			
			if (rs) {
				status = "True";
				o.put("status", status);
				return new ResponseEntity<Object>(o, HttpStatus.OK);

			} else {
				status = "Mật khẩu có ít nhất 6 kí tự gồm chữ cái, chữ số, và các kí tự đặc biệt:@#$%^&, không dấu,không khoảng cách";
				eror = new ApiError("404", status);
				return new ResponseEntity<Object>(eror, HttpStatus.OK);
			}
		}
	}

	// Lâm làm ngày 25/10
	@PostMapping("/checkUsernameEmail")
	public ResponseEntity<Object> checkUsernameEmail(@RequestBody User user) {
		String status = "";
		Map<String, String> o = new HashMap<String, String>();
		ApiError eror;
		
		if(user.getUserName().equals("")) {
			status = "Username not entered";
			eror = new ApiError("404", status);
			return new ResponseEntity<Object>(eror, HttpStatus.OK);
		}
		
		if(user.getEmail().equals("")) {
			status = "Email not entered";
			eror = new ApiError("404", status);
			return new ResponseEntity<Object>(eror, HttpStatus.OK);
		}

		boolean rsname = userService.checkUserName(user.getUserName());
		boolean rsemail = userService.checkEmail(user.getEmail());
		//Lâm thêm ngày 25/10/2021
		boolean sameemail = userService.checkSameEmail(user.getEmail(),user.getUserId());
		

		if (!rsname) {
			status = "Username contains no special characters or numbers.";
			eror = new ApiError("404", status);
			return new ResponseEntity<Object>(eror, HttpStatus.OK);
		}
		if (!rsemail) {
			status = "Email in the format xxxxxx@xxxx.xxxx";
			eror = new ApiError("404", status);
			return new ResponseEntity<Object>(eror, HttpStatus.OK);
		}

		if(!sameemail) {
			status = "Email already exists";
			eror = new ApiError("404", status);
			return new ResponseEntity<Object>(eror, HttpStatus.OK);
		}
		status = "True";
		o.put("status", status);
		return new ResponseEntity<Object>(o, HttpStatus.OK);
	}
	// update password Lâm cập nhật ngày 25/10/2021
	@PutMapping("/users/password")
	public  ResponseEntity<Object> updatePassword(@RequestBody User user) {
		ApiError eror;
		String status;
		Map<String, String> o = new HashMap<String, String>();
		User u = userService.getUserByPhoneNumber(user.getPhoneNumber());
		u.setPassword(user.getPassword());
		try {
			userService.updateUser(u);
		} catch (Exception e) {
			eror = new ApiError("404", "Update failed");
			return new ResponseEntity<Object>(eror,HttpStatus.OK);
		}
		
		status = "True";
		o.put("status", status);
		return new ResponseEntity<Object>(o, HttpStatus.OK);
	}

	// signin new user
	@PostMapping("/signup")
	public ResponseEntity<Object> signin(@RequestBody User user) {
		ApiError eror;
		User user2 = userService.saveUser(user);
		Map<String, String> o = new HashMap<String, String>();

		if (user2 != null) {
			o.put("status", "OK");
			return new ResponseEntity<Object>(o, HttpStatus.OK);
		} else {
			eror = new ApiError("404", "Can't create a new account");
			return new ResponseEntity<Object>(eror, HttpStatus.OK);
		}
	}

	// check invalid user lam update at 25/10/2021 // 31/10
	@PostMapping("/signup/checkvaliduser")
	public ResponseEntity<Object> checkvaliduser(@RequestBody User user) {
		ApiError eror;
		String status = "";
		Map<String, String> o = new HashMap<String, String>();

		if (user.getUserName().equals("")) {
			status = "Username not entered";
			eror = new ApiError("404", status);
			return new ResponseEntity<Object>(eror, HttpStatus.OK);
		}
		if (user.getEmail().equals("")) {
			status = "Email not entered";
			eror = new ApiError("404", status);
			return new ResponseEntity<Object>(eror, HttpStatus.OK);
		}
		if (user.getPassword().equals("")) {
			status = "Password not entered";
			eror = new ApiError("404", status);
			return new ResponseEntity<Object>(eror, HttpStatus.OK);
		}
		if (user.getPhoneNumber().equals("")) {
			status = "PhoneNumber not entered";
			eror = new ApiError("404", status);
			return new ResponseEntity<Object>(eror, HttpStatus.OK);
		}

		boolean rsusername = userService.checkUserName(user.getUserName());
		boolean rspassword = userService.checkPasswordRegex(user.getPassword());
		boolean rsemail = userService.checkEmail(user.getEmail());
		boolean rsphone = userService.checkPhone(user.getPhoneNumber());

		boolean rssameemail = userService.checkSameEmailSignup(user.getEmail());
		boolean rssamephone = userService.checkSamePhone(user.getPhoneNumber());

		if (!rsemail) {
			status = "Email in format xxx@xxx.xxx";
			eror = new ApiError("404", status);
			return new ResponseEntity<Object>(eror, HttpStatus.OK);
		}
		if (!rsusername) {
			status = "Username does not contain special characters";
			eror = new ApiError("404", status);
			return new ResponseEntity<Object>(eror, HttpStatus.OK);
		}

		if (!rspassword) {
			status = "Password must contain at least 6 characters including letters, numbers, and special characters:@#$%^&, no signs, no spaces";
			eror = new ApiError("404", status);
			return new ResponseEntity<Object>(eror, HttpStatus.OK);
		}
		if (!rsphone) {
			status = "Phone number consists of 10 digits. Start with the prefix 09|01|08|03|02|07|05";
			eror = new ApiError("404", status);
			return new ResponseEntity<Object>(eror, HttpStatus.OK);
		}
		if (!rssameemail) {
			status = "Email already exists";
			eror = new ApiError("404", status);
			return new ResponseEntity<Object>(eror, HttpStatus.OK);
		}
		if (!rssamephone) {
			status = "PhoneNumer already exists";
			eror = new ApiError("404", status);
			return new ResponseEntity<Object>(eror, HttpStatus.OK);
		}

		status = "True";
		o.put("status", status);
		return new ResponseEntity<Object>(o, HttpStatus.OK);
	}

	// hào thêm 29/10
	@GetMapping("/contact/{id}/{sdt}")
	public ResponseEntity<Object> getUser(@PathVariable("id") int userID, @PathVariable("sdt") String sdt) {
		User u = userService.getUserByPhoneNumber(sdt);
		if (u == null) {
			ApiError err = new ApiError("404", "Not found");
			return new ResponseEntity<Object>(err, HttpStatus.OK);
		} else {
			List<Friend> f1 = friendService.getFriendByRecevice(u);
			List<Friend> f2 = friendService.getFriendBySender(u);
			f1.addAll(f2);
			for (Friend friend : f1) {
				if (friend.getSender().getUserId() == userID || friend.getRecevice().getUserId() == userID) {
					ApiError err = new ApiError("404", "Not found");
					return new ResponseEntity<Object>(err, HttpStatus.OK);
				}
			}
			return new ResponseEntity<Object>(getUserDTO(u), HttpStatus.OK);
		}
	}

	@PostMapping("/resetPassword")
	public ResponseEntity<Object> resetPassword(@RequestBody User user) {
		ApiError err;
		if(!userService.resetPassword(user.getPhoneNumber(), user.getPassword())) {
			err = new ApiError("404", "Update fail");

			return new ResponseEntity<Object>(err, HttpStatus.OK);
		};
		return new ResponseEntity<Object>(user, HttpStatus.OK);

	}

	@GetMapping("/userphone/{sdt}")
	public ResponseEntity<Object> getUserByPhone(@PathVariable("sdt") String sdt) {
		User u = userService.getUserByPhoneNumber(sdt);
		if (u == null) {
			ApiError err = new ApiError("404", "Not found");
			return new ResponseEntity<Object>(err, HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>(getUserDTO(u), HttpStatus.OK);
		}
	}
}
