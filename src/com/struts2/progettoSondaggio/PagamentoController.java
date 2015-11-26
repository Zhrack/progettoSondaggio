package com.struts2.progettoSondaggio;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class PagamentoController {

	
	public PagamentoController()
	{
		
	}
	
	public boolean effettuaPagamento(String username)
	{
		// Aggiorna data pagamento DB e torna true
		String updatePagamento = "UPDATE utente SET dataPagamento=? WHERE nickname='" + username + "'";
		  try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection con = DriverManager.getConnection(LoginController.url, LoginController.user, LoginController.psw);
			
			PreparedStatement ps = con.prepareStatement(updatePagamento);
			Date sqlDate = new java.sql.Date(new java.util.Date().getTime());
			ps.setDate(1, sqlDate);
			
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
			
		  } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return false;

		
	}
}
