package com.struts2.progettoSondaggio;

public class UtenteDB {
	String userID;
	String nickname;
	String pswrd;
	String nome;
	String cognome;
	
	public UtenteDB(String userID, String nickname, String pswrd, String nome, String cognome)
	{
		this.userID = userID;
		this.nickname = nickname;
		this.pswrd = pswrd;
		this.nome = nome;
		this.cognome = cognome;
		
	}
	
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
}
