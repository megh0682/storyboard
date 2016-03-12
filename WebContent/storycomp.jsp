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
<script type="text/javascript" src="story.js"></script>
<script type="text/javascript" src="cis.js"></script>
<title>Insert title here</title>
</head>
<body>
<div class="container">
     <a href="draw.html" id ="lnkcanvas"><input type="button" id="buttonCanvas" value ="Draw on Canvas"/></a>
  <!--     <form id="myname" role="form">
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
	
 -->	
   
    <form id="formTitle" role="form">
    <!--   <div class="col-md-3" class = "form-group" id="storytitle"> -->
            <label for = "storytitle"><h3><i>Story Title:</i></h3></label><textarea id="txtareatitle" style="border:2px solid grey" rows="1" cols="100" required class="form-control"></textarea><p id="titlesuccess"></p>
            <input type="button" value="Save"/><span></span><span><input type="button" value="Cancel"/></span>
      <!-- </div> -->
    </form>
       
    <form id="formbgn" role="form">
	  <!--  	 <div class="col-md-3" class = "form-group" id="storybgn" > -->
           
            <label for ="storybgn"><h3><i>Story Beginning:</i></h3></label><textarea style="border:2px solid grey" rows="10" cols="100" wrap="hard" required class="form-control"></textarea><p id="bgnsuccess"></p>
            <input type="button" value="Save"/><span></span><span><input type="button" value="Cancel"/></span>
        <!--     </div> -->
     </form>
        
	 <form id="formmiddle" role="form">	
      <!--    <div class="col-md-3" class = "form-group" id="storymiddle" >-->
            <label for ="storymiddle"><h3><i>Story Middle:</i></h3></label><textarea style="border:2px solid grey" rows="10" cols="100" wrap="hard" required class="form-control"></textarea><p id="middlesuccess"></p>
            <input type="button" value="Save"/><span></span><span><input type="button" value="Cancel"/></span>
       <!--  </div>-->
     </form>
		 
     <form id="formend" role="form">
     <!--   <div class="col-md-3" class = "form-group" id="storyend" >-->
            <label for ="storyend"><h3><i>Story End:</i></h3></label><textarea style="border:2px solid grey" rows="10" cols="100" wrap="hard" required class="form-control"></textarea><p id="endsuccess"></p>
            <input type="button" value="Save"/><span></span><span><input type="button" value="Cancel"/></span>
      <!--   </div>-->
     </form>
     
     <form id="formsubmitstory" role="form">
     <!--   <div class="col-md-3" class = "form-group" id="submitstory" >-->
            <label for ="storyend"><h3><i>Once submitted, you cannot edit your story!</i></h3></label><br>
            <input type="button" value="Submit"/><span></span>
     <!--    </div>-->
     </form>
    
</div>
</div>  
</body>
</html>