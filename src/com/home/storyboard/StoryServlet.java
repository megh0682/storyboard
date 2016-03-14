package com.home.storyboard;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import org.json.simple.JSONObject;

import com.google.gson.Gson;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

public class StoryServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	JSONObject json = new JSONObject();  
	String jsontostring=null;		
	String action =null;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		action = request.getParameter("action");
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
	if (action == null) action = "login";    
    switch (action) {
        case "login": jsontostring = login(request);break;
        case "createStory": jsontostring = createStory(request);break;
        case "passtitle": jsontostring = passtitle(request);break;
        case "passbegin":jsontostring = passbegin(request);break;
        case "passmiddle":jsontostring = passmiddle(request);break;
        case "passend":jsontostring = passend(request);break;
        case "passcanvas": jsontostring = passcanvas(request);break;
        default: jsontostring = homepage(request);break;
    	
    }
    out.write(jsontostring);
}//doPost method ends here.
	
//login post method	
private String login(HttpServletRequest request){
		String username = request.getParameter("name");
		String password = request.getParameter("password");
		
		DAOdb db = null;
        try {
              db = new DAOdb();
            } catch (Exception e) {
 
            e.printStackTrace();
        }
					
	    if(username!=null && password!=null){
		   LoginBean bean = new LoginBean(username, password);
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
	    return (json.toString());	
}
//createStory post method	
private String createStory(HttpServletRequest request){
	    String storytitle= request.getParameter("storyTitle");
		String storybegin=request.getParameter("storyBegin");
		String storymiddle=request.getParameter("storyMiddle");
		String storyend=request.getParameter("storyEnd");
		String image_contents = request.getParameter("contents");
		DAOdb db = null;
        try {
              db = new DAOdb();
            } catch (Exception e) {
 
            e.printStackTrace();
        }
		User user = (User) request.getSession().getAttribute("user");
	if (user!=null)
	{
		    
		    Integer userid = user.getId();
		    System.out.println(userid);
			if(storytitle!=null && storybegin!=null && storymiddle!=null && storyend!=null ){
				Story S = new Story();
				S.setId(null);
				S.setTitle(storytitle);
				S.setFirstPart(storybegin);
				S.setMiddlePart(storymiddle);
				S.setLastPart(storyend);
				S.setStorypic(null);
				S.setAuthorid(userid);
				db.addStory(S);		
			  if(image_contents!=null){
				System.out.println(image_contents);
	            image_contents = image_contents.substring("data:image/png;base64,".length());
	            byte[] decodedBytes = DatatypeConverter.parseBase64Binary(image_contents);
	            InputStream is = new ByteArrayInputStream(decodedBytes);
			    try {
					db.updateStoryPic(S.getId(), "png", is);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					String error = e.getMessage().toString();
		            json.put("error",error);
				}
		      }
			String url = "story.jsp";
			request.getSession().setAttribute("story", S);
			json.put("redirect", url);
		   }
     }
	return json.toString();
}


private String passtitle(HttpServletRequest request){
	request.getSession().setAttribute("passtitle", request.getParameter("storyTitle"));
	json.put("success", "success");
	return json.toString();

}


private String passbegin(HttpServletRequest request){
	request.getSession().setAttribute("passbegin", request.getParameter("storyBegin"));
	json.put("success", "success");
	return json.toString();

}

private String passmiddle(HttpServletRequest request){
	request.getSession().setAttribute("passmiddle", request.getParameter("storyMiddle"));
	json.put("success", "success");
	return json.toString();

}

private String passend(HttpServletRequest request){
	request.getSession().setAttribute("passend", request.getParameter("storyEnd"));
	json.put("success", "success");
	return json.toString();

}

private String passcanvas(HttpServletRequest request){
	request.getSession().setAttribute("passcanvas", request.getParameter("contents"));
	json.put("imagesuccess", "success");
	return json.toString();

}

private String homepage(HttpServletRequest request){
	json.put("storycomppage", "storycomp.jsp");
	return json.toString();
}


protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");   
        if (action == null) action = "main";
        switch (action) {
            case "main": action =  "main";break;
            case "createStory": action = "storycomp";break;
            case "login": action = "login"; break;
            case "logout": action = logout(request); break;
            case "register": action = "register"; break;
            case "profile": action = "profile"; break;
            default: action = "main";
        }
        request.getRequestDispatcher(action + ".jsp").forward(request,response);
    }
	
	private String logout(HttpServletRequest request) {
    	 request.getSession().invalidate();
         return "login";
     }
     
  
}
