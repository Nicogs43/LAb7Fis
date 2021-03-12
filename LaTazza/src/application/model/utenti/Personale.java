package application.model.utenti;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;


import application.utils.Euro;
import connectionDB.ConnectionFactory;

public class Personale {

	private LinkedHashSet<Persona> personale;
	private LinkedHashSet<PagamentoDebito> pagamentiDebito;
	
	public Personale() {
		personale = new LinkedHashSet<Persona>();
		pagamentiDebito = new LinkedHashSet<PagamentoDebito>();
	}
	
	
	public LinkedHashSet<Persona> getPersonale() {
		return personale;
	}

	public LinkedHashSet<PagamentoDebito> getPagamentiDebito() {
		return pagamentiDebito;
	}
	

	public void load(String path) {
		try (BufferedReader br = new BufferedReader( 
				new InputStreamReader(new FileInputStream(path), Charset.forName("UTF-8")))) {

		      //String sCurrentLine;
		      Connection connection = ConnectionFactory.getConnection();
		      try {
		    	  Statement stmt = connection.createStatement();
		          ResultSet rs = stmt.executeQuery("SELECT * FROM Persona");  
		          while(rs.next()) {
		        	 String Nome = rs.getString("Nome");
		        	 Integer Debito = rs.getInt("Debito");
		        	 personale.add(new Persona(Nome , new Euro(0,Debito)));
		          }
		      } catch (SQLException ex) {
		          ex.printStackTrace();
		      }
		      
		      //Connection connection = ConnectionFactory.getConnection();
		      try {
		    	  Statement stmt = connection.createStatement();
		          ResultSet rs = stmt.executeQuery("SELECT * FROM Paga");  
		          while(rs.next()) {
		        	 String Nome = rs.getString("NomePersona");
		        	 Integer Debito = rs.getInt("Ammontare");
		        	 //Date Data = rs.getDate("Data");
		        	 Long Data = rs.getLong("Data");
		        	 pagamentiDebito.add(new PagamentoDebito(new Persona(Nome),new Euro(0,Debito), new Date(Data)));
		          }
		      } catch (SQLException ex) {
		          ex.printStackTrace();
		      }
		      /*while ((sCurrentLine = br.readLine()) != null) {
		    	if(sCurrentLine.equals("PAGAMENTO")){
		    		  while ((sCurrentLine = br.readLine()) != null) {
		    			  if(sCurrentLine.equals(""))
		    				  return;
		    			  String[] persona = sCurrentLine.split(" ");
		    			  pagamentiDebito.add(new PagamentoDebito(new Persona(persona[1]),new Euro(0,Integer.valueOf(persona[2])),new Date(Long.parseLong(persona[0]))));
		    		  }
		    	  }
		      }*/
		    } catch (IOException e) {
		    }
	}


	public boolean addPersona (String p) {	
		Connection connection = ConnectionFactory.getConnection();
		try {
			 PreparedStatement ps = connection.prepareStatement("INSERT INTO Persona VALUES ( ?, 0)");
		     ps.setString(1, p);  
		     int i = ps.executeUpdate();
		     
		      if(i == 1) {
		    	 personale.add(new Persona(p));
		        return true;
		      }

		    } catch (SQLException ex) {
		        ex.printStackTrace();
		    }

		    return false;
		
	}
	
	
	public boolean removePersona (Persona p) {	
		Connection connection = ConnectionFactory.getConnection();
		try {
			 PreparedStatement ps = connection.prepareStatement("SELECT * FROM Persona WHERE Nome = ?");
			 ps.setString(1, p.getNome());
			 ResultSet rs = ps.executeQuery();
			 if(rs.next()) {
        	 Integer Debito = rs.getInt("Debito");
        	 if(Debito>0) return false;
        	 	else {
        	 		PreparedStatement ps1 = connection.prepareStatement("DELETE FROM Persona WHERE Nome= ?" );
        	 		ps1.setString(1, p.getNome());
        	 		int i = ps1.executeUpdate();
        	        if(i == 1) {
        	        	return personale.remove(p);
        	           
        	              }
        	 	}
        	 
			 }
		
        	 
		}catch (SQLException ex) {
	        ex.printStackTrace();
	    }
		return false;
	}
	
	
	public Set<Persona> getIndebitati(){
		LinkedHashSet<Persona> personaleConDebiti = new LinkedHashSet<Persona>();
		Connection connection = ConnectionFactory.getConnection();
		try {
			  Statement stmt = connection.createStatement();
	          ResultSet rs = stmt.executeQuery("SELECT * FROM Persona");  
	          while(rs.next()) {
	        	 String Nome = rs.getString("Nome");
	        	 Integer Debito = rs.getInt("Debito");
	        	 if(Debito>0)
	        	 personaleConDebiti.add(new Persona(Nome , new Euro(0,Debito)));
	          }
		} catch (SQLException ex) {
	        ex.printStackTrace();
	    }
		return personaleConDebiti;
	}
	
	
	public boolean diminuisciDebito(Persona pers, Euro ammontare) {
		if(!pers.diminuisciDebito(ammontare))
			return false;
		try {
			Connection connection = ConnectionFactory.getConnection();
			PagamentoDebito pd = new PagamentoDebito(pers, ammontare);
			PreparedStatement ps = connection.prepareStatement("INSERT INTO PagamentoDiDebito VALUES ( ?, ? ,? )");
			ps.setString(1, pers.getNome());
			ps.setInt(2, (int) ammontare.getValore());
			ps.setLong(3, pd.getEpoch());
			int i = ps.executeUpdate();
			if(i==1) {
				pagamentiDebito.add(pd);
				return true;
			}
		}
		catch (SQLException ex) {
	        ex.printStackTrace();
	    }
		return false;
	}
	
	public String print() {
		String personaleString="PERSONALE\n";
		for(Persona pers : personale)
			personaleString+=pers.toString()+" "+pers.getDebito().getValore()+'\n';
		String pagamentiDebitiString="PAGAMENTO\n";
		for(PagamentoDebito pagDeb : pagamentiDebito) 
			pagamentiDebitiString+=pagDeb.getEpoch()+" "+pagDeb.getPersona()+' '+pagDeb.getAmmontare().getValore()+'\n';
		return personaleString+='\n'+pagamentiDebitiString+'\n';
	}

}