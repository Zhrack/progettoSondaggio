package com.struts2.progettoSondaggio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.struts2.interceptor.SessionAware;

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
	private HashMap<String, ArrayList<Float>> percentualiDomande;

	private boolean startup;
	public ReportAction()
	{
		startup = false;
	}
	
	public void init()
	{
		setPercentualiDomande(new HashMap<String, ArrayList<Float>>());

	}
	
	public String generaReport()
	{
		if(this.percentualeSesso() && this.creaStatisticheDomande())
			return SUCCESS;
		else
			return ERROR;
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
					"SELECT COUNT(*) AS numero FROM Utente U, Partecipazione P, Risposta R, Domanda D, Sondaggio S" +
					" WHERE U.userID=P.userID_fk AND P.rispostaID_fk=R.rispostaID" +
					" AND R.domandaID_fk=D.domandaID AND D.sondaggioID_fk=S.sondaggioID AND S.sondaggioID=" + sondaggioIDReport
					);
			
			if (!result.isBeforeFirst() ) 
	        {    
	        	con.close();
	      	  	return false;
	        } 
			result.next();
			// prendi numero partecipanti
			int partecipanti = result.getInt("numero");
			
			// prendi numero partecipanti maschili
			result = stmt.executeQuery(
					"SELECT COUNT(*) AS maschi FROM Utente U, Partecipazione P, Risposta R, Domanda D, Sondaggio S" +
					" WHERE U.userID=P.userID_fk AND P.rispostaID_fk=R.rispostaID" +
					" AND R.domandaID_fk=D.domandaID AND D.sondaggioID_fk=S.sondaggioID AND S.sondaggioID=" + sondaggioIDReport +
					" AND U.sesso=1"
					);
			if (!result.isBeforeFirst() ) 
	        {    
	        	con.close();
	      	  	return false;
	        } 
			result.next();
			int numMaschi = result.getInt("maschi");
			int numFemmine = partecipanti - numMaschi;
			
			percentualeMaschi = (numMaschi * 100) / partecipanti;
			percentualeFemmine = 100.0f - percentualeMaschi;
			totalePartecipanti = partecipanti;
			System.out.println("numero femmine: " + numFemmine);
			System.out.println("numero maschi: " + numMaschi);
			System.out.println("perc femmine: " + percentualeFemmine);
			System.out.println("perc maschi: " + percentualeMaschi);
			
	        con.close();
	        return true;
		} catch (Exception e) {
			Logger.getLogger(ReportAction.class.getName()).log( 
                    Level.SEVERE, null, e);
		}
        return false;        
	}
	
	public void creaStatisticheDomande()
	{
		// prendi domande del sondaggio
		
		
		// prendi le loro risposte
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

	public HashMap<String, ArrayList<Float>> getPercentualiDomande() {
		return percentualiDomande;
	}

	public void setPercentualiDomande(HashMap<String, ArrayList<Float>> percentualiDomande) {
		this.percentualiDomande = percentualiDomande;
	}
}
