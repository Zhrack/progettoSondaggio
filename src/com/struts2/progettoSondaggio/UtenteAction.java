package com.struts2.progettoSondaggio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.struts2.dispatcher.Dispatcher;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.config.ConfigurationManager;
import com.opensymphony.xwork2.config.entities.Parameterizable;
import org.json.*;

import com.mysql.jdbc.ResultSetMetaData;
import com.opensymphony.xwork2.ActionSupport;

public class UtenteAction extends ActionSupport implements SessionAware,Parameterizable{
	static final String url = "jdbc:mysql://localhost:3306/sondaggioDB";
	static final String user = "root";
	static final String psw = user;
	public String resultQuery;
	private Map<String, Object> ses;
	private PagamentoController pagamentoController;

	// varibili per generare la lista di sondaggi disponibili
	private ArrayList<SondaggioData> sondaggiDisponibili;
	
	private SondaggioDB sondaggioDB;
	private PartecipazioneDB partecipazioneDB;
	
	// variabili usate per partecipare ad un sondaggio
	private String sondaggioIDScelto;
	private String nomeSondaggioUtente;
	private String autoreSondaggioUtente;
	private ArrayList<String> testiDomandaUtente;
	private ArrayList<String> domandaID;
	private ArrayList<RispostaData> testiRispostaUtente;
	private ArrayList<String> testiRispostaUtenteInStringa;
	private String listaRisultatoPartecipazione;
	
	


	private boolean startup;
	
	private String usernameSicurezza;

	public UtenteAction()
	{
		startup = false;
	}
	

	
	
	public void init()
	{
		pagamentoController = new PagamentoController();
		
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
			Logger.getLogger(UtenteAction.class.getName()).log( 
                    Level.SEVERE, null, ex);
		}
		
