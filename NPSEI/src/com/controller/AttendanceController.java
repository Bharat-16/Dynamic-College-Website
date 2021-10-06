package com.controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.service.StudentService;

@WebServlet("/AttendanceController")
public class AttendanceController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		//HttpSession session = request.getSession();
		
		ResultSet rs = StudentService.classStudents(request.getParameter("branch"), Integer.valueOf(request.getParameter("sem")));
		try {
			while(rs.next()) {
				String attendance = request.getParameter(rs.getString("student_id"));
				if(StudentService.fillAttendance(rs.getString("student_id"),request.getParameter("subject"),request.getParameter("branch"),Integer.valueOf(request.getParameter("sem")),attendance))
					System.out.println("success");
				else
					System.out.println("kuch gadbad");
			}
			StudentService.increaseLecture(request.getParameter("branch"), Integer.valueOf(request.getParameter("sem")), request.getParameter("subject"));
			response.sendRedirect("/NPSEI/faculty/profile/profile.jsp");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			response.sendRedirect("/NPSEI/faculty/roll-call.jsp");
		}
	}

}
