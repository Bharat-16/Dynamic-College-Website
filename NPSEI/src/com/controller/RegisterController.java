package com.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.service.LoginService;

@WebServlet("/RegisterController")
public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		HttpSession session = request.getSession();
		
		if(request.getParameter("page").equals("register")) {
			session.setAttribute("username", request.getParameter("username"));
			session.setAttribute("number", request.getParameter("number"));
			session.setAttribute("email", request.getParameter("email"));
			session.setAttribute("pass", request.getParameter("pass"));
			
			if(request.getParameter("user").equals("student")) {
				session.setAttribute("user", "student");
				response.sendRedirect("student/profile/otp_verification.jsp");
			}
			else if(request.getParameter("user").equals("faculty")) {
				session.setAttribute("user", "faculty");
				response.sendRedirect("faculty/profile/otp_verification.jsp");
			}
				
		}
		else if(request.getParameter("page").equals("verification")) {
			String username,email,mobile,password,otp,otp2;
			username=session.getAttribute("username").toString();
			email=session.getAttribute("email").toString();
			mobile=session.getAttribute("number").toString();
			password=session.getAttribute("pass").toString();
			otp=request.getParameter("confirmotp");
			otp2=request.getParameter("otp");
			
			if(otp.equals(otp2)) {
				if(session.getAttribute("user").equals("student")) {
					if(LoginService.registration(session.getAttribute("user").toString(),username,email,mobile,password))
						request.getRequestDispatcher("student/profile/add_profile_detail.jsp").forward(request, response);
					else
						response.sendRedirect("student/profile/register.jsp");
				}
				else if(session.getAttribute("user").equals("faculty")) {
					if(LoginService.registration(session.getAttribute("user").toString(),username,email,mobile,password))
						request.getRequestDispatcher("faculty/profile/add_profile_detail.jsp").forward(request, response);
					else
						response.sendRedirect("faculty/profile/register.jsp");
				}
			}
			else {
				if(session.getAttribute("user").equals("student"))
					response.sendRedirect("student/profile/register.jsp");
				else
					response.sendRedirect("faculty/profile/register.jsp");
			}
				
		}
	}

}
