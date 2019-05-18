package com.yychatclient.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import com.yychat.model.Message;
import com.yychat.model.User;
import com.yychatserver.controller.ClientConnect;


public class ClientLogin extends JFrame implements ActionListener{
	public static HashMap hmFriendlist=new HashMap<String,FriendList>();
	JLabel jlbl1;
	
	JTabbedPane jtp1;
	JPanel jp2,jp3,jp4;
	JLabel jlbl2,jlbl3,jlbl4,jlbl5;
	JTextField jtf1;
	JPasswordField jpf1;
	JButton jb4;
	JCheckBox jcb1,jcb2;
	
	
	JButton jb1,jb2,jb3;
	JPanel jp1;
	
	public ClientLogin() {
		jlbl1=new JLabel(new ImageIcon("images/tou.gif"));
		this.add(jlbl1,"North");
		
		jp2=new JPanel(new GridLayout(3,3));
		jp3=new JPanel();jp4=new JPanel();
		jlbl2=new JLabel("YY号码",JLabel.CENTER);jlbl3=new JLabel("YY密码",JLabel.CENTER);
		jlbl4=new JLabel("忘记密码",JLabel.CENTER);
		jlbl4.setForeground(Color.blue);
		jlbl5=new JLabel("申请密码保护",JLabel.CENTER);
		jtf1=new JTextField();
		jpf1=new JPasswordField();
		jb4=new JButton(new ImageIcon("images/clear.gif"));
		jcb1=new JCheckBox("隐身登录");jcb2=new JCheckBox("记住密码");
		jp2.add(jlbl2);jp2.add(jtf1);jp2.add(jb4);
		jp2.add(jlbl3);jp2.add(jpf1);jp2.add(jlbl4);
		jp2.add(jcb1);jp2.add(jcb2);jp2.add(jlbl5);
		jtp1=new JTabbedPane();
		jtp1.add(jp2,"YY号码");jtp1.add(jp3,"手机号码");jtp1.add(jp4,"电子邮箱");
		this.add(jtp1);
		
		jb1=new JButton(new ImageIcon("images/denglu.gif"));
		jb1.addActionListener(this);
		jb2=new JButton(new ImageIcon("images/zhuce.gif"));
		jb3=new JButton(new ImageIcon("images/quxiao.gif"));
		jp1=new JPanel();
		jp1.add(jb1);jp1.add(jb2);jp1.add(jb3);
		this.add(jp1,"South");
		
		
		this.setSize(350,240);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		
		
}

	
	public static void main(String[] args) {
		ClientLogin ClientLogin=new ClientLogin();

	}


	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==jb1) {
			String userName = jtf1.getText().trim();
			String password=new String(jpf1.getPassword());
			
			User user=new User();
			user.setUserName(userName);
			user.setPassword(password);
			
			//boolean loginSuccess=new ClientConnect().loginValidate(user);
			Message mess=new ClientConnect().loginValidateFromD8(user);
			
			//if(loginSuccess){
			if(mess.getMessageType().equals(Message.message_LoginSuccess)){
				String friendString=mess.getContent();
				
		     //FriendList friendList=new FriendList(userName);
				FriendList friendList=new FriendList(userName,friendString);
		     hmFriendlist.put(userName,friendList);
		     
		     //第一步向服务器发送获取在线用户信息的请求（）Message)
		     Message mess1=new Message();
			mess1.setSender(userName);
	           mess1.setReceiver("Server");
	            mess1.setMessageType(Message.message_RequestOnlineFriend);
	            	Socket s=(Socket)ClientConnect.hmSocket.get(userName);
		     ObjectOutputStream oss;
		     try{
		    	 oss=new ObjectOutputStream(s.getOutputStream());
		    	 oss.writeObject(mess1);
		     }
		     catch (IOException e1){
		    e1.printStackTrace();
		     }
		     this.dispose();
		     }else{
				JOptionPane.showMessageDialog(this, "密码错误");
		     }
	    }
	}

}
