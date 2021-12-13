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
@Table(name = "accuracy")
public class Accuracy {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private int maOTP;
	
	private LocalDateTime authenticationTime;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMaOTP() {
		return maOTP;
	}

	public void setMaOTP(int maOTP) {
		this.maOTP = maOTP;
	}

	public LocalDateTime getAuthenticationTime() {
		return authenticationTime;
	}

	public void setAuthenticationTime(LocalDateTime authenticationTime) {
		this.authenticationTime = authenticationTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	 
	
}
