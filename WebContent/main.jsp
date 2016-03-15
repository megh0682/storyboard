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
<title>Hello ${user.username}</title>
</head>
<body>
<header>
  <img src="http://www.w3schools.com/tags/smiley.gif" alt="mypic" />
  <h1>My Story Board</h1>
</header>
<div class="container">
<div class="row">

<div class="col-md-4" class = "form-group">
<h1>Hello ${user.username} with  ${user.id} ! </h1>
<c:choose>
<c:when test="${user ne null}">
<label for="fn"> First-Name: </label><input type="text" disabled class="form-control" value = ${profile.firstname}>
<label for="ln"> Last-Name: </label><input type="text" disabled class="form-control" value = ${profile.lastname}>
<label for="email"> Email: </label><input type="email" disabled class="form-control"value = ${profile.email}>
<label for="profilepic"> Profile Picture: </label><img src="http://www.w3schools.com/tags/smiley.gif" alt="Smiley face" width="42" height="42"><button id="editprofile">Change Your Picture</button>
</div>
 <form name="myform" id="myform" method="POST" action="uploadimage" enctype="multipart/form-data" role="form" hidden="true">
	<div class="col-md-4" class = "form-group">
		<label for="fileinput">Upload your photo: </label>
		<input type="file" name="file" id="file" /> 
		<input type="submit" value="Upload" />
	</div>
</form>

<div class="col-md-4">
<h1>My Stories</h1>
<ol>
<c:forEach var="story" items="${stories}">
  <li><a href="StoryServlet?id=${story.id}&action=storyid">${story.title}</a></li>    
</c:forEach>
</ol>
</div>    
         
<div class="col-md-4">
<a href="StoryServlet?action=createStory"><h1>Create a new Story!</h1></a>
</div> 
</div>
</div> 
<a href="StoryServlet?action=logout">Log Out</a>
</p>
 </c:when>
<c:otherwise>
 <p><a href="StoryServlet?action=login">Log in to Storyboard</a> |
 <a href="StoryServlet?action=register">I'm sold. Sign me up!</a>
 </p>
</c:otherwise>
</c:choose>

<footer>
 love reading stories & love writing stories & love sharing stories
</footer>
</body>
</html>