/**
 * Story content post forms using jquery ajax and validation of UI inputs.
 */
 $(document).ready(function(){
	alert("I am in");
	$("#myname input[value=Login]").click(function(){
	var name = $("#myname input[type=text]").val();
	var password = $("#myname input[type=password]").val();
	if(name=="")
	  alert("Please enter your name!");
	else if(password=="")
      alert("Please enter your password!");
	else
	  $.ajax({
		  type: "POST",
		  url: "StoryServlet",
		  data: {name:name, password:password},
		  dataType: "json",
		  success: function(json){
			alert("Success")  
			if (json.redirect) {
            // data.redirect contains the string URL to redirect to
            window.location.href = json.redirect;
            }else{
            // data.form contains the HTML for the replacement form
            alert(json.error);
            $("#myname input[type=text]").val("");	
            }
           
			/*$("#namesuccess").show("slow", function() {
				  $(this).css("color","green");
			      $(this).text("Username "+responsetext+" saved successfully.");
			    });
			$("#namesuccess").hide("slow", function() {
				  $(this).text("");
			    });
			$("#namesuccess").show(5,function() {
				  $(this).text("");
			    });
			    */
		   },
		   error: function(){
 			  console.log("fail") ; 
	       },
		 
		});
     });
	
	$("#myname input[value=Cancel]").click(function(){
		$("#myname input[type=text]").val("");	
	 });
 
	/**********************************************************************/
	
	$("#formTitle input[value=Save]").click(function(){
		var formTitle = $("#formTitle input[type=text]").val();
		if(formTitle=="")
		  alert("Please enter the title of your story!");
		else
		  $.ajax({
			  type: "POST",
			  url: "StoryServlet",
			  data: {storyTitle:formTitle},
			  success: function(responsetext){
				$("#titlesuccess").show("slow", function() {
					  $(this).css("color","green");
				      $(this).text("Story Title "+responsetext+" saved successfully.");
				    });
				$("#titlesuccess").hide("slow", function() {
					  $(this).text("");
				    });
				$("#titlesuccess").show(5,function() {
					  $(this).text("");
				    });
			   },
			   error: function(){
	 			  console.log("fail") ; 
		       },
			 
			});
	     });
		
		$("#formTitle input[value=Cancel]").click(function(){
			$("#formTitle textarea").val("");	
		 });
	
    /**********************************************************************/
		$("#formbgn input[value=Save]").click(function(){
			var formbgn = $("#formbgn input[type=text]").val();
			if(formbgn=="")
			  alert("Please enter the title of your story!");
			else
			  $.ajax({
				  type: "POST",
				  url: "StoryServlet",
				  data: {storyBegin:formbgn},
				  success: function(responsetext){
					$("#bgnsuccess").show("slow", function() {
						  $(this).css("color","green");
					      $(this).text("Entered text "+responsetext+" saved successfully.");
					    });
					$("#bgnsuccess").hide("slow", function() {
						  $(this).text("");
					    });
					$("#bgnsuccess").show(5,function() {
						  $(this).text("");
					    });
				   },
				   error: function(){
		 			  console.log("fail") ; 
			       },
				 
				});
		     });
			
			$("#formbgn input[value=Cancel]").click(function(){
				$("#formbgn textarea").val("");	
			 });	
		
   /**********************************************************************/	
	
			$("#formmiddle input[value=Save]").click(function(){
				var formmiddle = $("#formbgn input[type=text]").val();
				if(formmiddle=="")
				  alert("Story beginning cannot be blank.");
				else
				  $.ajax({
					  type: "POST",
					  url: "StoryServlet",
					  data: {storyMiddle:formmiddle},
					  success: function(responsetext){
						$("#middlesuccess").show("slow", function() {
							  $(this).css("color","green");
						      $(this).text("Entered text "+responsetext+" saved successfully.");
						    });
						$("#middlesuccess").hide("slow", function() {
							  $(this).text("");
						    });
						$("#middlesuccess").show(5,function() {
							  $(this).text("");
						    });
					   },
					   error: function(){
			 			  console.log("fail") ; 
				       },
					 
					});
			     });
				
				$("#formmiddle input[value=Cancel]").click(function(){
					$("#formmiddle textarea").val("");	
				 });	
			
	 /**********************************************************************/	
				$("#formend input[value=Save]").click(function(){
					var formend = $("#formbgn input[type=text]").val();
					if(formend=="")
					  alert("Story beginning cannot be blank.");
					else
					  $.ajax({
						  type: "POST",
						  url: "StoryServlet",
						  data: {storyEnd:formend},
						  success: function(responsetext){
							$("#endsuccess").show("slow", function() {
								  $(this).css("color","green");
							      $(this).text("Entered text "+responsetext+" saved successfully.");
							    });
							$("#endsuccess").hide("slow", function() {
								  $(this).text("");
							    });
							$("#endsuccess").show(5,function() {
								  $(this).text("");
							    });
						   },
						   error: function(){
				 			  console.log("fail") ; 
					       },
						 
						});
				     });
					
					$("#formend input[value=Cancel]").click(function(){
						$("#formend textarea").val("");	
					 });	
				
	/**********************************************************************/	
				   	$("#btndownload").click(function(){
						 alert("I m ikn btndownload");
						 var canvas = document.getElementById('canvas');
						 var dataURL = canvas.toDataURL();
						 console.log(dataURL);
						 //alert(dataURL);
						 
						 $.ajax({
							  type: "POST",
							  url: "uploadcanvas",
							  data: {contents: dataURL},
							  success: function(responsetext){
									$("#endsuccess").show("slow", function() {
										  $(this).css("color","green");
									      $(this).text("Entered text "+responsetext+" saved successfully.");
									    });
									$("#endsuccess").hide("slow", function() {
										  $(this).text("");
									    });
									$("#endsuccess").show(5,function() {
										  $(this).text("");
									    });
								   },
								   error: function(){
						 			  console.log("fail") ; 
							       },
								 
								});
					});
    /**********************************************************************/
				   	
				   	$('textarea').on('input', function() {
				   	  $(this).outerHeight(38).outerHeight(this.scrollHeight);
				   	});


				
				
				
});
