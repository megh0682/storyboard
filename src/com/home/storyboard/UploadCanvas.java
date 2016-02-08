package com.home.storyboard;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

public class UploadCanvas extends HttpServlet {
    
	private static final long serialVersionUID = 205242440643911308L;
    private static final String UPLOAD_DIR = "images";
    
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        
    	 InputStream in = null;
         FileOutputStream fos = null;
         String imgName = "canvas"
                 + String.valueOf(System.currentTimeMillis()) + ".png";
         String applicationPath = "C:\\Users\\megha iyer\\git\\storyboard\\WebContent";
         String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR + File.separator + imgName;
         try {
             String image_contents = request.getParameter("contents");
             System.out.println(image_contents);
             image_contents = image_contents.substring("data:image/png;base64,"
                     .length());
             byte[] decodedBytes = DatatypeConverter.parseBase64Binary(image_contents);
             BufferedImage bfi = ImageIO.read(new ByteArrayInputStream(decodedBytes));    
             File outputfile = new File(uploadFilePath);
             ImageIO.write(bfi , "png", outputfile);
             bfi.flush();
                      
         } catch (Exception e) {
             e.printStackTrace();
         } finally {
             if (in != null) {
                 in.close();
             }
             if (fos != null) {
                 fos.close();
             }
         }

    }
}
