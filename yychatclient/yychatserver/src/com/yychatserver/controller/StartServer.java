package com.yychatserver.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

//import com.mysql.jdbc.PreparedStatement;
//import com.mysql.jdbc.Connection;
import com.yychat.model.Message;
import com.yychat.model.User;

public class StartServer {
	public static HashMap hmSocket=new HashMap<String,Socket>();
	
	ServerSocket ss;
	Socket s;
	String userName;
	String passWord;
	Message mess;
	ObjectOutputStream oos;
	
	
	public StartServer(){
		try {//捕获异常
			ss= new ServerSocket(3456);
			System.out.println("服务器已经启动，监听3456端口");
			while(true){//?Thread多线程
				s= ss.accept();//接收客户端连接请求
				System.out.println("连接成功:"+s);
				
				//接收User对象
				ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
				User user=(User)ois.readObject();
				userName=user.getUserName();
				passWord=user.getPassWord();
				System.out.println(userName);
				System.out.println(passWord);
				
				 //注册新用户,步骤7：在服务端完成新用户的注册
				if(user.getUserMessageType().equals("USER_REGISTER")){
					//注册新用户,步骤8：对新注册用户名进行查询,
					//seekUserResult为true有同名用户,false没有同名用户
					boolean seekUserResult=YychatDbUtil.seekUser(userName);
					mess=new Message();//使用类来创建Message对象，mess是对象的名字
					mess.setSender("Server");
					mess.setReceiver(userName);
					if(seekUserResult){
						//返回客户端注册失败
						mess.setMessageType(Message.message_RegisterFailure);
					}else{
						//注册新用户,步骤9：如果没有同名用户，把新用户的名字和密码写入到user表中，返回客户端注册成功
						YychatDbUtil.addUser(userName,passWord);
						mess.setMessageType(Message.message_RegisterSuccess);
					}
					sendMessage(s,mess);
					s.close();//注册完成，关闭服务器端的socket对象
				}
				
				
				if(user.getUserMessageType().equals("USER_LOGIN")){
					//从数据库中实现用户的登录验证				
					
					boolean loginSuccess=YychatDbUtil.loginValidate(userName, passWord);
					
					//实现密码验证功能
					mess=new Message();//使用类来创建Message对象，mess是对象的名字
					mess.setSender("Server");
					mess.setReceiver(userName);
					//if(passWord.equals("123456")){//对象比较
					if(loginSuccess){//对象比较
						//告诉客户端密码验证通过的消息，可以创建Message类				
						mess.setMessageType(Message.message_LoginSuccess);//"1"为验证通过
						
						//利用数据表中好友信息来更新好友列表1、服务器查询好友信息表，并发送到客户端					
						String friendString=YychatDbUtil.getFriendString(userName);
						
						mess.setContent(friendString);
						System.out.println(userName+"的relation数据表中好友："+friendString);					
						
					}else {
						mess.setMessageType(Message.message_LoginFailure);//"0"为验证不通过		
					}				
					sendMessage(s,mess);
					
					//在这里接收聊天信息，可不可以？不可以，应该新建一个接收线程
					//if(passWord.equals("123456")){
					if(loginSuccess){
						//激活新上线用户图标步骤1、在此处把自己登录成功的消息发送到在该用户之前登录的所有用户
						//构建了要发送的消息
						mess.setMessageType(Message.message_NewOnlineFriend);//类型
						mess.setSender("Server");
						mess.setContent(userName);//发送消息的内容,this指对象本事
						
						//拿到已经在线用户的名字
						Set onlineFriendSet=hmSocket.keySet();
						Iterator it=onlineFriendSet.iterator();
						String friendName;
						while (it.hasNext()) {//向全部在线用户发送新用户上线的消息
							friendName=(String)it.next();
							mess.setReceiver(friendName);
							//向friendName发送消息
							Socket s1=(Socket)hmSocket.get(friendName);
							sendMessage(s1, mess);						
						}
						
						
						hmSocket.put(userName, s);
						new ServerReceiverThread(s).start();//就绪,每个用户都有一个对应的服务线程
					}				
				}
				}
				
			
		} catch (IOException|ClassNotFoundException e) {
			e.printStackTrace();//处理异常
		}
	}
	public void sendMessage(Socket s,Message mess) throws IOException {
		ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
		oos.writeObject(mess);
	}
}
