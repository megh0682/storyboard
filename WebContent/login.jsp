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
<title>Login Page</title>
</head>
<body>
<div class = "page-header">
<h1 align ="center"><b> Sign in to your Storyboard </b> </h1>
</div>
<div class="container">
<div class="row">
 <div style="width: 200px; margin: 100px auto 0 auto;"> 
     <form id="myname" role="form">
	 <div class="col-md-16" class = "form-group">
            <label for ="myname"></label><input type="text" placeholder="Username" required class="form-control"/><br>
            <label for ="mypasscode"></label><input type="password" placeholder="Password" required class="form-control"/><br>
            <input type="button"  id="savename" value="Login"/><span><input type="button" value="Cancel"/></span><p id="namesuccess"></p>
            <input type="button" id="gotoregister" value="New User Register here"/>
     </div>  
	 </form> 
</div>
     
 </div>
</div>  
</body>
</html>