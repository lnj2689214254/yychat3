package com.yychat.model;

import java.io.Serializable;

public class User implements Serializable{
     private String  userName;
     private  String password;
     private  String userMessageType;//"User_LOGIN_USER_REGISTER"
     
    
     
     
	public String getUserMessageType() {
		return userMessageType;
	}
	public void setUserMessageType(String userMessageType) {
		this.userMessageType = userMessageType;
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
	public static void main(String[] args){
     
}
}
