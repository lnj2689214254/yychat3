package com.yychatclient.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.Socket;
import java.util.HashMap;

import javax.swing.*;

public class FriendList extends JFrame implements ActionListener,MouseListener{//顶层容器
	public static HashMap hmFriendChat1=new HashMap<String,FriendChat1>();
    CardLayout cardLayout;//卡片布局
	//定义对象的引用变量
	JPanel  myFriendPanel;//第一张
	JButton  myFriendJButton;
	
	JScrollPane myFriendJScrollPane;
	JPanel myFriendListJPanel;
	static final int FRIENDCOUNT=51;
	JLabel[]myfriendJLabel=new JLabel[FRIENDCOUNT];//对象数组
	//myfriendJLabel[0]...myfriendJLabel[50] 每一个都是引用变量
	
	
	JPanel  myStrangerBlackListJPanel;
	JButton  myStrangerJButton;
	JButton  blackListJButton;
	
	JPanel  myStrangerPanel;//第二张
	
	JPanel myFriendStrangerPanel;
	JButton  myFriendJButton1;
	JButton  myStrangerJButton1;
	
	JScrollPane myFriendJScrollPane1;
	JPanel myFriendListJPanel1;
	static final int FRIENDCOUNT1=21;
	JLabel[]myfriendJLabel1=new JLabel[FRIENDCOUNT1];
	
	JButton  blackListJButton1;
	
	String userName;
	
	public FriendList(String userName, String friendString){
		this.userName=userName;//局部变量给成员变量赋值
		//第一张卡片，创建对象
		myFriendPanel=new JPanel(new BorderLayout());//边界布局
		
		myFriendJButton=new JButton("我的好友"); 
		myFriendPanel.add(myFriendJButton,"North"); 
		
		//中部
		/*JScrollPane myFriendJScrollPane;
		JPanel myFriendListJPanel;
		static final int FRIENDCOUNT=51;
		JLabel[]myfriendJLabel;*/
		
		
		
			
			String[] friendName=friendString.split(" ");
			int count=friendName.length;
			
			myFriendListJPanel=new JPanel(new GridLayout(count,1));
			for(int i=0;i<count;i++){
				myfriendJLabel[i]=new JLabel(friendName[i]+"",new ImageIcon("images/qq.gif"),JLabel.LEFT);
			
			
			myfriendJLabel[i].addMouseListener(this);
			myFriendListJPanel.add(myfriendJLabel[i]);
		//myFriendListJPanel=new JPanel(new GridLayout(FRIENDCOUNT-1,1));
		//for(int i=1;i<FRIENDCOUNT;i++){
			//myfriendJLabel[i]=new JLabel(i+"",new ImageIcon("images/qq.gif"),JLabel.LEFT);//"1"
			//myfriendJLabel[i].setEnabled(false);
			myfriendJLabel[i].addMouseListener(this);//添加鼠标监听器
			myFriendListJPanel.add(myfriendJLabel[i]);
			}
		//myfriendJLabel[Integer.parseInt(userName)].setEnabled(true);
		myFriendJScrollPane =new JScrollPane();
		myFriendJScrollPane.add(myFriendListJPanel);
		myFriendJScrollPane =new JScrollPane(myFriendListJPanel);
		myFriendPanel.add(myFriendJScrollPane);
		
		
		myStrangerBlackListJPanel=new JPanel(new GridLayout(2,1));//网格布局
		myStrangerJButton=new JButton("我的陌生人");
		myStrangerJButton.addActionListener(this);//事件监听器
		blackListJButton=new  JButton("黑名单");
		myStrangerBlackListJPanel.add(myStrangerJButton);
		myStrangerBlackListJPanel.add(blackListJButton);
		myFriendPanel.add(myStrangerBlackListJPanel,"South");
		
		//另一张卡片
		myStrangerPanel = new JPanel(new BorderLayout()); 
		
		myFriendStrangerPanel = new JPanel(new GridLayout(2,1));
		myFriendJButton1=new JButton("我的好友");
		myFriendJButton1.addActionListener(this);//事件监听器
		myStrangerJButton1=new JButton("我的陌生人"); 
		myFriendStrangerPanel.add(myFriendJButton1);
		myFriendStrangerPanel.add(myStrangerJButton1);
		myStrangerPanel.add(myFriendStrangerPanel,"North");
		
		myFriendListJPanel1=new JPanel(new GridLayout(FRIENDCOUNT1-1,1));
		for(int i=1;i<FRIENDCOUNT1;i++){
			myfriendJLabel1[i]=new JLabel(i+"",new ImageIcon("images/qq.gif"),JLabel.LEFT);//"1"
			myfriendJLabel1[i].addMouseListener(this);//添加鼠标监听器
			myFriendListJPanel1.add(myfriendJLabel1[i]);
		}
		myFriendJScrollPane1 =new JScrollPane(myFriendListJPanel1);
		myStrangerPanel.add(myFriendJScrollPane1);
		
		
		
		blackListJButton1=new  JButton("黑名单");
		myStrangerPanel.add(blackListJButton1,"South");
		
		cardLayout=new CardLayout();
		this.setLayout(cardLayout);
		this.add(myFriendPanel,"1");
	    this.add(myStrangerPanel,"2");
	    
	    this.setSize(150,500);
	    this.setTitle(this.userName+"的好友列表");
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setLocationRelativeTo(null);
        this.setVisible(true);
	}
	
	
	public static void main(String[] args) {
		//FriendList  friendList=new FriendList();

	}
	
	
	public void setEnableFriendIcon(String friendString){
		String[] friendName=friendString.split(" ");
		int count=friendName.length;
		System.out.println("好友个数"+count);
		for(int i=1;i<count;i++){
			
			System.out.println("friendName["+i+"];"+friendName[i]);
			myfriendJLabel[Integer.parseInt(friendName[i])].setEnabled(true);
		}
		
	}
public void setEnableNewFriendIcon(String newOnlinefriendString){
		
		myfriendJLabel[Integer.parseInt(newOnlinefriendString)].setEnabled(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource()==myStrangerJButton){
			cardLayout.show(this.getContentPane(), "2");
			 
		 }
		if(arg0.getSource()==myFriendJButton1){
			cardLayout.show(this.getContentPane(), "1");
			 
		 }
		  
		 }


	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getClickCount()==2){
			JLabel jlbl=(JLabel)e.getSource();
			String receiver=jlbl.getText();
			//new FriendChat1(this.userName,receiver);
		    // new Thread(new FriendChat(this.userName,receiver)).start();//创建线程
			
			
			FriendChat1 friendChat1=(FriendChat1)hmFriendChat1.get(userName+"to"+receiver);
			if(friendChat1==null)
			{
				 friendChat1=new FriendChat1(this.userName,receiver);
				hmFriendChat1.put(userName+"to"+receiver,friendChat1);
			}else{
				friendChat1.setVisible(true);
			}
				
			
		}
	}
		



	@Override
	public void mousePressed(MouseEvent e) {
		JLabel jLabel=(JLabel)e.getSource();
		jLabel.setForeground(Color.red);
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		JLabel jLabel=(JLabel)e.getSource();
		jLabel.setForeground(Color.black);
		
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	
	
}