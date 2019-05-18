package com.yychatserver.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;

import com.yychat.model.Message;
import com.yychat.model.User;
import com.yychatclient.view.FriendList;

public class ClientConnect {
	public Socket s;
	
	public static HashMap hmSocket=new HashMap<String,Socket>();
	
        public  ClientConnect(){
        	try {
				s=new Socket("127.0.0.1",3456);
			} catch (IOException e) {
				e.printStackTrace();
			}
        	
        }
        public Message loginValidateFromD8(User user){
        	//boolean loginSuccess=false;
        	ObjectOutputStream oos;
        	 ObjectInputStream ois;
        		Message  mess=null;
        	 try {
				 oos =new ObjectOutputStream(s.getOutputStream());
                 oos.writeObject(user);		
                 
                 
                  ois=new ObjectInputStream(s.getInputStream());
                 
				  mess=(Message)ois.readObject();
				  
				  
				  if(mess.getMessageType().equals(Message.message_LoginSuccess)){
					  //loginSuccess=true;
					 System.out.println(user.getUserName()+"µÇÂ½³É¹¦");
					  hmSocket.put(user.getUserName(),s);
					  new ChlientReceiverThread(s).start();
				  }
						
        	 } catch (IOException e) {
				e.printStackTrace();{
					
				}
        	 } 
        	 
        	 catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
        	 return mess;
        }
        	 public static void main(String[]args){
        }
}



