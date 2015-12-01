package com.struts2.progettoSondaggio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.opensymphony.xwork2.ActionSupport;

public class UtenteAction extends ActionSupport{
	static final String url = "jdbc:mysql://localhost:3306/sondaggioDB";
	static final String user = "root";
	static final String psw = user;
	public String Prova; 

	
	private PagamentoController pagamentoController;
	
	private String username;
	
	public UtenteAction()
	{
		pagamentoController = new PagamentoController();
	}
	
	
	public String getSurvey() throws Exception {	
		
		this.Prova="cacca";
		System.out.println("adaaad");
		this.requestSurvey();
		return SUCCESS;
	}
	
	
	public boolean requestSurvey()
	{
		System.out.println("ijijijijij");
		
		// esegue la query per ottenere i sondaggi da far apparire nell'utenteView
	      try {
	    	  System.out.println("ijijijijij");
	          Class.forName("com.mysql.jdbc.Driver").newInstance();
	          Connection con = DriverManager.getConnection(url, user, psw);
	          Statement stmt = con.createStatement();

	          ResultSet result = stmt.executeQuery("SELECT * FROM sondaggioDB.Sondaggio;");
	          if (!result.isBeforeFirst() ) 
	          {    
	        	  return false;
	          } 
	          result.next();
	          System.out.println("Braaaa: " + result.getString("sondaggioID"));
	          if(Integer.parseInt(result.getString("sondaggioID"))>0)
	          {
	        	  System.out.println("sadasfadf");
	        	  con.close();
	        	  return true;
	          }
	          else 
	          {
	        	  con.close(); 
	        	  return false;
	          }

	      } catch (Exception ex) {
	          Logger.getLogger(LoginController.class.getName()).log( 
	                           Level.SEVERE, null, ex);
	      }
	      return false;
	}
	
	
	/*
	public String execute() throws Exception {	
		
		System.out.println("adaaad");
		if(pagamentoController.effettuaPagamento(username))
		{
			System.out.println("pagamento true");
			return SUCCESS;
		}
		else return ERROR;
		
	}
	*/
	
	
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
	
	 public String getProva() {
	        return this.Prova;
	    }
	 
	    public void setProva(String prova) {
	        this.Prova = prova;
	    }

}
