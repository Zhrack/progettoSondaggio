package com.struts2.progettoSondaggio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class AmministratoreAction extends ActionSupport implements SessionAware, ModelDriven<SondaggioDB>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Map<String, Object> ses;
	
	private SondaggioDB sondaggioDB;
	
	private String username;
	
	// dati per creazione sondaggio
	private String nomeSondaggio;
	private ArrayList<String> testiDomanda;
	private ArrayList<ElencoRisposte> testiRisposta;
	
	// dati per modifica sondaggio
	private String sondaggioIDScelto;
	
	private SondaggioData modificaSondaggioData;
	private ArrayList<DomandaData> modificaDomandeData;
	private ArrayList<RispostaData> modificaRisposteData;

	public AmministratoreAction() throws Exception
	{
		System.out.println("Ctor AmmAction");
		username = "";
	}
	
	public void init()
	{
		username = (String)this.ses.get("username");
		System.out.println("Session username: " + username);
		
		String userID;
		try {
			userID = userIDFromNickname(username);
			if(userID != null)
			{
				System.out.println("Session userID: " + userID);
				this.ses.put("userID", userID);
			}
			else
			{
				System.out.println("Errore session userID");
			}
		} catch (Exception ex) {
			Logger.getLogger(LoginController.class.getName()).log( 
                    Level.SEVERE, null, ex);
		}
		
		// inizializzo per creazione sondaggio
		testiDomanda = new ArrayList<String>();
		testiRisposta = new ArrayList<ElencoRisposte>();
		sondaggioDB = new SondaggioDB(this, testiDomanda, testiRisposta, ses);
		
		// inizializzo per modifica sondaggio
		modificaDomandeData = new ArrayList<DomandaData>();
		modificaRisposteData = new ArrayList<RispostaData>();
	}
	
	public String creaSondaggio()
	{
		try
		{
			String result = sondaggioDB.creaSondaggio(nomeSondaggio);
			
			return result;
		}
		catch(Exception ex)
		{
			Logger.getLogger(LoginController.class.getName()).log( 
                    Level.SEVERE, null, ex);
		}
		return ERROR;
	}
	
	public String prendiDatiSondaggio()
	{
		try
		{
			modificaSondaggioData.setNomeSondaggio("");
			modificaSondaggioData.setSondaggioID("");
			modificaDomandeData.clear();
			modificaRisposteData.clear();
			String result = sondaggioDB.prendiDatiSondaggio(sondaggioIDScelto, modificaSondaggioData, modificaDomandeData, modificaRisposteData);
			
			return result;
		}
		catch(Exception ex)
		{
			Logger.getLogger(LoginController.class.getName()).log( 
                    Level.SEVERE, null, ex);
		}
		return ERROR;
	}
	
	public String modificaSondaggio()
	{
		try
		{
			String result = sondaggioDB.applicaModificaSondaggio(modificaSondaggioData, modificaDomandeData, modificaRisposteData);
			
			return result;
		}
		catch(Exception ex)
		{
			Logger.getLogger(LoginController.class.getName()).log( 
                    Level.SEVERE, null, ex);
		}
		return ERROR;
	}
	
	public String toCreaSondaggioView() throws Exception
	{
		return SUCCESS;
	}
	
	public String logout() throws Exception
	{
		this.ses.clear();
		return SUCCESS;
	}
	
	private String userIDFromNickname(String username) throws Exception
	{
		Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection con = DriverManager.getConnection(LoginController.url, LoginController.user, LoginController.psw);

        Statement stmt = con.createStatement();

        ResultSet result = stmt.executeQuery("select userID from utente WHERE nickname='" + username + "'");
        if (!result.isBeforeFirst() ) 
        {    
        	con.close();
      	  	return null;
        } 
        result.next();
        
        String res = result.getString("userID");
        con.close();
        
		return res;
	}
	
	public Map<String, Object> getSession()
	{
		return ses;
	}

	@Override
	public void setSession(Map<String, Object> arg0) {
		this.ses = arg0;
		
		if(username.equals(""))
		{
			init();
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNomeSondaggio() {
		return nomeSondaggio;
	}

	public void setNomeSondaggio(String testoSondaggio) {
		this.nomeSondaggio = testoSondaggio;
	}

	public ArrayList<String> getTestiDomanda() {
		return testiDomanda;
	}

	public void setTestiDomanda(ArrayList<String> testiDomanda) {
		this.testiDomanda = testiDomanda;
	}

	public ArrayList<ElencoRisposte> getTestiRisposta() {
		return testiRisposta;
	}

	public void setTestiRisposta(ArrayList<ElencoRisposte> testiRisposta) {
		this.testiRisposta = testiRisposta;
	}

	@Override
	public SondaggioDB getModel() {
		return sondaggioDB;
	}

	public SondaggioData getModificaSondaggioData() {
		return modificaSondaggioData;
	}

	public void setModificaSondaggioData(SondaggioData modificaSondaggioData) {
		this.modificaSondaggioData = modificaSondaggioData;
	}

	public ArrayList<DomandaData> getModificaDomandeData() {
		return modificaDomandeData;
	}

	public void setModificaDomandeData(ArrayList<DomandaData> modificaDomandeData) {
		this.modificaDomandeData = modificaDomandeData;
	}

	public ArrayList<RispostaData> getModificaRisposteData() {
		return modificaRisposteData;
	}

	public void setModificaRisposteData(ArrayList<RispostaData> modificaRisposteData) {
		this.modificaRisposteData = modificaRisposteData;
	}

	public String getSondaggioIDScelto() {
		return sondaggioIDScelto;
	}

	public void setSondaggioIDScelto(String sondaggioIDScelto) {
		this.sondaggioIDScelto = sondaggioIDScelto;
	}

}
