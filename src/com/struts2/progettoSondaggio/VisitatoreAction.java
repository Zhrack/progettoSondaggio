package com.struts2.progettoSondaggio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;


public class VisitatoreAction extends ActionSupport implements SessionAware{	
	
	public class LoginData
	{
		public String username;
		public String password;
		public boolean isAmministratore;
		
		public LoginData(String username, String password, boolean isAmministatore)
		{
			this.username = username;
			this.password = password;
			this.isAmministratore = isAmministratore;
		}
	}

	private LoginData loginData;
	private LoginController loginController;
	

	// mi serve per vedere se è amm
	private String isAmministratore;

	
	private String username;
	private String password;
	private String nome;
	private String cognome;
	private String sesso;
	private String messaggioSuccessoPartecipazione="hidden";
	private String messaggioErrorePartecipazione="hidden";
	private String option;
	private String accessoAmministratoreComeUtente="0";
	
	
	// Android output data
	private boolean isMobile = false;
	private boolean loginSuccessful = false;
	private boolean registrazioneSuccessful = false;
	private boolean usernameAvailable = false;
	
	// ----

	private Map<String, Object> ses;
	
	
	// -1 default
	//  1 blocco partecipazione attivo 
	private String bloccoPartecipazione="-1";

		
	public VisitatoreAction()
	{
		loginController = new LoginController();
		loginData = new LoginData("", "", false);
		System.out.println("Visitatore Ctor");
	}

	public String execute() throws Exception {		
		System.out.println("Option selezionata: " + option);
		if(option.equals("login"))
		{
			System.out.println("Dentro login");
			loginData.username = username;
			loginData.password = password;
			loginData.isAmministratore = false;
			
			
			
			if(loginController.login(loginData))
			{
				System.out.println("Login OK " + loginData.isAmministratore);
				System.out.println("accessoAmministratoreComeUtente :" + accessoAmministratoreComeUtente);
				
				ses.put("username", username);
				ses.put("userID", userIDFromNickname(username));
				ses.put("password", password);
				ses.put("isMobile", isMobile);
				ses.put("isAmministratore", (loginData.isAmministratore)? "1" : "0");
				loginSuccessful = true;
				
				if(loginData.isAmministratore && this.accessoAmministratoreComeUtente.equals("0"))
				{					
					System.out.println("Session username: " + (String)this.ses.get("username"));
					return "amministratoreSuccess";
				}
				else 
				{
					return "utenteSuccess";
				}
			}
			else 
			{
				loginSuccessful = false;
				return "loginFallito";
			}
		}
		else if(option.equals("registrazione"))
		{
			System.out.println("Dentro registrazione");
			// controllo se i campi della registrazione non contengono valori strani
			if(!(loginController.checkFieldsRegistration(username, password, nome, cognome, sesso)))
			{
				registrazioneSuccessful = false;
				return "erroreCampiRegistrazione";
			}
		
			
			// query per la registrazione
			if(loginController.registrazione(username, password, nome, cognome, sesso))
			{
				ses.put("username", username);
				ses.put("userID", userIDFromNickname(username));
				System.out.println("Reg OK");
				registrazioneSuccessful = true;
				return SUCCESS;
			}
			else 
			{
				registrazioneSuccessful = false;
				return "registrazioneFallita";
			}
		
		}
		else
		{
			System.out.println("Failed: " + option);
			return "loginFallito";
		}
			
	}

	public String executeAndroid() {		
		System.out.println("Android: " + option);
		isMobile = true;
		try {
			execute();
		} catch (Exception e) {
			Logger.getLogger(VisitatoreAction.class.getName()).log( 
                    Level.SEVERE, null, e);
		}
		
		return SUCCESS;
	}
	
	public String checkUsernameAndroid() {		
		System.out.println("checkUsernameAndroid");
		isMobile = true;
		try {
			usernameAvailable = loginController.checkUsernameInUso(username);
		} catch (Exception e) {
			Logger.getLogger(VisitatoreAction.class.getName()).log( 
                    Level.SEVERE, null, e);
		}
		
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
	
	public String successoPartecipazione()
	{
		this.messaggioSuccessoPartecipazione="visible";
		return "success";
	}

	public String errorePartecipazione()
	{
		this.messaggioErrorePartecipazione="visible";
		return "success";
	}
	
	
	public String bloccaPartecipazione()
	{
		this.setBloccoPartecipazione("1");
		return "success";
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	
	@Override 
	public void setSession(Map<String, Object> arg0) {
		this.ses = arg0;
	}
	
	public Map<String, Object> getSession() {
		return this.ses;
	}

	public String getMessaggioSuccessoPartecipazione() {
		return messaggioSuccessoPartecipazione;
	}

	public void setMessaggioSuccessoPartecipazione(String messaggioSuccessoPartecipazione) {
		this.messaggioSuccessoPartecipazione = messaggioSuccessoPartecipazione;
	}

	public String getMessaggioErrorePartecipazione() {
		return messaggioErrorePartecipazione;
	}

	public void setMessaggioErrorePartecipazione(String messaggioErrorePartecipazione) {
		this.messaggioErrorePartecipazione = messaggioErrorePartecipazione;
	}

	public String getAccessoAmministratoreComeUtente() {
		return accessoAmministratoreComeUtente;
	}

	public void setAccessoAmministratoreComeUtente(String accessoAmministratoreComeUtente) {
		this.accessoAmministratoreComeUtente = accessoAmministratoreComeUtente;
	}

	public boolean isLoginSuccessful() {
		return loginSuccessful;
	}

	public void setLoginSuccessful(boolean loginSuccessful) {
		this.loginSuccessful = loginSuccessful;
	}

	public boolean isUsernameAvailable() {
		return usernameAvailable;
	}

	public void setUsernameAvailable(boolean isUsernameAvailable) {
		this.usernameAvailable = isUsernameAvailable;
	}

	public boolean isRegistrazioneSuccessful() {
		return registrazioneSuccessful;
	}

	public void setRegistrazioneSuccessful(boolean registrazioneSuccessful) {
		this.registrazioneSuccessful = registrazioneSuccessful;
	}

	public boolean isMobile() {
		return isMobile;
	}

	public void setMobile(boolean isMobile) {
		this.isMobile = isMobile;
	}
	
	

	public String getIsAmministratore() {
		return (String) this.ses.get("isAmministratore");
	}


	public void setIsAmministratore(String isAmministratore) {
		
		this.ses.put("isAmministratore",isAmministratore);
	}

	public String getBloccoPartecipazione() {
		return bloccoPartecipazione;
	}

	public void setBloccoPartecipazione(String bloccoPartecipazione) {
		System.out.println("asdasdasdasdasdasdsa");
		this.bloccoPartecipazione = bloccoPartecipazione;
	}

	

}
