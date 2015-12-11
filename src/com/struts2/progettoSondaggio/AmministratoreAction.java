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
	

	
	// dati per creazione sondaggio
	private String nomeSondaggio;
	private ArrayList<String> testiDomanda;
	private ArrayList<ElencoRisposte> testiRisposta;
	//---------

	private String sondaggioIDScelto; // usato anche per cancellare il sondaggio
	private SondaggioData modificaSondaggioData;// possiede i dati del sondaggio aggiornato
	private ArrayList<DomandaData> modificaDomandeData;// possiede le domande già presenti, con le modifiche aggiornate
	private ArrayList<RispostaData> modificaRisposteData;// possiede le risposte già presenti, con le modifiche aggiornate
	
	private String nomeSondaggioModifica;
	private ArrayList<String> testiDomandaModifica;
	private ArrayList<ElencoRisposte> testiRispostaModifica;
	//---------
	
	private boolean startup;
	public AmministratoreAction() throws Exception
	{
		System.out.println("Ctor AmmAction");
		startup = false;
	}
	
	public void init()
	{
		String username = (String)this.ses.get("username");
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

		
		testiDomandaModifica = new ArrayList<String>();
		testiRispostaModifica = new ArrayList<ElencoRisposte>();
	}
	
	public String creaSondaggio()
	{
		try
		{
			String result = sondaggioDB.creaSondaggio(nomeSondaggio, testiDomanda, testiRisposta);
			
			return result;
		}
		catch(Exception ex)
		{
			Logger.getLogger(AmministratoreAction.class.getName()).log( 
                    Level.SEVERE, null, ex);
		}
		return ERROR;
	}
	
	public String modificaSondaggio()
	{
		try
		{
			if(sondaggioDB.cancellaSondaggio(sondaggioIDScelto))
			{
				return sondaggioDB.creaSondaggio(nomeSondaggioModifica, testiDomandaModifica, testiRispostaModifica);
			}
			else
			{
				System.out.println("errore cancellaSondaggio");
			}
		}
		catch(Exception e)
		{
			Logger.getLogger(AmministratoreAction.class.getName()).log( 
                    Level.SEVERE, null, e);
		}
		return ERROR;
	}
	
	public String prendiDatiSondaggio()
	{
		try
		{
			modificaSondaggioData.setNomeSondaggio("");
			modificaSondaggioData.setSondaggioID("");
			modificaSondaggioData.setAutore(""); // non usato in questo caso
			modificaDomandeData.clear();
			modificaRisposteData.clear();
			String result = sondaggioDB.prendiDatiSondaggio(sondaggioIDScelto, modificaSondaggioData, modificaDomandeData,  modificaRisposteData);
			
			return result;
		}
		catch(Exception ex)
		{
			Logger.getLogger(AmministratoreAction.class.getName()).log( 
                    Level.SEVERE, null, ex);
		}
		return ERROR;
	}
	
	public String cancellaSondaggio()
	{
		try
		{
			if(sondaggioDB.cancellaSondaggio(sondaggioIDScelto))
			{
				return SUCCESS;
			}
			else
			{
				System.out.println("errore cancellaSondaggio");
				return ERROR;
			}
		}
		catch(Exception e)
		{
			Logger.getLogger(AmministratoreAction.class.getName()).log( 
                    Level.SEVERE, null, e);
		}
		return ERROR;
	}
//	
//	public String modificaSondaggio()
//	{
//		try
//		{
//			String result = sondaggioDB.applicaModificaSondaggio(
//					modificaSondaggioData, modificaDomandeData, testiNuoveDomandeModifica, 
//					modificaRisposteData, testiNuoveRisposteModifica);
//			
//			return result;
//		}
//		catch(Exception e)
//		{
//			Logger.getLogger(AmministratoreAction.class.getName()).log( 
//                    Level.SEVERE, null, e);
//		}
//		return ERROR;
//	}
	
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

        ResultSet result = stmt.executeQuery("SELECT userID FROM utente WHERE nickname='" + username + "'");
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
		
		if(!startup)
		{
			startup = true;
			init();
		}
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

	public String getSondaggioIDScelto() {
		return sondaggioIDScelto;
	}

	public void setSondaggioIDScelto(String sondaggioIDScelto) {
		this.sondaggioIDScelto = sondaggioIDScelto;
	}

	public String getNomeSondaggioModifica() {
		return nomeSondaggioModifica;
	}

	public void setNomeSondaggioModifica(String nomeSondaggioModifica) {
		this.nomeSondaggioModifica = nomeSondaggioModifica;
	}

	public ArrayList<String> getTestiDomandaModifica() {
		return testiDomandaModifica;
	}

	public void setTestiDomandaModifica(ArrayList<String> testiDomandaModifica) {
		this.testiDomandaModifica = testiDomandaModifica;
	}

	public ArrayList<ElencoRisposte> getTestiRispostaModifica() {
		return testiRispostaModifica;
	}

	public void setTestiRispostaModifica(ArrayList<ElencoRisposte> testiRispostaModifica) {
		this.testiRispostaModifica = testiRispostaModifica;
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

}
