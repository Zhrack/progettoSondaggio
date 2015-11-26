package com.struts2.progettoSondaggio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.struts2.progettoSondaggio.VisitatoreAction.LoginData;

public class LoginController {
	static final String url = "jdbc:mysql://localhost:3306/sondaggioDB";
	static final String user = "root";
	static final String psw = user;

	public LoginController()
	{
		
	}
	
	public boolean login(LoginData data)
	{
		// Check username e password sul DB
	      try {
	    	  System.out.println("username: " + data.username);
	    	  System.out.println("password: " + data.password);
	          Class.forName("com.mysql.jdbc.Driver").newInstance();
	          Connection con = DriverManager.getConnection(url, user, psw);

	          Statement stmt = con.createStatement();

	          ResultSet result = stmt.executeQuery("select * from utente WHERE nickname='" + data.username + "'");
	          if (!result.isBeforeFirst() ) 
	          {    
	        	  return false;
	          } 
	          result.next();
	          System.out.println("Pass DB: " + result.getString("pswrd"));
	          if(result.getString("pswrd").equals(data.password))
	          {
	        	  if(result.getString("dataPagamento") != null)
	        	  {
	        		  System.out.println("amministratore");
	        		  data.isAmministratore = true;
	        	  }
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
	
	public boolean registrazione(String username, String password, String nome, String cognome, String nascita)
	{
		// Aggiungi nuovo utente
		try {

	          String insert = "INSERT INTO utente(nickname, pswrd, nome, cognome, nascita, dataPagamento)" +
	                  "VALUES (?, ?, ?, ?, ?, null)";

	          Class.forName("com.mysql.jdbc.Driver").newInstance();
	          Connection con = DriverManager.getConnection(url, user, psw);
	 
	          PreparedStatement ps = con.prepareStatement(insert);
	          
	          ps.setString(1, username);
	          ps.setString(2, password);
	          ps.setString(3, nome);
	          ps.setString(4, cognome);
	          ps.setString(5, nascita);
	          ps.executeUpdate();
	          con.close();
	          return true;
	      } catch (Exception ex) {
	          Logger.getLogger(LoginController.class.getName()).log(
	                           Level.SEVERE, null, ex);
	      }
		
		return false;
	}
}
