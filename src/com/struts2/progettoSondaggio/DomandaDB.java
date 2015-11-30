package com.struts2.progettoSondaggio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DomandaDB {
	private RispostaDB rispostaDB;
	
	public DomandaDB()
	{
		rispostaDB = new RispostaDB();
	}
	
	public boolean aggiungiDomanda(String testo, String sondaggioID, ElencoRisposte testiRisposta, int index) throws Exception
	{
		Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection con = DriverManager.getConnection(LoginController.url, LoginController.user, LoginController.psw);


        PreparedStatement ps = con.prepareStatement("INSERT INTO Domanda(testoDomanda, sondaggioID_fk) VALUES ('" + testo + "', " +
        																											sondaggioID + ")");
        ps.executeUpdate();

        // trova id della domanda
        Statement stmt = con.createStatement();

        ResultSet result = stmt.executeQuery("SELECT MAX(domandaID) AS domandaID FROM Domanda WHERE sondaggioID_fk=" + sondaggioID);
        if (!result.isBeforeFirst() ) 
        {    
        	con.close();
      	  	return false;
        } 
        result.next();
        
        String domandaID = result.getString("domandaID");
        con.close();
        boolean errore = false;
        //scorri risposte di questa domanda
        ArrayList<String> risposte = testiRisposta.getRisposte();
		for(int j = 0; j < risposte.size(); ++j)
		{
			if(!rispostaDB.aggiungiRisposta(risposte.get(j), domandaID))
			{
				System.out.println("Errore rispostaID: " + j);
				errore = true;
			}
		}
        
        return !errore;
	}
}
