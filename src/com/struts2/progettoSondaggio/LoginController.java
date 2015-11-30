package com.struts2.progettoSondaggio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;


import org.apache.commons.lang.ArrayUtils;

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
	
	public boolean registrazione(String username, String password, String nome, String cognome, String sesso)
	{
		// trasforma il sesso in un valore 0 o 1
		if((Objects.equals("maschio",sesso)))
			sesso="1";
		else if((Objects.equals("femmina",sesso)))
			sesso="0";
		
		// mette la prima lettere maiuscola
		nome = nome.substring(0, 1).toUpperCase() + nome.substring(1);
		cognome = cognome.substring(0, 1).toUpperCase() + cognome.substring(1);
		
		
		
		
		// Aggiungi nuovo utente
		try {

	          String insert = "INSERT INTO utente(nickname, pswrd, nome, cognome, sesso, dataPagamento)" +
	                  "VALUES (?, ?, ?, ?, ?, null)";

	          Class.forName("com.mysql.jdbc.Driver").newInstance();
	          Connection con = DriverManager.getConnection(url, user, psw);
	 
	          PreparedStatement ps = con.prepareStatement(insert);
	          
	          ps.setString(1, username);
	          ps.setString(2, password);
	          ps.setString(3, nome);
	          ps.setString(4, cognome);
	          ps.setString(5, sesso);
	          ps.executeUpdate();
	          con.close();
	          return true;
	      } catch (Exception ex) {
	          Logger.getLogger(LoginController.class.getName()).log(
	                           Level.SEVERE, null, ex);
	      }
		
		return false;
	}
	
	
	
	public boolean checkFieldsRegistration(String username, String password, String nome, String cognome, String sesso)
	{
		String[] valueAlphaNumeric={username, password};
		String[] valueAlpha={nome, cognome};
		String[] both=(String[]) ArrayUtils.addAll(valueAlphaNumeric, valueAlpha);
		boolean allCorrect=true;
		
		// controlla se cè un campo vuoto
		for(int i=0;i<both.length;i++)
		{
			if(!(both[i] != null && !both[i].isEmpty()))
			{
				allCorrect = false;
			}
		}
		
		System.out.println(allCorrect);
		
		// check sui valori alfanumerici
		for(int i=0;i<valueAlphaNumeric.length;i++)
		{
			if(!this.isAlphaNumeric(valueAlphaNumeric[i]))
			{
				allCorrect = false;
			}
		}
		
		
		System.out.println(allCorrect);
		// check sui valori alfabetici
		for(int i=0;i<valueAlpha.length;i++)
		{
			if(!this.isAlpha(valueAlpha[i]))
			{
				allCorrect = false;
			}
		}
		
		System.out.println(allCorrect);
		// check sul sesso
		if(!((Objects.equals("maschio",sesso)) || (Objects.equals("femmina",sesso))))
			{
				allCorrect=false;
			}
		
		System.out.println(allCorrect);
		
		return allCorrect;
	}
	

	
	
	public boolean isAlphaNumeric(String str)
	{
		Pattern p = Pattern.compile("[^a-zA-Z0-9]");
		boolean hasSpecialChar = p.matcher(str).find();
		return !hasSpecialChar;
	}
	
	public boolean isAlpha(String str)
	{
		return str.matches("[a-zA-Z]+");
	}
}
