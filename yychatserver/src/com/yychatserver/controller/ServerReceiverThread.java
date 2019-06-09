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
	public ServerReceiverThread(Socket s){//s���뷢�������Ӧ�ķ�����Socket����
		this.s=s;
	}
	public void run(){
		while(true){
		try {
			ois=new ObjectInputStream(s.getInputStream());
		      mess=(Message)ois.readObject();
		      sender=mess.getSender();
		System.out.println(mess.getSender()+"��"+mess.getReceiver()+"˵: "+mess.getContent());
		
		//����º��ѣ�ʵ�鲽��5���������˴�����Ӻ��ѵ���Ϣ��
		if(mess.getMessageType().equals(Message.message_AddFriend)){
			String addFriendName=mess.getContent();
			System.out.println("��Ҫ����º��ѵ����֣�"+addFriendName);
			//����º��ѣ�ʵ�鲽��6����ѯ�û��Ƿ����
			if(!YychatDbUtil.seekUser(addFriendName)){
				//�û������ڣ�������Ӻ���
				mess.setMessageType(Message.message_AddFriendFailure_NoUser);
			}else{
				//�û�����
				//����º��ѣ�ʵ�鲽��7���жϸ��û��Ƿ��Ѿ��Ǻ���
				String relationType="1";//��1����ʾ����
				if(YychatDbUtil.seekRelation(sender,addFriendName,relationType)){//��ѯ�ú���
					//�Ѿ��Ǻ��ѣ��������
					mess.setMessageType(Message.message_AddFriendFailure_AlreadyFriend);
				}else {
					//����º��ѣ�ʵ�鲽��8����Ӻ��ѣ���ȫ���������ַ��͵��ͻ���
					int count=YychatDbUtil.addRelation(sender,addFriendName,relationType);
					if(count!=0){
						mess.setMessageType(Message.message_AddFriendSuccess);
						//�õ�ȫ������
						String  allFriendName=YychatDbUtil.getFriendString(sender);
						mess.setContent(allFriendName);
					}
				}
			}
			sendMessage(s, mess);//���͵��ͻ���
		}
		
		if(mess.getMessageType().equals(Message.message_Common)){
	Socket s1=(Socket)StartServer.hmSocket.get(mess.getReceiver());
		//oos=new ObjectOutputStream(s1.getOutputStream());
		//oos.writeObject(mess);
		sendMessage(s1,mess);
		}
		
		//�ڶ��������������ܵ�����������ߺ�����Ϣ
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
					System.out.println("ȫ�����ѵ����֣�"+friendString);
					
					
					//���ͺ������ֵ��ͻ���
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