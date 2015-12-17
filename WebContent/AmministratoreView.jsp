<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="statix/css/Amministratore.css" rel='stylesheet' type='text/css' />
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
		<style type="text/css">
			.bs-example{
				margin: 20px;
				}
		</style>

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
<div class="bs-example" id="messageBox"><s:property value="prova"/>
    <div class="alert fade in">
    <script type="text/javascript">
    $(document).ready(function(){
    	var esitoCancSond='<s:property value="esitoCancSond"/>';
    	var message;
		
    	
    	if(esitoCancSond=="1")
    		{
    			message="<strong>Successo! La cancellazione del sondaggio � riuscita.</strong>"
    			$("#messageBox").children().addClass("alert-success");
    			$('#messageBox').css("visibility","visible");
    		}
    	else if(esitoCancSond=="0")
    		{
    			message="<strong>Errore! La cancellazione del sondaggio � fallita, riprova.</strong>"
    			$("#messageBox").children().addClass("alert-danger");
    			$('#messageBox').css("visibility","visible");
    		}
    	else if(esitoCancSond=="-1")
    		{
    			$('#messageBox').css("visibility","hidden");
    		}
    	
    	
    	
    	$(message).insertAfter($("#dismissMessageBox"));
    });
		</script>
        <a id="dismissMessageBox" href="#" class="close" data-dismiss="alert">&times;</a>
    </div>
</div>

<ul id="listaSondaggi">
<s:hidden id="hiddenSondaggioIDScelto" name="sondaggioIDScelto" value=""/> 
<s:iterator value="listaSondaggiAmministratore">
	<li>
		<s:property value="nomeSondaggio"/>
		<s:hidden id="hiddenSondaggioID" name="sondaggioID" value="%{sondaggioID}"/>
		<br>
		<div class="contanierOption">
			<form method="get" class="leftDiv">
				<input type="submit" value="Report" class="inputReport"/>
   			</form>
   			<form action="modificaSondaggio" method="get" class="leftDiv">
   				<s:hidden id="%{sondaggioID}" name="sondaggioIDScelto" value=""/>
				<input type="submit" value="Modifica" class="inputModifica"/>
   			</form>
   			<form action="cancellaSondaggio" method="get" class="rightDiv">
   				<s:hidden id="%{sondaggioID}" name="sondaggioIDScelto" value=""/>
				<input type="submit" value="Cancella" class="inputCancella"/>
   			</form>
		</div>	
	</li>
</s:iterator>
</ul>

<script type="text/javascript">

//----------------------------------------------------------------------------------------------------------------
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
 
 
 
//-----------------------------------------------------------------------------------------------------
// setto l'attributo della action "sondaggioIDscelto" per cancellarlo
 $('.inputCancella').on("click",function() {
	 var sondaggioIDscelto=$(this).parent().parent().parent().find("#hiddenSondaggioID").val()
	 alert(sondaggioIDscelto);
	 
	 $("#"+sondaggioIDscelto).val(sondaggioIDscelto);
	});
 
 
 
//-----------------------------------------------------------------------------------------------------
//setto l'attributo della action "sondaggioIDscelto" per modificarlo
 $('.inputModifica').on("click",function() {
	 var sondaggioIDscelto=$(this).parent().parent().parent().find("#hiddenSondaggioID").val()
	 alert(sondaggioIDscelto);
	 
	 $("#"+sondaggioIDscelto).val(sondaggioIDscelto);
	});
 </script>
</body>
</html>