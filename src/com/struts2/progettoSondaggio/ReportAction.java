package com.struts2.progettoSondaggio;

import com.opensymphony.xwork2.ActionSupport;

public class ReportAction extends ActionSupport{
	
	private String sondaggioIDReport; 

	public ReportAction()
	{
		
	}
	
	public String generaReport()
	{
		return SUCCESS;
	}

	public String getSondaggioIDReport() {
		return sondaggioIDReport;
	}

	public void setSondaggioIDReport(String sondaggioIDReport) {
		this.sondaggioIDReport = sondaggioIDReport;
	}
}
