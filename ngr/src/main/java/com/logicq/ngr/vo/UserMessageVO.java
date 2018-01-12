package com.logicq.ngr.vo;

public class UserMessageVO {
	
	private String userName;
	private String message;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "UserMessageVO [userName=" + userName + ", message=" + message + "]";
	}
	
}
