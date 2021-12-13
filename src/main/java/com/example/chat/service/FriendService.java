package com.example.chat.service;



import java.util.List;

import com.example.chat.entity.Friend;
import com.example.chat.entity.User;

public interface FriendService {
	Friend saveFriend(Friend friend);
	List<Friend> getAllFriend();
	List<Friend> getFriendBySender(User sender);
	List<Friend> getFriendByRecevice(User recevice);
	Friend updateFriend(Friend friend);
	void deleteFriend(int id);
	Friend getFriendByID(int id);
	boolean checkFriend(Friend friend);
	Boolean updateFriendBlock(int friendId, String block, String blockId);
	Boolean updateFriendStatus(int friendId, String status);

}
