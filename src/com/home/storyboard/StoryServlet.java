package com.home.storyboard;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Base64.Decoder;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.codec.binary.StringUtils;
import org.json.simple.JSONObject;

import com.google.gson.Gson;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.xml.bind.DatatypeConverter;

@MultipartConfig 
public class StoryServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final int IMG_WIDTH = 80;
	private static final int IMG_HEIGHT = 65;
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
        
        case "editprofile":jsontostring = profileEdit(request);break;
        
        case "editprofilepic":jsontostring = editprofilepic(request,response);break;
        
        case "register": jsontostring = register(request);break;
        
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
//System.out.println(image_contents);
//byte[] bImg64 = image_contents.getBytes();
byte[] asBytes = java.util.Base64.getDecoder().decode(image_contents);
System.out.println(new String(asBytes));
//byte[] decodedBytes = Base64.decodeBase64(bImg64);
//InputStream is = new ByteArrayInputStream(asBytes);
try{
   db.updateStoryPic(storyid, "png", asBytes);
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
	               	   if(db.storyExists(username)){
	                      List <Story> stories = db.getStoriesbyUsername(username);
	                      System.out.println(stories.get(0) );
	               	      request.getSession().setAttribute("stories", stories);
	               	      Gson gson = new Gson();
	               	      String jsonStories = gson.toJson(stories);
	               	      System.out.println("jsonStories = " + jsonStories);
	               	      }else{
	               	    	 json.put("storycount", "none");
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
			List <Story> stories = db.getStoriesbyUsername(user.getUsername());
            request.getSession().setAttribute("stories", stories);
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

private String profileEdit(HttpServletRequest request){
	DAOdb db = null;
	Integer profileid = 0;
	String profid = request.getParameter("profileid");	
	String firstname = request.getParameter("fn");
	String lastname = request.getParameter("ln");
	String email = request.getParameter("email");
    try {
          db = new DAOdb();
        } catch (Exception e) {
        e.printStackTrace();
        }
    try{
        if(profid != null)
          profileid = Integer.parseInt(profid);
       }catch (NumberFormatException e){
    	profileid = 0;
       }
    if((firstname!=null) || (!firstname.isEmpty())){
		db.updateProfilefn(firstname, profileid);
	}
	
	if((lastname!=null) || (!lastname.isEmpty())){
		db.updateProfileln(lastname, profileid);
	}
	
	if((email!=null) || (!email.isEmpty())){
		db.updateProfileemail(email, profileid);
	}
	
	json.put("profileurl", "profile.jsp");
    return json.toString();
    
}

/***
 *  Edit profile picture method
 * @throws IOException 
 * @throws ServletException */
private String editprofilepic(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
	DAOdb db = null;
	Integer profileid = 0;
	String error = null;
	String flash=null;
	String pid = request.getParameter("profilepicid");
	Part filepart = null;
	try {
          db = new DAOdb();
        } catch (Exception e) {
        e.printStackTrace();
        }
    try{
        if(pid != null)
          profileid = Integer.parseInt(pid);
       }catch (NumberFormatException e){
    	profileid = 0;
       }
    try {
		  filepart = request.getPart("file");
		  String filename = getFileName(filepart);
	      InputStream filecontent = filepart.getInputStream();
	      if(filecontent.available()!= -1 && filepart.getSize()!=0L){
	    	byte[] bytes = IOUtils.toByteArray(filecontent);
	    	db.updateProfilepic(bytes, profileid);
	    	json.put("profilepicurl","profile.jsp");
	      }else{
	    	json.put("error","file is empty.Please select your file");
	      }
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			error = e.getMessage().toString();
			json.put("error",error);
		}catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			error = e.getMessage().toString();
			json.put("error",error);
		}
	
	return json.toString();
}

/**
 * Utility method to get file name from HTTP header content-disposition
 */
private String getFileName(Part part) {
    String contentDisp = part.getHeader("content-disposition");
    System.out.println("content-disposition header= "+contentDisp);
    String[] tokens = contentDisp.split(";");
    for (String token : tokens) {
        if (token.trim().startsWith("filename")) {
            return token.substring(token.indexOf("=") + 2, token.length()-1);
        }
    }
    return "";
}

/**
 * register a new user method
 **/

private String register(HttpServletRequest request){
	DAOdb db = null;
	String error = null;
	String fn = request.getParameter("firstname");
	String ln = request.getParameter("lastname");
	String un = request.getParameter("username");
	String pwd = request.getParameter("password");
	String email = request.getParameter("email");
	
	try {
	     db = new DAOdb();
		    }catch (Exception e) {
		 e.printStackTrace();
		    }	
	
	if((fn!=null) && (ln!=null) && (un!=null) && (pwd!=null) && (email!=null)){
		
	//check for user name uniqueness	
	if(db.isUsernameAvailable(un)){
	
	//add User
    User U = new User(un,pwd);
	db.addUser(U);
	 
	 //add Profile
	Profile P = new Profile(fn,ln,email,U.getId());
	db.addProfile(P);
	
	db.updateUserprofileid(U.getUsername(),P.getId());
	
   if (U!= null) {
        request.getSession().setAttribute("user",U);
       	if(P!=null){
       	   request.getSession().setAttribute("profile", P);
       	   if(db.storyExists(U.getUsername())){
              List <Story> stories = db.getStoriesbyUsername(U.getUsername());
              request.getSession().setAttribute("stories", stories);
       	      }else{
       	    	 json.put("storycount", "No Stories");
       	      }
       	      
             
    }
       	json.put("redirect","main.jsp");
   	}
	
	}else{
		json.put("error", "Username already exists.Select another username");
	}
	
	}else{
		json.put("error", "One of fields on registration form is empty");
	}
	
	return json.toString();
}

/*******************************************************************************************************************************************************/
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
		String action = request.getParameter("action");   
        if (action == null) action = "main";
        switch (action) {
            case "main": gethomepage(request,response);break;
            case "createStory": getcreateStory(request,response);break;
            case "canvas":getcanvas(request,response);break;
            case "getStories":getStories(request,response);break;
            case "image": getprofileimage(request,response);break;
            case "storypdf" : storypdf(request,response);break;
            case "login":  getlogin(request,response);break;
            case "logout": logout(request,response);break;
            case "register":getregister(request,response); break; 
            case "profile": request.getRequestDispatcher("profile" + ".jsp").forward(request,response);break;
            case "storyid": getStoryDetails(request,response);break;
            default: action = "main";request.getRequestDispatcher("main" + ".jsp").forward(request,response);
        }
        
        
        
    }
	
