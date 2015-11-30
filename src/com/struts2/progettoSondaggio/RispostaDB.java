package com.struts2.progettoSondaggio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;

public class RispostaDB {

	private PartecipazioneDB partecipazioneDB;
	
	private Map<String, Object> ses;
	
	public RispostaDB(Map<String, Object> ses)
	{
		partecipazioneDB = new PartecipazioneDB(ses);
	}
	
	public boolean aggiungiRisposta(String testo, String domandaID) throws Exception
	{
		System.out.println("aggiungiRisposta: " + testo + " " + domandaID);
		Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection con = DriverManager.getConnection(LoginController.url, LoginController.user, LoginController.psw);


        PreparedStatement ps = con.prepareStatement("INSERT INTO Risposta(testoRisposta, domandaID_fk) VALUES ('" + testo + "', " +
        																											domandaID + ")");
        ps.executeUpdate();

        // trova id della domanda
        Statement stmt = con.createStatement();
        
        ResultSet result = stmt.executeQuery("SELECT MAX(rispostaID) AS rispostaID FROM Risposta WHERE domandaID_fk=" + domandaID);
        if (!result.isBeforeFirst() ) 
        {    
        	con.close();
      	  	return false;
        } 
        result.next();
        boolean errore = false;
        String rispostaID = result.getString("rispostaID");
        if(rispostaID == null || !partecipazioneDB.aggiungiPartecipazione(rispostaID))
        {
        	errore = true;
        }
        con.close();
        
        return !errore;
	}
}
