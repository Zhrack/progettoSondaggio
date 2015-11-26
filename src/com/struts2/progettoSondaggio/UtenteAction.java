package com.struts2.progettoSondaggio;

import com.opensymphony.xwork2.ActionSupport;

public class UtenteAction extends ActionSupport{
	
	private PagamentoController pagamentoController;
	
	public UtenteAction()
	{
		pagamentoController = new PagamentoController();
	}

}
