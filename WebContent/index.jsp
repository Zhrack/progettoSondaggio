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
   <h1>Simple Login with Struts2</h1>
   <form action="loginSubmitted" method="post">
<!-- 		Username:<br> -->
<!-- 		<input type="text" name="user"> -->
<!-- 		<br> -->
<!-- 		Password:<br> -->
<!-- 		<input type="password" name="psw"> -->
<!-- 		<br><br> -->
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