private void gethomepage (HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
	response.setContentType("application/json");
    response.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();
	json.put("redirect", "main.jsp");
	out.write(json.toString());
}

private void getregister (HttpServletRequest request,HttpServletResponse response) throws IOException{
	 response.setContentType("application/json");
	 response.setCharacterEncoding("utf-8");
	 PrintWriter out = response.getWriter();
	 json.put("redirect", "register.jsp");
	 out.write(json.toString());
}

private void getlogin (HttpServletRequest request,HttpServletResponse response) throws IOException{
	response.setContentType("application/json");
	response.setCharacterEncoding("utf-8");
	PrintWriter out = response.getWriter();
	json.put("redirect", "login.jsp");
	out.write(json.toString());	
}

private void logout (HttpServletRequest request,HttpServletResponse response) throws IOException{
	response.setContentType("application/json");
	response.setCharacterEncoding("utf-8");
	PrintWriter out = response.getWriter();
	request.getSession().invalidate();
	json.put("redirect", "login.jsp");
	out.write(json.toString());	
}

private void getcreateStory (HttpServletRequest request,HttpServletResponse response) throws IOException{
	response.setContentType("application/json");
	response.setCharacterEncoding("utf-8");
	PrintWriter out = response.getWriter();
	json.put("redirect", "storycomp.jsp");
	out.write(json.toString());	
}


private void getStoryDetails(HttpServletRequest request,HttpServletResponse response) throws IOException{
	    response.setContentType("application/json");
	    response.setCharacterEncoding("utf-8");
	    PrintWriter out = response.getWriter();
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
	   json.put("redirect", "story.jsp");
	   out.write(json.toString());	
	}
	
