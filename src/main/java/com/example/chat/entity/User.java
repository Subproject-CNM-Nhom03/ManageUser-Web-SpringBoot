package com.example.chat.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;



@Entity
@Table(name = "users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;
	
	private String userName;
	
	private String password;
	
	private String firstName;
	
	private String lastName;
	
	@Column(unique = true)
	private String phoneNumber;
	
	private String email;
	
	private boolean gender;
	
	private String avatar;
	
	private String status;
	
	private boolean disable;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Address> listAddress;
	
	@OneToMany(mappedBy = "sender",cascade = CascadeType.ALL)
	private List<Friend> listFriendSender;
	
	@OneToMany(mappedBy = "recevice",cascade = CascadeType.ALL)
	private List<Friend> listFriendRecevice;
	
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
	private List<Accuracy> listAccuracy;
	
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
	private List<Authentication> listAuthentications = new ArrayList<Authentication>();
	
	
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean getGender() {
		return gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean getDisable() {
		return disable;
	}

	public void setDisable(boolean disable) {
		this.disable = disable;
	}

	public List<Address> getListAddress() {
		return listAddress;
	}

	public void setListAddress(List<Address> listAddress) {
		this.listAddress = listAddress;
	}

	public List<Friend> getListFriendSender() {
		return listFriendSender;
	}

	public void setListFriendSender(List<Friend> listFriendSender) {
		this.listFriendSender = listFriendSender;
	}

	public List<Friend> getListFriendRecevice() {
		return listFriendRecevice;
	}

	public void setListFriendRecevice(List<Friend> listFriendRecevice) {
		this.listFriendRecevice = listFriendRecevice;
	}

	public List<Accuracy> getListAccuracy() {
		return listAccuracy;
	}

	public void setListAccuracy(List<Accuracy> listAccuracy) {
		this.listAccuracy = listAccuracy;
	}
	
	
	public List<Authentication> getListAuthentications() {
		return listAuthentications;
	}

	public void setListAuthentications(List<Authentication> listAuthentications) {
		this.listAuthentications = listAuthentications;
	}

//	public List<GrantedAuthority> getAuthorities() {
//		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
//		for (Authentication auth : listAuthentications) {
//			authorities.add(new SimpleGrantedAuthority(auth.getRole()));
//		}
//		return authorities;
//	}

	
	
	



}
