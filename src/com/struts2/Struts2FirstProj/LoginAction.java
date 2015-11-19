package com.struts2.Struts2FirstProj;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class LoginAction {
	private static final String name1 = "Davide";
	private static final String name2 = "Andrea";
	private static final String name3 = "Matteo";
	private static final String name4 = "Simone";
	
	private static final String psw1 = "davide";
	private static final String psw2 = "andrea";
	private static final String psw3 = "matteo";
	private static final String psw4 = "simone";
	
//	  static final String url = "jdbc:mysql://localhost:3306/world";
//	  static final String user = "root";
//	  static final String psw = user;
	
	private String user;
	private String psw;

	public String execute() throws Exception {		
	
		switch(user)
		{
		case name1:
			if(psw.equals(psw1)) return "success";

			return "failed";
		case name2:
			if(psw.equals(psw2)) return "success";
			
			return "failed";
		case name3:
			if(psw.equals(psw3)) return "success";

			return "failed";
		case name4:
			if(psw.equals(psw4)) return "success";
			
			return "failed";
		default:
			return "failed";
		}
		
//		List<String> list = new ArrayList<String>();
//
//	      try {
//
//	          Class.forName("com.mysql.jdbc.Driver").newInstance();
//	          Connection con = DriverManager.getConnection(url, user, psw);
//
//	          Statement stmt = con.createStatement();
//
//	          ResultSet result = stmt.executeQuery("SELECT * FROM utente");
//	          
//	          while(result.next())
//	          {
//	        	  System.out.println("getBooks(): ID: " + result.getString("id"));
//	             list.add(result.getString("id"));
//	             list.add(result.getString("img"));
//	             list.add(result.getString("titolo"));
//	             list.add(result.getString("autore"));
//	             list.add(result.getString("isbn"));
//	             list.add(result.getString("anno"));
//	             list.add(result.getString("prezzo"));
//	          } 
//
//	          con.close();
//
//	      } catch (Exception ex) {
//	          Logger.getLogger(LoginAction.class.getName()).log( 
//	                           Level.SEVERE, null, ex);
//	      }
//	          return list;
			
	}

	public String getUser() {
		return this.user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
	public String getPsw() {
		return this.psw;
	}

	public void setPsw(String psw) {
		this.psw = psw;
	}
	
}
