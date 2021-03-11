package application.model.vendite;

import java.util.Date;

import application.model.utenti.Cliente;
import application.utils.TipoCialda;

public class Vendita {
	
	private Cliente cliente;
	private TipoCialda tipoCialda;
	private int quantita;
	private boolean inContanti;
	private Date data;
	
	
	public Vendita(Cliente cl, int quantita, TipoCialda tipoCialda, boolean contanti) {
		this(cl,quantita,tipoCialda,contanti,new Date());
	}
	
	
	public Vendita(Cliente cl, int quantita, TipoCialda tipoCialda, boolean contanti, Date data) {
		this.cliente=cl;
		this.quantita=quantita;
		this.tipoCialda=tipoCialda;
		this.inContanti=contanti;
		this.data=data;
	}

	
	public boolean isContanti() {
		return inContanti;
	}

	
	public TipoCialda getTipoCialda() {
		return tipoCialda;
	}

	
	public int getQuantita() {
		return quantita;
	}

	
	public Cliente getCliente() {
		return cliente;
	}
	
	
	public Date getData() {
		return data;
	}
	
	
	public long getEpoch() {
		return data.getTime();
	}

}
