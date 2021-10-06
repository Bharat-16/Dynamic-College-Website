package com.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.service.LoginService;

@WebServlet("/ProfileCreation")
public class ProfileCreation extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		HttpSession session = request.getSession();
		
		
		String username,firstname,lastname,dob,gender,father,mother,category,aadhaar,caddress,paddress;
		username = session.getAttribute("username").toString();
		firstname=request.getParameter("firstname");
		lastname=request.getParameter("lastname");
		dob=request.getParameter("dob");
		gender=request.getParameter("gender");
		father=request.getParameter("fathername");
		mother=request.getParameter("mothername");
		category=request.getParameter("category");
		aadhaar=request.getParameter("aadhaar");
		caddress=request.getParameter("caddress");
		paddress=request.getParameter("paddress");
		if(LoginService.setPersonalInformation(username,firstname,lastname,dob,gender,father,mother,category,aadhaar,caddress,paddress)) {
			request.getRequestDispatcher("student/profile/add_academic_detail.jsp").forward(request, response);
		}else {
			response.sendRedirect("student/profile/add_profile_detail.jsp");
		}
	}

}
