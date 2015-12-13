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

<SCRIPT language="javascript">

        function aggiungiDomanda(tableID) 
        {
                var table = document.getElementById(tableID);

                var rowCount = table.rows.length;
                var row = table.insertRow(rowCount);
                var counts = rowCount - 1;

                var cell1 = row.insertCell(0);
                var houseNo = document.createElement("input");
                houseNo.type = "text";
                houseNo.name = "addresses[" + counts + "].houseNo";
                cell1.appendChild(houseNo);

                var cell2 = row.insertCell(1);
                var street = document.createElement("input");
                street.type = "text";
                street.name = "addresses[" + counts + "].street";
                cell2.appendChild(street);

                var cell3 = row.insertCell(2);
                var city = document.createElement("input");
                city.type = "text";
                city.name = "addresses[" + counts + "].city";
                cell3.appendChild(city);

                var cell4 = row.insertCell(3);
                var country = document.createElement("input");
                country.type = "text";
                country.name = "addresses[" + counts + "].country";
                cell4.appendChild(country);

        }
        
        function aggiungiRisposta(divDomanda, divRisposta) 
        {
                var domanda = document.getElementById(divDomanda);
                var risposta = document.getElementById(divRisposta);

                var rowCountDomanda = $("#divDomanda > div").length;
                var countsDomanda = rowCountDomanda - 1;
                
                var rowCountRisposta = $("#divRisposta > input").length;
                var rowRisposta = risposta.insertRow(rowCountRisposta);
                var countsRisposta = rowCountRisposta - 1;

                

        }
</SCRIPT>
</head>
<body>
<h1>Crea un Sondaggio</h1>

<form action="creaSondaggio" method="post" id="formCreaSondaggio">
		<div id="divLeft">
		Nome Sondaggio
		<input name="nomeSondaggio" type="text" class="text" value="Nome Sondaggio" onfocus="this.value = '';" onblur="if (this.value == '') {this.value = 'Nome Sondaggio';}" >
		</div>
		<div class="submit" id="divRight">
			<input type="submit" onclick="return controlloCreaSondaggio()" value="Crea" >
		</div>
		<br><br>
		<div id='divDomande'>
			<div id='divDomanda0'>
				Domanda: <input type="text" name="testiDomanda[0]" ><br><br>
				
				- Risposta: <input type="text" name="testiRisposta[0].risposte[0]" ><br>
				
				<br>
				<input type="button" value="Nuova risposta" onClick="aggiungiRisposta('divDomanda0');"> 
			</div>
		</div>
		<input type="button" value="Nuova domanda" onClick="aggiungiDomanda('divDomande');"> 
			 
</form>




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
	
function controlloCreaSondaggio(){
		if(controlloNumeroDomande()==false)
			return false;
		else
			{
			var data=new Array(counterDomande);;
			var i=1;
			k=0;
			for(i=0;i<counterDomande;i++)
			{
			     for(k=0;k<$(".domanda_"+i).length;k++)
			    {
			        var testoRisposta=$(".domanda_"+i+":nth("+k+") input").val();
			         alert(testoRisposta);
			         data[i][k]= testoRisposta;
			    }
			  }
			// STAVO QUA DEVO mettere tutte le risposte dentro un oggetto da passare al server
			}
	}
</script>

</body>
</html>