/**
 * Story content post forms using jquery ajax and validation of UI inputs.
 */
 $(document).ready(function(){
	 
	 String s_title;
	 String s_begin;
	 String s_middle;
	 String s_end;
	 String S_image;
	 
	 $("input[value=Draw on Canvas]").click(function(){
		 $("#lnkcanvas").href = "draw.html";
	 });
	  

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
		if(formTitle==""){			
		  alert("Please enter the title of your story!");
		}else{
		  s_title = formTitle;
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
				    
		}
	});
	     
		
    $("#formTitle input[value=Cancel]").click(function(){
   	$("#formTitle textarea").val("");	
	});
	
    /**********************************************************************/
		$("#formbgn input[value=Save]").click(function(){
			var formbgn = $("#formbgn input[type=text]").val();
			if(formbgn=="")
			  alert("Please enter the beginning of your story!");
			else{
				 s_begin = formbgn;
				  $("#bgnsuccess").show("slow", function() {
							  $(this).css("color","green");
						      $(this).text("Story beginning "+responsetext+" saved successfully.");
						    });
				  $("#bgnsuccess").hide("slow", function() {
							  $(this).text("");
						    });
				  $("#bgnsuccess").show(5,function() {
							  $(this).text("");
				  });
						    
				}
			});
			
			$("#formbgn input[value=Cancel]").click(function(){
				$("#formbgn textarea").val("");	
			 });	
		
   /**********************************************************************/	
	
			$("#formmiddle input[value=Save]").click(function(){
				var formmiddle = $("#formbgn input[type=text]").val();
				if(formmiddle=="")
				  alert("Story middle cannot be blank.");
				else{
				s_middle = formmiddle;
				 $("#middlesuccess").show("slow", function() {
					  $(this).css("color","green");
				      $(this).text("Story middle "+responsetext+" saved successfully.");
				    });
		  $("#middlesuccess").hide("slow", function() {
					  $(this).text("");
				    });
		  $("#middlesuccess").show(5,function() {
					  $(this).text("");
		  });
				    
		}
	});
				
				$("#formmiddle input[value=Cancel]").click(function(){
					$("#formmiddle textarea").val("");	
				 });	
			
	 /**********************************************************************/	
				$("#formend input[value=Save]").click(function(){
					var formend = $("#formbgn input[type=text]").val();
					if(formend=="")
					  alert("Story end cannot be blank.");
					else{
							s_end = formend;
					  $("#endsuccess").show("slow", function() {
								  $(this).css("color","green");
							      $(this).text("Story middle "+responsetext+" saved successfully.");
							    });
					  $("#endsuccess").hide("slow", function() {
								  $(this).text("");
							    });
					  $("#endsuccess").show(5,function() {
								  $(this).text("");
					  });
							    
					}
				});
					
					$("#formend input[value=Cancel]").click(function(){
						$("#formend textarea").val("");	
					 });	
				
	/**********************************************************************/	
					$("#formsubmitstory input[value=Submit]").click(function(){
				    if(s_title!=""&& s_begin!="" && s_middle!="" && s_end!=""){
						$.ajax({
							  type: "POST",
							  url: "StoryServlet",
							  dataType: "json",
							  data: {action:"createStory", storyTitle:s_title, storyBegin:s_begin, storyMiddle:s_middle, storyEnd:s_end, contents:s_image},
							  success: function(json){
									alert("Success")  
									if (json.redirect) {
						            // data.redirect contains the string URL to redirect to
						            window.location.href = json.redirect;
						            }else{
						            // data.form contains the HTML for the replacement form
						            alert(json.error);
						            $("#formTitle input[type=text]").val("");	
						            }
						        
								   },
							   error: function(){
					 			  console.log("fail") ; 
						       },
							 
							});
					}
				    else{
				    	alert("Title, beginning,middle and end cannot be blank.");
				    }
				        });
						
								
/*******************************************************************************************************************************/			
					
					
				   	$("#btndownload").click(function(){
						// alert("I m ikn btndownload");
						 var canvas = document.getElementById('canvas');
						 var dataURL = canvas.toDataURL();
						 s_image=dataURL;
						 console.log(dataURL);
						 //alert(dataURL);
						
						  $("#canvassuccess").show("slow", function() {
									  $(this).css("color","green");
								      $(this).text("Story canvas saved successfully.");
								    });
						  $("#canvassuccess").hide("slow", function() {
									  $(this).text("");
								    });
						  $("#canvassuccess").show(5,function() {
									  $(this).text("");
						  });
								    
										
					});
    /**********************************************************************/
				   	
				   	$('textarea').on('input', function() {
				   	  $(this).outerHeight(38).outerHeight(this.scrollHeight);
				   	});
/************************************************************************************/
				   	
				  
					
						
					

				
				
				
});
