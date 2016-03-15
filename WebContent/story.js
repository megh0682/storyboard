/**
 * Story content post forms using jquery ajax and validation of UI inputs.
 */
 $(document).ready(function(){
	 
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
		var formTitle = $("#formTitle textarea").val();
		if(formTitle=="")
		  alert("Please enter the title of your story!");
		else
		  $.ajax({
			  type: "POST",
			  url: "StoryServlet",
			  data: {storyTitle:formTitle,action:"passtitle"},
			  dataType: "json",
			  success: function(json){
				$("#titlesuccess").show("slow", function() {
					  $(this).css("color","green");
				      $(this).text("Story Title saved successfully.");
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
			var formbgn = $("#formbgn textarea").val();
			if(formbgn=="")
			  alert("Please enter the beginning of your story!");
			else
			  $.ajax({
				  type: "POST",
				  url: "StoryServlet",
				  data: {storyBegin:formbgn,action:"passbegin"},
				  dataType: "json",
				  success: function(json){
					$("#bgnsuccess").show("slow", function() {
						  $(this).css("color","green");
					      $(this).text("Form beginning saved successfully.");
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
				var formmiddle = $("#formmiddle textarea").val();
				if(formmiddle=="")
					 alert("Please enter the middle of your story!");
				else
				  $.ajax({
					  type: "POST",
					  url: "StoryServlet",
					  data: {storyMiddle:formmiddle,action:"passmiddle"},
					  dataType: "json",
					  success: function(json){
						$("#middlesuccess").show("slow", function() {
							  $(this).css("color","green");
						      $(this).text("Story middle content saved successfully.");
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
					var formend = $("#formend textarea").val();
					if(formend=="")
						 alert("Please enter the end of your story!");
					else
					  $.ajax({
						  type: "POST",
						  url: "StoryServlet",
						  data: {storyEnd:formend,action:"passend"},
						  dataType: "json",
						  success: function(json){
							$("#endsuccess").show("slow", function() {
								  $(this).css("color","green");
							      $(this).text("Story end contents saved successfully.");
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
						 alert("upload canvas jquery");
						 var hiddenid = $("#hiddenstoryid").val();
						 var canvas = document.getElementById('canvas');
						 var dataURL = canvas.toDataURL();
						 console.log(dataURL);
						 
						//alert(dataURL);
						 
						 $.ajax({
							  type: "POST",
							  url: "StoryServlet",
							  data: {action:"storypic", contents: dataURL,storyid:hiddenid},
							  dataType: "json",
							  success: function(json){
								  alert("Success I am on story jsp page");
								  if (json.storyjsp) {
							            // data.redirect contains the string URL to redirect to
							            window.location.href = "story.jsp";
							      }else{
							            // data.form contains the HTML for the replacement form
							            alert(json.error);
							      }
								  
								  },
							  error: function(){
						 			  console.log("fail") ; 
							       },
								 
								});
					});
/**************************************************************************************************************************************************************/
				   	
				   	$('textarea').on('input', function() {
				   	  $(this).outerHeight(38).outerHeight(this.scrollHeight);
				   	});
				   	
/**************************************************************************************************************************************************************/
					$("#lnktosubmit").click(function(){
						 alert("submitting a story and on my way to draw jsp");
						 var formTitle = $("#formTitle textarea").val();
						 var formbgn = $("#formbgn textarea").val();
						 var formmiddle =$("#formmiddle textarea").val();
						 var formend = $("#formend textarea").val();
						 
					if (formTitle!="" && formbgn!="" && formmiddle!="" && formend!="")
					  {
						
						 $.ajax({
							  type: "POST",
							  url: "StoryServlet",
							  data: {action:"createStory", storyTitle:formTitle, storyBegin:formbgn, storyMiddle:formmiddle, storyEnd:formend},
							  dataType: "json",
							  success: function(json){
								alert("Success: I am on draw jsp page") ;
								if (json.redirect) {
					            // data.redirect contains the string URL to redirect to
					            window.location.href = json.redirect;
					            }else{
					            // data.form contains the HTML for the replacement form
					            alert(json.error);
					            $("#formTitle textarea").val(formTitle);	
					            $("#formbgn textarea").val(formbgn);	
					            $("#formmiddle textarea").val(formmiddle);	
					            $("#formend textarea").val(formend);	
					            }
					           
								},
							   error: function(){
					 			  console.log("fail") ; 
						       },
							 
							});
						
					}else{
						  
						alert("Story title, beginning , middle and end should be filled and saved successfully!")  
					  }
					
					
					});   	
				  
					
						
					

				
				
				
});
