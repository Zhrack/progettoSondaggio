package com.struts2.progettoSondaggio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
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
        if(ps.executeUpdate() == 0)
        {
        	con.close();
        	return false;
        }
        con.close();
        
        return true;
	}
	
	public String prendiDatiRisposte(String sondaggioID, ArrayList<RispostaData> risposteData) throws Exception
	{
		Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection con = DriverManager.getConnection(LoginController.url, LoginController.user, LoginController.psw);
        
        Statement stmt = con.createStatement();

        ResultSet result = stmt.executeQuery(
        		"SELECT R.rispostaID, R.testoRisposta" +
        		" FROM Risposta R, Domanda D, Sondaggio WHERE sondaggioID=" + sondaggioID + 
        		" AND sondaggioID=sondaggioID_fk AND D.domandaID=R.domandaID_fk");
        
        if (!result.isBeforeFirst() ) 
        {    
        	con.close();
      	  	return "error";
        } 
        
        while(result.next())
        {
        	RispostaData data = new RispostaData();
        	data.setRispostaID(result.getString("rispostaID"));
        	data.setTestoRisposta(result.getString("testoRisposta"));
        	risposteData.add(data);
        }        
        con.close();
        
        return "success";
	}
	
	public String applicaModificaRisposte(ArrayList<RispostaData> risposteData) throws Exception
	{
		Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection con = DriverManager.getConnection(LoginController.url, LoginController.user, LoginController.psw);
        
        // aggiorna domande
        for(int i = 0; i < risposteData.size(); ++i)
        {
        	PreparedStatement ps = con.prepareStatement(
            		"UPDATE Risposta SET testoRisposta='" + risposteData.get(i).getTestoRisposta() + 
            		"' WHERE rispostaID=" + risposteData.get(i).getRispostaID()
            		);
            
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
