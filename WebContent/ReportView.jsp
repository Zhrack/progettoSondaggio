<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="statix/js/amcharts/amcharts.js" type="text/javascript"></script>
<script src="statix/js/amcharts/serial.js" type="text/javascript"></script>
<link href="statix/css/ReportView.css" rel='stylesheet' type='text/css' />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<title>Report sondaggio</title>
</head>
<body>
<h1>Report <s:property value="nomeSondaggio"/></h1>
<p>Statistiche maschi/femmine</p>
<div id="chartSexDiv" class="classChart"></div>
<div id="container"></div>

<script type="text/javascript">

	
	var string='<s:property value="jsonQuery"/>';
	var jsonString=string.replace(/&quot;/g,'"');
	var obj=jQuery.parseJSON(jsonString);
	var chartDataArray=[];
	var testoDomande=[];
	
	for(var domandaID in obj)
	{
		var testoDomanda=objDomanda.testoDomanda;
		testoDomande.push(testoDomanda);
		var percentuali=objDomanda.percentuali;		
		var testoRisposte= objDomanda.testoRisposte;
		var chartData=[];

		for(var i in idRisposte)
			{
 				chartData.push({
 					"testoRisposta":testoRisposte[i],
 					"voto":percentuali[i]
 				})
			}
		
		chartDataArray.push(chartData);
	}
	
	
	
	
	AmCharts.ready(function() {
	
		// statistiche maschi/femmine
		var percentualeMaschi='<s:property value="percentualeMaschi"/>';
		var percentualeFemmine='<s:property value="percentualeFemmine"/>';
		var chartDataSex=[{
				"sesso":"maschi",
				"partecipazione":percentualeMaschi
			},{ "sesso":"femmine",
			"partecipazione":percentualeFemmine
			}];
		
		var chartSex = new AmCharts.AmSerialChart();
		chartSex.dataProvider = chartDataSex;
		chartSex.categoryField = "sesso";
		chartSex.angle = 30;
		chartSex.depth3D = 15;
		
		var categoryAxis = chartSex.categoryAxis;
		categoryAxis.autoGridCount  = false;
		categoryAxis.gridCount = chartDataSex.length;
		categoryAxis.gridPosition = "start";
		categoryAxis.labelRotation = 90;
		
		var graph = new AmCharts.AmGraph();
		graph.valueField = "partecipazione";
		graph.type = "column";
		graph.fillAlphas = 0.8;
		
		chartSex.addGraph(graph);
		chartSex.write('chartSexDiv');
		//------------------------------------------
		//------------------------------------------

		
		
		
		// statistiche delle domande
		for(var i in chartDataArray)
			{
				var chart = new AmCharts.AmSerialChart();
				chart.dataProvider = chartDataArray[i];
				chart.categoryField = "testoRisposta";
				chart.angle = 30;
				chart.depth3D = 15;
				
				
				var categoryAxis = chart.categoryAxis;
				categoryAxis.autoGridCount  = false;
				categoryAxis.gridCount = chartDataArray[i].length;
				categoryAxis.gridPosition = "start";
				categoryAxis.labelRotation = 90;
				
				var graph = new AmCharts.AmGraph();
				graph.valueField = "voto";
				graph.type = "column";
				graph.fillAlphas = 0.8;
				
				
				chart.addGraph(graph);
				var idChartDiv="chartDiv_"+i;
				var testoDomanda=testoDomande[i];
				
				$( "<h3>"+testoDomanda+"</h3>" ).appendTo($( "#container" ));
				$( "<div id="+idChartDiv+" class='classChart'></div>" ).appendTo($( "#container" ));
				
		
				chart.write(idChartDiv);
			}	
	});
	
</script>
</body>
</html>