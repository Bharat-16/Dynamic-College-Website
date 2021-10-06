package com.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.service.LoginService;

@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		HttpSession session = request.getSession();
		
		String username,password;
		username=request.getParameter("username");
		password=request.getParameter("password");
		
		System.out.println(request.getParameter("user"));
		if(request.getParameter("user").equals("student")) {
			if(LoginService.authenticateUser(username, password)) {
				session.setAttribute("username", LoginService.getUsername(username));
				session.setAttribute("user", "student");
				if(LoginService.isPersonalInfo(LoginService.getUsername(username))) {
					if(LoginService.isAcademicInfo(LoginService.getUsername(username)))
						response.sendRedirect("student/profile/profile.jsp");
					else
						response.sendRedirect("student/profile/add_academic_detail.jsp");
				}else {
					response.sendRedirect("student/profile/add_profile_detail.jsp");
				}
			}else {
				session.setAttribute("errmsg", "invalid credential");
				response.sendRedirect("student/profile/login.jsp");
			}
		} else  {
			if(LoginService.authenticateFaculty(username, password)) {
				session.setAttribute("username", LoginService.getFacultyID(username));
				session.setAttribute("user", "faculty");
				if(LoginService.isFacultyInfo(LoginService.getFacultyID(username))) {
					response.sendRedirect("faculty/profile/profile.jsp");
				}else {
					response.sendRedirect("faculty/profile/add_profile_detail.jsp");
				}
			}else {
				session.setAttribute("errmsg", "invalid credential");
				response.sendRedirect("faculty/profile/login.jsp");
			}
		}
	}

}
