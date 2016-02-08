package com.home.storyboard;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/plain");
		String username = request.getParameter("name");
		String storytitle= request.getParameter("storyTitle");
		String storybegin=request.getParameter("storyBegin");
		String storymiddle=request.getParameter("storyMiddle");
		String storyend=request.getParameter("storyEnd");
		
		if(username!=null){
			response.getWriter().write(username);
		}
		if(storytitle!=null){
			response.getWriter().write(storytitle);
		}
		if(storybegin!=null){
			response.getWriter().write(storybegin);
		}
		if(storymiddle!=null){
			response.getWriter().write(storymiddle);
		}
		if(storyend!=null){
			response.getWriter().write(storyend);
		}
	}

}
