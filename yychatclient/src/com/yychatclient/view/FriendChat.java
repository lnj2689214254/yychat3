/*package com.yychatclient.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.*;

import com.yychat.model.Message;
import com.yychatclient.controller.ClientConnect;

public class FriendChat extends JFrame implements ActionListener,Runnable{//单继承

	//Center部分
	JScrollPane jsp;
	JTextArea jta;
	
	//South部分
	JPanel jp;
	JTextField jtf;
	JButton jb;
	
	String sender;
	String receiver;
	
	public FriendChat(String sender,String receiver){
		this.sender=sender;
		this.receiver=receiver;
		
		jta = new JTextArea();//文本区域
		jta.setEditable(false);
		jta.setForeground(Color.red);
		jsp = new JScrollPane(jta);
		this.add(jsp,"Center");
		
		jp=new JPanel();
		jtf=new JTextField(15);
		jtf.setForeground(Color.red);
		jb=new JButton("发送");
		jb.addActionListener(this);
		jp.add(jtf);
		jp.add(jb);
		this.add(jp,"South");
		
		this.setSize(350,240);
		this.setTitle(sender+"正在和"+receiver+"聊天");
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);//居中显示窗口
		this.setVisible(true);		
	}
	
	public static void main(String[] args) {
		FriendChat friendChat=new FriendChat("1","2");

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {//事件处理代码
		if(arg0.getSource()==jb){
			jta.append(jtf.getText()+"\r\n");
			
			//向服务器发送聊天信息
			Message mess=new Message();
			mess.setSender(sender);
			mess.setReceiver(receiver);
			mess.setContent(jtf.getText());
			mess.setMessageType(Message.message_Common);
			ObjectOutputStream oos;
			try {
				oos = new ObjectOutputStream(ClientConnect.s.getOutputStream());
				oos.writeObject(mess);
				
				//能不能在这里接收其他人发送来的聊天信息？
				ObjectInputStream ois = new ObjectInputStream(ClientConnect.s.getInputStream());
				mess=(Message)ois.readObject();//接收聊天信息
				String showMessage=mess.getSender()+"对"+mess.getReceiver()+"说："+mess.getContent();
				System.out.println(showMessage);
				jta.append(showMessage+"\r\n");
				
			} catch (IOException  e) {				
				e.printStackTrace();
			}			
		}		
	}

	@Override
	public void run() {
		ObjectInputStream ois;
		while(true){
			try {
				ois = new ObjectInputStream(ClientConnect.s.getInputStream());
				Message mess=(Message)ois.readObject();//接收聊天信息,线程阻塞
				String showMessage=mess.getSender()+"对"+mess.getReceiver()+"说："+mess.getContent();
				System.out.println(showMessage);
				jta.append(showMessage+"\r\n");
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}	
		}
			
	}

}
*/