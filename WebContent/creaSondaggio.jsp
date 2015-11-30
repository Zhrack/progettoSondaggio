<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
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

<form action="creaSondaggio" method="post">
		Nome Sondaggio
		<input name="nomeSondaggio" type="text" class="text" value="Nome Sondaggio" onfocus="this.value = '';" onblur="if (this.value == '') {this.value = 'Nome Sondaggio';}" >
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
		<div class="submit">
			<input type="submit" onclick="myFunction()" value="Crea" >
		</div>	 
</form>


</body>
</html>