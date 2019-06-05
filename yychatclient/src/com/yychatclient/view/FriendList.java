package com.yychatclient.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

import javax.swing.*;

import com.yychat.model.Message;
import com.yychatclient.controller.ClientConnect;

public class FriendList extends JFrame implements ActionListener,MouseListener{//顶层容器
	public static HashMap hmFriendChat1=new HashMap<String,FriendChat1>();//键值对
	
	CardLayout cardLayout;//卡片布局
	//定义对象的引用变量
	JPanel myFriendPanel;
	
	JPanel addFriendPanel;
	JButton addFriendJButton;
	JButton myFriendJButton;
	
	JScrollPane myFriendJScrollPane;
	JPanel myFriendListJPanel;
	static final int FRIENDCOUNT=51;
	JLabel[] myFriendJLabel=new JLabel[FRIENDCOUNT];//对象数组?
	//myFriendJLabel[0]...myFriendJLabel[50] 每一个都是引用变量
	
	JPanel myStrangerBlackListJPanel;
	JButton myStrangerJButton;
	JButton blackListJButton;
	
	JPanel myStrangerPanel;
	
	JPanel myFriendStrangerPanel;
	JButton myFriendJButton1;
	JButton myStrangerJButton1;
	
	JButton blackListJButton1;
	
	String userName;
	
