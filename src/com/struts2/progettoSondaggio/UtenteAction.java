package com.struts2.progettoSondaggio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;

import com.mysql.jdbc.ResultSetMetaData;
import com.opensymphony.xwork2.ActionSupport;

public class UtenteAction extends ActionSupport{
	static final String url = "jdbc:mysql://localhost:3306/sondaggioDB";
	static final String user = "root";
	static final String psw = user;
	public String resultQuery;

	
	

	public String getResultQuery() {
		return resultQuery;
	}


	public void setResultQuery(String resultQuery) {
		this.resultQuery = resultQuery;
	}

	private PagamentoController pagamentoController;
	
	private String username;
	
	public UtenteAction()
	{
		pagamentoController = new PagamentoController();
	}
	
	
	public String getSurvey() throws Exception {	
		
		
		System.out.println("adaaad");
		
		
		this.requestSurvey();
		 
		
		
		return SUCCESS;
	}
	
	
	
	
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
	          
	          
	          ArrayList<String> arrayList = new ArrayList<String>(); 
	          
	          
	          
	          ResultSetMetaData metadata = (ResultSetMetaData) result.getMetaData();
	          int numberOfColumns = metadata.getColumnCount();
	          System.out.println("numberOfColumns="+numberOfColumns);
	          
	          while (result.next()) {              
	                  int i = 1;
	                  while(i <= numberOfColumns) {
	                      arrayList.add(result.getString(i++));
	                  }
	                  System.out.println(result.getString("sondaggioID"));
	                  System.out.println(result.getString("nome"));
	                  System.out.println("i="+i);
	          }
	          
	          
	          
	          System.out.println("Braaaaaaaaaaa");
	          System.out.println(arrayList);
	          JSONArray ar=new JSONArray(arrayList);
	          System.out.println(ar);
	          System.out.println(34444);
	          
	          
	          if(result.getFetchSize()>0)
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
	
	
}
