<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Crea Sondaggio</title>

<script language="Javascript" type="text/javascript"> 

	var counter = 1; 
	var limit = 20; 
	function aggiungiArgomento(divName){ 
		if (counter == limit) { 
			alert("Reached adding limit " + counter + " inputs"); 
		} 
		else { 
			var newdiv = document.createElement('div'); 
			newdiv.innerHTML = "Entry " + counter + "<br><input type='text' name='myInputs[]'>"; 
			document.getElementById(divName).appendChild(newdiv); 
			counter++; 
		} 
	};
	
	function aggiungiDomanda(divName){ 
		if (counter == limit) { 
			alert("Reached adding limit " + counter + " inputs"); 
		} 
		else { 
			var newdiv = document.createElement('div'); 
			newdiv.innerHTML = "Entry " + counter + "<br><input type='text' name='myInputs[]'>"; 
			document.getElementById(divName).appendChild(newdiv); 
			counter++; 
		} 
	};
	
	function aggiungiRisposta(divName){ 
		if (counter == limit) { 
			alert("Reached adding limit " + counter + " inputs"); 
		} 
		else { 
			var newdiv = document.createElement('div');
			newdiv.innerHTML = "Entry " + counter + "<br><input type='text' name='myInputs[]'>"; 
			document.getElementById(divName).appendChild(newdiv); 
			counter++; 
		} 
	};
		
</script> 
</head>
<body>
<h1>Crea un Sondaggio</h1>

<form action="creaSondaggio" method="post">
		<input name="username" type="text" class="text" value="USERNAME" onfocus="this.value = '';" onblur="if (this.value == '') {this.value = 'USERNAME';}" >
		<div id='argomento'>
			<div id='domanda'>
				<div id='risposta'>
				
				</div>
				<input type="button" value="Nuova risposta" onClick="aggiungiRisposta('risposta');"> 
			</div>
			<input type="button" value="Nuova domanda" onClick="aggiungiDomanda('domanda');"> 
		</div>
		<input type="button" value="Nuovo Argomento" onClick="aggiungiArgomento('argomento');"> 
		<div class="submit">
			<input type="submit" onclick="myFunction()" value="Crea" >
		</div>	 
</form>

</body>
</html>