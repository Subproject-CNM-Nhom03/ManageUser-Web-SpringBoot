package com.example.chat.rest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.example.chat.entity.Friend;
import com.example.chat.entity.User;
import com.example.chat.entity.DTO.ApiError;
import com.example.chat.entity.DTO.UserDTO;
import com.example.chat.service.FriendService;

@RestController
@RequestMapping("/api")
public class FriendController {

	@Autowired
	private FriendService friendService;

	@PostMapping("/friends")
	public Friend saveFriend(@RequestBody Friend friend) {
		if (!friendService.checkFriend(friend)) {
			return friendService.saveFriend(friend);
		}else {
			return null;
		}
	}

	@GetMapping("/friends")
	public List<Friend> getListFriends() {
		return friendService.getAllFriend();
	}

	// update
	@PutMapping("/friends")
	public Friend updateFriend(@RequestBody Friend friend) {
		friend.setDateAccept(LocalDateTime.now());
		return friendService.saveFriend(friend);
	}

	// delete
	@DeleteMapping("/friends/{id}")
	public ResponseEntity<Object> deleteFriendById(@PathVariable int id) {
		friendService.deleteFriend(id);
		return new ResponseEntity<Object>("Deleted!", HttpStatus.OK);
	}

	@PutMapping("/friends/block")
	public ResponseEntity<Object> updateFriendBlock(@RequestBody Friend friend) {
		ApiError apiError;
		Map<String, String> o = new HashMap<String, String>();

		boolean rs = friendService.updateFriendBlock(friend.getFriendId(), String.valueOf(friend.getBlock()),
				friend.getBlockId());
		String status = "";
		if (rs) {
			status = "True";
			o.put("status", status);
			return new ResponseEntity<Object>(o, HttpStatus.OK);
		} else {
			apiError = new ApiError("404", "Update Fail");
			return new ResponseEntity<Object>(apiError, HttpStatus.OK);
		}
	}

	@PutMapping("/friends/status")
	public ResponseEntity<Object> updateFriendStatus(@RequestBody Friend friend) {
		ApiError apiError;
		Map<String, String> o = new HashMap<String, String>();

		boolean rs = friendService.updateFriendStatus(friend.getFriendId(), String.valueOf(friend.getStatus()));
		String status = "";
		if (rs) {
			status = "True";
			o.put("status", status);
			return new ResponseEntity<Object>(o, HttpStatus.OK);
		} else {
			apiError = new ApiError("404", "Update Fail");
			return new ResponseEntity<Object>(apiError, HttpStatus.OK);
		}
	}
}
