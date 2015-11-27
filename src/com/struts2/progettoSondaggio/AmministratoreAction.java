package com.struts2.progettoSondaggio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

public class AmministratoreAction extends ActionSupport implements SessionAware{
	
	private Map<String, Object> ses;
	
	private SondaggioDB sondaggioDB;
	
	private String username;
	
	
	private String nomeSondaggio;
	private ArrayList<String> testiDomanda;
	private ArrayList<String> testiRisposta;

	public AmministratoreAction() throws Exception
	{
		username = (String)this.ses.get("username");
		System.out.println("Session username: " + username);
		
		String userID = userIDFromNickname(username);
		if(userID != null)
		{
			System.out.println("Session userID: " + userID);
			this.ses.put("userID", userID);
		}
		else
		{
			System.out.println("Errore session userID");
		}
		sondaggioDB = new SondaggioDB(this, testiDomanda, testiRisposta);
	}
	
	public String creaSondaggio() throws Exception
	{
		return sondaggioDB.creaSondaggio(nomeSondaggio);
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

	public ArrayList<String> getTestiRisposta() {
		return testiRisposta;
	}

	public void setTestiRisposta(ArrayList<String> testiRisposta) {
		this.testiRisposta = testiRisposta;
	}
}
