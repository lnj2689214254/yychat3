package com.yychatclient.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

import com.yychat.model.Message;
import com.yychatclient.view.ClientLogin;
import com.yychatclient.view.FriendChat1;
import com.yychatclient.view.FriendList;

public class ClientReceiverThread extends Thread{

	private Socket s;
	
	public ClientReceiverThread(Socket s){
		this.s=s;
	}
	public void run(){
		ObjectInputStream ois;
		while(true){
			try {
				ois = new ObjectInputStream(s.getInputStream());
				Message mess=(Message)ois.readObject();//接收聊天信息,线程阻塞
				String showMessage=mess.getSender()+"对"+mess.getReceiver()+"说："+mess.getContent();
				System.out.println(showMessage);
				
				//添加新好友，实验步骤9、处理添加好友消息
				if(mess.getMessageType().equals(Message.message_AddFriendFailure_NoUser)){
					JOptionPane.showMessageDialog(null, "添加好友失败！用户不存在！");
				}
				if(mess.getMessageType().equals(Message.message_AddFriendFailure_AlreadyFriend)){
					JOptionPane.showMessageDialog(null, "添加好友失败！不能重复添加好友！");
				}
				if(mess.getMessageType().equals(Message.message_AddFriendSuccess)){
					JOptionPane.showMessageDialog(null, "添加好友成功！");
					String allFriendName=mess.getContent();
					FriendList friendList=(FriendList)ClientLogin.hmFriendlist.get(mess.getSender());
					friendList.updateFriendIcon(allFriendName);
					friendList.revalidate();//重绘好友列表界面				
				}
				
				if(mess.getMessageType().equals(Message.message_Common)){
					//jta.append(showMessage+"\r\n");
					//在好友界面FriendChat1上显示聊天信息
					//1、如何拿到好友聊天界面，思路：保存好友聊天界面，
					FriendChat1 friendChat1=(FriendChat1)FriendList.hmFriendChat1.get(mess.getReceiver()+"to"+mess.getSender());//还是接收者+发送者吗？
									
					//2、再显示信息
					friendChat1.appendJta(showMessage);
				}
				
				
				//第3步：客户端接收服务器发送来的在线好友信息，然后利用该信息激活在线好友的图标
				if(mess.getMessageType().equals(Message.message_OnlineFriend)){
					System.out.println("在线好友"+mess.getContent());
					
					//首先要拿到好友列表对象
					FriendList friendList=(FriendList)ClientLogin.hmFriendlist.get(mess.getReceiver());
					//激活对应图标
					friendList.setEnableFriendIcon(mess.getContent());
				}
				
				//激活新上线用户图标步骤2、接收消息，激活图标
				if(mess.getMessageType().equals(Message.message_NewOnlineFriend)){
					System.out.println("新用户上线了，用户名："+mess.getContent());
					//首先要拿到好友列表对象
					FriendList friendList=(FriendList)ClientLogin.hmFriendlist.get(mess.getReceiver());
					System.out.println("FriendList对象名："+mess.getReceiver());
					//激活对应图标
					friendList.setEnableNewFriendIcon(mess.getContent());//新上线用户的名字	
					//friendList.setEnableFriendIcon(mess.getContent());	
					
				}
				
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}	
		}
	}
}