private void getcanvas(HttpServletRequest request, HttpServletResponse response)throws IOException{
	        response.setContentType("application/json");
	        response.setCharacterEncoding("utf-8");
	        PrintWriter out = response.getWriter();
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
	            String image_string = "/WebContent/images/defaultstorypic.png";
	            json.put("redirect", image_string);
	            out.write(json.toString());
	        }else{        
	            StringBuilder sb = new StringBuilder();
	        	sb.append("data:image/png;base64,");
	        	sb.append(" ");
	        	sb.append(java.util.Base64.getEncoder().encodeToString(storypic));
	        	String newString = sb.toString();
	        	System.out.println("base64encoded:" +newString);	
	        	json.put("redirect", newString);
	        	out.write(json.toString());	
	        	//response.setContentType("image/png");
	        	//response.getWriter().println(newString);*/


	        	
	        	//byte[] imageBytes = java.util.Base64.getEncoder().encode(storypic);
	        	//String imageString = java.util.Base64.getEncoder().encodeToString(storypic);
	        	//byte[]decodedbytes = java.util.Base64.getDecoder().decode(imageString);
	        	// convert byte array back to BufferedImage
				//InputStream in = new ByteArrayInputStream(decodedbytes);
				//BufferedImage bImageFromConvert = ImageIO.read(in);
				//ImageIO.write(bImageFromConvert, "png", new File("C:\\Users\\megha iyer\\git\\storyboard\\WebContent\\images\\canvasforpdf.png"));
	        	//response.setContentType("image/png");
	        	//response.setContentLength(decodedbytes.length);
	        	//response.getOutputStream().write(decodedbytes);
	        	     	
	        	
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
	    
	    String filename = "" + S.getId()+S.getAuthorid()+"canvas"+".png";
  	    String oldfilepath = "C:\\Users\\megha iyer\\git\\storyboard\\WebContent\\images\\" + filename;
	    File oldfile = new File(oldfilepath);
		if(oldfile.exists() && oldfile.isFile()) {
			oldfile.delete();
		}
		
		String filepath = CreateCanvasImage(S.getId(),"png");
		
	    
	    StoryPdf pdf =new StoryPdf(S.getTitle(),S.getFirstPart(),S.getMiddlePart(),S.getLastPart());
	    pdf.createPdf("mypdf", filepath);
	    
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
	
	
  private String CreateCanvasImage(Integer storyid, String mimetype){
	    
		
		DAOdb db = null;
    	Story S = null;
    	String filepath = null;
    	
	           
	    try {
	          db = new DAOdb();
	        } catch (Exception e) {
	         
	         e.printStackTrace();
	    }	
	    
	    S = db.getStorybyStoryId(storyid);
	    byte[] storypic = S.getStorypic();
        if (storypic == null) {
            filepath = "C:\\Users\\megha iyer\\git\\storyboard\\WebContent\\images\\defaultstorypic.png";
        }else{
              try{
            	  String filename = "" + S.getId()+S.getAuthorid()+"canvas"+".png";
            	  filepath = "C:\\Users\\megha iyer\\git\\storyboard\\WebContent\\images\\" + filename;
        	      String imageString = java.util.Base64.getEncoder().encodeToString(storypic);
        	      byte[]decodedbytes = java.util.Base64.getDecoder().decode(imageString);
        	      // convert decoded byte array to BufferedImage
        	      InputStream in = new ByteArrayInputStream(decodedbytes);
        	      BufferedImage bImageFromConvert = ImageIO.read(in);
        	      ImageIO.write(bImageFromConvert, "png", new File(filepath));
        	     }catch(IOException e) {
        	      e.getMessage().toString();
                 }
        }
        
		return filepath;
	}
	
	
	/**
	 *get profile image method
	 */
	
private void getprofileimage(HttpServletRequest request,HttpServletResponse response) throws IOException{
	    response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
    	DAOdb db = null;
    	Profile P =null;
    	String profileid  = request.getParameter("for");
        Integer pid = 0;
      
        try
        {
          if(profileid != null)
            pid = Integer.parseInt(profileid);
        }
        catch (NumberFormatException e)
        {
        	pid = 0;
        }
        
        
	    try {
	          db = new DAOdb();
	        } catch (Exception e) {
	         
	         e.printStackTrace();
	    }	
	    
	    P = db.getProfilebyProfileId(pid);
	    byte[] profilepic = P.getProfpic();
        if (profilepic == null) {
            //response.setStatus(404);
        	String defaultfilepath = "C:\\Users\\megha iyer\\git\\storyboard\\WebContent\\images\\defaultprofilepic.png";
            json.put("filepath", defaultfilepath);
        }
        
        try{
        	
        	String filename = "" + P.getId()+P.getUserId()+"profile";
       	    String filepath = "C:\\Users\\megha iyer\\git\\storyboard\\WebContent\\images\\" + filename;
        	String imageString = java.util.Base64.getEncoder().encodeToString(profilepic);
        	byte[]decodedbytes = java.util.Base64.getDecoder().decode(imageString);
           	InputStream in = new ByteArrayInputStream(decodedbytes);
  	        BufferedImage bImageFromConvert = ImageIO.read(in);
  	        ImageIO.write(bImageFromConvert, "png", new File(filepath+".png"));
            BufferedImage oldbi =  ImageIO.read(new File(filepath+".png"));
    		int type = oldbi.getType();
  	        BufferedImage newbi = resizeImage(oldbi, type);
  		    ImageIO.write(newbi, "png", new File(filepath+".png")); 
  		    json.put("filepath", filepath);
  		    //byte[] imageInbytes = bufferedImagetobytearray(newbi);
  		  	//response.getOutputStream().write( imageInbytes );
  		      			
  	       }catch (IOException e) {
        	e.getMessage().toString();
        }
        
       out.write(json.toString());
    }
	
/**
 * Resize image function	
 */

private static BufferedImage resizeImage(BufferedImage originalImage, int type){
		BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
		g.dispose();
			
		return resizedImage;
}

@SuppressWarnings("unused")
private static byte[] bufferedImagetobytearray(BufferedImage bi){
	byte[] imageInByte = null;
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	try {
		ImageIO.write(bi, "png", baos );
		baos.flush();
		imageInByte = baos.toByteArray();
		baos.close();
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		
	}
	
	return imageInByte;
}

private void getStories(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
	
	String un = request.getParameter("user");
	DAOdb db = new DAOdb();
	 try {
         db = new DAOdb();
       } catch (Exception e) {
        
        e.printStackTrace();
       }	
	if(db.storyExists(un)){
        List <Story> stories = db.getStoriesbyUsername(un);
        System.out.println(stories.get(0) );
 	    request.getSession().setAttribute("stories", stories);
 	    Gson gson = new Gson();
 	    String jsonStories = gson.toJson(stories);
 	    System.out.println("jsonStories = " + jsonStories);
 	    request.setAttribute("userstories", stories);
 	 }
	//out.write(json.toString());
	
}
     
  
}
