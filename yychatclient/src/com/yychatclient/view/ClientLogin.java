package com.yychatclient.view;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

import javax.swing.*;

import com.yychat.model.Message;
import com.yychat.model.User;
import com.yychatclient.controller.ClientConnect;

public class ClientLogin extends JFrame implements ActionListener{//类名：ClientLogin，模板，对象的模板
	public static HashMap hmFriendlist=new HashMap<String,FriendList>();
	
	//定义北部组件
	JLabel jlbl1;//定义标签
	
	//定义中部组件
	JTabbedPane jtp1;//选项卡
	JPanel jp2,jp3,jp4;
	JLabel jlbl2,jlbl3,jlbl4,jlbl5;
	JTextField jtf1;
	JPasswordField jpf1;
	JButton jb4;
	JCheckBox jcb1,jcb2;
	
	//定义南部组件
	JButton jb1,jb2,jb3;
	JPanel jp1;//面板，容器
	
	public ClientLogin(){//构造方法
		//创建北部组件
		jlbl1=new JLabel(new ImageIcon("images/tou.gif"));//标签组件
		this.add(jlbl1,"North");
		
		//创建中部组件
		jp2=new JPanel(new GridLayout(3,3));//布局的问题
		jp3=new JPanel();jp4=new JPanel();
		jlbl2=new JLabel("YY号码",JLabel.CENTER);jlbl3=new JLabel("YY密码",JLabel.CENTER);
		jlbl4=new JLabel("忘记密码",JLabel.CENTER);
		jlbl4.setForeground(Color.blue);
		jlbl5=new JLabel("申请密码保护",JLabel.CENTER);
		jtf1=new JTextField();
		jpf1=new JPasswordField();
		jb4=new JButton(new ImageIcon("images/clear.gif"));
		jcb1=new JCheckBox("隐身登录");jcb2=new JCheckBox("记住密码");
		jp2.add(jlbl2);	jp2.add(jtf1);	jp2.add(jb4);	
		jp2.add(jlbl3);	jp2.add(jpf1);	jp2.add(jlbl4);
		jp2.add(jcb1);	jp2.add(jcb2);	jp2.add(jlbl5);		
		jtp1=new JTabbedPane();
		jtp1.add(jp2,"YY号码");jtp1.add(jp3,"手机号码");jtp1.add(jp4,"电子邮箱");
		this.add(jtp1);
		
		//创建南部组件
		jb1=new JButton(new ImageIcon("images/denglu.gif"));
		jb1.addActionListener(this);
		
		//注册新用户步骤1、为注册按钮添加事件响应代码
		jb2=new JButton(new ImageIcon("images/zhuce.gif"));
		jb2.addActionListener(this);//给注册按钮添加动作监听器
		
		jb3=new JButton(new ImageIcon("images/quxiao.gif"));
		jp1=new JPanel();
		jp1.add(jb1);jp1.add(jb2);jp1.add(jb3);
		this.add(jp1,"South");
		
		
		this.setSize(350,240);//设置窗口大小
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);		
	}
	

	public static void main(String[] args) {
		ClientLogin clientLogin=new ClientLogin();//创建对象，构造方法
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		//注册新用户,步骤2：响应动作代码
		if(e.getSource()==jb2){
			String userName = jtf1.getText().trim();//拿到用户名
			String passWord=new String(jpf1.getPassword());//把密码转换成字符串
			//创建User对像
			User user=new User();//对象放在堆内存，引用变量放在栈内存
			user.setUserName(userName);
			user.setPassWord(passWord);
			user.setUserMessageType("USER_REGISTER");//该user对象用来注册
			boolean registerSuccess=new ClientConnect().registerUserIntoDB(user);
			//注册新用户,步骤4：显示注册成功或失败的提示信息
			if(registerSuccess){
				JOptionPane.showMessageDialog(this,"注册成功！");
			}else{
				JOptionPane.showMessageDialog(this,"注册失败！可能有同名用户");
			}
			
		}
		
		if(e.getSource()==jb1) {
			String userName = jtf1.getText().trim();//拿到用户名
			String passWord=new String(jpf1.getPassword());//把密码转换成字符串
			//创建User对像
			User user=new User();//对象放在堆内存，引用变量放在栈内存
			user.setUserName(userName);
			user.setPassWord(passWord);
			user.setUserMessageType("USER_LOGIN");//该user对象用来登录
			
			//boolean loginSuccess=new ClientConnect().loginValidate(user);
			Message mess=new ClientConnect().loginValidateFromDB(user);
			//if(loginSuccess){
			if(mess.getMessageType().equals(Message.message_LoginSuccess)){
				//保存FriendList对象
				String friendString=mess.getContent();
				//FriendList friendList=new FriendList(userName); //构造方法需要拿到好友的名字
				FriendList friendList=new FriendList(userName,friendString); //构造方法需要拿到好友的名字
				hmFriendlist.put(userName, friendList);
				
				
				//第1步：向服务器发送获取在线用户信息的请求（Message）,类型：message_RequestOnlineFriend
				Message mess1=new Message();
				mess1.setSender(userName);
				mess1.setReceiver("Server");
				mess1.setMessageType(Message.message_RequestOnlineFriend);//请求获得服务器在线好友信息
				Socket s=(Socket)ClientConnect.hmSocket.get(userName);
				ObjectOutputStream oos;
				try {
					oos=new ObjectOutputStream(s.getOutputStream());
					oos.writeObject(mess1);
				} catch (IOException e1) {
					e1.printStackTrace();
				}				
				
				this.dispose();
			}else {
				JOptionPane.showMessageDialog(this,"密码错误");
			}			
		}			
		
	}

}
