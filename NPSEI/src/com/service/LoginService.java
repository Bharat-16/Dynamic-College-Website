package com.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginService {

	public static boolean authenticateUser(String username, String password) {
		String query;
		query="SELECT PASSWORD FROM `student-login-info` WHERE email=? or mobile=?";
		try {
			PreparedStatement pst=DBService.getDBConnection().prepareStatement(query);
			pst.setString(1, username);
			pst.setString(2, username);
			ResultSet rs= pst.executeQuery();
			if(rs.next()) {
				if(rs.getString("password").equals(password))
					return true;
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	public static boolean registration(String user, String id, String email, String mobile, String password) {
		String query;
		if(user.equals("student"))
			query="INSERT INTO `student-login-info` VALUES (?,?,?,?);";
		else
			query="INSERT INTO `faculty-login-info` VALUES (?,?,?,?);";
		try {
			PreparedStatement pst=DBService.getDBConnection().prepareStatement(query);
			pst.setString(1, id);
			pst.setLong(2, Long.parseLong(mobile));
			pst.setString(3, email);
			pst.setString(4, password);
			pst.executeUpdate();
			return true;
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	public static String getUsername(String email) {
		String query;
		query="SELECT Student_id FROM `student-login-info` WHERE email=? or mobile=?";
		try {
			PreparedStatement pst=DBService.getDBConnection().prepareStatement(query);
			pst.setString(1, email);
			pst.setString(2, email);
			ResultSet rs= pst.executeQuery();
			if(rs.next()) {
				return rs.getString("student_id");
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public static String getStudentName(String id) {
		String query;
		query="SELECT name FROM `student-personal-info` WHERE student_id=?";
		try {
			PreparedStatement pst=DBService.getDBConnection().prepareStatement(query);
			pst.setString(1, id);
			ResultSet rs= pst.executeQuery();
			if(rs.next()) {
				return rs.getString("name");
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public static boolean setPersonalInformation(String id, String firstname, String lastname,
			String dob, String gender, String father, String mother, String category, String aadhaar,
			String caddress,String paddress) {
		String query;
		query="INSERT INTO `student-personal-info` VALUES ('"+id+"', '"+firstname+" "+lastname+"', '"+dob+"', '"+gender+"', '"+father+"', '"+mother+"', '"+category+"', '"+aadhaar+"', '"+caddress+"', '"+paddress+"');";
		try {
			PreparedStatement pst=DBService.getDBConnection().prepareStatement(query);
			pst.executeUpdate();
			return true;
		} catch(SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	public static boolean setBasicAcademic(String id, String exam, String school, String board,
			float percent, int year) {
		String query;
		query="INSERT INTO `student-basic-academic` VALUES (?,?,?,?,?,?)";
		try {
			PreparedStatement pst=DBService.getDBConnection().prepareStatement(query);
			pst.setString(1, id);
			pst.setString(2, exam);
			pst.setString(3, school);
			pst.setString(4, board);
			pst.setFloat(5, percent);
			pst.setInt(6, year);
			pst.executeUpdate();
			return true;
		} catch(SQLException e) {
			System.out.println(e.getMessage());
			//error aaya to us user ki detail delete krni hogi database se
			return false;
		}
	}
	
	public static boolean isPersonalInfo(String username) {
		String query;
		query="SELECT * FROM `student-personal-info` WHERE student_id=?";
		try {
			PreparedStatement pst=DBService.getDBConnection().prepareStatement(query);
			pst.setString(1, username);
			ResultSet rs= pst.executeQuery();
			if(rs.next()!=false) {
				return true;
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	public static boolean isAcademicInfo(String username) {
		String query;
		query="SELECT * FROM `student-basic-academic` WHERE student_id=?";
		try {
			PreparedStatement pst=DBService.getDBConnection().prepareStatement(query);
			pst.setString(1, username);
			ResultSet rs= pst.executeQuery();
			if(rs.next()!=false) {
				return true;
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	//about faculty
	public static boolean setFacultyInfo(String id, String fName, String lName, String dob, String gender, 
			String designation, String department, String qualification, String specification) {
		String query;
		query="INSERT INTO `faculty-info` VALUES (?,?,'"+dob+"',?,?,?,?,?);";
		try {
			PreparedStatement pst=DBService.getDBConnection().prepareStatement(query);
			pst.setString(1, id);
			pst.setString(2, fName+" "+lName);
			
			pst.setString(3, gender);
			pst.setString(4, designation);
			pst.setString(5, department);
			pst.setString(6, qualification);
			pst.setString(7, specification);
			pst.executeUpdate();
			return true;
		} catch(SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	public static boolean isFacultyInfo(String id) {
		String query = "SELECT * FROM `faculty-info` WHERE `t_id`=?;";
		try {
			PreparedStatement pst = DBService.getDBConnection().prepareStatement(query);
			pst.setString(1, id);
			ResultSet rs = pst.executeQuery();
			return rs.next();
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	public static boolean authenticateFaculty(String username, String password) {
		String query;
		query="SELECT PASSWORD FROM `faculty-login-info` WHERE email=? or mobile=?";
		try {
			PreparedStatement pst=DBService.getDBConnection().prepareStatement(query);
			pst.setString(1, username);
			pst.setString(2, username);
			ResultSet rs= pst.executeQuery();
			if(rs.next()) {
				if(rs.getString("password").equals(password))
					return true;
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	public static String getFacultyID(String email) {
		String query;
		query="SELECT t_id FROM `faculty-login-info` WHERE email=? or mobile=?";
		try {
			PreparedStatement pst=DBService.getDBConnection().prepareStatement(query);
			pst.setString(1, email);
			pst.setString(2, email);
			ResultSet rs= pst.executeQuery();
			if(rs.next()) {
				return rs.getString("t_id");
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public static String getFacultyName(String username) {
		String query;
		query="SELECT name FROM `faculty-info` WHERE t_id=?";
		try {
			PreparedStatement pst=DBService.getDBConnection().prepareStatement(query);
			pst.setString(1, username);
			ResultSet rs= pst.executeQuery();
			if(rs.next()) {
				return rs.getString("name");
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
}
