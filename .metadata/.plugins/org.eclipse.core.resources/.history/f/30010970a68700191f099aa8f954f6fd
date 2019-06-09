package com.yychatserver.controller;

import java.nio.channels.ClosedByInterruptException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class YychatDbUtil {
	//类（静态）成员
	public static final String MYSQLDRIVER="com.mysql.jdbc.Driver";
	public static final String URL="jdbc:mysql://127.0.0.1:3306/yychat?useUnicode=true&characterEncoding=UTF-8";
	public static final String DBUSER="root";
	public static final String DBPASS="";
	
	//1、加载驱动程序
	public static void loadDriver(){
		try {
			Class.forName(MYSQLDRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace(); 
		}		
	}			
	
	//2、获取连接对象
	public static Connection getConnection(){
		loadDriver();
		Connection conn=null;
		try {
			conn = DriverManager.getConnection(URL,DBUSER,DBPASS);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public static int addRelation(String majorUser,String slaveUser,String relationType){
		int count=0;
		Connection conn=getConnection();
		//创建PreparedStatement对象，用来执行SQL语句,标准
		String relation_Add_Sql="insert into relation(majoruser,slaveuser,relationtype) values(?,?,?)";
		PreparedStatement ptmt=null;		
		try {
			ptmt = conn.prepareStatement(relation_Add_Sql);
			ptmt.setString(1, majorUser);
			ptmt.setString(2, slaveUser);
			ptmt.setString(3,  relationType);
			
			//执行查询，返回结果集
			count=ptmt.executeUpdate();//插入记录的条数
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDB(conn,ptmt);
		}			
		return count;
	}
	
	//添加新用户的方法
	public static void addUser(String userName,String passWord){
		Connection conn=getConnection();
		//创建PreparedStatement对象，用来执行SQL语句,标准
		String user_Login_Sql="insert into user(username,password,registertimestamp) values(?,?,?)";
		PreparedStatement ptmt=null;		
		try {
			ptmt = conn.prepareStatement(user_Login_Sql);
			ptmt.setString(1, userName);
			ptmt.setString(2, passWord);
			//java.util.Date date=new java.util.Date();//类完全限定名
			Date date=new Date();
			java.sql.Timestamp timestamp=new java.sql.Timestamp(date.getTime());			
			ptmt.setTimestamp(3, timestamp);
			
			//执行查询，返回结果集
			int count=ptmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDB(conn,ptmt);
		}		
	}
	
	//查找用户的方法
	public static boolean seekUser(String userName){
		boolean seekUserResult=false;
		Connection conn=getConnection();
		//创建PreparedStatement对象，用来执行SQL语句,标准
		String user_Login_Sql="select * from user where username=?";
		PreparedStatement ptmt=null;
		ResultSet rs=null;
		try {
			ptmt = conn.prepareStatement(user_Login_Sql);
			ptmt.setString(1, userName);
			
			//执行查询，返回结果集
			rs=ptmt.executeQuery();
			//根据结果集来判断是否能登录
			seekUserResult=rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDB(conn,ptmt,rs);
		}		
		
		return seekUserResult;
	}
	
	public static boolean loginValidate(String userName,String passWord){
		boolean loginSuccess=false;
		Connection conn=getConnection();
		//3、创建PreparedStatement对象，用来执行SQL语句,标准
		String user_Login_Sql="select * from user where username=? and password=?";
		PreparedStatement ptmt=null;
		ResultSet rs=null;
		try {
			ptmt = conn.prepareStatement(user_Login_Sql);
			ptmt.setString(1, userName);
			ptmt.setString(2, passWord);
			
			//4、执行查询，返回结果集
			rs=ptmt.executeQuery();
			//5、根据结果集来判断是否能登录
			loginSuccess=rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDB(conn,ptmt,rs);
		}						
		System.out.println("loginSuccess为："+loginSuccess);
		return loginSuccess;
	}
	
	public static boolean seekRelation(String majorUser,String slaveUser,String relationType){
		boolean seekRelationResult=false;
		Connection conn=getConnection();
		String seek_Relation_Sql="select * from relation where majoruser=? and slaveuser=? and relationtype=?";
		PreparedStatement ptmt=null;
		ResultSet rs=null;
		try {
			ptmt = conn.prepareStatement(seek_Relation_Sql);
			ptmt.setString(1, majorUser);
			ptmt.setString(2, slaveUser);
			ptmt.setString(3, relationType);
			rs=ptmt.executeQuery();			
			seekRelationResult=rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDB(conn,ptmt,rs);
		}
		return seekRelationResult;
	}
	
	public static String getFriendString(String userName){//拿到userName的全部好友
		Connection conn=getConnection();
		String friend_Relation_Sql="select slaveuser from relation where majoruser=? and relationtype='1'";
		PreparedStatement ptmt=null;
		String friendString="";
		ResultSet rs=null;
		try {
			ptmt = conn.prepareStatement(friend_Relation_Sql);
			ptmt.setString(1, userName);
			rs=ptmt.executeQuery();
			
			while(rs.next()){//移动结果集中的指针,把好友的名字一个个的取出来
				friendString=friendString+rs.getString("slaveuser")+" ";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDB(conn,ptmt,rs);
		}
		return friendString;
	}
	
	public static void closeDB(Connection conn,PreparedStatement ptmt){//方法的重载，面向对象编程的一大特征：多态，一种表现形式
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(ptmt!=null){
			try {
				ptmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	}
	
	public static void closeDB(Connection conn,PreparedStatement ptmt,ResultSet rs){
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(ptmt!=null){
			try {
				ptmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
}
