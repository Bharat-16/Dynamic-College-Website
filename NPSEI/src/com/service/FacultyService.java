package com.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FacultyService {
	public static ResultSet getFacultyDetail(String id) {
		String query="SELECT * FROM `faculty-info` WHERE `t_id`=?;";
		ResultSet rs=null;
		try{
			PreparedStatement pst=DBService.getDBConnection().prepareStatement(query);
			pst.setString(1, id);
			rs= pst.executeQuery();
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return rs;
	}
	
	public static ResultSet getFacultyLoginDetail(String id) {
		String query="SELECT * FROM `faculty-login-info` WHERE `t_id`=?;";
		ResultSet rs=null;
		try{
			PreparedStatement pst=DBService.getDBConnection().prepareStatement(query);
			pst.setString(1, id);
			rs= pst.executeQuery();
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return rs;
	}
	
	public static ResultSet getFacultySubjects(String id) {
		ResultSet rs = null;
		String query = "SELECT * FROM `all-subjects` WHERE `t_id`=? ORDER BY `semester` ASC" ;
		try{
			PreparedStatement pst=DBService.getDBConnection().prepareStatement(query);
			pst.setString(1, id);
			rs= pst.executeQuery();
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return rs;
	}
	
	public static ResultSet getAssignments(String subject, String branch) {
		ResultSet rs = null;
		String query = "SELECT * FROM `assignment-list` WHERE `subject`=? and `branch`=? ORDER BY `ass_no` ASC" ;
		try{
			PreparedStatement pst=DBService.getDBConnection().prepareStatement(query);
			pst.setString(1, subject);
			pst.setString(2, branch);
			rs= pst.executeQuery();
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return rs;
	}
	
	public static String getMarks(String id, String subject, int assign) {
		ResultSet rs = null;
		String query = "SELECT marks FROM `assignment-marks` WHERE `student_id`=? and`subject`=? and `ass_no`=?" ;
		try{
			PreparedStatement pst=DBService.getDBConnection().prepareStatement(query);
			pst.setString(1, id);
			pst.setString(2, subject);
			pst.setInt(3, assign);
			rs= pst.executeQuery();
			if(rs.next())
				return rs.getString("marks");
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return "-";
	}
	
	public static boolean setMarks(String id, String subject, int assign, String ass_title, int marks) {
		String query = "INSERT INTO `assignment-marks` VALUES (?,?,?,?,?,'0');";
		try {
			PreparedStatement pst=DBService.getDBConnection().prepareStatement(query);
			pst.setString(1, id);
			pst.setString(2, subject);
			pst.setInt(3, assign);
			pst.setString(4, ass_title);
			pst.setInt(5, marks);
			pst.executeUpdate();
			return true;
		}catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	public static int countAssignment(String subject, String branch){
		String query = "SELECT COUNT(*) FROM `assignment-list` WHERE `subject`=? and `branch`=?" ;
		try{
			PreparedStatement pst=DBService.getDBConnection().prepareStatement(query);
			pst.setString(1, subject);
			pst.setString(2, branch);
			ResultSet rs = pst.executeQuery();
			if(rs.next())
				return rs.getInt(1);
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return 0;
	}
	
	public static boolean addAssignments(String subject, String branch, String ass_title, int ass_no, int marks, String date, String file) {
		String query = "INSERT INTO `assignment-list` VALUES ('-',?,?,?,?,?,'"+date+"',?);";
		try {
			PreparedStatement pst=DBService.getDBConnection().prepareStatement(query);
			pst.setString(1, branch);
			pst.setString(2, subject);
			pst.setInt(3, ass_no);
			pst.setString(4, ass_title);
			pst.setString(5, file);
			pst.setInt(6, marks);
			pst.executeUpdate();
			return true;
		}catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	public static String assignmentFiles(String subject, String branch, int assign) {
		String query = "SELECT file FROM `assignment-list` WHERE `subject`=? and `branch`=? and `ass_no`=?;";
		try {
			PreparedStatement pst=DBService.getDBConnection().prepareStatement(query);
			pst.setString(1, subject);
			pst.setString(2, branch);
			pst.setInt(3, assign);
			ResultSet rs = pst.executeQuery();
			if(rs.next())
				return rs.getString("file");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return null;
	}
	
}
