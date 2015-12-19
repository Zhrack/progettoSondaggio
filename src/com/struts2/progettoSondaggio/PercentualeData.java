package com.struts2.progettoSondaggio;

import java.util.ArrayList;

public class PercentualeData {
	private String testoDomanda;
	private ArrayList<Integer> idRisposte;
	private ArrayList<Float> percentuali;
	
	
	public PercentualeData() 
	{
		this.testoDomanda="";
		this.idRisposte=new ArrayList<Integer>();
		this.percentuali=new ArrayList<Float>();
	}
	
	
	public String getTestoDomanda() {
		return testoDomanda;
	}
	public void setTestoDomanda(String testoDomanda) {
		this.testoDomanda = testoDomanda;
	}
	public ArrayList<Float> getPercentuali() {
		return percentuali;
	}
	public void setPercentuali(ArrayList<Float> percentuali) {
		this.percentuali = percentuali;
	}
	public ArrayList<Integer> getIdRisposte() {
		return idRisposte;
	}
	public void setIdRisposte(ArrayList<Integer> idRisposte) {
		this.idRisposte = idRisposte;
	}
	
}
