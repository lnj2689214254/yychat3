package com.yychatserver.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import com.yychat.model.Message;
import com.yychatclient.view.FriendChat1;
import com.yychatclient.view.FriendList;

public class ChlientReceiverThread  extends Thread{

	private Socket s;
	
	public  ChlientReceiverThread(Socket s){
		this.s=s;
		
	}
	public void run(){
		ObjectInputStream ois;
		while(true){
			try{
			ois=new ObjectInputStream(s.getInputStream());
		     Message mess=(Message)ois.readObject();//接受聊天消息，线程阻塞
		String showMessage=mess.getSender()+"对"+mess.getReceiver()+"说"+mess.getContent();
		System.out.println(showMessage);
		//jta.append(showMessage+"\r\n");
		//在好友界面上显示聊天信息
		//1、如何得到好友聊天界面
		FriendChat1  friendChat1=(FriendChat1)FriendList.hmFriendChat1.get(mess.getReceiver()+"to"+mess.getSender());
		
		//2、显示信息
		friendChat1.appendJta(showMessage);
		
		
		//第三步：
		
		
	}catch (IOException | ClassNotFoundException e){
		e.printStackTrace();
	}
		}
	}
}
	
	
	
	
	
	
	
		




