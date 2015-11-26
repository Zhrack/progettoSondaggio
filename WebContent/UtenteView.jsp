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
 
 <s:iterator value="list">
	<s:property value="userID"/> , 
	<s:property value="nickname"/>,
	<s:property value="pswrd"/> , 
	<s:property value="nome"/>,
	<s:property value="cognome"/><br/><br/>
</s:iterator>
</body>
</html>