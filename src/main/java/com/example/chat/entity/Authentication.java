package com.example.chat.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "authentication")
public class Authentication {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	private String role;

	private boolean enable;
	
	public Authentication() {
		// TODO Auto-generated constructor stub
	}
	
	public Authentication( User user, String role, boolean enable) {
		super();
		this.user = user;
		this.role = role;
		this.enable = enable;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean getEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

}
