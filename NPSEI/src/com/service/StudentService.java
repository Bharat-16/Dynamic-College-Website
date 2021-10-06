package com.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentService {

	public static ResultSet getStudentDetail(String id) {
		String query="SELECT * FROM `student-personal-info` WHERE `student_id` =?;";
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
	
	public static ResultSet getStudentLoginDetail(String id) {
		String query="SELECT * FROM `student-login-info` WHERE `student_id` ='"+id+"';";
		ResultSet rs=null;
		try{
			PreparedStatement pst=DBService.getDBConnection().prepareStatement(query);
			//pst.setString(1, id);
			rs= pst.executeQuery(query);
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return rs;
	}
	
	public static ResultSet getAcademicInfo(String id) {
		ResultSet rs= null;
		String query = "SELECT * FROM `student-academic-info` WHERE `student_id` ='"+id+"';";
		try{
			PreparedStatement pst=DBService.getDBConnection().prepareStatement(query);
			//pst.setString(1, id);
			rs= pst.executeQuery(query);
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return rs;
	}
	
	public static ResultSet getAttendance(String student, String subject) {
		ResultSet rs=null;
		String query = "SELECT * FROM `student-attendance` where student_id=? and subject=?;";
		try{
			PreparedStatement pst=DBService.getDBConnection().prepareStatement(query);
			pst.setString(1, student);
			pst.setString(2, subject);
			rs= pst.executeQuery();
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return rs;
	}
	
	public static ResultSet classStudents(String branch, int sem) {
		
		ResultSet rs = null;
		String query = "SELECT `student_id` FROM `student-academic-info` where dept_code=? and sem=?;";
		try{
			PreparedStatement pst=DBService.getDBConnection().prepareStatement(query);
			pst.setString(1, branch);
			pst.setInt(2, sem);
			rs= pst.executeQuery();
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return rs;
	}
	
	public static int attendanceCount(String dept, int sem, String subject) {
		String query = "SELECT lecture FROM `attendance-count` WHERE dept_code=? and semester=? and subject=?";
		try {
			PreparedStatement pst = DBService.getDBConnection().prepareStatement(query);
			pst.setString(1, dept);
			pst.setInt(2, sem);
			pst.setString(3, subject);
			ResultSet rs = pst.executeQuery();
			if(rs.next())
				return rs.getInt("lecture");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return 0;
	}
	public static boolean increaseLecture(String dept, int sem, String subject) {
		String query;
		if(attendanceCount(dept,sem,subject)==0)
			query = "INSERT INTO `attendance-count` VALUES ('"+dept+"','"+sem+"','"+subject+"','1');";
		else
			query = "UPDATE `attendance-count` SET lecture='"+(attendanceCount(dept, sem, subject)+1)+"' WHERE dept_code='"+dept+"' and semester='"+sem+"' and subject='"+subject+"';";
		PreparedStatement pst;
		try {
			pst = DBService.getDBConnection().prepareStatement(query);
			pst.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	public static boolean fillAttendance(String id, String subject, String dept, int sem, String attendance){
		String query;
		ResultSet rs = StudentService.getAttendance(id, subject);
		try {
			if(rs.next()) {
				if(attendance.equals("NULL"))
					query = "";
				else
					query = "UPDATE `student-attendance` SET `l"+(attendanceCount(dept,sem,subject)+1)+"`='"+attendance+"' WHERE student_id='"+id+"' and subject='"+subject+"';";
			} else {
				if(attendance.equals("NULL"))
					query = "INSERT INTO `student-attendance` (`student_id`, `subject`) VALUES ('"+id+"', '"+subject+"');";
				else
					query = "INSERT INTO `student-attendance` (`student_id`, `subject`, `l"+(attendanceCount(dept,sem,subject)+1)+"`) VALUES ('"+id+"', '"+subject+"', '"+attendance+"');";
			}
			PreparedStatement pst=DBService.getDBConnection().prepareStatement(query);
			pst.executeUpdate();
			return true;
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	public static String getName(String id) {
		String query="SELECT name FROM `student-personal-info` WHERE `student_id` =?;";
		try{
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
	
	public static ResultSet getStudentAttendance(String student) {
		ResultSet rs=null;
		String query = "SELECT * FROM `student-attendance` where student_id=?;";
		try{
			PreparedStatement pst=DBService.getDBConnection().prepareStatement(query);
			pst.setString(1, student);
			rs= pst.executeQuery();
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return rs;
	}
}
