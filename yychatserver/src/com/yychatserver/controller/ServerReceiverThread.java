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
	public ServerReceiverThread(Socket s){//s是与发送者相对应的服务器Socket对象
		this.s=s;
	}
	public void run(){
		ObjectInputStream ois;
		ObjectOutputStream oos;
		
		Message mess;
		while(true){
		try {
			ois=new ObjectInputStream(s.getInputStream());
		      mess=(Message)ois.readObject();
		System.out.println(mess.getSender()+"对"+mess.getReceiver()+"说"+mess.getContent());
		
		
		if(mess.getMessageType().equals(Message.message_Common)){
	Socket s1=(Socket)StartServer.hmSocket.get(mess.getReceiver());
		oos=new ObjectOutputStream(s1.getOutputStream());
		oos.writeObject(mess);
		}
		//第二步：服务器接受到请求后发送在线好友消息
				//if(mess.getMessageType().equals(Message.message_RequestOnlineFriend));
				//Set friendSet=StartServer.hmSocket.keySet();
				//Iterator it=friendSet.iterator();
				//String friendName;
				//String friendtring=" ";
				//while(it.hasNext()){
					
				//}
				
		}
	
		
		catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
	
			e.printStackTrace();
		}
	    
	
	}
}
}