package com.struts2.progettoSondaggio;

import com.opensymphony.xwork2.ActionSupport;

public class UtenteAction extends ActionSupport{
	
	private PagamentoController pagamentoController;
	
	private String username;
	
	public UtenteAction()
	{
		pagamentoController = new PagamentoController();
	}
	
	public String execute() throws Exception {	
		if(pagamentoController.effettuaPagamento(username))
		{
			System.out.println("pagamento true");
			return SUCCESS;
		}
		else return ERROR;
		
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
