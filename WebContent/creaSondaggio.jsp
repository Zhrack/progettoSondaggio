<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="statix/css/CreaSondaggio.css" rel='stylesheet' type='text/css' />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<title>Crea Sondaggio</title>
</head>
<body>
<h1>Crea un Sondaggio</h1>

<!-- Form bottone crea sondaggio -->
<form action="creaSondaggio" method="post" id="formCreaSondaggio">
		<s:hidden id="oggettoJSON" name="oggettoJSON" value=""/>
		<s:hidden id="nomeSondaggioHidden" name="nomeSondaggio" value=""/>
		<div class="submit">
			<input type="submit" onclick="return controlloCreaSondaggio()" value="Crea" >
		</div>	 
</form>


<!-- Form bottoni dinamici aggiungi risposte e domande -->
<form method="POST">
     <div id="dynamicInput">
     </div>
     <input type="button" value="Aggiungi una Risposta" onClick="addRisposta('dynamicInput');">
     <input type="button" value="Aggiungi una domanda" onClick="addDomanda('dynamicInput');">
</form>


<script language="Javascript" type="text/javascript">
var counterDomande = 0;
var counterRisposte = 0;
var limit = 15;
function addDomanda(divName){
	
	if(controlloNumeroRisposteDomanda()==false)
		{
			return;
		}
		
		
     if (counterDomande == limit)  {
          alert("Hai superato il limite di domande da inserire");
     }
     else {
    	 counterRisposte=0;
    	 counterDomande++;
          var newdiv = document.createElement('div');
          $(newdiv).attr('id', 'domanda_'+counterDomande);

          newdiv.innerHTML = "Domanda " + (counterDomande) + " <br><input type='text' name='myInputs[]'>";
          document.getElementById(divName).appendChild(newdiv);
     }
}


//----------------------------------------------------------------------------------------------------------------
function addRisposta(divName){
	if(counterDomande==0)
		{
			alert("Prima di inserire una risposta devi inserire una domanda");
		}
	else if (counterRisposte == limit)  {
    	 alert("Hai superato il limite di risposte da inserire");
    }
    else {
    	 counterRisposte++;
    	 var newdiv = document.createElement('div');
    	 $(newdiv).attr('class', 'domanda_'+counterDomande);
         newdiv.innerHTML = "Risp " + (counterRisposte) + " <br><input type='text' name='myInputs[]'>";
         document.getElementById(divName).appendChild(newdiv);
         
    }
}



//----------------------------------------------------------------------------------------------------------------
function controlloNumeroRisposteDomanda(){
	console.log("numero risposte della domanda_"+counterDomande+":"+$(".domanda_"+counterDomande).length);
		if($(".domanda_"+counterDomande).length==0 && counterDomande>0)
			{
				alert("Per ogni domanda devi inserire almeno una risposta");
				return false;
			}
		else
			return true;
	}
	
	
//----------------------------------------------------------------------------------------------------------------
function controlloNumeroDomande()
	{
		if(counterDomande==0)
			{
				alert("Per ogni sondaggio devi inserire almeno una domanda");
				return false;
			}
		else
		{ 
			if(controlloNumeroRisposteDomanda()==false)
				{
					return false;
				}
		}
	}
	
	
//----------------------------------------------------------------------------------------------------------------
// Questa funzione viene chiamata quando l'utente clicca sul bottone "Crea", controlla che i dati
// sono stati inseriti correttamente e poi setta gli attributi della action (che mi serviranno)
// per salvare i dati del nuovo sondaggio nel DB
function controlloCreaSondaggio(){
		if(controlloNumeroDomande()==false)
			return false;
		else
			{
				
			 // creo l'oggetto con le domande e risposte
			 var data={};
			 var z;
			 for(var i=0;i<counterDomande;i++)
					{
						z=i+1;
						data[i]={};
						data[i].domanda=[];
						data[i].domanda.push(($("#domanda_"+z+" input").val()));
						data[i].risposte=[];
						
					    for(var k=0;k<$(".domanda_"+z).length;k++)
					    {
					         var el=$(".domanda_"+z+":nth("+k+") input");
					         var text=$(el).val();
							 data[i].risposte.push(text);
					    }
					  }
				var testiDomanda=$("#oggettoJSON");
				$(testiDomanda).val(JSON.stringify(data));
				
				// setto l'attributo dell'action "nome del sondaggio" 
				var nomeSondaggio=$("#nomeSondaggioInput").val();
				$("#nomeSondaggioHidden").val(nomeSondaggio); 				
			}
	}
</script>

</body>
</html>