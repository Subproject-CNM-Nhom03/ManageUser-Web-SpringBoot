package com.example.chat.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.chat.entity.Friend;
import com.example.chat.entity.User;
import com.example.chat.repository.FriendRepository;
import com.example.chat.service.FriendService;

@Service
public class FriendServiceImpl implements FriendService {

	@Autowired
	private FriendRepository friendRepository;

	@Override
	public Friend saveFriend(Friend friend) {

		return friendRepository.saveAndFlush(friend);
	}

	@Override
	public List<Friend> getAllFriend() {

		return friendRepository.findAll();
	}

	@Override
	public List<Friend> getFriendBySender(User sender) {

		return friendRepository.getFriendBySender(sender);
	}

	@Override
	public List<Friend> getFriendByRecevice(User recevice) {

		return friendRepository.getFriendByRecevice(recevice);
	}

	@Override
	public Friend updateFriend(Friend friend) {
		
		return friendRepository.save(friend);
	}

	@Override
	public void deleteFriend(int id) {
		friendRepository.deleteById(id);
	}

	@Override
	public Friend getFriendByID(int id) {
		return friendRepository.getById(id);
	}

	@Override
	public boolean checkFriend(Friend friend) {
		boolean rs = false;
		List<Friend> listF = getAllFriend();
		for (Friend f : listF) {
			if ((friend.getSender().getUserId() == f.getSender().getUserId() && friend.getRecevice().getUserId() == f.getRecevice().getUserId())
					|| (friend.getSender().getUserId() == f.getRecevice().getUserId() && friend.getRecevice().getUserId() == f.getSender().getUserId())) {
				rs = true;
			}
		}
		
		return rs;
	}

	@Override
	public Boolean updateFriendBlock(int friendId, String block, String blockId) {
		try {
			Boolean temp=Boolean.parseBoolean(block);
			friendRepository.updateFriendBlock(friendId, temp, blockId);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public Boolean updateFriendStatus(int friendId, String status) {
		try {
			Boolean temp=Boolean.parseBoolean(status);
			friendRepository.updateFriendStatus(friendId, temp);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
