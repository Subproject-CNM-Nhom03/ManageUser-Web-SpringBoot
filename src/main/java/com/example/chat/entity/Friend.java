package com.example.chat.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "friend")
public class Friend {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int friendId;

	@ManyToOne
	@JoinColumn(name = "sender_id")
	private User sender;

	@ManyToOne
	@JoinColumn(name = "recevice_id")
	private User recevice;

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

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public User getRecevice() {
		return recevice;
	}

	public void setRecevice(User recevice) {
		this.recevice = recevice;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public boolean getBlock() {
		return block;
	}

	public void setBlock(boolean block) {
		this.block = block;
	}

	public LocalDateTime getDateAccept() {
		return dateAccept;
	}

	public void setDateAccept(LocalDateTime dateAccept) {
		this.dateAccept = dateAccept;
	}

	public String getBlockId() {
		return blockId;
	}

	public void setBlockId(String blockId) {
		this.blockId = blockId;
	}

	@Override
	public String toString() {
		return "Friend [friendId=" + friendId + ", sender=" + sender + ", recevice=" + recevice + ", status=" + status
				+ ", block=" + block + ", blockId=" + blockId + ", dateAccept=" + dateAccept + "]";
	}

}
