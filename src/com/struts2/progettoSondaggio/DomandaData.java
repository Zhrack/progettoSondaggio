package com.struts2.progettoSondaggio;

public class DomandaData {
	private String domandaID;
	private String testo;
	private String domandaIDPerModificaSondaggio;
	

	public String getDomandaID() {
		return domandaID;
	}
	public void setDomandaID(String domandaID) {
		this.domandaID = domandaID;
	}
	public String getTesto() {
		return testo;
	}
	public void setTesto(String testo) {
		this.testo = testo;
	}
	public String getDomandaIDPerModificaSondaggio() {
		return domandaIDPerModificaSondaggio;
	}
	public void setDomandaIDPerModificaSondaggio(String domandaIDPerModificaSondaggio) {
		this.domandaIDPerModificaSondaggio = domandaIDPerModificaSondaggio;
	}
}
