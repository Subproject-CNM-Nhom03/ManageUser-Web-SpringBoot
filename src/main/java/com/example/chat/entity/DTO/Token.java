package com.example.chat.entity.DTO;

public class Token {
	private String access_token;
	private String userID;
	private String userName;
	private String avatarURL;
	public Token() {
	}

	
	public Token(String access_token, String userID, String userName, String avatarURL) {
		super();
		this.access_token = access_token;
		this.userID = userID;
		this.userName = userName;
		this.avatarURL = avatarURL;
	}


	public String getUserID() {
		return userID;
	}


	public void setUserID(String userID) {
		this.userID = userID;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getAvatarURL() {
		return avatarURL;
	}


	public void setAvatarURL(String avatarURL) {
		this.avatarURL = avatarURL;
	}


	

	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	

}
