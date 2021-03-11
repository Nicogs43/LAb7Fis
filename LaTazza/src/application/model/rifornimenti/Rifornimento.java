package application.model.rifornimenti;

import java.util.Date;

import application.utils.TipoCialda;

public class Rifornimento {
	
	int numeroScatole;
	TipoCialda tc;
	Date data;
	
	
	public Rifornimento(int numeroScatole, TipoCialda tc) {
		this(numeroScatole,tc,new Date());
	}
	
	
	public Rifornimento(int numeroScatole, TipoCialda tc, Date data) {
		this.numeroScatole=numeroScatole;
		this.tc=tc;
		this.data=data;
	}
	
	
	public int getNumeroScatole() {
		return numeroScatole;
	}
	
	
	public Date getData() {
		return data;
	}
	
	
	public long getEpoch() {
		return data.getTime();
	}
	
	
	public TipoCialda getTipoCialda() {
		return tc;
	}
	

}