		testiDomandaUtente = new ArrayList<String>();
		domandaID=new ArrayList<String>();
		testiRispostaUtente = new ArrayList<RispostaData>();
		testiRispostaUtenteInStringa=new ArrayList<String>();
	
		
		sondaggioDB = new SondaggioDB(ses);
		partecipazioneDB = new PartecipazioneDB(ses);
		sondaggiDisponibili = new ArrayList<SondaggioData>();
	}
	
	public String execute() {	
		
		if(pagamentoController.effettuaPagamento((String)this.ses.get("userID"), usernameSicurezza, (String)this.ses.get("username")))
		{
			System.out.println("pagamento true");
			return SUCCESS;
		}
		else return ERROR;
}
	
	public String getSurvey() throws Exception {	
		this.requestSurvey();
		return SUCCESS;
	}
	
	
	
	// metodo per richiedere i sondaggi da far vedere nell'utenteView
	public boolean requestSurvey()
	{
		 try {
	          Class.forName("com.mysql.jdbc.Driver").newInstance();
	          Connection con = DriverManager.getConnection(url, user, psw);
	          Statement stmt = con.createStatement();

	          ResultSet result = stmt.executeQuery("SELECT * FROM sondaggioDB.Sondaggio;");
	          if (!result.isBeforeFirst() ) 
	          {    
	        	  return false;
	          } 
	          
	          
	          // creo l'oggetto JSON con il risultato della query, ovvero i sondaggi
	          ResultSetMetaData metadata = (ResultSetMetaData) result.getMetaData();
	          int numberOfColumns = metadata.getColumnCount();
	          HashMap<Integer,String[]> resultQuery = new HashMap<Integer,String[]>();
	          
	          int i;
              int j;
              int k=0;
              
	          while (result.next()) {              
	                  i=0;
	                  j=1;
	                  String[] sondaggio = new String[3];
	                  
	                  while(i < numberOfColumns) {
	                	 
	                	  sondaggio[i]=result.getString(j);
	                      i++;
	                      j++;
	                  }
	                  
	                  resultQuery.put(k,sondaggio);
	                  k++;
	          }
	          
	          String[] sondaggio2 = new String[3];
	          sondaggio2=resultQuery.get(1);
	          JSONObject JSONobj = new JSONObject(resultQuery);
	          this.resultQuery=JSONobj.toString();
	          
	          
	          con.close(); 
        	  return false;

	      } catch (Exception ex) {
	          Logger.getLogger(LoginController.class.getName()).log( 
	                           Level.SEVERE, null, ex);
	      }
		 
		 
		return false;
	}
	
	public String prendiListaSondaggi() 
	{	
		sondaggiDisponibili.clear();
		try {
			return sondaggioDB.prendiListaSondaggi(sondaggiDisponibili);
		} catch (Exception e) {
			Logger.getLogger(UtenteAction.class.getName()).log( 
                    Level.SEVERE, null, e);
		}
		return "error";
	}
	
	public String prendiListaSondaggiAndroid() 
	{	
		prendiListaSondaggi();
		return SUCCESS;
	}
	
	// prende i dati da DB per partecipare ad un sondaggio
	public String prendiInfoSondaggio() 
	{	
		System.out.println("sondaggioIDScelto:"+this.sondaggioIDScelto);
		System.out.println("autoreSondaggioUtente:"+this.autoreSondaggioUtente);
		System.out.println("nomeSondaggioUtente:"+this.nomeSondaggioUtente);
		
		
		
		testiDomandaUtente.clear();
		testiRispostaUtente.clear();
		testiRispostaUtenteInStringa.clear();
		try {
			return sondaggioDB.prendiInfoSondaggio(sondaggioIDScelto, nomeSondaggioUtente, autoreSondaggioUtente, testiDomandaUtente,domandaID,testiRispostaUtente,testiRispostaUtenteInStringa);
		} catch (Exception e) {
			Logger.getLogger(UtenteAction.class.getName()).log( 
                    Level.SEVERE, null, e);
		}
		return "error";
	}
	
	
	
	//aggiorna la tabella Partecipazione nel DB
	public String aggiungiPartecipazione() throws JSONException 
	{
		System.out.println("listaRisultatoPartecipazione"+listaRisultatoPartecipazione);
		
		
		// se non ha selezionato niente do errore
		if(listaRisultatoPartecipazione.length()==2)
		{
			return "error";
		}
		
		
		// creo l'array delle risposte selezionate parsando la stringa
		listaRisultatoPartecipazione=listaRisultatoPartecipazione.substring(0, listaRisultatoPartecipazione.length()-1);
		listaRisultatoPartecipazione=listaRisultatoPartecipazione.substring(1);
		String strArray[] = listaRisultatoPartecipazione.split(",");
		
		
		// ora dall'array di stringhe appena create mi creo un'arrayList
		ArrayList<String> listdata = new ArrayList<String>();	 
		for( int i = 0; i <= strArray.length - 1; i++)
		 {
			 strArray[i]=strArray[i].substring(0, strArray[i].length()-1);
			 strArray[i]=strArray[i].substring(1);
			 listdata.add(strArray[i]);
		 }
		 
		
		// faccio la query con l'arrayList con le risposte selezionate
		try {
			return partecipazioneDB.aggiungiPartecipazione(listdata);
		} catch (Exception e) {
			Logger.getLogger(UtenteAction.class.getName()).log( 
                    Level.SEVERE, null, e);
		}
		return "error";
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


	public String getResultQuery() {
		return resultQuery;
	}


	public void setResultQuery(String resultQuery) {
		this.resultQuery = resultQuery;
	}
	
	public String logout() throws Exception
	{
		this.ses.clear();
		return SUCCESS;
	}

	public ArrayList<SondaggioData> getSondaggiDisponibili() {
		return sondaggiDisponibili;
	}

	public void setSondaggiDisponibili(ArrayList<SondaggioData> sondaggiDisponibili) {
		this.sondaggiDisponibili = sondaggiDisponibili;
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

	public String getSondaggioIDScelto() {
		return sondaggioIDScelto;
	}

	public void setSondaggioIDScelto(String sondaggioIDScelto) {
		this.sondaggioIDScelto = sondaggioIDScelto;
	}

	public String getNomeSondaggioUtente() {
		return nomeSondaggioUtente;
	}

	public void setNomeSondaggioUtente(String nomeSondaggioUtente) {
		this.nomeSondaggioUtente = nomeSondaggioUtente;
	}

	public String getAutoreSondaggioUtente() {
		return autoreSondaggioUtente;
	}

	public void setAutoreSondaggioUtente(String autoreSondaggioUtente) {
		this.autoreSondaggioUtente = autoreSondaggioUtente;
	}

	public ArrayList<String> getTestiDomandaUtente() {
		return testiDomandaUtente;
	}

	public void setTestiDomandaUtente(ArrayList<String> testiDomandaUtente) {
		this.testiDomandaUtente = testiDomandaUtente;
	}

	public ArrayList<RispostaData> getTestiRispostaUtente() {
		return testiRispostaUtente;
	}

	public void setTestiRispostaUtente(ArrayList<RispostaData> testiRispostaUtente) {
		this.testiRispostaUtente = testiRispostaUtente;
	}

	public String getUsernameSicurezza() {
		return usernameSicurezza;
	}

	public void setUsernameSicurezza(String usernameSicurezza) {
		this.usernameSicurezza = usernameSicurezza;
	}
	

	public ArrayList<String> getTestiRispostaUtenteInStringa() {
		return testiRispostaUtenteInStringa;
	}

	public void setTestiRispostaUtenteInStringa(ArrayList<String> testiRispostaUtenteInStringa) {
		this.testiRispostaUtenteInStringa = testiRispostaUtenteInStringa;
	}

	
	public ArrayList<String> getDomandaID() {
		return domandaID;
	}

	public void setDomandaID(ArrayList<String> domandaID) {
		this.domandaID = domandaID;
	}

	

	public String getListaRisultatoPartecipazione() {
		return listaRisultatoPartecipazione;
	}

	public void setListaRisultatoPartecipazione(String listaRisultatoPartecipazione) {
		this.listaRisultatoPartecipazione = listaRisultatoPartecipazione;
	}
	@Override
	public void addParam(String arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, String> getParams() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setParams(Map<String, String> arg0) {
		// TODO Auto-generated method stub
		
	}




}
