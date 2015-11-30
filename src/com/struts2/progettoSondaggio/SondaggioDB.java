package com.struts2.progettoSondaggio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

public class SondaggioDB {
	
	private AmministratoreAction amm;
	private DomandaDB domandaDB;
	
	private ArrayList<String> testiDomanda;
	private ArrayList<ElencoRisposte> testiRisposta;
	
	private Map<String, Object> ses;

	public SondaggioDB(AmministratoreAction amm, ArrayList<String> testiDomanda, ArrayList<ElencoRisposte> testiRisposta, Map<String, Object> ses)
	{
		this.amm = amm;
		this.testiDomanda = testiDomanda;
		this.testiRisposta = testiRisposta;
		
		domandaDB = new DomandaDB(ses);
	}
	
	public String creaSondaggio(String nomeSondaggio) throws Exception
	{
		// Aggiungi sondaggio a DB
		String sondaggioID = aggiungiSondaggioDB(nomeSondaggio, (String)amm.getSession().get("userID"));
		boolean errore = false;
		// Crea domanda
		if(sondaggioID != null)
		{
			System.out.println("sondaggioID " + sondaggioID);
			//scorri domande
			for(int i = 0; i < testiDomanda.size(); ++i)
			{
				System.out.println("testiDomanda[" + i + "]: " + testiDomanda.get(i));
				if(!domandaDB.aggiungiDomanda(testiDomanda.get(i), sondaggioID, testiRisposta.get(i), i))
				{
					System.out.println("Errore domandaID: " + i);
					errore = true;
				}				
			}
		}
		else
		{
			System.out.println("sondaggioID == null");
		}
		if(!errore)
		{
			return "success";
		}
		else
		{
			return "error";
		}
	}
	
	private String aggiungiSondaggioDB(String nomeSondaggio, String userID) throws Exception
	{
		Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection con = DriverManager.getConnection(LoginController.url, LoginController.user, LoginController.psw);


        PreparedStatement ps = con.prepareStatement("INSERT INTO Sondaggio(nome, amministratore_fk) VALUES ('" + nomeSondaggio + "', " +
        																							(String)amm.getSession().get("userID") + ")");
        if(ps.executeUpdate() == 0)
        {
        	con.close();
        	return null;
        }

        Statement stmt = con.createStatement();

        ResultSet result = stmt.executeQuery("SELECT MAX(sondaggioID) AS sondaggioID FROM sondaggio WHERE amministratore_fk=" + userID);
        if (!result.isBeforeFirst() ) 
        {    
        	con.close();
      	  	return null;
        } 
        result.next();
        
        String sondaggioID = result.getString("sondaggioID");
        con.close();
        
        return sondaggioID;
	}
}
