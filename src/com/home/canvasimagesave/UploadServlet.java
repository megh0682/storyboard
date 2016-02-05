package com.home.canvasimagesave;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
 
@MultipartConfig(fileSizeThreshold=1024*1024*10,    // 10 MB 
                 maxFileSize=1024*1024*50,          // 50 MB
                 maxRequestSize=1024*1024*100)      // 100 MB
public class UploadServlet extends HttpServlet {
  
    private static final long serialVersionUID = 205242440643911308L;
     
    /**
     * Directory where uploaded files will be saved, its relative to
     * the web application directory.
     */
    private static final String UPLOAD_DIR = "images";
    
    FileOutputStream out = null;
    java.io.InputStream filecontent = null;
   
      
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        // gets absolute path of the web application
        //String applicationPath = request.getServletContext().getRealPath("");
    	String applicationPath = "C:\\Users\\megha iyer\\workspace\\CanvasImageSave\\WebContent";
        // constructs path of the directory to save uploaded file
        final PrintWriter writer = response.getWriter();
	    final Part filePart = request.getPart("file");
	    final String fileName = getFileName(filePart);
        String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR+File.separator+fileName;
        System.out.println("uploadfilepath:" + uploadFilePath); 
          
        // creates the save directory if it does not exists
        
        try{
        out = new FileOutputStream(new File(uploadFilePath));
        filecontent = filePart.getInputStream();

        int read = 0;
        final byte[] bytes = new byte[1024];

        while ((read = filecontent.read(bytes)) != -1) {
            out.write(bytes, 0, read);
        }
        writer.println("<p style = 'background-color:grey;'>" + "File with name " + "<strong style = 'background-color:green;' >" + fileName + "</strong>" + " uploaded successfully" + "</p>");
        writer.println("<html>" + "<head>" +"<script type= 'text/javascript'> alert('File ' + "+ fileName + "</strong>" + " uploaded successfully)" + "</script></head></html>");
        }catch(FileNotFoundException fne){
        	writer.println("<br/> ERROR: " + fne.getMessage());
        }finally{
          if(out!=null){
        	  out.close();
          }
          if(filecontent!=null){
        	  filecontent.close();
          }
          if(writer!=null){
        	  writer.close();
          }
        }
        
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
}