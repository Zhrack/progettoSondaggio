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
        
        // controllo che l'utente non abbia già partecipato
        // prima raccolgo le domandeID a cui l'utente ha partecipato
        boolean partecipato = false;
        ArrayList<String> listaDomandeFatte = new ArrayList<String>();
        ArrayList<String> listaDomandeDaInserire = new ArrayList<String>();
        Statement s = con.createStatement();
        ResultSet r = s.executeQuery(
        		"SELECT R.domandaID_fk AS domandaID FROM Utente U, Partecipazione P, Risposta R" +
                " WHERE U.userID=P.userID_fk AND P.rispostaID_fk=R.rispostaID" +
                " AND U.userID=" + userID);
        if (r.isBeforeFirst() ) 
        {    
        	while(r.next())
            {
            	listaDomandeFatte.add(r.getString("domandaID"));
            }
        }        
        
        // poi raccolgo le domandeID delle risposte in arrivo
        for(int i = 0; i < risposte.size(); ++i)
        {
        	Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery(
            		"SELECT domandaID_fk AS domandaID FROM Risposta R" +
                    " WHERE rispostaID=" + risposte.get(i));
            if (result.isBeforeFirst() ) 
            {    
            	result.next();
            	listaDomandeDaInserire.add(result.getString("domandaID"));
            } 
        }
        
        // controllo che i 2 array non abbiano elementi in comune, altrimenti significa che l'utente ha già partecipato
        for(int i = 0; i < listaDomandeFatte.size(); ++i)
        {
        	String domandaFatta = listaDomandeFatte.get(i);
        	for(int j = 0; j < listaDomandeDaInserire.size(); ++j)
        	{
        		if(domandaFatta.equals(listaDomandeDaInserire.get(j)))
        		{
        			// l'utente ha già una partecipazione in questa domanda, stiamo partecipando di nuovo ad un sondaggio
        			partecipato = true;
        			break;
        		}
        	}
        }

        
        if(!partecipato)
        {
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
        else
        {
        	System.out.println("Utente ha già partecipato!");
        	con.close();
      	  	return "error";
        }
	}

}
