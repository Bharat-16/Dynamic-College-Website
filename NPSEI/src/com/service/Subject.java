package com.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Subject {
	
	public static ResultSet getSubjectList(String branch, int sem) {
		String query= "SELECT `subject_code`, `subject_name` FROM `all-subjects` WHERE dept_code=? and semester=?;";
		ResultSet rs = null;
		try{
			PreparedStatement pst=DBService.getDBConnection().prepareStatement(query);
			pst.setString(1,branch);
			pst.setInt(2, sem);
			rs= pst.executeQuery();
			return rs;
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}		
		return rs;
	}
	
	public static ResultSet getElectiveSubject(String branch, String type) {
		String query= "SELECT `subject_code`, `subject_name` FROM `elective-subjects` WHERE dept_code=? and type=?;";
		ResultSet rs = null;
		try{
			PreparedStatement pst=DBService.getDBConnection().prepareStatement(query);
			pst.setString(1,branch);
			pst.setString(2, type);
			rs= pst.executeQuery();
			return rs;
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}		
		return rs;
	}
	
	public static String getSubjectTeacher(String branch, String code) {
		String query= "SELECT `t_id` FROM `all-subjects` WHERE dept_code=? and subject_code=?;";
		try{
			PreparedStatement pst=DBService.getDBConnection().prepareStatement(query);
			pst.setString(1,branch);
			pst.setString(2, code);
			ResultSet rs= pst.executeQuery();
			if(rs.next())
				return rs.getString("t_id");
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}		
		return null;
	}
	
	public static String getElectiveTeacher(String branch, String name) {
		String query= "SELECT `t_id` FROM `all-subjects` WHERE dept_code=? and subject_name=?;";
		try{
			PreparedStatement pst=DBService.getDBConnection().prepareStatement(query);
			pst.setString(1,branch);
			pst.setString(2, name);
			ResultSet rs= pst.executeQuery();
			if(rs.next())
				return rs.getString("t_id");
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}		
		return null;
	}
	
	public static boolean setSubjectTeacher(String id, String code, String branch) {
		String query = "UPDATE `all-subjects` SET `t_id`=? WHERE `dept_code`=? and `subject_code`=?;";
		try{
			PreparedStatement pst=DBService.getDBConnection().prepareStatement(query);
			pst.setString(1, id);
			pst.setString(2,branch);
			pst.setString(3, code);
			pst.executeUpdate();
			return true;
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}		
		return false;
	}
	
	public static boolean setElectiveTeacher(String id, String elective_name, String elective_code, String branch) {
		String query = "UPDATE `all-subjects` SET `t_id`=? WHERE `dept_code`=? and `subject_name`=?;";
		try{
			PreparedStatement pst=DBService.getDBConnection().prepareStatement(query);
			pst.setString(1, id);
			pst.setString(2,branch);
			pst.setString(3, elective_name);
			pst.executeUpdate();
			ResultSet rs = pst.executeQuery("SELECT * FROM `selected-elective` WHERE dept_code='"+branch+"' and type='"+elective_name+"';");
			if(rs.next())
				query = "UPDATE `selected-elective` SET `subject_code`='"+elective_code+"' WHERE dept_code='"+branch+"' and type='"+elective_name+"';";
			else
				query = "INSERT INTO `selected-elective` VALUES ('"+branch+"','"+elective_code+"','"+elective_name+"');";
			pst.executeUpdate(query);
			return true;
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	public static int getSemester(String branch, String name) {
		String query= "SELECT `semester` FROM `all-subjects` WHERE dept_code=? and subject_name=?;";
		ResultSet rs = null;
		try{
			PreparedStatement pst=DBService.getDBConnection().prepareStatement(query);
			pst.setString(1,branch);
			pst.setString(2, name);
			rs= pst.executeQuery();
			if(rs.next())
				return rs.getInt("semester");
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}		
		return 0;
	}
	
}
