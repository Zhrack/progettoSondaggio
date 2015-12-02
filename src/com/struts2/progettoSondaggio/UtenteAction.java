package com.struts2.progettoSondaggio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.struts2.interceptor.SessionAware;
import org.json.*;

import com.mysql.jdbc.ResultSetMetaData;
import com.opensymphony.xwork2.ActionSupport;

public class UtenteAction extends ActionSupport implements SessionAware{
	static final String url = "jdbc:mysql://localhost:3306/sondaggioDB";
	static final String user = "root";
	static final String psw = user;
	public JSONArray resultQuery;
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
	          JSONArray mJSONArray = new JSONArray(Arrays.asList(arrayList));
	          this.resultQuery=mJSONArray;
	          System.out.println("ddddddd");
	          System.out.println(this.resultQuery);
	          
	          con.close(); 
        	  return false;

	      } catch (Exception ex) {
	          Logger.getLogger(LoginController.class.getName()).log( 
	                           Level.SEVERE, null, ex);
	      }
		 
		 
		return false;
	}
	
public String prendiListaSondaggi() throws Exception 
	{	
		sondaggiDisponibili.clear();
		return sondaggioDB.prendiListaSondaggi(sondaggiDisponibili);
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


	public JSONArray getResultQuery() {
		return resultQuery;
	}


	public void setResultQuery(JSONArray resultQuery) {
		this.resultQuery = resultQuery;
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

}
