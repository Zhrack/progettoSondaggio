package com.struts2.progettoSondaggio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

public class UtenteAction extends ActionSupport implements SessionAware{
	
	private Map<String, Object> ses;
	
	private PagamentoController pagamentoController;
	
	private String username;
	
	private ArrayList<SondaggioData> sondaggiDisponibili;
	
	private SondaggioDB sondaggioDB;
	
	public UtenteAction()
	{
		username = "";
	}
	
	public void init()
	{
		pagamentoController = new PagamentoController();
		
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
		
		sondaggioDB = new SondaggioDB(ses);
		sondaggiDisponibili = new ArrayList<SondaggioData>();
	}
	
	public String execute() throws Exception {	
		
		if(pagamentoController.effettuaPagamento(username))
		{
			System.out.println("pagamento true");
			return SUCCESS;
		}
		else return ERROR;
		
	}
	
public String prendiListaSondaggi() throws Exception 
	{	
		sondaggiDisponibili.clear();
		return sondaggioDB.prendiListaSondaggi(sondaggiDisponibili);
	}
	
	public String logout() throws Exception
	{
		return SUCCESS;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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
		
		if(username.equals(""))
		{
			init();
		}
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

}
