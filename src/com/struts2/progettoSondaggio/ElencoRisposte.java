package com.struts2.progettoSondaggio;

import java.util.ArrayList;

public class ElencoRisposte {
	public ArrayList<String> testiRisposta;
	
	public ElencoRisposte(ArrayList<String> testiRisposta)
	{
		this.testiRisposta = testiRisposta;
	}

	public ArrayList<String> getTestiRisposta() {
		return testiRisposta;
	}

	public void setTestiRisposta(ArrayList<String> testiRisposta) {
		this.testiRisposta = testiRisposta;
	}
}
