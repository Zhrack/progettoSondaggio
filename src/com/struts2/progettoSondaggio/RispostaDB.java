package com.struts2.progettoSondaggio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class RispostaDB {

	
	public String aggiungiRisposta(String testo, String domandaID) throws Exception
	{
		Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection con = DriverManager.getConnection(LoginController.url, LoginController.user, LoginController.psw);


        PreparedStatement ps = con.prepareStatement("INSERT INTO Domanda(testoDomanda, sondaggioID_fk) VALUES ('" + testo + "', " +
        																											domandaID + ")");
        ps.executeUpdate();

        // trova id della domanda
        Statement stmt = con.createStatement();

        ResultSet result = stmt.executeQuery("SELECT MAX(rispostaID) AS rispostaID FROM Risposta WHERE domandaID_fk=" + domandaID);
        if (!result.isBeforeFirst() ) 
        {    
        	con.close();
      	  	return null;
        } 
        result.next();
        
        String rispostaID = result.getString("rispostaID");
        con.close();
        
        return rispostaID;
	}
}
