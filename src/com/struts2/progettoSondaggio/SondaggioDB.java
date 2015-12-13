package com.struts2.progettoSondaggio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SondaggioDB {
	
	private AmministratoreAction amm;
	private DomandaDB domandaDB;
	
//	private ArrayList<String> testiDomanda;
//	private ArrayList<ElencoRisposte> testiRisposta;
	
	private Map<String, Object> ses;
	
	public SondaggioDB(Map<String, Object> ses)
	{
		this.amm = null;
		
		this.ses = ses;
		domandaDB =  new DomandaDB(ses);
	}

	public SondaggioDB(AmministratoreAction amm, ArrayList<String> testiDomanda, ArrayList<ElencoRisposte> testiRisposta, Map<String, Object> ses)
	{
		this.amm = amm;
		
		this.ses = ses;
		domandaDB = new DomandaDB(ses);
	}
	
	public String creaSondaggio(String nomeSondaggio, ArrayList<String> testiDomanda, ArrayList<ElencoRisposte> testiRisposta) throws Exception
	{
		// Aggiungi sondaggio a DB
		String sondaggioID = aggiungiSondaggioDB(nomeSondaggio, (String)amm.getSession().get("userID"));
		boolean errore = false;
		// Crea domanda
		if(sondaggioID != null)
		{
			System.out.println("sondaggioID:" + sondaggioID);
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
			errore = true;
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
	
	// amministratore ha cliccato su "modifica" di un sondaggio, raccogli i dati del sondaggio
	public String prendiDatiSondaggio(String sondaggioID, SondaggioData sondaggioData, ArrayList<DomandaData> domandeData, ArrayList<RispostaData> risposteData) throws Exception
	{
		// aggiorna sondaggioData
		sondaggioData.setSondaggioID(sondaggioID);
		
		Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection con = DriverManager.getConnection(LoginController.url, LoginController.user, LoginController.psw);
        
        Statement stmt = con.createStatement();

        ResultSet result = stmt.executeQuery("SELECT nome FROM sondaggio WHERE sondaggioID=" + sondaggioID);
        if (!result.isBeforeFirst() ) 
        {    
        	con.close();
      	  	return "error";
        } 
        result.next();
        
        String nome = result.getString("nome");
        sondaggioData.setNomeSondaggio(nome);
        
        con.close();
        
        // aggiorna domandaData
        String res = domandaDB.prendiDatiDomande(sondaggioID, domandeData, risposteData);
        
        return res;
	}
	
//	public String applicaModificaSondaggio(
//			SondaggioData sondaggioData,
//			ArrayList<DomandaData> domandeModifcate, ArrayList<String> domandeAggiunte,
//			ArrayList<RispostaData> risposteModificate, ArrayList<ElencoRisposte> risposteAggiunte
//			) throws Exception
//	{
//		Class.forName("com.mysql.jdbc.Driver").newInstance();
//        Connection con = DriverManager.getConnection(LoginController.url, LoginController.user, LoginController.psw);
//        
//        // aggiorna sondaggio
//        PreparedStatement ps = con.prepareStatement(
//        		"UPDATE Sondaggio SET nome='" + sondaggioData.getNomeSondaggio() + 
//        		"' WHERE sondaggioID=" + sondaggioData.getSondaggioID()
//        		);
//        
//		if(ps.executeUpdate() == 0)
//		{
//			con.close();
//			return "error";
//		}
//		con.close();
//		// aggiorna domande
//		String res = domandaDB.applicaModificaDomande(sondaggioData, domandeModifcate, domandeAggiunte, risposteModificate, risposteAggiunte);
//
//		return res;
//	}
	
	public boolean cancellaSondaggio(String sondaggioID) throws Exception
	{
		Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection con = DriverManager.getConnection(LoginController.url, LoginController.user, LoginController.psw);
        
        // cancella tutte le partecipazioni di questo sondaggio
        PreparedStatement ps = con.prepareStatement(
        		"DELETE FROM Partecipazione WHERE rispostaID_fk IN " +
	"(SELECT rispostaID FROM Risposta WHERE domandaID_fk IN " +
		"(SELECT domandaID FROM Domanda WHERE sondaggioID_fk=" + sondaggioID + "))");
        if(ps.executeUpdate() == 0)
		{
			con.close();
			return false;
		}
        
        // cancella tutte le risposte di questo sondaggio
        ps = con.prepareStatement(
        		"DELETE FROM Risposta WHERE domandaID_fk IN " +
        		"(SELECT domandaID FROM Domanda WHERE sondaggioID_fk=" + sondaggioID + ")");
        if(ps.executeUpdate() == 0)
		{
			con.close();
			return false;
		}
        
        // cancella sondaggio
        ps = con.prepareStatement(
        		"DELETE FROM Sondaggio WHERE sondaggioID=" + sondaggioID);
        if(ps.executeUpdate() == 0)
		{
			con.close();
			return false;
		}
        
		con.close();
        
        return true;
	}
	
	public String prendiListaSondaggi(ArrayList<SondaggioData> sondaggi) throws Exception
	{
		Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection con = DriverManager.getConnection(LoginController.url, LoginController.user, LoginController.psw);
        
        Statement stmt = con.createStatement();

        ResultSet result = stmt.executeQuery("SELECT S.sondaggioID, S.nome, U.nickname FROM Sondaggio S, Utente U WHERE S.amministratore_fk=U.userID");
        if (!result.isBeforeFirst() ) 
        {    
        	con.close();
      	  	return "error";
        } 
        
        while(result.next())
        {
        	SondaggioData data = new SondaggioData();
        	
        	data.setSondaggioID(result.getString("sondaggioID"));
        	data.setNomeSondaggio(result.getString("nome"));
        	data.setAutore(result.getString("nickname"));
        	sondaggi.add(data);
        }
        con.close();
        
        return "success";
	}
	
	public String prendiInfoSondaggio(
			String sondaggioID, String nomeSondaggio, String autoreSondaggio, 
			ArrayList<String> testiDomanda, ArrayList<String> domandaID,
			ArrayList<RispostaData> testiRisposta, ArrayList<String> testiRispostaStringa) throws Exception 
	{	
		
		
		Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection con = DriverManager.getConnection(LoginController.url, LoginController.user, LoginController.psw);
        Statement stmt = con.createStatement();    
        ResultSet result = stmt.executeQuery("SELECT S.nome, U.nickname FROM Sondaggio S, Utente U WHERE S.amministratore_fk=U.userID" +
        		" AND S.sondaggioID=" + sondaggioID);
        
        if (!result.isBeforeFirst() ) 
        {
        	con.close();
      	  	return "error";
        } 
        result.next();
        nomeSondaggio = result.getString("nome");
        autoreSondaggio = result.getString("nickname");
        
        
        con.close();
        
        // prendi dati per testiDomanda e testiRisposta
        String res = domandaDB.prendiInfoSondaggio(sondaggioID, testiDomanda,domandaID, testiRisposta,testiRispostaStringa);
        
        return res;
	}
	
	public String prendiListaSondaggiAmministratore(ArrayList<SondaggioData> listaSondaggiAmministratore) throws Exception
	{
		Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection con = DriverManager.getConnection(LoginController.url, LoginController.user, LoginController.psw);
        Statement stmt = con.createStatement();    
        ResultSet result = stmt.executeQuery("SELECT * FROM Sondaggio WHERE amministratore_fk=" + (String)this.ses.get("userID"));
        
        if (!result.isBeforeFirst() ) 
        {
        	con.close();
      	  	return "error";
        } 
        while(result.next())
        {
        	SondaggioData data = new SondaggioData();
        	data.setSondaggioID(result.getString("sondaggioID"));
        	data.setNomeSondaggio(result.getString("nome"));
        	data.setAutore(result.getString("amministratore_fk"));
        	
        	listaSondaggiAmministratore.add(data);
        }
        
        return "success";
	}
}
