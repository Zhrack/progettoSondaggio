<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Amministratore - Pannello di controllo</title>
</head>
<body>
 <h1>Pannello Amministratore</h1>
  <div>
	<form action="ToCreaSondaggioView" method="get">
		<input type="submit" value="Crea nuovo Sondaggio"/>
   	</form>
</div><br><br>
 <div>
	<form action="logout" method="get">
		<input type="submit" value="Logout"/>
   	</form>
</div>
</body>
</html>