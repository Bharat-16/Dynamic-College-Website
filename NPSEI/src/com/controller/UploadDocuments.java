package com.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.service.LoginService;

@WebServlet("/UploadDocuments")
@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
maxFileSize=1024*1024*10,      // 10MB
maxRequestSize=1024*1024*75)   // 75MB
public class UploadDocuments extends HttpServlet {

	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		HttpSession session = request.getSession();
		
		String appPath = null;
		if(session.getAttribute("user").equals("student"))
			appPath = "C:\\Users\\Hp\\eclipse-workspace1\\NPSEI\\WebContent\\student-documents";
		else if(session.getAttribute("user").equals("faculty"))
			appPath = "C:\\Users\\Hp\\eclipse-workspace1\\NPSEI\\WebContent\\faculty\\image";
        // constructs path of the directory to save uploaded file
        String savePath = appPath + File.separator + session.getAttribute("username").toString();
        
        // creates the save directory if it does not exists
        try {
	        File fileSaveDir = new File(savePath);
	        if (!fileSaveDir.exists()) {
	            fileSaveDir.mkdir();
	        }
	      
	        for (Part part : request.getParts()) {
	            String fileName = extractFileName(part);
	            // refines the fileName in case it is an absolute path
	            fileName = new File(fileName).getName();
	            if(fileName.equals(""))
	            	continue;
	            String type = part.getContentType();
	            part.write(savePath + File.separator + part.getName() + "." + 
	            		type.substring(type.indexOf("/")+1, type.length()));
	        }
        }catch(Exception e) {
        	System.out.println(e.getMessage());
        	if(session.getAttribute("user").equals("student"))
        		response.sendRedirect("student/profile/add_academic_detail.jsp");
    		else if(session.getAttribute("user").equals("faculty"))
    			response.sendRedirect("faculty/profile/add_profile_detail.jsp");
        	return;
        }
        session.setAttribute("message", "Upload has been done successfully!");
        if(session.getAttribute("user").equals("student")) {
	        try {
				if(LoginService.setBasicAcademic(session.getAttribute("username").toString(), "10", 
						request.getParameter("school10"), request.getParameter("board10"), 
						Float.valueOf(request.getParameter("marks10")), Integer.valueOf(request.getParameter("year10")))
						&& LoginService.setBasicAcademic(session.getAttribute("username").toString(), "12", 
				        		request.getParameter("school12"), request.getParameter("board12"), 
				        		Float.valueOf(request.getParameter("marks12")), Integer.valueOf(request.getParameter("year12")))) {
					if(!request.getParameter("schoolpoly").isEmpty()) {
						LoginService.setBasicAcademic(session.getAttribute("username").toString(), "poly", 
				        		request.getParameter("schoolpoly"), request.getParameter("boardpoly"), 
				        		Float.valueOf(request.getParameter("markspoly")), Integer.valueOf(request.getParameter("yearpoly")));
					
					}
					response.sendRedirect("student/profile/profile.jsp");
				}else {
					System.out.println("basic academic insert mai dikkt hai");
					response.sendRedirect("student/profile/add_academic_detail.jsp");
				}
			} catch (Exception e) {
				e.printStackTrace();
				response.sendRedirect("student/profile/add_academic_detail.jsp");
			}
        } else {
        	if(LoginService.setFacultyInfo(session.getAttribute("username").toString(), request.getParameter("firstname"),
        			request.getParameter("lastname"), request.getParameter("dob"), request.getParameter("gender"),
        			request.getParameter("designation"), request.getParameter("department"), request.getParameter("qualification"),
        			request.getParameter("specification")))
        		response.sendRedirect("faculty/profile/profile.jsp");
        	else
        		response.sendRedirect("faculty/profile/add_profile_detail.jsp");
        }
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
