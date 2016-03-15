<%@page contentType="text/html" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css">
<link rel="stylesheet" href="cis.css" type="text/css">
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<script type="text/javascript"
	src="http://code.jquery.com/jquery-1.10.2.js"></script>
<script type="text/javascript"
	src="http://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
<script type="text/javascript" src="story.js"></script>
<script type="text/javascript" src="cis.js"></script>
<title>${story.title}</title>
</head>
<body>
<header>
  <img src="http://www.w3schools.com/tags/smiley.gif" alt="mypic" />
  <h1>My Story Board</h1>
</header>
	<div class="container">
		<div class="row">
				<c:choose>
					<c:when test="${(user ne null) && (story ne null)}">
				     <b><h3><center>${story.title}</center></h3></b>
						<p>${story.firstPart}</p>
						<p>${story.middlePart}</p>
						<p>${story.lastPart}</p>
					<!-- 	<c:if test="${story.storypic ne null}">
                           <p>${story.storypic}</p> 
                        </c:if>
					 -->	
					</c:when>
					<c:otherwise>
						<p>
							<a href="StoryServlet?action=login">Log in to Storyboard</a> | <a
								href="StoryServlet?action=register">I'm sold. Sign me up!</a>
						</p>
					</c:otherwise>
				</c:choose>
	  <a href="StoryServlet?action=main">Homepage</a> |
      <a href="StoryServlet?action=logout">Logout</a>
				
	</div>
	</div>

				
				<footer>Read, Write and Share</footer>
</body>
</html>