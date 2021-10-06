package com.hod;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.service.Department;

@WebServlet("/AssignClassCoordinators")
public class AssignClassCoordinators extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		HttpSession session = request.getSession();
		
		if(request.getParameter("year")!=null) {
			for(int i=1; i<=4; i++) {
				if(request.getParameter("year").equals(Integer.toString(i))) {
					if(Department.setClassCoordinators(request.getParameter("dept"), i, request.getParameter("coordinator"))) {
						session.setAttribute(Integer.toString(i),"updated");
					}else {
						session.setAttribute(Integer.toString(i), "retry");
					}
					response.sendRedirect("/NPSEI/hod/assign_class_teacher.jsp");
					return;
				}
			}
		}
		for(int i=1; i<=4; i++) {
			session.removeAttribute(Integer.toString(i));
		}
		response.sendRedirect("/NPSEI/faculty/profile/profile.jsp");
			
	}

}
