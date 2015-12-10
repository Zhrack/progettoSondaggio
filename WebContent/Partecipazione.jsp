<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="utf-8" />
		<link href="http://fonts.googleapis.com/css?family=Cantarell:regular,italic,bold,bolditalic" rel="stylesheet" />
		<link href="http://fonts.googleapis.com/css?family=Droid+Serif:regular,italic,bold,bolditalic" rel="stylesheet" />
		<link rel="stylesheet" href="statix/css/grey.css" />
		<script src="http://code.jquery.com/jquery-latest.min.js"type="text/javascript"></script>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Partecipazione</title>
	</head>
	<body>
		<div id="dsssss" style="display: inline-block; width:100%">
				<h1><s:property value="nomeSondaggioUtente"/></h1>
		</div>
		<form id="formDomande">
		<script type="text/javascript">
		
		var testiDomanda='<s:property value="testiDomandaUtente"/>';
		testiDomanda=testiDomanda.split("[").pop();
		testiDomanda = testiDomanda.substring(0, testiDomanda.length - 1);
		testiDomanda=testiDomanda.substring().split(",");
		console.log("testiDomanda--"+testiDomanda);
		
		
		var testiRisposta='<s:property value="testiRispostaUtenteInStringa"/>';
		testiRisposta=testiRisposta.split("[").pop();
		testiRisposta = testiRisposta.substring(0, testiRisposta.length - 1);
		testiRisposta=testiRisposta.substring().split(",");
		console.log(testiRisposta);
		var objRisp={};
		
		var k=0;
		for(var i in testiRisposta)
    	{
 			var oggettoRisposta=testiRisposta[i];
 			lista=oggettoRisposta.substring().split("-");
 			var rispostaID=lista[0];
 			var rispostaTesto=lista[1];
 			var rispostaDomandaID=lista[2];
 			
 			var obj={
 					rispostaID : rispostaID,
 					rispostaTesto : rispostaTesto,
 					rispostaDomandaID : rispostaDomandaID,
 			};
 			objRisp[k]=obj;
 			k++;
    	}
		console.log(objRisp);
		
		
		
		
		var domandaID='<s:property value="domandaID"/>';
		console.log(domandaID);
		domandaID=domandaID.split("[").pop();
		domandaID = domandaID.substring(0, domandaID.length - 1);
		domandaID=domandaID.substring().split(",");
		
		
		var domandaIDParsato=[];
		var k=0;
		for(var i in domandaID)
    	{
			domandaIDParsato[k]=parseInt(domandaID[i]);
			k++;
    	}
		
		console.log(domandaIDParsato);
		console.log("asdasdasdasdsa");
		
		
		
		for(var i in domandaIDParsato)
    	{
 			var html="";
 			for(var j in objRisp)
 				{
 					
 					if(objRisp[j].rispostaDomandaID==domandaIDParsato[i])
 						{
 						console.log(" ");
 	 					console.log(objRisp[j].rispostaDomandaID);
 	 					console.log(objRisp[j].rispostaTesto);
 	 					console.log(" ");
 						  html+='<input type="radio" name="sex" value="female" id="male" />'+
 							'<label for="male">'+objRisp[j].rispostaTesto+'</label>';
 						 
 						}
 					console.log(html);
 				}
 			
 			
 			console.log(testiDomanda[i]);
 			var html2='<legend>'+testiDomanda[i]+'</legend>';
 			$('<fieldset>',{html: html2+html}).appendTo("#formDomande");
 			
    	}
		
		</script>
		
			<fieldset>
				<legend><s:property value="testiDomandaUtente[0]"/></legend>
				<p>
					<input type="radio" name="sex" value="female" id="male" />
					<label for="male"><s:property value="testiRispostaUtente[0].toString()"/></label>
				</p>
				<p>
					<input type="radio" name="sex" value="male" id="female" />
					<label for="female">Female</label>
				</p>
			</fieldset>
			
			
			<p class="nolabel">
				<button>button</button>
			</p>
		</form>
		<script src="statix/js/modernizr-1.5.min.js"></script>
		<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.0/jquery.min.js"></script>
		<script src="statix/js/main.js" type="text/javascript"></script>
</body>
</html>