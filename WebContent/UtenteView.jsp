<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="statix/css/UtenteView.css" rel='stylesheet' type='text/css' />
<script src="http://code.jquery.com/jquery-latest.min.js"type="text/javascript"></script>
<title>Utente - Home</title>
</head>
<body>
<script>  
		$(document).ready(function(){	
          $.ajax({  
        	  	 dataType: "json",
                 url:'getSurvey',
                 data: null,
                 success: function(response){  
                    
                	var obj = jQuery.parseJSON(response.resultQuery);
                	console.log(obj);
                 	for(var i in obj)
                    	{
                 			var id=obj[i][0];
                 			var nomeSondaggio=obj[i][1];
                 			var ammID=obj[i][2];
                 			var p_id=id+"_"+ammID+"_"+nomeSondaggio;
                 			
                 			
                 			
                 		   
                 		   
                 			//var link='<a href="<s:url action="mostraSondaggio"/>">Partecipa</a>';
                 			var form='<form action="mostraSondaggio"><s:hidden name="sondaggioIDScelto" value="'+id+'"/><s:hidden name="nomeSondaggioUtente" value="'+nomeSondaggio+'"/><s:hidden name="autoreSondaggioUtente" value="'+ammID+'"/><input type="submit" value="Partecipa" ></form>';	
                 			
                 			
                 			
                    	  $('<li>', {html: "<p id="+p_id+">nome: "+nomeSondaggio+" ammID: "+ammID+form+"</p>"}).appendTo('ul.ulID')
                    	}
                 }  
			});  
		});  
		
		
		
		function a_onClick() {
			   alert('a_onClick');
			  }
</script> 

<div id="dsssss" style="display: inline-block; width:100%">
   
	<div>
		<h1>Pannello Utente</h1>
	</div>
	<div id="logout">
   		<form action="logoutUtente" method="get">
		<input type="submit" value="Logout" id="inputLogout"/>
   	</form>
   	</div>
	
</div>




<div style="text-align: center">
	<div id="yourdiv" style="display: inline-block">
    <h3>Vuoi diventare Amministratore?</h3>
   	<form id="formID" action="effettuaPagamento" method="post">
		Riscrivi il tuo username per sicurezza:<br>
		<input type="text" name="usernameSicurezza">
		<br>
		<input type="submit" value="Paga"/>
   	</form>
   	</div>
</div>

<br> 

<div style="text-align: center">
	<div id="yourdiv" style="display: inline-block">
    <h3>Vuoi partecipare a un sondaggio?</h3>

	<ul class="ulID">
	</ul>
	</div>
</div>
	
	
<div>
	
</div>

</body>
</html>