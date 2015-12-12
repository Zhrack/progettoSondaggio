<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="statix/css/Amministratore.css" rel='stylesheet' type='text/css' />
<script src="http://code.jquery.com/jquery-latest.min.js"type="text/javascript"></script>
<title>Amministratore - Pannello di controllo</title>
</head>
<body>
 <h1>Pannello Amministratore di <s:property value="username"/></h1>
 <div id="divLogout">
	<form action="logoutAmministratore" method="get">
		<input type="submit" value="Logout"/>
   	</form>
</div>
  <div id="top">
	<form action="ToCreaSondaggio" method="get" id="formNuovoSondaggio" class="formTop">
		<input type="submit" value="Crea nuovo Sondaggio"/>
   	</form>
   	<form action="loginSubmitted" method="get" id="formPartecipaSondaggio" class="formTop">
   	<s:hidden id="accessoAmministratoreComeUtente" name="accessoAmministratoreComeUtente" value="1"/>
   	<s:hidden id="option" name="option" value="login"/>
   	<s:hidden id="tagUsername" name="username" value=""/>
   	<s:hidden id="tagPassword" name="password" value=""/>
		<input type="submit" value="Partecipa ai Sondaggio" id="inputPartecipaSondaggi"/>
   	</form>
</div><br><br>
<h2>Lista dei sondaggi creati</h2>
<ul id="ulID">
	<li>cacca</li>
	<li>forcio</li>
</ul>
<script type="text/javascript">

 $('#inputPartecipaSondaggi').on("click",function() {
	  	
	 	// setto l'username e la password con quelli della sessione 
	 	// (mi servira' se l'amm vuole fare l'accesso come se fosse un'utente)
	    var tagUsername=$("#tagUsername");
	 	var username='<s:property value="username"/>';
	 	$(tagUsername).val(username);
	 	
	 	
	 	var tagPassword=$("#tagPassword");
	 	var password='<s:property value="password"/>';
	 	$(tagPassword).val(password);
	}); 
 </script>
</body>
</html>