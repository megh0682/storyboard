package com.home.storyboard;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.json.simple.JSONObject;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class StoryServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
			
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String username = request.getParameter("name");
		String password = request.getParameter("password");
		String storytitle= request.getParameter("storyTitle");
		String storybegin=request.getParameter("storyBegin");
		String storymiddle=request.getParameter("storyMiddle");
		String storyend=request.getParameter("storyEnd");
		
		DAOdb db = null;
	        try {
	              db = new DAOdb();
	            } catch (Exception e) {
	 
	            e.printStackTrace();
	        }
	    
	     JSONObject json = new JSONObject();    
	        
		if(username!=null && password!=null){
		   LoginBean bean = new LoginBean(username, password);
			  //create Json Object
			
		  
           if (LoginValidator.validate(bean)) {
			User user = db.Authenticate(username, password);
			    if (user == null) {
	                String error = db.getLastError();
	                json.put("error", error);
	            }else {
	                request.getSession().setAttribute("user", user);
	               	Profile userprof = db.getProfilebyUsername(username);
	               	if(userprof!=null){
	               	      System.out.println(userprof.getFirstname() + " "+ userprof.getLastname());
	               	      request.getSession().setAttribute("profile", userprof);
	                      List <Story> stories = db.getStoriesbyUsername(username);
	                      System.out.println(stories.get(0) );
	               	      if(!stories.isEmpty()){
	               	    	  request.getSession().setAttribute("stories", stories);
	               	    	  Gson gson = new Gson();
	               	    	  String jsonStories = gson.toJson(stories);
	               	          System.out.println("jsonStories = " + jsonStories);
	               	    	  
	               	      }else{
	               	    	request.getSession().setAttribute("stories", stories);
	               	      }
	                      String url ="main.jsp";
	                      json.put("redirect",url);
	               	}else{
	               	     String error = db.getLastError();
		                 json.put("error", error);
	               	}
	                
	            }
		   }else{
			    System.out.println("Username or password cannot be validated.");
	            String error = "Invalid user name or password";
	            json.put("error",error);
		   }
		}
			
      // finally output the json string       
		    out.write(json.toString());	
		
				
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
	//}else{
	//	response.getWriter().write("Database not initialized");
	//}

}
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");   
        if (action == null) action = "main";
        switch (action) {
            case "main": action = main(request); break;
            case "createStory": action = createStory(request); break;
            case "login": action = login(request); break;
            case "logout": action = logout(request); break;
            case "register": action = register(request); break;
            case "profile": action = profile(request); break;
            default: action = "main";
        }
        request.getRequestDispatcher(action + ".jsp").forward(request,response);
    }
	
	 private String main(HttpServletRequest request) {
					 
		 return "main";
	 }
		
     private String createStory(HttpServletRequest request) {
    	 String storycomp = "storycomp";
    	 if (request.getMethod().equals("GET"))  {
    		 storycomp = "storycomp";
    	 }
		 return storycomp;
	 }	
	 
     private String login(HttpServletRequest request) {
    	 String login = "login";
    	 if (request.getMethod().equals("GET"))  {
    		 login ="login";
    	 }
    		 return login;
     }
	
     private String logout(HttpServletRequest request) {
    	 request.getSession().invalidate();
         return login(request);
     }
     
     private String register(HttpServletRequest request) {
		 
		 return "register";
	 }
     
     private String profile(HttpServletRequest request) {
		 
		 return "profile";
	 }
	
}
