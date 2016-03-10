<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
<link rel="stylesheet" href = "http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css">
<link rel="stylesheet" href="cis.css"  type="text/css">  
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<script type="text/javascript" src = "http://code.jquery.com/jquery-1.10.2.js"></script>
<script type="text/javascript" src = "http://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
<script type="text/javascript" src="cis.js"></script>
<script type="text/javascript" src="story.js"></script>
<title>Insert title here</title>
</head>
<body onload="newCanvas();">
<div class="container">

     <label for="canvas">Describe your story on a Canvas</label><br>
     <canvas id="canvas" width="800" height="400" style="border:2px solid grey; cursor:crosshair ; background-color:#fff;"></canvas><br><br>
     <input type="button" id="btndownload" value="Save"/>
     <span class="saveCanvasbutton">
     <a  id="lnkcanvassave" href="#"  onclick= "saveCanvas();"><input type="button" value="Download"/></a><br><br></span>
     
          
     <form id="myname" role="form">
	 <div class="col-md-4" class = "form-group">
            <label for ="myname">Enter your name:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label><input type="text" required class="form-control"/>
            <input type="button"  id="savename"value="Save"/><span><input type="button" value="Cancel"/></span><p id="namesuccess"></p>
     </div>  
	 </form>
	 
	 
	 <form name="myform" id="myform" method="POST" action="uploadimage" enctype="multipart/form-data" role="form">
		<div class="col-md-4" class = "form-group">
		<label for="fileinput">Upload your photo: </label>
		<input type="file" name="file" id="file" /> 
		<input type="submit" value="Upload" />
		</div>
	</form>
   
    <form id="formTitle" role="form">
     <div class="col-md-4" class = "form-group" id="storytitle">
            <p>What is the title of your story?</p>
            <label for = "storytitle"></label><textarea id="txtareatitle" rows="1" cols="100" required class="form-control"></textarea><p id="titlesuccess"></p>
            <input type="button" value="Save"/><span></span><span><input type="button" value="Cancel"/></span>
     </div>
    </form>
       
    <form id="formbgn" role="form">
		 <div class="col-md-4" class = "form-group" id="storybgn" >
            <p>Begin your story: </p>
            <label for ="storybgn"></label><textarea rows="10" cols="100" wrap="hard" required class="form-control"></textarea><p id="bgnsuccess"></p>
            <input type="button" value="Save"/><span></span><span><input type="button" value="Cancel"/></span>
         </div>
     </form>
        
	 <form id="formmiddle" role="form">	
          <div class="col-md-4" class = "form-group" id="storymiddle" >
            <p>Continue your story: </p>
            <label for ="storymiddle"></label><textarea rows="10" cols="100" wrap="hard" required class="form-control"></textarea><p id="middlesuccess"></p>
            <input type="button" value="Save"/><span></span><span><input type="button" value="Cancel"/></span>
         </div>
     </form>
		 
     <form id="formend" role="form">
        <div class="col-md-4" class = "form-group" id="storyend" >
           <p>Put an end to your story: </p>
            <label for ="storyend"></label><textarea rows="10" cols="100" wrap="hard" required class="form-control"></textarea><p id="endsuccess"></p>
            <input type="button" value="Save"/><span></span><span><input type="button" value="Cancel"/></span>
         </div>
     </form>
     
     
 </div>
</div>  
</body>
</html>