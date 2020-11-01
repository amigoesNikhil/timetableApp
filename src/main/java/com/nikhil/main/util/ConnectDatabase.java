package com.nikhil.main.util;

import java.sql.Connection;
import java.sql.DriverManager;

import org.springframework.stereotype.Component;

@Component
public class ConnectDatabase {
	
	/*static String username="root";
	static String password="nikhil20165520";
	static String url="jdbc:mysql://localhost:3306/trialtimetable1";*/
	static String username="amigoes1_user";
	static String password="BitAllUsers";
	static String url="jdbc:mysql://johnny.heliohost.org:3306/amigoes1_trialtimetable12";
	Connection connection=null;
	
	public ConnectDatabase() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection=DriverManager.getConnection(url, username, password);
		}catch(Exception e) {
			System.out.println("Sorry not able to connect");
		}
	}
	
	

	public Connection getConnection() {
		return this.connection;
	}



	/*public Connection getConnection() {
		Connection connect=null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connect=DriverManager.getConnection(url, username, password);
		}catch(Exception e) {
			System.out.println("Sorry not able to connect");
		}
		return connect;
		
	}*/
	
	

}
