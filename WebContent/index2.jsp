<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
   pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
   <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Login Example</title>
</head>
<body>
   <h1>Login</h1>
   <form action="loginSubmitted" method="post">
   		<s:hidden name="option" value="login"/>
		Username:<br>
		<input type="text" name="username">
		<br>
		Password:<br>
		<input type="password" name="password">
		<br><br>
		<input type="submit" value="Submit"/>
   </form>
   <h1>Registrati</h1>
   <form action="registrationSubmitted" method="post">
   		<s:hidden name="option" value="registrazione"/>
		Username:<br>
		<input type="text" name="username">
		<br>
		Password:<br>
		<input type="password" name="password">
		<br>
		Nome:<br>
		<input type="text" name="nome">
		<br>
		Cognome:<br>
		<input type="text" name="cognome">
		<br>
		Data di nascita:<br>
		<s:date name="nascita" format="MM/dd/yyyy" />
		<br><br>
		<input type="submit" value="Submit"/>
   </form>
   <br><br><br><hr>
<h3>Accepted credentials:</h3>
<br>
<p>User: Davide  Psw: davide</p>
<p>User: Andrea  Psw: andrea</p>
<p>User: Matteo  Psw: matteo</p>
<p>User: Simone  Psw: simone</p>
   
</body>
</html>