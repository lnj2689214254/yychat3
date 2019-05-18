package com.yychatserver.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Iterator;
import java.util.Set;




import com.yychat.model.Message;

public class ServerReceiverThread extends Thread{
	Socket s;
	
	ObjectInputStream ois;
	ObjectOutputStream oos;
	Message mess;
	String sender;
	public ServerReceiverThread(Socket s){//s是与发送者相对应的服务器Socket对象
		this.s=s;
	}
	public void run(){
		while(true){
		try {
			ois=new ObjectInputStream(s.getInputStream());
		      mess=(Message)ois.readObject();
		      sender=mess.getSender();
		System.out.println(mess.getSender()+"对"+mess.getReceiver()+"说: "+mess.getContent());
		
		
		if(mess.getMessageType().equals(Message.message_Common)){
	Socket s1=(Socket)StartServer.hmSocket.get(mess.getReceiver());
		//oos=new ObjectOutputStream(s1.getOutputStream());
		//oos.writeObject(mess);
		sendMessage(s1,mess);
		}
		
		//第二步：服务器接受到请求后发送在线好友消息
				if(mess.getMessageType().equals(Message.message_RequestOnlineFriend)){
				Set friendSet=StartServer.hmSocket.keySet();
				Iterator it=friendSet.iterator();
				String friendName;
				String friendString=" ";
				while(it.hasNext()){
					friendName=(String)it.next();
					if(!friendName.equals(mess.getSender()));
					friendString=friendName+" "+friendString;
				}
					System.out.println("全部好友的名字："+friendString);
					
					
					//发送好友名字到客户端
					mess.setContent(friendString);
	                mess.setMessageType(Message.message_OnlineFriend);			
				mess.setSender("Server");
				mess.setReceiver(sender);
				sendMessage(s,mess);
				}
				
		}
	
		
		catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
	
			e.printStackTrace();
		}
	    
	
	}	
}
      public void sendMessage(Socket s,Message mess) throws IOException {
    	  oos=new ObjectOutputStream(s.getOutputStream());
    	  oos.writeObject(mess);
      }
}