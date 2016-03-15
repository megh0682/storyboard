<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<title>Draw on Canvas</title>
</head>
<body onload="newCanvas();">
<header>
  <img src="http://www.w3schools.com/tags/smiley.gif" alt="mypic" />
  <h1>My Story Board</h1>
</header>
	<label for="canvas"><h3>
			<i>Describe your story on a Canvas:</i>
		</h3></label>
	<br>
	<canvas id="canvas" width="1100" height="400"
		style="border: 2px solid grey; cursor: crosshair; background-color: #fff;"></canvas>
	<br>
	<br>
	StoryID:${story.id}
	<input type="hidden" id ="hiddenstoryid" value= "${story.id}" />
	<input type="button" id="btndownload" value="Save and Submit" />
	<span class="saveCanvasbutton"> <a id="lnkcanvassave" href="#"
		onclick="saveCanvas();"><input type="button" value="Download" /></a></span><span><input type="button" id="btnclearcanvas" value="Clear Canvas" onclick="newCanvas();"/></span><br>
	<a href="StoryServlet?action=main">Homepage</a> |
    <a href="StoryServlet?action=logout">Logout</a>	
	<footer>Read, Write and Share</footer>
	
</body>
</html>
