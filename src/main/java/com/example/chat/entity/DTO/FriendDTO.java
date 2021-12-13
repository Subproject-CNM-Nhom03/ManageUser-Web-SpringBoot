package com.example.chat.entity.DTO;

import java.time.LocalDateTime;

public class FriendDTO {
	
	private int friendId;

	private int sender;

	private int recevice;

	private boolean status;

	private boolean block;
	
	private String blockId;

	private LocalDateTime dateAccept;

	public int getFriendId() {
		return friendId;
	}

	public void setFriendId(int friendId) {
		this.friendId = friendId;
	}

	public int getSender() {
		return sender;
	}

	public void setSender(int sender) {
		this.sender = sender;
	}

	public int getRecevice() {
		return recevice;
	}

	public void setRecevice(int recevice) {
		this.recevice = recevice;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public boolean isBlock() {
		return block;
	}

	public void setBlock(boolean block) {
		this.block = block;
	}

	public String getBlockId() {
		return blockId;
	}

	public void setBlockId(String blockId) {
		this.blockId = blockId;
	}

	public LocalDateTime getDateAccept() {
		return dateAccept;
	}

	public void setDateAccept(LocalDateTime dateAccept) {
		this.dateAccept = dateAccept;
	}
	
	

	
}
