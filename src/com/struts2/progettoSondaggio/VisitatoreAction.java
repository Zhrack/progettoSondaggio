package com.struts2.progettoSondaggio;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;


public class VisitatoreAction extends ActionSupport{	

	
	private LoginController loginController;
	
	private String username;
	private String password;
	private String nome;
	private String cognome;
	
	private String option;

	
	private List<UtenteDB> list;
		
	public VisitatoreAction()
	{
		list = new ArrayList<UtenteDB>();
		loginController = new LoginController();
	}

	public String execute() throws Exception {		
	
//	      try {
//
//	          Class.forName("com.mysql.jdbc.Driver").newInstance();
//	          Connection con = DriverManager.getConnection(url, user, psw);
//
//	          Statement stmt = con.createStatement();
//
//	          ResultSet result = stmt.executeQuery("SELECT * FROM utente");
//	          
//	          while(result.next())
//	          {
//	        	 list.add(new UtenteDB(result.getString("userID"), result.getString("nickname"), result.getString("pswrd")
//	        			 , result.getString("nome"), result.getString("cognome")));
//	          } 
//
//	          con.close();
//
//	      } catch (Exception ex) {
//	          Logger.getLogger(VisitatoreAction.class.getName()).log( 
//	                           Level.SEVERE, null, ex);
//	      }
		System.out.println("Option selezionata: " + option);
		if(option.equals("login"))
		{
			System.out.println("Dentro login");
			if(loginController.login(username, password))
			{
				System.out.println("Login OK");
				return SUCCESS;
			}
			else return "loginFallito";
		}
		else if(option.equals("registrazione"))
		{
			System.out.println("Dentro registrazione");
			if(loginController.registrazione(username, password, nome, cognome))
			{
				System.out.println("Reg OK");
				return SUCCESS;
			}
			else return "registrazioneFallita";
		}
		else
		{
			System.out.println("Failed: " + option);
			return "loginFallito";
		}
			
	}
	
	public List<UtenteDB> getList() {
		return this.list;
	}

	public void setList(List<UtenteDB> list) {
		this.list = list;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
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
	
}
