package application.model.utenti;

import java.util.Date;

import application.utils.Euro;

public class PagamentoDebito {
	
	private Persona persona;
	private Euro ammontare;
	private Date date;
	
	
	public PagamentoDebito(Persona persona,Euro ammontare) {
		this(persona,ammontare,new Date());
	}

	public PagamentoDebito(Persona persona,Euro ammontare,Date data) {
		this.persona=persona;
		this.ammontare=ammontare;
		if(data.compareTo(new Date()) > 0)
			this.date=new Date();
		else	this.date=data;

	}

	public Persona getPersona() {
		return persona;
	}

	public Euro getAmmontare() {
		return ammontare;
	}

	public Date getDate() {
		return date;
	}

	public long getEpoch() {
		return date.getTime();
	}
	
}