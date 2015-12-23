package com.struts2.progettoSondaggio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.struts2.interceptor.SessionAware;
import org.json.JSONObject;

import com.opensymphony.xwork2.ActionSupport;

public class ReportAction extends ActionSupport implements SessionAware{
	private Map<String, Object> ses;
	
	private String sondaggioIDReport; 
	
	private String nomeSondaggio;
	 
	// dati per percentuale
	private float percentualeMaschi;
	private float percentualeFemmine;
	private int totalePartecipanti;
	
	// dati domande
	private HashMap<Integer, PercentualeData> percentualiDomande;
	private String jsonQuery;

	private boolean startup;
	
	public ReportAction()
	{
		startup = false;
	}
	
	public void init()
	{
		setPercentualiDomande(new HashMap<Integer, PercentualeData>());
	}
	

	public String mostraReport()
	{
		System.out.println("mostraReport()");
		System.out.println("mostraReport(): ID sondaggio report->"+this.sondaggioIDReport);
		
		
		return this.generaReport();
	}
	
	
	
	public String generaReport()
	{
		percentualiDomande.clear();
		if(this.percentualeSesso() && this.creaStatisticheDomande())
			return SUCCESS;
		else
		{
			System.out.println("errore oppure nessun partecipante");
			
			return ERROR;
		}
			
	}
	
