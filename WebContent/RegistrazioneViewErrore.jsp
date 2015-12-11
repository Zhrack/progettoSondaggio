<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
		<style type="text/css">
			.bs-example{
				margin: 20px;
				}
		</style>
<link href="statix/css/index.css" rel='stylesheet' type='text/css' />
<title>Riprova la registrazione</title>
</head>
<body>

<div class="bs-example">
    <div class="alert alert-danger fade in">
        <a href="#" class="close" data-dismiss="alert">&times;</a>
        <strong>Errore!</strong> Registrazione non riuscito, riprova.
    </div>
</div>



<div class="main">
		<div class="login-form">
		
		<h2>Registrazione</h2>
				<form action="registrationSubmitted" method="post">
				<s:hidden name="option" value="registrazione"/>
						<input name="username" type="text" class="text" value="USERNAME" onfocus="this.value = '';" onblur="if (this.value == '') {this.value = 'USERNAME';}" >
						<input name="password" type="password" value="Password" onfocus="this.value = '';" onblur="if (this.value == '') {this.value = 'Password';}">
						<input name="nome" type="text" class="text" value="Nome" onfocus="this.value = '';" onblur="if (this.value == '') {this.value = 'Nome';}" >
						<input name="cognome" type="text" class="text" value="Cognome" onfocus="this.value = '';" onblur="if (this.value == '') {this.value = 'Cognome';}" >
						<s:radio label="Sesso" name="sesso" list="{'maschio','femmina'}" />
						<div class="submit">
							<input id="regSubmit" type="submit" onclick="myFunction()" value="Registrati" >
					</div>	 
				</form>
</div>
</div>

</body>
</html>