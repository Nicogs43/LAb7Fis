package application.model.rifornimenti;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;

import application.utils.TipoCialda;

public class Magazzino {
	
	
	private Rifornimenti rifornimenti;
	HashMap<TipoCialda, Integer> mag=new HashMap<TipoCialda,Integer>();
	
	public Magazzino() {	
		mag.put(TipoCialda.caffè, 0);
		mag.put(TipoCialda.caffèArabica, 0);
		mag.put(TipoCialda.thè, 0);
		mag.put(TipoCialda.thèLimone, 0);
		mag.put(TipoCialda.cioccolata, 0);
		mag.put(TipoCialda.camomilla, 0);
		rifornimenti=new Rifornimenti();
	}
	
	public ArrayList<Rifornimento> getRifornimenti() {
		return rifornimenti.getRifornimenti();
	}


	public void load(String path) {
		
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream(path), Charset.forName("UTF-8")))) {

		      String sCurrentLine;

		      while ((sCurrentLine = br.readLine()) != null) {
		    	  if(sCurrentLine.equals("MAGAZZINO")){
		    		  while ((sCurrentLine = br.readLine()) != null) {
		    			  if(sCurrentLine.equals("")) {
		    				  rifornimenti.load(path);
		    				  return;
		    			  }
		    			  String[] cialda = sCurrentLine.split(" ");
		    			  mag.put(TipoCialda.fromString(cialda[0]), Integer.valueOf(cialda[1]));
		    		  }
		    	  }
		      }
		 } catch (IOException e) {
		 }	
	}
	
	
	public int numeroCialdeDisponibili(TipoCialda tipoCialda) {	
		return mag.get(tipoCialda);
	}
	
	
	public boolean aggiungiRifornimento(int numScatole, TipoCialda tipoCialda) {
		if(numScatole<1)
			return false;
		mag.put(tipoCialda, mag.get(tipoCialda)+50*numScatole);
		rifornimenti.addRifornimento(numScatole, tipoCialda);
		return true;
	}
	
	
	
	public boolean rimuoviCialde(int numeroCialde,TipoCialda tipoCialda) {
		if(numeroCialde<1)
			return false;
		mag.put(tipoCialda,mag.get(tipoCialda)-numeroCialde);
		return true;
	}
	
	
	public String print() {
		String magString="MAGAZZINO\n";
		for(TipoCialda tipoCialda : mag.keySet())
			magString+=tipoCialda.toString()+" "+mag.get(tipoCialda)+'\n';
		return magString+="\n\n"+rifornimenti.print();
	}
	
}
