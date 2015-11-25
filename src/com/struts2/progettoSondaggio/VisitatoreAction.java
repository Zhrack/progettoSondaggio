package com.struts2.progettoSondaggio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.opensymphony.xwork2.ActionSupport;


public class VisitatoreAction extends ActionSupport{
	private static final String name1 = "Davide";
	private static final String name2 = "Andrea";
	private static final String name3 = "Matteo";
	private static final String name4 = "Simone";
	
	private static final String psw1 = "davide";
	private static final String psw2 = "andrea";
	private static final String psw3 = "matteo";
	private static final String psw4 = "simone";
	
	  static final String url = "jdbc:mysql://localhost:3306/sondaggioDB";
	  static final String user = "root";
	  static final String psw = user;

	
	List<UtenteDB> list;
		
	public VisitatoreAction()
	{
		list = new ArrayList<UtenteDB>();
	}

	public String execute() throws Exception {		
	
	      try {

	          Class.forName("com.mysql.jdbc.Driver").newInstance();
	          Connection con = DriverManager.getConnection(url, user, psw);

	          Statement stmt = con.createStatement();

	          ResultSet result = stmt.executeQuery("SELECT * FROM utente");
	          
	          while(result.next())
	          {
	        	 list.add(new UtenteDB(result.getString("userID"), result.getString("nickname"), result.getString("pswrd")
	        			 , result.getString("nome"), result.getString("cognome")));
	          } 

	          con.close();

	      } catch (Exception ex) {
	          Logger.getLogger(VisitatoreAction.class.getName()).log( 
	                           Level.SEVERE, null, ex);
	      }
	          return SUCCESS;
			
	}
	
	public List<UtenteDB> getList() {
		return this.list;
	}

	public void setList(List<UtenteDB> list) {
		this.list = list;
	}
	
}
