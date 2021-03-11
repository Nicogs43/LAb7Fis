package application.model.rifornimenti;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;

import application.model.rifornimenti.Rifornimento;
import application.utils.TipoCialda;

public class Rifornimenti {
	
	private ArrayList<Rifornimento> rifornimenti;
	
	public Rifornimenti() {
		rifornimenti=new ArrayList<Rifornimento>();
	}
	
	
	public ArrayList<Rifornimento> getRifornimenti() {
		return rifornimenti;
	}

	
	public void load(String path) {
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(
						new FileInputStream(path), Charset.forName("UTF-8")))) {

		      String sCurrentLine;

		      while ((sCurrentLine = br.readLine()) != null) {
		    	  if(sCurrentLine.equals("RIFORNIMENTI")){
		    		  
		    		  while ((sCurrentLine = br.readLine()) != null) {
		    			  if(sCurrentLine.equals("")) return;
		    			  
		    			  String[] rifornimento = sCurrentLine.split(" ");
		    			  
		    			  rifornimenti.add(new Rifornimento(
		    					  Integer.valueOf(rifornimento[1]), 
		    					  TipoCialda.fromString(rifornimento[2]), 
		    					  new Date(Long.valueOf(rifornimento[0])))
		    					  );
		    		  }
		    	  }
		      }
		    } catch (Exception e) {    
		    }
	}
	

	public boolean addRifornimento (int numeroScatole, TipoCialda tc) {		
		return rifornimenti.add(new Rifornimento(numeroScatole, tc));	
	}
	
	
	public String print() {
		String rifornimentoString="RIFORNIMENTI\n";
		for(Rifornimento rif : rifornimenti)
			rifornimentoString+=String.valueOf(rif.getEpoch())+" "+rif.getNumeroScatole()+" "+rif.getTipoCialda()+'\n';
		return rifornimentoString+='\n';
	}
	
}