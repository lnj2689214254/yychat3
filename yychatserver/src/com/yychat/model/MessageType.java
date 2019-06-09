package com.yychat.model;

public interface MessageType {

	String message_LoginFailure="0";
	String message_LoginSuccess="1";
	String message_Common="2";
	String message_RequestOnlineFriend="3";
	String message_OnlineFriend="4";
	String message_NewOnlineFriend="5";
	String message_RegisterSuccess="6";
	String message_RegisterFailure="7";
	
	
	String  message_AddFriend="8";
	String  message_AddFriendFailure_NoUser="9";
	String  message_AddFriendFailure_AlreadyFriend="10";
	String  message_AddFriendSuccess="11";
}
