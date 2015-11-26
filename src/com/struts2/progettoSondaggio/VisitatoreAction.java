package com.struts2.progettoSondaggio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;


public class VisitatoreAction extends ActionSupport{	
	
	public class LoginData
	{
		public String username;
		public String password;
		public boolean isAmministratore;
		
		public LoginData(String username, String password, boolean isAmministatore)
		{
			this.username = username;
			this.password = password;
			this.isAmministratore = isAmministratore;
		}
	}

	private LoginData loginData;
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
		loginData = new LoginData("", "", false);
	}

	public String execute() throws Exception {		
		System.out.println("Option selezionata: " + option);
		if(option.equals("login"))
		{
			System.out.println("Dentro login");
			loginData.username = username;
			loginData.password = password;
			loginData.isAmministratore = false;
			if(loginController.login(loginData))
			{
				System.out.println("Login OK " + loginData.isAmministratore);
				if(loginData.isAmministratore)
				{
					return "amministratoreSuccess";
				}
				else return "utenteSuccess";
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
