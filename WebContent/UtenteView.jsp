<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Utente - Pannello di controllo</title>
</head>
<body>
 <h1>Pannello Utente</h1>
 <script src="http://code.jquery.com/jquery-latest.min.js"
        type="text/javascript"></script>
        <script>  
		$(document).ready(function(){	
          $.ajax({  
        	  	 dataType: "json",
                 url:'getSurvey',
                 data: null,
                 success: function(response){  
                	alert("fdsfsdf");
                    alert("risposta: " + response.resultQuery);
                    //$("#liID").text(response.resultQuery);
                    //var array = JSON.parse("[" + response.resultQuery + "]");
                    console.log(response)
                    var obj = jQuery.parseJSON(response.resultQuery);
                    alert("2222");
                    alert(obj);
                    for (var property in obj) {
                        if (object.hasOwnProperty(property)) {
                            // do stuff
                            alert(property)
                        }
                    }
					
					
                    alert(response.resultQuery);
                     
                     
                    // DEVO CAPIRE COME TRASFORMARE LA STRINGA DI JSON IN ARRAY DI JAVASCRIPT
                   
					var array = response.resultQuery.split(",");
					alert(array[0].toString());
                     
                  
                     
                    $('<li />', {html: response.resultQuery}).appendTo('ul.ulID')
                 }  
});  
});  

</script> 
 
 
 
 
 
 <!-- 
 <s:action name="getSurvey" executeResult="true">
 	<s:property value="myBeanProperty" />
 </s:action>
 -->
 
<div>
    <h3>Vuoi diventare Amministratore?</h3>
   	<form action="effettuaPagamento" method="post">
		Riscrivi il tuo username per sicurezza:<br>
		<input type="text" name="username">
		<br>
		<input type="submit" value="Paga"/>
   	</form>
</div>

<br><br><br>

<div>
	<ul class="ulID">
		<li id="liID">asdasdas</li>
	</ul>
</div>	
	
	
<div>
	<form action="logout" method="get">
		<input type="submit" value="Logout"/>
   	</form>
</div>

</body>
</html>