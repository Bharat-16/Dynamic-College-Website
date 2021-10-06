package com.controller;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.service.FacultyService;
import com.service.StudentService;

@WebServlet("/AssignmentController")
@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
maxFileSize=1024*1024*10,      // 10MB
maxRequestSize=1024*1024*75)   // 75MB
public class AssignmentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		HttpSession session = request.getSession();		
		
		String subject, branch, ass_title;
		subject = request.getParameter("subject");
		branch = request.getParameter("branch");
		
		if(request.getParameter("submission")!=null) {
			ass_title = request.getParameter("ass_title");
			int assign = Integer.valueOf(request.getParameter("ass_no"));
			ResultSet rs = StudentService.classStudents(request.getParameter("branch"), Integer.valueOf(request.getParameter("sem")));
			try {
				while(rs.next()) {
					if(request.getParameter(rs.getString("student_id"))==null) {
						continue;
					}else if(request.getParameter(rs.getString("student_id")).equals("")){
						continue;
					}
					else {
						int marks = Integer.valueOf(request.getParameter(rs.getString("student_id")));
						if(!FacultyService.setMarks(rs.getString("student_id"), subject, assign, ass_title, marks)) {
							response.sendRedirect("/NPSEI/faculty/selectAssignment.jsp");
							return;
						}
					}
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			session.setAttribute("message", "Marks Saved Successfully!!");
			response.sendRedirect("/NPSEI/faculty/profile/profile.jsp");
			return;
		}
		
		
		
		String appPath = "C:\\Users\\Hp\\eclipse-workspace1\\NPSEI\\WebContent\\docs\\assignments" + File.separator + subject;
        
        // creates the save directory if it does not exists
        try {
	        File fileSaveDir = new File(appPath);
	        if (!fileSaveDir.exists()) {
	            fileSaveDir.mkdir();
	        }
	      
	        int assign = FacultyService.countAssignment(subject, branch)+1;
	        
	        for (Part part : request.getParts()) {
	            String fileName = extractFileName(part);
	            // refines the fileName in case it is an absolute path
	            fileName = new File(fileName).getName();
	            if(fileName.equals(""))
	            	continue;
	            part.write(appPath + File.separator + fileName );
	            
	            String sdate;
	            int maxmarks;
	            ass_title = request.getParameter("assignment"+assign);
	            sdate = request.getParameter("date"+assign);
	            maxmarks = Integer.valueOf(request.getParameter("max_marks"+assign));
				if(!FacultyService.addAssignments(subject, branch, ass_title, assign, maxmarks, sdate, fileName)) {
					response.sendRedirect("/NPSEI/faculty/selectAssignment.jsp");
					return;
				}
				assign++;
	        }
	        /*
	         * 
	         * 
	         * 
	        	saare bcho ko mail bhi send krna hoga assignment k regarding
	         *
	         *
	         *
	         */
	        session.setAttribute("message", "Assignment Uploaded Successfully!!");
        }catch(Exception e) {
        	System.out.println(e.getMessage());
        	response.sendRedirect("/NPSEI/faculty/selectAssignment.jsp");
        	return;
        }
		
		response.sendRedirect("/NPSEI/faculty/profile/profile.jsp");
		
	}
	
	private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length()-1);
            }
        }
        return "";
    }

}
