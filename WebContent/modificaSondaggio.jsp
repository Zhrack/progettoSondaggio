<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="statix/css/CreaSondaggio.css" rel='stylesheet' type='text/css' />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<title>Modifica Sondaggio</title>
</head>
<body>
<h1>Modifica Sondaggio</h1>

<!-- Form bottone crea sondaggio -->
<form action="modificaSondaggio" method="post" id="formCreaSondaggio">
		<div id="divNomeSondaggio">
		Nome Sondaggio
		<input id="nomeSondaggioInput" type="text" class="text" >
		<s:hidden id="sondaggioIDHidden" name="sondaggioIDSceltoPerModifica" value=""/>
		<script type="text/javascript">
			var nomeSondaggioDaModificare='<s:property value="%{modificaSondaggioData.nomeSondaggio}"/>';
			$("#nomeSondaggioInput").val(nomeSondaggioDaModificare);
			
			var sondaggioIDDaModificare='<s:property value="%{modificaSondaggioData.sondaggioID}"/>';
			$("#sondaggioIDHidden").val(sondaggioIDDaModificare);
		</script>
		</div>
		<s:hidden id="oggettoJSON" name="oggettoJSON" value=""/>
		<s:hidden id="nomeSondaggioHidden" name="nomeSondaggioModifica" value=""/>
		<div class="submit">
			<input class="inputTop" type="submit" onclick="return controlloModificaSondaggio()" value="Modifica" >
		</div>	 
</form>




<!-- domande e risposte gia' presenti nel sondaggio -->
<ul id="listaSondaggi">
<s:iterator value="modificaDomandeData">
	<li>
	<s:hidden id="hiddenDomandaID" name="prova"/>
		<input type='text'>
		<s:iterator value="modificaRisposteData">
			<s:if test="%{domandaID==domandaIDPerModificaSondaggio}">
				<div>
					<input type='text' id="%{domandaID}">
					<s:hidden id="%{rispostaID}"/>
					<script text="text/javascript">
						// scrivo le risposte nei textfield
						var rispostaID='<s:property value="%{rispostaID}"/>';
						var testoRisposta='<s:property value="testoRisposta"/>';
						$("#"+rispostaID).parent().find("input").val(testoRisposta);
						
						
						
						// scrivo il testo della domanda nel textfield
						var testoDomanda='<s:property value="testo"/>';			
						$("#"+rispostaID).parent().parent().find("input:nth(1)").val(testoDomanda);
					</script>
					<script text="text/javascript">
			
		</script>
				</div>
			</s:if>
		</s:iterator>
	</li>
	<br>
</s:iterator>
</ul>
<!-- -------------------- -->








<!-- Form bottoni dinamici aggiungi risposte e domande -->
<form method="POST">
     <div id="dynamicInput">
     </div>
     <input class="inputButton" type="button" value="Aggiungi una Risposta" onClick="addRisposta('dynamicInput');">
     <input class="inputButton" type="button" value="Aggiungi una domanda" onClick="addDomanda('dynamicInput');">
</form>


<script language="Javascript" type="text/javascript">
var counterDomande = 0;
var counterRisposte = 0;
var limit = 15;
var data={};
var data2={};
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
function controlloModificaSondaggio(){
		
	 if(aggiuntaNuoveDomande()==true)
		 {
		 	uniscoDomandeRisposte();
		 	return true;
		 }
	 else
		 {
		 	var i=0;
		 	data={};
		 	$("#listaSondaggi li").each(function(index){
		 		data[i]={};
				data[i].domanda=[];
				data[i].domanda.push($(this).find("input:nth(1)").val());
				data[i].risposte=[];
		 		
		 		var that=$(this);
		 		that.find("div").each(function(index){
				 		var that2=$(this)
				 		console.log(that2.find("input:nth(0)").val());
				 		var testoRisposta=that2.find("input:nth(0)").val();
				 		data[i].risposte.push(testoRisposta);
		 			})
		 			
		 		i++;	
		 	});
		 	
		 	console.log(data);
		 	var testiDomanda=$("#oggettoJSON");
			$(testiDomanda).val(JSON.stringify(data));
			console.log($(testiDomanda));
			
			// setto l'attributo dell'action "nome del sondaggio" 
			var nomeSondaggio=$("#nomeSondaggioInput").val();
			$("#nomeSondaggioHidden").val(nomeSondaggio);
			console.log($("#nomeSondaggioHidden"));
			
			return true;		
		 }
	 
	 
}


//----------------------------------------------------------------------------------------------------------------
function aggiuntaNuoveDomande(){
	 data={};
	 // creo l'oggetto con le domande e risposte
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
	 
	 if($.isEmptyObject(data)==true)
		 {
		 	return false;
		 }
	 else
		 {
		 	return true;
		 }
}


// creo l'oggetto con le domande e risposte da passare al DB
//----------------------------------------------------------------------------------------------------------------
function uniscoDomandeRisposte()
{
	 data={};
	 
	 // l'oggetto con le domande nuove aggiunte 
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

	  // l'oggetto con le domande vecchie
	  $("#listaSondaggi li").each(function(index){
	 		data[i]={};
	 		data[i].domanda=[];
	 		data[i].domanda.push($(this).find("input:nth(1)").val());
	 		data[i].risposte=[];
	 		
	 		var that=$(this);
	 		that.find("div").each(function(index){
			 		var that2=$(this)
			 		console.log(that2.find("input:nth(0)").val());
			 		var testoRisposta=that2.find("input:nth(0)").val();
			 		data[i].risposte.push(testoRisposta);
	 			})
	 			
	 		i++;	
	 	});
	 	
		// setto l'attributo dell'action per passare le domande e risposte
	 	var testiDomanda=$("#oggettoJSON");
		$(testiDomanda).val(JSON.stringify(data));
		
		// setto l'attributo dell'action "nome del sondaggio" 
		var nomeSondaggio=$("#nomeSondaggioInput").val();
		$("#nomeSondaggioHidden").val(nomeSondaggio);
}

//----------------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------------
// Questa funzione viene chiamata quando l'utente clicca sul bottone "Crea", controlla che i dati
// sono stati inseriti correttamente e poi setta gli attributi della action (che mi serviranno)
// per salvare i dati del nuovo sondaggio nel DB
function controlloNuoveDomande(){
		if(controlloNumeroDomande()==false)
			return false;
		else
			{
			
			 data={};
			 // creo l'oggetto con le domande e risposte
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



<script language="Javascript" type="text/javascript">
var esitoModificaSondaggio='<s:property value="esitoCreazioneSondaggio"/>';
if(esitoModificaSondaggio=="0")
	{
		alert("Spiacente, errore nella modifica del sondaggio.");		
	}
</script>

</body>
</html>