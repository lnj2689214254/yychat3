package com.yychatserver.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Iterator;
import java.util.Set;

import javax.management.relation.RelationType;

import com.yychat.model.Message;

public class ServerReceiverThread extends Thread{//代码复用
	Socket s;
	
	ObjectInputStream ois;
	ObjectOutputStream oos;
	Message mess;
	String sender;
	
	public ServerReceiverThread(Socket s){//s是与发送者相对应的服务器Socket对象
		this.s=s;
	}
	
	public void run(){
		
		while (true) {
			try {
				ois = new ObjectInputStream(s.getInputStream());
				mess=(Message)ois.readObject();//接收聊天信息，阻塞
				sender=mess.getSender();
				System.out.println(mess.getSender()+"对"+mess.getReceiver()+"说："+mess.getContent());
				
				//添加新好友，实验步骤5、服务器端处理添加好友的消息。
				if(mess.getMessageType().equals(Message.message_AddFriend)){
					String addFriendName=mess.getContent();
					System.out.println("需要添加新好友的名字："+addFriendName);
					//添加新好友，实验步骤6、查询用户是否存在
					if(!YychatDbUtil.seekUser(addFriendName)){
						//用户不存在，不能添加好友
						mess.setMessageType(Message.message_AddFriendFailure_NoUser);
					}else{
						//用户存在
						//添加新好友，实验步骤7、判断该用户是否已经是好友
						String relationType="1";//“1”表示好友
						if(YychatDbUtil.seekRelation(sender,addFriendName,relationType)){//查询该好友
							//已经是好友，不能添加
							mess.setMessageType(Message.message_AddFriendFailure_AlreadyFriend);
						}else {
							//添加新好友，实验步骤8、添加好友，把全部好友名字发送到客户端
							int count=YychatDbUtil.addRelation(sender,addFriendName,relationType);
							if(count!=0){
								mess.setMessageType(Message.message_AddFriendSuccess);
								//拿到全部好友
								String  allFriendName=YychatDbUtil.getFriendString(sender);
								mess.setContent(allFriendName);
							}
						}
					}
					sendMessage(s, mess);//发送到客户端
				}
				
				if(mess.getMessageType().equals(Message.message_Common)){
					Socket s1=(Socket)StartServer.hmSocket.get(mess.getReceiver());//得到了与接收者相对应的服务器Socket对象
					sendMessage(s1,mess);
				}
				
				//第2步：服务器接收到该请求后发送在线好友信息(类型：message_OnlineFriend)
				if(mess.getMessageType().equals(Message.message_RequestOnlineFriend)){
					//拿到全部在线好友的名字
					Set friendSet=StartServer.hmSocket.keySet();//键集合,在线好友集合
					Iterator it=friendSet.iterator();//迭代器对象
					String friendName;
					String friendString=" ";
					while(it.hasNext()){//判断还有没有下一个元素
						friendName=(String)it.next();//取出下一个元素
						if(!friendName.equals(mess.getSender()))
							friendString=friendString+friendName+" ";//为什么用空格？
					}
					System.out.println("全部好友的名字："+friendString);
					
					//发送全部好友的名字到客户端
					mess.setContent(friendString);
					mess.setMessageType(Message.message_OnlineFriend);
					mess.setSender("Server");
					mess.setReceiver(sender);					
					sendMessage(s, mess);
				}
				
				
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
			
		}
		
		
	}

	public void sendMessage(Socket s,Message mess) throws IOException {
		oos=new ObjectOutputStream(s.getOutputStream());
		oos.writeObject(mess);//转发聊天信息
	}
}
