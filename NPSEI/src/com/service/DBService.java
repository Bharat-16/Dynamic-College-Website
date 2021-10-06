package com.service;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBService {

	public static Connection conn=null;
	public static Connection getDBConnection() {
		String url,username,password;
		//Register Driver Class
		try {
			Class.forName("com.mysql.jdbc.Driver");
			url="jdbc:mysql://localhost:3306/collegedb";
			username="root";
			password="";
			conn=DriverManager.getConnection(url,username,password);
		}catch(Exception e) {
			System.out.println("Something went wrong");
		}
		return conn;
	}
}
