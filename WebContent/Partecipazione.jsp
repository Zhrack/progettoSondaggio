<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link href="http://fonts.googleapis.com/css?family=Cantarell:regular,italic,bold,bolditalic" rel="stylesheet" />
		<link href="http://fonts.googleapis.com/css?family=Droid+Serif:regular,italic,bold,bolditalic" rel="stylesheet" />
		<link href="statix/css/Partecipazione.css" rel='stylesheet' type='text/css' />
		<script src="http://code.jquery.com/jquery-latest.min.js"type="text/javascript"></script>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
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
		<title>Partecipazione</title>
	</head>
	<body>
	

		<div  style="display: inline-block; width:100%">
				<h1><s:property value="nomeSondaggioUtente"/></h1>
				<h2><s:property value="prova"/></h2>
		</div>
		
		
		<div id="main">
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
		
		
		// creo "objRisp" che mi servira per salvari oggetti con dentro tutte le cose che mi
		// servono della risposte (ID,testo,domandaID)
		var k=0;
		for(var i in testiRisposta)
    	{
 			var oggettoRisposta=testiRisposta[i];
 			lista=oggettoRisposta.substring().split("-");
 			var rispostaID=lista[0];
 			var rispostaTesto=lista[1];
 			var rispostaDomandaID=lista[2];
 			
 			var obj={
 					rispostaID : parseInt(rispostaID),
 					rispostaTesto : rispostaTesto,
 					rispostaDomandaID : rispostaDomandaID,
 			};
 			objRisp[k]=obj;
 			k++;
    	}
		console.log(objRisp);
		
		
		
		// creo l'array "domandaIDParsato" che contiene tutti gli ID delle domande
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
		
		
	
		for(var i in domandaIDParsato)
    	{
			// per ogni domanda creo tutti gli input con le possibili risposte
			// relative a quella specifica domanda
 			var html="";
 			for(var j in objRisp)
 				{
 					if(objRisp[j].rispostaDomandaID==domandaIDParsato[i])
 						{
 						  html+='<p><input type="radio" name="domandaID_'+domandaIDParsato[i]+'" value="'+objRisp[j].rispostaID+'" />'+
 							'<label for="'+j+'">'+objRisp[j].rispostaTesto+'</label></p>';
 						}
 				}
 			
 			var html2='<h3>'+testiDomanda[i]+'</h3>';
 			var form=$('<form>',{html: html2+html}).appendTo("#main");
    	}		
		</script>
				
		<form action="aggiungiPartecipazione" method="post">
				<s:hidden id="risultato" name="listaRisultatoPartecipazione" value=""/>
						<div class="submit">
							<input type="submit" onclick="return settaListaRisultatoPartecipazione()" value="Invia" >
					</div>	 			
		</form>
		
		<script type="text/javascript">
			function settaListaRisultatoPartecipazione()
			{
				// prendo il campo s:hidden risultato dove mettero' "listaRisultatoPartecipazione"
				var listaRisultatoPartecipazione=$("#risultato");		
				
				// prendo tutte le risposte selezionate
				var risultati=[];
				$("input:checked").each(function () {
					 var rispostaID=$(this).val();
					 risultati.push(rispostaID);
					});
				var string = JSON.stringify(risultati);
				
				
				
				// setto listaRisultatoPartecipazione
				$(listaRisultatoPartecipazione).val(string);

				if(risultati.length==0)
					{
						alert("Per inviare la tua partecipazione devi selezionare almeno una risposta");
						return false;
					}
			}
		</script>
		
		</div>
</body>
</html>




