package com.struts2.progettoSondaggio;

public class SondaggioData {
	private String sondaggioID;
	private String nomeSondaggio;
	private String autore;
	private String sondaggioIDPerCancellazione;
	private String sondaggioIDPerModifica;

	public String getSondaggioID() {
		return sondaggioID;
	}

	public void setSondaggioID(String sondaggioID) {
		this.sondaggioID = sondaggioID;
	}

	public String getNomeSondaggio() {
		return nomeSondaggio;
	}

	public void setNomeSondaggio(String nomeSondaggio) {
		this.nomeSondaggio = nomeSondaggio;
	}

	public String getAutore() {
		return autore;
	}

	public void setAutore(String autore) {
		this.autore = autore;
	}

	public String getSondaggioIDPerCancellazione() {
		return sondaggioIDPerCancellazione;
	}

	public void setSondaggioIDPerCancellazione(String sondaggioIDPerCancellazione) {
		this.sondaggioIDPerCancellazione = sondaggioIDPerCancellazione;
	}

	public String getSondaggioIDPerModifica() {
		return sondaggioIDPerModifica;
	}

	public void setSondaggioIDPerModifica(String sondaggioIDPerModifica) {
		this.sondaggioIDPerModifica = sondaggioIDPerModifica;
	}
	
}