	//public FriendList(String userName){
	public FriendList(String userName,String friendString){
		this.userName=userName;//局部变量给成员变量赋值
		
		//第一张卡片，创建对象
		myFriendPanel=new JPanel(new BorderLayout());//边界布局
		//System.out.println(myFriendPanel.getLayout());
		
		//添加新好友实验步骤：1、增加添加好友的按钮，添加监听器
		addFriendJButton=new JButton("添加好友");
		addFriendJButton.addActionListener(this);//动作监听器
		
		myFriendJButton=new JButton("我的好友");		
		addFriendPanel=new JPanel(new GridLayout(2, 1));//默认布局流式,使用网格布局
		addFriendPanel.add(addFriendJButton);
		addFriendPanel.add(myFriendJButton);
		myFriendPanel.add(addFriendPanel,"North");
		//myFriendPanel.add(myFriendJButton,"North");
		
		//中部
		/*JScrollPane myFriendJScrollPane;
		JPanel myFriendListJPanel;
		static final int FRIENDCOUNT=51;
		JLabel[] myFriendJLabel;//对象数组*/
		
		//利用数据表中好友信息来更新好友列表2、使用好友名字(friendString)来更新好友列表的图标
		myFriendListJPanel=new JPanel();
		updateFriendIcon(friendString);
		
		/*myFriendListJPanel=new JPanel(new GridLayout(FRIENDCOUNT-1,1));
		for(int i=1;i<FRIENDCOUNT;i++){
			myFriendJLabel[i]=new JLabel(i+"",new ImageIcon("images/YY1.gif"),JLabel.LEFT);//"1"标签
			myFriendJLabel[i].setEnabled(false);//未激活所有图标
			//激活自己的图标
			//if(Integer.parseInt(userName)==i) myFriendJLabel[i].setEnabled(true);
			
			myFriendJLabel[i].addMouseListener(this);//添加鼠标监听器
			myFriendListJPanel.add(myFriendJLabel[i]);
		}*/
		
		//myFriendJLabel[Integer.parseInt(userName)].setEnabled(true);
		//myFriendJScrollPane =new JScrollPane();
		//myFriendJScrollPane.add(myFriendListJPanel);
		myFriendJScrollPane =new JScrollPane(myFriendListJPanel);
		myFriendPanel.add(myFriendJScrollPane);
		
		myStrangerBlackListJPanel=new JPanel(new GridLayout(2,1));//网格布局
		myStrangerJButton=new JButton("我的陌生人");
		//添加事件监听器
		myStrangerJButton.addActionListener(this);
		
		blackListJButton=new JButton("黑名单");
		myStrangerBlackListJPanel.add(myStrangerJButton);
		myStrangerBlackListJPanel.add(blackListJButton);
		myFriendPanel.add(myStrangerBlackListJPanel,"South");
		
		//第二张卡片
		myStrangerPanel = new JPanel(new BorderLayout());
		
		myFriendStrangerPanel=new JPanel(new GridLayout(2,1));
		myFriendJButton1=new JButton("我的好友");//添加监听器
		myFriendJButton1.addActionListener(this);
		myStrangerJButton1=new JButton("我的陌生人");
		myFriendStrangerPanel.add(myFriendJButton1);
		myFriendStrangerPanel.add(myStrangerJButton1);
		myStrangerPanel.add(myFriendStrangerPanel,"North");	
		
		blackListJButton1=new JButton("黑名单");
		myStrangerPanel.add(blackListJButton1,"South");
		
		cardLayout=new CardLayout();
		this.setLayout(cardLayout);
		this.add(myFriendPanel,"1");		 
		this.add(myStrangerPanel,"2");
		
		this.setSize(150,500);
		this.setTitle(this.userName+" 的好友列表");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public void updateFriendIcon(String friendString) {
		myFriendListJPanel.removeAll();//移除全部好友图标
		String[] friendName=friendString.split(" ");
		int count=friendName.length;
		
		myFriendListJPanel.setLayout(new GridLayout(count,1));
		for(int i=0;i<count;i++){
			myFriendJLabel[i]=new JLabel(friendName[i]+"",new ImageIcon("images/YY1.gif"),JLabel.LEFT);//"1"标签
			//myFriendJLabel[i].setEnabled(false);//未激活所有图标
			//激活自己的图标
			//if(Integer.parseInt(userName)==i) myFriendJLabel[i].setEnabled(true);
			
			myFriendJLabel[i].addMouseListener(this);//添加鼠标监听器
			myFriendListJPanel.add(myFriendJLabel[i]);
		}
	}
	
	public static void main(String[] args) {
		//FriendList friendList=new FriendList();

	}
	
	
	public void setEnableFriendIcon(String friendString){
		//取出好友名字
		//friendString=friendString.trim();
		String[] friendName=friendString.split(" ");
		int count=friendName.length;
		System.out.println("好友个数"+count);
		for(int i=1;i<count;i++){	
			
			System.out.println("friendName["+i+"]:"+friendName[i]);
			myFriendJLabel[Integer.parseInt(friendName[i])].setEnabled(true);//激活在线好友图标
		}		
	}
	
	public void setEnableNewFriendIcon(String newOnlinefriendString){
		myFriendJLabel[Integer.parseInt(newOnlinefriendString)].setEnabled(true);
	}
	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		//添加新好友，实验步骤2、增加处理事件的代码
		if(arg0.getSource()==addFriendJButton){
			String addFriendName=JOptionPane.showInputDialog(null,"请输入好友的名字：","添加好友",JOptionPane.DEFAULT_OPTION);
			Message mess=new Message();
			mess.setSender(userName);
			mess.setReceiver("Server");
			mess.setContent(addFriendName);
			mess.setMessageType(Message.message_AddFriend);
			//添加新好友，实验步骤4、发送消息到服务器端
			Socket s=(Socket)ClientConnect.hmSocket.get(userName);
			ObjectOutputStream oos;
			try {
				oos = new ObjectOutputStream(s.getOutputStream());
				oos.writeObject(mess);//发送到服务器端			
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		
		if(arg0.getSource()==myStrangerJButton){
			cardLayout.show(this.getContentPane(), "2");
		}
		if(arg0.getSource()==myFriendJButton1){
			cardLayout.show(this.getContentPane(), "1");
		}		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(arg0.getClickCount()==2){
			JLabel jlbl=(JLabel)arg0.getSource();
			String receiver=jlbl.getText();
			//new FriendChat(this.userName,receiver);
			//new Thread(new FriendChat(this.userName,receiver)).start();//创建线程
			
			//思路：首先去查找好友聊天界面，如何没找到，才新建，找到的话就显示。
			FriendChat1 friendChat1=(FriendChat1)hmFriendChat1.get(userName+"to"+receiver);
			if(friendChat1==null){//为空说明该对象还不存在
				friendChat1=new FriendChat1(this.userName,receiver);//friendChat1是一个变量，引用变量，引用对象
				hmFriendChat1.put(userName+"to"+receiver,friendChat1);//保存对象到HashMap中
			}else{//不为空，直接显示该对象
				friendChat1.setVisible(true);
			}			
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		JLabel jLabel=(JLabel)e.getSource();
		jLabel.setForeground(Color.red);		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		JLabel jLabel=(JLabel)e.getSource();
		jLabel.setForeground(Color.black);	
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
