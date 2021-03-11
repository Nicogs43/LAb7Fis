package application.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Locale;

import application.utils.Euro;

public class Cassa {

	private Euro disponibilita;

	public Cassa() {
		disponibilita = new Euro(0);
	}

	public void load(String path) {
		
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
		    	  if(sCurrentLine.equals("CASSA")){
		    		  if ((sCurrentLine = br.readLine()) != null) {
		    			  disponibilita=new Euro(0,Long.parseLong(sCurrentLine));
		    			  return;
		    			  }
		    	  }
			}
			} catch (Exception e) {
		    }
		disponibilita=new Euro(2000);	//se file salvataggio non esiste, diamo 2000euro di partenza	
	}
	
	
	public boolean riceviPagamento(Euro euro) {
		if (euro.minoreDi(new Euro(0)))
			return false;
		disponibilita.somma(euro);
		return true;
	}

	public boolean effettuaPagamento(Euro euro) {
		if (euro.minoreDi(new Euro(0)) || disponibilita.minoreDi(euro))
			return false;
		disponibilita.sottrai(euro);
		return true;
	}

	public Euro getDisponibilita() {
		return disponibilita;
	}

	@Override
	public String toString() {
		return String.format(Locale.US, "%,.2f", (double)disponibilita.getValore() / 100);
	}

	public String print() {
		return "CASSA\n"+disponibilita.getValore() + "\n\n";
	}

}
