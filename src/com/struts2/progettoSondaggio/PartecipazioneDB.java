package com.struts2.progettoSondaggio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

public class PartecipazioneDB{
	
	private Map<String, Object> ses;
	
	public PartecipazioneDB(Map<String, Object> ses)
	{
		this.ses = ses;
	}
	
	public boolean aggiungiPartecipazione(String rispostaID) throws Exception
	{
		Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection con = DriverManager.getConnection(LoginController.url, LoginController.user, LoginController.psw);

        String userID = (String)ses.get("userID");
        PreparedStatement ps = con.prepareStatement("INSERT INTO Partecipazione(userID_fk, rispostaID_fk) VALUES ('" + userID + "', '" +
        																											rispostaID + "')");
        if(ps.executeUpdate() == 0)
        {
        	con.close();
        	return false;
        }
        else
        {
        	con.close();
        	return true;
        }
	}

}
