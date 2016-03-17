package com.home.storyboard;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONObject;

import com.google.gson.Gson;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
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
        case "storypic": jsontostring = upload(request);break;
        
        case "login": jsontostring = login(request);break;
        
        case "createStory": jsontostring = createStory(request);break; 
        
        case "passtitle": jsontostring = passtitle(request);break;
        
        case "passbegin":jsontostring = passbegin(request);break;
        
        case "passmiddle":jsontostring = passmiddle(request);break;
        
        case "passend":jsontostring = passend(request);break;
        
        case "upload": jsontostring = upload(request);break;
        
        default       : jsontostring = homepage(request);
    	
    }
    out.write(jsontostring);
}//doPost method ends here.
	
//upload method
	
private String upload(HttpServletRequest request){
	Story S=null;
	String error;
	String image_contents = request.getParameter("contents");
	Integer storyid = Integer.parseInt(request.getParameter("storyid"));
	System.out.println(image_contents);
	System.out.println(storyid);
	DAOdb db = null;
	    try {
	          db = new DAOdb();
	        }catch (Exception e) {         
	         e.printStackTrace();
	        }	
		
//if((image_contents!=null) && (storyid!=null)){
System.out.println(image_contents + storyid);
image_contents = image_contents.substring("data:image/png;base64,".length());
byte[] decodedBytes = DatatypeConverter.parseBase64Binary(image_contents);
InputStream is = new ByteArrayInputStream(decodedBytes);
try{
   db.updateStoryPic(storyid, "png", is);
   String url = "story.jsp";
   json.put("storyjsp", url);
   S=db.getStorybyStoryId(storyid);
   request.getSession().setAttribute("story", S);
//}
}catch(SQLException e){
	error= e.getMessage().toString();
	json.put("error",error);
}
return (json.toString());
}
	
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
	               	      System.out.println(userprof.getFirstname() + " "+ userprof.getLastname() + "profile[ic:" +userprof.getProfpic() + ""+userprof.getEmail());
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
			String url = "draw.jsp";
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

@SuppressWarnings("unchecked")
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

private String homepage(HttpServletRequest request){
	json.put("storycomppage", "storycomp.jsp");
	return json.toString();
}

/*******************************************************************************************************************************************************/


protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");   
        if (action == null) action = "main";
        switch (action) {
            case "main": request.getRequestDispatcher(action + ".jsp").forward(request,response); break;
            case "createStory": request.getRequestDispatcher(action + ".jsp").forward(request,response);break;
            case "canvas":getcanvas(request,response);break;
            case "storypdf" : storypdf(request,response);break;
            case "login": request.getRequestDispatcher(action + ".jsp").forward(request,response);break;
            case "logout": request.getRequestDispatcher(action + ".jsp").forward(request,response);break;
            case "register": request.getRequestDispatcher(action + ".jsp").forward(request,response);break;
            case "profile": request.getRequestDispatcher(action + ".jsp").forward(request,response);break;
            case "storyid": action = getStoryDetails(request);request.getRequestDispatcher(action + ".jsp").forward(request,response);break;
            default: action = "main";request.getRequestDispatcher(action + ".jsp").forward(request,response);
        }
        
    }
	
	private String logout(HttpServletRequest request) {
    	 request.getSession().invalidate();
         return "login";
     }
	
	private String getStoryDetails(HttpServletRequest request){
		Integer storyid = Integer.parseInt(request.getParameter("id"));
		Story S = null;
		
		DAOdb db = null;
	    try {
	          db = new DAOdb();
	        } catch (Exception e) {
	         
	         e.printStackTrace();
	    }	
	    
	   S = db.getStorybyStoryId(storyid);
	   request.getSession().setAttribute("story", S);
	   return "story";
	}
	
	private void getcanvas(HttpServletRequest request, HttpServletResponse response) {
	    	DAOdb db = null;
	    	Story S = null;
		    String storyid  = request.getParameter("for");
	        Integer sid = 0;
	        try
	        {
	            if(storyid != null)
	              sid = Integer.parseInt(storyid);
	        }
	        catch (NumberFormatException e)
	        {
	        	sid = 0;
	        }
	        
	        
		    try {
		          db = new DAOdb();
		        } catch (Exception e) {
		         
		         e.printStackTrace();
		    }	
		    
		    S = db.getStorybyStoryId(sid);
		    byte[] storypic = S.getStorypic();
	        if (storypic == null) {
	            response.setStatus(404);
	            return;
	        }
	        try {
	        	Base64 base64 = new Base64();
	            ByteArrayInputStream bis = new ByteArrayInputStream(storypic);
	            BufferedImage image = ImageIO.read(bis);
	            bis.close();
	            ImageIO.write(image, "png", new File("C:\\Users\\megha iyer\\git\\storyboard\\WebContent\\images\\canvas.png"));
	        	response.setContentType("image/png");
	            response.getWriter().write("C:\\Users\\megha iyer\\git\\storyboard\\WebContent\\images\\canvas.png");
	        } catch (IOException ioe) {
	            request.setAttribute("flash", ioe.getMessage());
	        }
	    }
	
	
	private void storypdf(HttpServletRequest request, HttpServletResponse response) {
    	DAOdb db = null;
    	Story S = null;
	    String storyid  = request.getParameter("for");
        Integer sid = 0;
        try
        {
            if(storyid != null)
              sid = Integer.parseInt(storyid);
        }
        catch (NumberFormatException e)
        {
        	sid = 0;
        }
        
        
	    try {
	          db = new DAOdb();
	        } catch (Exception e) {
	         
	         e.printStackTrace();
	    }	
	    
	    S = db.getStorybyStoryId(sid);
	    StoryPdf pdf =new StoryPdf(S.getTitle(),S.getFirstPart(),S.getMiddlePart(),S.getLastPart());
	    pdf.createPdf("mypdf", "C:\\Users\\megha iyer\\git\\storyboard\\WebContent\\images\\defaultstorypic.png");
	    
	    try {
	    	// reads input file from an absolute path
	        String filePath = "C:\\Users\\megha iyer\\git\\storyboard\\WebContent\\images\\temp.pdf";
	        File downloadFile = new File(filePath);
	        FileInputStream inStream = new FileInputStream(downloadFile);
	        
	        // obtains ServletContext
	        ServletContext context = getServletContext();
	         
	        // gets MIME type of the file
	        String mimeType = context.getMimeType(filePath);
	        if (mimeType == null) {        
	            // set to binary type if MIME mapping not found
	            mimeType = "application/octet-stream";
	        }
	        System.out.println("MIME type: " + mimeType);
	         
	        // modifies response
	        response.setContentType(mimeType);
	        response.setContentLength((int) downloadFile.length());
	         
	        // forces download
	        String headerKey = "Content-Disposition";
	        String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
	        response.setHeader(headerKey, headerValue);
	        
	        // obtains response's output stream
	        OutputStream outStream = response.getOutputStream();
	         
	        byte[] buffer = new byte[4096];
	        int bytesRead = -1;
	         
	        while ((bytesRead = inStream.read(buffer)) != -1) {
	            outStream.write(buffer, 0, bytesRead);
	        }
	         
	        inStream.close();
	        outStream.close();     
	        
        } catch (IOException ioe) {
            request.setAttribute("flash", ioe.getMessage());
        }
    }
     
  
}
