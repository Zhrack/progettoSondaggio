package com.struts2.Struts2FirstProj;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.opensymphony.xwork2.ActionSupport;


public class VisitatoreAction extends ActionSupport{
	private static final String name1 = "Davide";
	private static final String name2 = "Andrea";
	private static final String name3 = "Matteo";
	private static final String name4 = "Simone";
	
	private static final String psw1 = "davide";
	private static final String psw2 = "andrea";
	private static final String psw3 = "matteo";
	private static final String psw4 = "simone";
	
	  static final String url = "jdbc:mysql://localhost:3306/world";
	  static final String user = "root";
	  static final String psw = user;
	
//	private String user;
//	private String psw;
	
	List<VisitatoreAction> list;
	
	String userID;
	String nickname;
	String pswrd;
	String nome;
	String cognome;
	
	public VisitatoreAction()
	{
		list = new ArrayList<VisitatoreAction>();
	}
	public VisitatoreAction(String userID, String nickname, String pswrd, String nome, String cognome)
	{
		this.userID = userID;
		this.nickname = nickname;
		this.pswrd = pswrd;
		this.nome = nome;
		this.cognome = cognome;
		
//		list = new ArrayList<VisitatoreAction>();
	}

	public String execute() throws Exception {		
	
//		switch(user)
//		{
//		case name1:
//			if(psw.equals(psw1)) return SUCCESS;
//
//			return ERROR;
//		case name2:
//			if(psw.equals(psw2)) return SUCCESS;
//			
//			return ERROR;
//		case name3:
//			if(psw.equals(psw3)) return SUCCESS;
//
//			return ERROR;
//		case name4:
//			if(psw.equals(psw4)) return SUCCESS;
//			
//			return ERROR;
//		default:
//			return ERROR;
//		}
	      try {

	          Class.forName("com.mysql.jdbc.Driver").newInstance();
	          Connection con = DriverManager.getConnection(url, user, psw);
	          con.setCatalog("sondaggioDB");

	          Statement stmt = con.createStatement();

	          ResultSet result = stmt.executeQuery("SELECT * FROM utente");
	          
	          while(result.next())
	          {
	        	 list.add(new VisitatoreAction(result.getString("userID"), result.getString("nickname"), result.getString("pswrd")
	        			 , result.getString("nome"), result.getString("cognome")));
	          } 

	          con.close();

	      } catch (Exception ex) {
	          Logger.getLogger(VisitatoreAction.class.getName()).log( 
	                           Level.SEVERE, null, ex);
	      }
	          return SUCCESS;
			
	}

//	public String getUser() {
//		return this.user;
//	}
//
//	public void setUser(String user) {
//		this.user = user;
//	}
//	
//	public String getPsw() {
//		return this.psw;
//	}
//
//	public void setPsw(String psw) {
//		this.psw = psw;
//	}
	
	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPswrd() {
		return pswrd;
	}

	public void setPswrd(String pswrd) {
		this.pswrd = pswrd;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	
	public List<VisitatoreAction> getList() {
		return this.list;
	}

	public void setList(List<VisitatoreAction> list) {
		this.list = list;
	}
	
}
