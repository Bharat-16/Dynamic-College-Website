package com.hod;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.service.Subject;


@WebServlet("/AssignSubjectTeacher")
public class AssignSubjectTeacher extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		HttpSession session = request.getSession();
		
		String t_id, subject_code, branch;
		t_id = request.getParameter("teacher");
		subject_code = request.getParameter("subject");
		branch = request.getParameter("branch");
		
		if(request.getParameter("branch")!=null) {
			if(subject_code.equalsIgnoreCase("elective")) {
				String elective = request.getParameter("elective_name");
				String elective_code = request.getParameter("elective_subject");
				
				if(Subject.setElectiveTeacher(t_id, elective, elective_code, branch)) {
					session.setAttribute(request.getParameter("row"), "updated");
				} else {
					session.setAttribute(request.getParameter("row"), "retry");
				}
			}
	
			if(Subject.setSubjectTeacher(t_id, subject_code, branch)) {
				session.setAttribute(request.getParameter("row"), "updated");
			} else {
				session.setAttribute(request.getParameter("row"), "retry");
			}
			session.setAttribute("block", request.getParameter("block"));
			response.sendRedirect("/NPSEI/hod/select_subjects.jsp");
			return;
		}
		response.sendRedirect("/NPSEI/faculty/profile/profile.jsp");
	}
}
