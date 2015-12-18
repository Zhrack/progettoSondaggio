package com.struts2.progettoSondaggio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

public class PartecipazioneDB{
	
	private Map<String, Object> ses;
	
	public PartecipazioneDB(Map<String, Object> ses)
	{
		this.ses = ses;
	}
	
	public String aggiungiPartecipazione(ArrayList<String> risposte, String userIDAndroid) throws Exception
	{
		String userID = (String)ses.get("userID");
		
		if(userID == null)
		{
			// siamo su Android
			userID = userIDAndroid;
		}
		
		
		Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection con = DriverManager.getConnection(LoginController.url, LoginController.user, LoginController.psw);

        
        for(int i = 0; i < risposte.size(); ++i)
        {
	        PreparedStatement ps = con.prepareStatement("INSERT INTO Partecipazione(userID_fk, rispostaID_fk) VALUES ('" + userID + "', '" +
	        		risposte.get(i) + "')");
	        if(ps.executeUpdate() == 0)
	        {
	        	con.close();
	        	return "error";
	        }
        }
        con.close();
    	return "success";
    	
	}

}
