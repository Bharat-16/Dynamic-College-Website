package com.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import com.bean.Syllabus;

public class Department {
	
	public static ArrayList<String> getDepartmentNames() {
		String query="select name_of_dept from departments";
		ArrayList<String> list = new ArrayList<String>();
		try{
			PreparedStatement pst=DBService.getDBConnection().prepareStatement(query);
			ResultSet rs= pst.executeQuery();
			while(rs.next()) {
				list.add(rs.getString(1));
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return list;
	}
	public static ArrayList<Integer> getYear(String branch){
		ArrayList<Integer> list = new ArrayList<Integer>();
		String query="SELECT DISTINCT year FROM `syllabus` WHERE branch=?;";
		try{
			System.out.println(branch);
			PreparedStatement pst=DBService.getDBConnection().prepareStatement(query);
			pst.setString(1, branch);
			ResultSet rs= pst.executeQuery();
			while(rs.next()) {
				list.add(rs.getInt(1));
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return list;
	}
	public static ArrayList<Syllabus> getSyllabus(String branch, int year){
		ArrayList<Syllabus> list = new ArrayList<Syllabus>();
		String query="select semester,link from syllabus where branch=? and year=?;";
		try{
			PreparedStatement pst=DBService.getDBConnection().prepareStatement(query);
			pst.setString(1, branch);
			pst.setInt(2, year);
			ResultSet rs= pst.executeQuery();
			while(rs.next()) {
				Syllabus s = new Syllabus();
				s.setSem(rs.getInt(1));
				s.setLink(rs.getString(2));
				list.add(s);
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return list;
	}
	public static int countDepartmentTeachers(String branch, String designation) {
		String query= "SELECT COUNT(*) FROM `faculty-info` WHERE dept_code=? and designation=?;";
		try{
			PreparedStatement pst=DBService.getDBConnection().prepareStatement(query);
			pst.setString(1,branch);
			pst.setString(2, designation);
			ResultSet rs= pst.executeQuery();
			if(rs.next())
				return rs.getInt(1);
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}		
		return 0;
	}
	
	public static boolean isHOD(String id,String dept) {
		String query = "SELECT `hod_id` FROM `departments` WHERE `dept_code`=?;";
		try{
			PreparedStatement pst=DBService.getDBConnection().prepareStatement(query);
			pst.setString(1,dept);
			ResultSet rs= pst.executeQuery();
			if(rs.next())
				if(rs.getString("hod_id").equals(id))
					return true;
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}	
		return false;
	}
	
	public static String getHODName(String branch) {
		String query = "SELECT name FROM `departments` D,`faculty-info` T WHERE D.dept_code=? and D.hod_id=T.t_id";
		try{
			PreparedStatement pst=DBService.getDBConnection().prepareStatement(query);
			pst.setString(1,branch);
			ResultSet rs= pst.executeQuery();
			if(rs.next())
				return rs.getString(1);
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}	
		return "To be Decided";
	}
	public static int countStudents(String branch, int sem) {
		String	query="SELECT COUNT(*) FROM `student-academic-info` WHERE dept_code=? and sem=?;";
		try{
			PreparedStatement pst=DBService.getDBConnection().prepareStatement(query);
			pst.setString(1,branch);
			pst.setInt(2, sem);
			ResultSet rs= pst.executeQuery();
			if(rs.next())
				return rs.getInt(1);
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}	
		return 0;
	}
	
	public static ResultSet departmentFacultyList(String dept) {
		String query = "SELECT * FROM `faculty-info` WHERE `dept_code`=?;";
		ResultSet rs = null;
		try {
			PreparedStatement pst=DBService.getDBConnection().prepareStatement(query);
			pst.setString(1,dept);
			rs= pst.executeQuery();
			return rs;
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return rs;
	}
	
	public static String getClassCoordinator(String dept, int year) {
		String query = "SELECT * FROM class WHERE dept_code=? AND year=?;";
		try {
			PreparedStatement pst=DBService.getDBConnection().prepareStatement(query);
			pst.setString(1, dept);
			pst.setInt(2, year);
			ResultSet rs = pst.executeQuery();
			if(rs.next())
				return rs.getString("coordinator");
		}catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public static boolean setClassCoordinators(String dept, int year, String coordinator) {
		String query = "SELECT * FROM class WHERE dept_code=? AND year=?;";
		ResultSet rs = null;
		try {
			PreparedStatement pst=DBService.getDBConnection().prepareStatement(query);
			pst.setString(1, dept);
			pst.setInt(2, year);
			rs = pst.executeQuery();
			if(rs.next()) {
				query = "UPDATE `class` SET `coordinator`=? WHERE `dept_code`=? and `year`=?;";
				pst=DBService.conn.prepareStatement(query);
				pst.setString(1, coordinator);
				pst.setString(2, dept);
				pst.setInt(3, year);
			} else {
				query = "INSERT INTO `class` (`dept_code`, `year`, `coordinator`) VALUES (?,?,?);";
				pst.close();
				pst=DBService.conn.prepareStatement(query);
				pst.setString(1, dept);
				pst.setInt(2, year);
				pst.setString(3, coordinator);
			}
			pst.executeUpdate();
			return true;
		}catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	public static int getSemester() {
		String query = "SELECT sem FROM `student-academic-info`";
		try {
			PreparedStatement pst=DBService.getDBConnection().prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				if(rs.getInt("sem")%2==0)
					return 1;
				else
					return 0;
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return 0;
	}
}
