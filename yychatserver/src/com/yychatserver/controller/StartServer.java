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

import com.yychat.model.Message;
import com.yychat.model.User;

public class StartServer {
	public static HashMap hmSocket=new HashMap<String,Socket>();
	ServerSocket ss;
	String userName;
	String passWord;
	Socket s;
	Message mess;
	ObjectOutputStream oos;
	 public StartServer() {
		 
		try {
			ss=new ServerSocket(3456);
			System.out.println("服务器已经启动，监听34556端口");
			while(true){
				s=ss.accept();
				System.out.println("连接成功"+s);
				ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
				User user=(User)ois.readObject();
				userName=user.getUserName();
				passWord=user.getPassword();
				System.out.println(userName);
				System.out.println(passWord);
				//System.out.println(user.getUserMessageType());
				
				if(user.getUserMessageType().equals("USER_REGISTER")){
				//输出
					boolean seekUserResult=YychatDbUtil.seekUser(userName);
					mess=new Message();
					mess.setSender("Server");
		            mess.setReceiver(userName);
				if(seekUserResult){
					mess.setMessageType(Message.message_RegisterFailure);
				}else{
					YychatDbUtil.addUser(userName,passWord);
					mess.setMessageType(Message.message_RegisterSuccess);
			}
				sendMessage(s,mess);
				s.close();
			}
				
				
				if(user.getUserMessageType().equals("USER_LOGIN")){
					
				
				   boolean loginSuccess=YychatDbUtil.loginValidate(userName, passWord);
					
					mess=new Message();
					mess.setSender("Server");
		            mess.setReceiver(userName);
					
					if(loginSuccess){
					  		 mess.setMessageType(Message.message_LoginSuccess);
					  		 
					  		//String friendString;
							/*String friend_Relation_Sql="select slaveuser from relation where majoruser=? and relationtype='1'";
							ptmt=conn.prepareStatement(friend_Relation_Sql);
							ptmt.setString(1,userName);
							rs=ptmt.executeQuery();
							String friendString="";
							while(rs.next()){
								friendString=friendString+rs.getString("slaveuser")+" ";		*/				
		     //}
					  		 String friendString=YychatDbUtil.getFriendString(userName);
							mess.setContent(friendString);
							System.out.println(userName+"的relation数据表中好友："+friendString);
					}else{ 		
					    mess.setMessageType(Message.message_LoginFailure);
					}
					sendMessage(s,mess);
					
					
					if(loginSuccess){
						mess.setMessageType(Message.message_NewOnlineFriend);
						mess.setSender("Server");
						mess.setContent(userName);
					    
						
						Set onlineFriendSet=hmSocket.keySet();
						Iterator it=onlineFriendSet.iterator();
						String friendName;
						while(it.hasNext()){
							friendName=(String)it.next();
							mess.setReceiver(friendName);
							
							Socket s1=(Socket)hmSocket.get(friendName);
							sendMessage(s1,mess);
							
						}
						
						hmSocket.put(userName,s);
						new ServerReceiverThread(s).start();
					}
				}

				//使用数据库进行用户身份认证
				//1、加载驱动程序
				/*Class.forName("com.mysql.jdbc.Driver");
				System.out.println("已经加载了数据库驱动！");
				//2、连接数据库
				//String url="jdbc:mysql://127.0.0.1:3306/yychat";
				//中文用户名必须用下面的url
				String url="jdbc:mysql://127.0.0.1:3306/yychat?useUnicode=true&characterEncoding=UTF-8";
				String dbUser="root";
				String dbPass="";				
				Connection conn=DriverManager.getConnection(url,dbUser,dbPass);
				
				//3、创建PreparedStatement对象，用来执行SQL语句
				String user_Login_Sql="select * from user where username=? and password=?";
				PreparedStatement ptmt=conn.prepareStatement(user_Login_Sql);
				ptmt.setString(1, userName);
				ptmt.setString(2, passWord);
				
				//4、执行查询，返回结果集
				ResultSet rs=ptmt.executeQuery();
				
				//5、根据结果集来判断是否能登录
				boolean loginSuccess=rs.next();	*/

   
		}
		}catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} 
	 }
	private void sendMessage(Socket s, Message mess) throws IOException{
		ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
		oos.writeObject(mess);
		
	}
	
}