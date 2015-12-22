package com.struts2.progettoSondaggio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.struts2.interceptor.SessionAware;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
	private String password;

	
	// -1 -> default
	//  0 -> errore
	//  1 -> successo della creazione sondaggio
	private String esitoCreazioneSondaggio="-1";
	
	
	// variabile per utilizzata l'interfaccia grafica
	// 0  -> tutto apposto
	// -1 -> tutto apposto, ma non cè nessun sondaggio 
	// -2 -> errore nella query
	private String esitoPrendiListaSondaggiAmm="0";
	
	// -1 -> default
	//  0 -> errore
	//  1 -> successo della cancellazione
	private String esitoCancSond="-1";
	
	// dati per creazione sondaggio
	private String nomeSondaggio;
	private ArrayList<String> testiDomanda;
	private String oggettoJSON; // usato per passare un'oggetto con tutte le domande e le risposte da salvare del nuovo sondaggio
	private ArrayList<ElencoRisposte> testiRisposta;
	//---------

	
	private String sondaggioIDScelto; // usato anche per cancellare il sondaggio 
	private SondaggioData modificaSondaggioData;// possiede i dati del sondaggio aggiornato
	private ArrayList<DomandaData> modificaDomandeData;// possiede le domande gi� presenti, con le modifiche aggiornate
	private ArrayList<RispostaData> modificaRisposteData;// possiede le risposte gi� presenti, con le modifiche aggiornate
	private String prova;
	
	
	// usate per la modifica sondaggio
	private String sondaggioIDSceltoPerModifica;
	private String nomeSondaggioModifica;
	private ArrayList<String> testiDomandaModifica;
	private ArrayList<ElencoRisposte> testiRispostaModifica;
	//---------
	
	
	// usate per la cancellazione del sondaggio
	private String sondaggioIDSceltoPerCancellazione;
	
	
	// dati per prendere lista sondaggi dato l'id amministratore
	private ArrayList<SondaggioData> listaSondaggiAmministratore;
	
	private boolean startup;
	public AmministratoreAction() throws Exception
	{
		System.out.println("Ctor AmmAction");
		startup = false;
	}
	
	public void init()
	{
		username = (String)this.ses.get("username");
		password = (String)this.ses.get("password");
		System.out.println("Session username: " + username);
		System.out.println("Session password: " + password);
		
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
		
		listaSondaggiAmministratore = new ArrayList<SondaggioData>();
	}
	
	public String creaSondaggio() throws JSONException
	{
		System.out.println("nome sondaggio"+this.nomeSondaggio);
		System.out.println("testiDomanda"+oggettoJSON);
		
		
		// ottengo l'HashMap dalla stringa JSON passata da Javascript
		JSONObject ob=new JSONObject(oggettoJSON);
		HashMap<String, HashMap<String,ArrayList<String>>> hash= (HashMap) this.jsonToMap(ob);
		
	
		
		// ciclo nell'HashMap per settare gli ArrayList:
		// 1 ->this.testiDomanda
		// 2 ->this.testiRisposta
		Iterator entries = hash.entrySet().iterator();
		while (entries.hasNext()) 
		{
		  Entry thisEntry = (Entry) entries.next();
		  String key = thisEntry.getKey().toString();
		  HashMap<String, ArrayList<String>> mappa = (HashMap<String,ArrayList<String>>) thisEntry.getValue();
		  String domanda=mappa.get("domanda").get(0);
		  this.testiDomanda.add(domanda);
		  ArrayList<String> risposte=mappa.get("risposte");
		  ElencoRisposte elencoRisposte=new ElencoRisposte();  
		  elencoRisposte.setRisposte(risposte);
		  this.testiRisposta.add(elencoRisposte);
		}
	
	
		try
		{
			// salvo nel db i dati del nuovo sondaggio ("nomeSondaggio" lo setto dal jsp con struts)
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
	
	public static Map<String, Object> jsonToMap(JSONObject json) throws JSONException {
	    Map<String, Object> retMap = new HashMap<String, Object>();

	    if(json != JSONObject.NULL) {
	        retMap = toMap(json);
	    }
	    return retMap;
	}

	public static Map<String, Object> toMap(JSONObject object) throws JSONException {
	    Map<String, Object> map = new HashMap<String, Object>();

	    Iterator<String> keysItr = object.keys();
	    while(keysItr.hasNext()) {
	        String key = keysItr.next();
	        Object value = object.get(key);

	        if(value instanceof JSONArray) {
	            value = toList((JSONArray) value);
	        }

	        else if(value instanceof JSONObject) {
	            value = toMap((JSONObject) value);
	        }
	        map.put(key, value);
	    }
	    return map;
	}

	public static List<Object> toList(JSONArray array) throws JSONException {
	    List<Object> list = new ArrayList<Object>();
	    for(int i = 0; i < array.length(); i++) {
	        Object value = array.get(i);
	        if(value instanceof JSONArray) {
	            value = toList((JSONArray) value);
	        }

	        else if(value instanceof JSONObject) {
	            value = toMap((JSONObject) value);
	        }
	        list.add(value);
	    }
	    return list;
	}
	
	public String prendiListaSondaggiAmministratore()
	{
		System.out.println("prendiListaSondaggiAmministratore");
		try
		{
			listaSondaggiAmministratore.clear();
			String result = sondaggioDB.prendiListaSondaggiAmministratore(listaSondaggiAmministratore);
			System.out.println("ritorno :"+result);
			
			return ERROR;
		}
		catch(Exception ex)
		{
			this.esitoPrendiListaSondaggiAmm="-2";
			Logger.getLogger(AmministratoreAction.class.getName()).log( 
                    Level.SEVERE, null, ex);
		}
		return ERROR;
	}

	public String modificaSondaggio()
	{
		System.out.println("modificaSondaggio(): id del sondaggio da modificare->"+this.sondaggioIDSceltoPerModifica);
		
		
		try
		{
			if(sondaggioDB.cancellaSondaggio(sondaggioIDSceltoPerModifica))
			{
				System.out.println("cancellazione riuscita");
				
				System.out.println("nome sondaggio"+this.nomeSondaggioModifica);
				System.out.println("testiDomanda"+oggettoJSON);
				
				
				// ottengo l'HashMap dalla stringa JSON passata da Javascript
				JSONObject ob=new JSONObject(oggettoJSON);
				HashMap<String, HashMap<String,ArrayList<String>>> hash= (HashMap) this.jsonToMap(ob);
				
			
				
				// ciclo nell'HashMap per settare gli ArrayList:
				// 1 ->this.testiDomanda
				// 2 ->this.testiRisposta
				Iterator entries = hash.entrySet().iterator();
				while (entries.hasNext()) 
				{
				  Entry thisEntry = (Entry) entries.next();
				  String key = thisEntry.getKey().toString();
				  HashMap<String, ArrayList<String>> mappa = (HashMap<String,ArrayList<String>>) thisEntry.getValue();
				  String domanda=mappa.get("domanda").get(0);
				  this.testiDomandaModifica.add(domanda);
				  ArrayList<String> risposte=mappa.get("risposte");
				  ElencoRisposte elencoRisposte=new ElencoRisposte();  
				  elencoRisposte.setRisposte(risposte);
				  this.testiRispostaModifica.add(elencoRisposte);
				}
			
				
				// ora crea il nuovo sondaggio con le modifiche
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
	
	public String toCreaSondaggioView() throws Exception
	{
		return SUCCESS;
	}

	public String prendiDatiSondaggio()
	{
		System.out.println("modificaSondaggio");
		System.out.println("----"+this.sondaggioIDSceltoPerModifica);
		
		
		try
		{
			modificaSondaggioData=new SondaggioData();
			modificaSondaggioData.setNomeSondaggio("");
			modificaSondaggioData.setSondaggioID("");
			modificaSondaggioData.setAutore(""); // non usato in questo caso
			modificaDomandeData.clear();
			modificaRisposteData.clear();
			String result = sondaggioDB.prendiDatiSondaggio(sondaggioIDSceltoPerModifica, modificaSondaggioData, modificaDomandeData,  modificaRisposteData);
			
			
			
			System.out.println("result:"+result);
			
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
		System.out.println("cancellaSondaggio :"+this.sondaggioIDSceltoPerCancellazione);
		System.out.println("nomeSondaggio da cancellare:"+this.sondaggioIDSceltoPerCancellazione);
		
		try
		{
			if(sondaggioDB.cancellaSondaggio(sondaggioIDSceltoPerCancellazione))
			{
				return "success";
			}
			else
			{
				System.out.println("errore cancellaSondaggio");
				return "error";
			}
		}
		catch(Exception e)
		{
			Logger.getLogger(AmministratoreAction.class.getName()).log( 
                    Level.SEVERE, null, e);
		}
	
		
		return "error";	
	}
	
	public String mostraBoxSuccesso()
	{
		this.esitoCancSond="1";
		System.out.println("this.esitoCancSond:"+this.esitoCancSond);
		this.prendiListaSondaggiAmministratore();
		return "success";
	}
	
	public String mostraBoxErrore()
	{
		this.esitoCancSond="0";
		System.out.println("this.esitoCancSond:"+this.esitoCancSond);
		this.prendiListaSondaggiAmministratore();
		return "success";
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

	public ArrayList<SondaggioData> getListaSondaggiAmministratore() {
		return listaSondaggiAmministratore;
	}

	public void setListaSondaggiAmministratore(
			ArrayList<SondaggioData> listaSondaggiAmministratore) {
		this.listaSondaggiAmministratore = listaSondaggiAmministratore;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOggettoJSON() {
		return oggettoJSON;
	}

	public void setOggettoJSON(String oggettoJSON) {
		this.oggettoJSON = oggettoJSON;
	}

	public String getEsitoCancSond() {
		return esitoCancSond;
	}

	public void setEsitoCancSond(String esitoCancSond) {
		this.esitoCancSond = esitoCancSond;
	}

	public String getSondaggioIDSceltoPerCancellazione() {
		return sondaggioIDSceltoPerCancellazione;
	}

	public void setSondaggioIDSceltoPerCancellazione(String sondaggioIDSceltoPerCancellazione) {
		this.sondaggioIDSceltoPerCancellazione = sondaggioIDSceltoPerCancellazione;
	}

	public String getSondaggioIDSceltoPerModifica() {
		return sondaggioIDSceltoPerModifica;
	}

	public void setSondaggioIDSceltoPerModifica(String sondaggioIDSceltoPerModifica) {
		this.sondaggioIDSceltoPerModifica = sondaggioIDSceltoPerModifica;
	}

	public String getProva() {
		return prova;
	}

	public void setProva(String prova) {
		this.prova = prova;
	}

	public String getEsitoPrendiListaSondaggiAmm() {
		return esitoPrendiListaSondaggiAmm;
	}

	public void setEsitoPrendiListaSondaggiAmm(String esitoPrendiListaSondaggiAmm) {
		this.esitoPrendiListaSondaggiAmm = esitoPrendiListaSondaggiAmm;
	}

	public String getEsitoCreazioneSondaggio() {
		return esitoCreazioneSondaggio;
	}

	public void setEsitoCreazioneSondaggio(String esitoCreazioneSondaggio) {
		this.esitoCreazioneSondaggio = esitoCreazioneSondaggio;
	}


}