	public boolean percentualeSesso()
	{
        ResultSet result;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
	        Connection con = DriverManager.getConnection(LoginController.url, LoginController.user, LoginController.psw);
	        
	        Statement stmt = con.createStatement();
	        
	        // prendi numero di partecipanti al sondaggio
	        
			result = stmt.executeQuery(
					"SELECT COUNT(*) AS numero FROM (" +
					"SELECT DISTINCT U.userID FROM Utente U, Partecipazione P, Risposta R, Domanda D, Sondaggio S" +
                    " WHERE U.userID=P.userID_fk AND P.rispostaID_fk=R.rispostaID" +
                    " AND R.domandaID_fk=D.domandaID AND D.sondaggioID_fk=S.sondaggioID AND S.sondaggioID=" + this.sondaggioIDReport +
                    ") AS Partecipanti"
					);
			
			if (!result.isBeforeFirst() ) 
	        {    
	        	con.close();
	      	  	return false;
	        } 
			result.next();
			// prendi numero partecipanti
			int partecipanti = result.getInt("numero");
			System.out.println("partecipanti : "+partecipanti);
			
			
			// prendi numero partecipanti maschili
			result = stmt.executeQuery(
					"SELECT COUNT(*) AS maschi FROM (" +
					"SELECT DISTINCT U.userID FROM" +
					" Utente U, Partecipazione P, Risposta R, Domanda D, Sondaggio S" +
                    " WHERE U.userID=P.userID_fk AND P.rispostaID_fk=R.rispostaID" +
                    " AND R.domandaID_fk=D.domandaID AND D.sondaggioID_fk=S.sondaggioID AND S.sondaggioID=" + this.sondaggioIDReport +
                    " AND U.sesso=1) AS Maschi"
					);
			if (!result.isBeforeFirst() ) 
	        {    
	        	con.close();
	      	  	return false;
	        } 
			result.next();
			int numMaschi = result.getInt("maschi");
			int numFemmine = partecipanti - numMaschi;
			
			if(partecipanti==0)
			{
				return false;
			}
			else
			{
				percentualeMaschi = (numMaschi * 100) / partecipanti;
				percentualeFemmine = 100.0f - percentualeMaschi;
				totalePartecipanti = partecipanti;
				System.out.println("numero femmine: " + numFemmine);
				System.out.println("numero maschi: " + numMaschi);
				System.out.println("perc femmine: " + percentualeFemmine);
				System.out.println("perc maschi: " + percentualeMaschi);
				
		        con.close();
		        return true;
			}
		} catch (Exception e) {
			Logger.getLogger(ReportAction.class.getName()).log( 
                    Level.SEVERE, null, e);
		}
        return false;        
	}
	

	public boolean creaStatisticheDomande()
	{
		// totalePartecipanti gi� trovato, 
		// cerca per ogni risposta del sondaggio il numero di partecipanti
		ResultSet result;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
	        Connection con = DriverManager.getConnection(LoginController.url, LoginController.user, LoginController.psw);
	        Statement stmt = con.createStatement();
	        
	        
	        // prendi numero di partecipanti al sondaggio	        
	        String query="SELECT COUNT(*) AS numero, S.nome AS nomeSondaggio FROM Risposta R, Domanda D,Sondaggio S "+
					"WHERE R.domandaID_fk=D.domandaID AND D.sondaggioID_fk=S.sondaggioID AND S.sondaggioID=" + this.sondaggioIDReport+";";
	        System.out.println(query);
	    
	        // numero di risposte nel db per quel sondaggio e il nome del sondaggio
			result = stmt.executeQuery(query);
			
			
			
			if (!result.isBeforeFirst() ) 
	        {    
	        	con.close();
	      	  	return false;
	        } 
			result.next();
			int numeroRisposte = result.getInt("numero");			
			nomeSondaggio = result.getString("nomeSondaggio");
			
			
			
			String query2="SELECT DISTINCT R.rispostaID, R.testoRisposta, D.testoDomanda, D.domandaID FROM Risposta R, Domanda D" +
					" WHERE R.domandaID_fk=D.domandaID AND D.sondaggioID_fk=" + sondaggioIDReport +
					" ORDER BY R.rispostaID";
			System.out.println(query2);
			// prendi tutte le id delle risposte e la domanda legata
			result = stmt.executeQuery(query2);
			
			if (!result.isBeforeFirst() ) 
	        {    
	        	con.close();
	      	  	return false;
	        } 
			
			
			
			
			int rispostaID, domandaID, numeroPartecipanti;
			float percentualePartecipanti;
			String testoDomanda = "";
			String testoRisposta="";
			PercentualeData perc;
			while(result.next())
			{
				System.out.println("**********");
				rispostaID = result.getInt("rispostaID");
				testoRisposta = result.getString("testoRisposta");
				testoDomanda = result.getString("testoDomanda");
				domandaID = result.getInt("domandaID");
				
				
				System.out.println("testoDomanda :"+testoDomanda);
				System.out.println("rispostaID :"+rispostaID);
				
				if(percentualiDomande.containsKey(domandaID))
				{
					perc = percentualiDomande.get(domandaID);
				}
				else
				{
					perc = new PercentualeData();
					percentualiDomande.put(domandaID, perc);
				}
				
				String query3="SELECT COUNT(*) AS numero FROM (" +
						"SELECT DISTINCT U.userID FROM Utente U, Partecipazione P " +
						"WHERE U.userID=P.userID_fk AND P.rispostaID_fk=" + rispostaID +
						") AS Partecipanti;";
				System.out.println(query3);
				
				
				// trova il numero di partecipanti a questa risposta
				Statement stmt2= con.createStatement();
				ResultSet result2= stmt2.executeQuery(query3);
				
				
				if (!result2.isBeforeFirst() ) 
		        {    
		        	con.close();
		      	  	return false;
		        } 
				
				result2.next();
				
				
				numeroPartecipanti = result2.getInt("numero");
				
				System.out.println("numeroPartecipanti: "+numeroPartecipanti);
				System.out.println("totalePartecipanti: "+totalePartecipanti);
				
				// calcola percentuale su totale partecipanti
				if(totalePartecipanti==0)
				{
					return false;
				}
				else
				{
					percentualePartecipanti = (numeroPartecipanti * 100.0f) / totalePartecipanti;
					
					// aggiungi percentuale ad array
					perc.setTestoDomanda(testoDomanda);
					perc.getIdRisposte().add(rispostaID);
					perc.getTestoRisposte().add(testoRisposta);
					perc.getPercentuali().add(percentualePartecipanti);
				}
				
			} // while
			
			JSONObject JSONobj = new JSONObject(percentualiDomande);
	        this.jsonQuery = JSONobj.toString();
	        
	        
	        
	        System.out.println("creaStatisticheDomande(): jsonQuery->"+jsonQuery);
			
	        con.close();
	        return true;
		} catch (Exception e) {
			Logger.getLogger(ReportAction.class.getName()).log( 
                    Level.SEVERE, null, e);
		}
        return false;        
	}
	
	
	public static void printMap(Map mp) {
	    Iterator it = mp.entrySet().iterator();
	    while (it.hasNext()) 
	    {
	        Map.Entry pair = (Map.Entry)it.next();
	        System.out.println(pair.getKey() + " = " + pair.getValue());
	        
	        
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	}


	public String getSondaggioIDReport() {
		return sondaggioIDReport;
	}

	public void setSondaggioIDReport(String sondaggioIDReport) {
		this.sondaggioIDReport = sondaggioIDReport;
	}

	@Override
	public void setSession(Map<String, Object> arg0) {
		this.ses = arg0;
		
		if(!startup)
		{
			init();
			startup = true;
		}
	}

	public float getPercentualeMaschi() {
		return percentualeMaschi;
	}

	public void setPercentualeMaschi(float percentualeMaschi) {
		this.percentualeMaschi = percentualeMaschi;
	}

	public float getPercentualeFemmine() {
		return percentualeFemmine;
	}

	public void setPercentualeFemmine(float percentualeFemmine) {
		this.percentualeFemmine = percentualeFemmine;
	}

	public String getNomeSondaggio() {
		return nomeSondaggio;
	}

	public void setNomeSondaggio(String nomeSondaggio) {
		this.nomeSondaggio = nomeSondaggio;
	}

	public int getTotalePartecipanti() {
		return totalePartecipanti;
	}

	public void setTotalePartecipanti(int totalePartecipanti) {
		this.totalePartecipanti = totalePartecipanti;
	}

	public HashMap<Integer, PercentualeData> getPercentualiDomande() {
		return percentualiDomande;
	}

	public void setPercentualiDomande(HashMap<Integer, PercentualeData> percentualiDomande) {
		this.percentualiDomande = percentualiDomande;
	}

	public String getJsonQuery() {
		return jsonQuery;
	}

	public void setJsonQuery(String jsonQuery) {
		this.jsonQuery = jsonQuery;
	}
}
