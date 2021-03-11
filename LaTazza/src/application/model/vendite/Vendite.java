package application.model.vendite;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;

import application.model.utenti.Cliente;
import application.model.utenti.Persona;
import application.model.vendite.Vendita;
import application.utils.TipoCialda;

public class Vendite {

	private ArrayList<Vendita> vendite;
	
	public Vendite() {
		vendite = new ArrayList<Vendita>();
	}
	
	
	public ArrayList<Vendita> getVendite() {
		return vendite;
	}
	
	
	public void load(String path) {
		
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream(path), Charset.forName("UTF-8")))) {

		      String sCurrentLine;

		      while ((sCurrentLine = br.readLine()) != null) {
		    	  if(sCurrentLine.equals("VENDITE")){
		    		  while ((sCurrentLine = br.readLine()) != null) {
		    			  if(sCurrentLine.equals(""))
		    				  return;
		    			  String[] vendita = sCurrentLine.split(" ");
		    			  vendite.add(new Vendita(
		    					  new Persona(vendita[1]),
		    					  Integer.valueOf(vendita[2]), 
		    					  TipoCialda.fromString(vendita[3]), 
		    					  Boolean.valueOf(vendita[4]),
		    					  new Date(Long.valueOf(vendita[0]))));
		    		  }
		    	  }
		      }

		    } catch (IOException e) {
		    }
	}
	
	
	public boolean addVendita (Cliente cl, int quantita, TipoCialda tipoCialda, boolean cont) {
		return vendite.add(new Vendita(cl, quantita, tipoCialda, cont));	
	}
	
	public boolean addVendita (Cliente cl, int quantita, TipoCialda tipoCialda, boolean cont, Date date) {
		return vendite.add(new Vendita(cl, quantita, tipoCialda, cont, date));	
	}	
	
	public String print() {
		String venditeString="VENDITE\n";
		for(Vendita vend : vendite)
			venditeString+=vend.getEpoch()+" "+vend.getCliente().getNome()+" "+vend.getQuantita()+" "+vend.getTipoCialda().toString()+" "+vend.isContanti()+'\n';
		return venditeString+='\n';
	}
	
}
