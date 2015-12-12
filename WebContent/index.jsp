<!--
Author: W3layouts
Author URL: http://w3layouts.com
License: Creative Commons Attribution 3.0 Unported
License URL: http://creativecommons.org/licenses/by/3.0/
-->
<!DOCTYPE html>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
	<title>Progetto Sondaggio</title>
		<meta charset="utf-8">
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
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
		<!--webfonts-->
		<link href='http://fonts.googleapis.com/css?family=Open+Sans:600italic,400,300,600,700' rel='stylesheet' type='text/css'>
		<!--//webfonts-->
</head>
<body>
	 <!-----start-main---->
	 <div class="main">
		<div class="login-form">
			<h1>Progetto Sondaggio</h1>
					<div class="head">
						<img src="statix/images/user.png" alt=""/>
					</div>
				<form action="loginSubmitted" method="post">
				<s:hidden name="option" value="login"/>
						<input name="username" type="text" class="text" value="davide" onfocus="this.value = '';" onblur="if (this.value == '') {this.value = 'USERNAME';}" >
						<input name="password" type="password" value="davide" onfocus="this.value = '';" onblur="if (this.value == '') {this.value = 'Password';}">
						<div class="submit">
							<input type="submit" onclick="myFunction()" value="Accedi" >
					</div>	 
					
				</form>
				<p><a href="RegistrazioneView.jsp">Sei un nuovo utente? Registrati.</a></p>
			</div>
			
			
			<!--//End-login-form-->
			 <!-----start-copyright---->
   					<div class="copy-right">
						<p id="footer">Developed by Davide Marcoccio, Matteo Tempesta, Simone Di Paolo</p> 
					</div>
				<!-----//end-copyright---->
		</div>
			 <!-----//end-main---->
		 		
</body>
</html>