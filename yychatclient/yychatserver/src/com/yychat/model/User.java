package com.yychat.model;

import java.io.Serializable;

public class User implements Serializable{
	private String userName;
	private String passWord;
	
	//新用户注册， 步骤3：在User类中添加新的成员变量
		private String userMessageType;//"USER_LOGIN"和"USER_REGISTER"
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
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	
	
}
