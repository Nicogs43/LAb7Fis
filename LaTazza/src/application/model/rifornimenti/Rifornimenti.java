package application.model.rifornimenti;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import application.model.rifornimenti.Rifornimento;
import application.utils.TipoCialda;
import connectionDB.ConnectionFactory;

public class Rifornimenti {
	
	private ArrayList<Rifornimento> rifornimenti;
	
	public Rifornimenti() {
		rifornimenti=new ArrayList<Rifornimento>();
	}
	
	
	public ArrayList<Rifornimento> getRifornimenti() {
		return rifornimenti;
	}

	
	public void load() {
		
		Connection connection = ConnectionFactory.getConnection();
		try {
			
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Rifornimento");
			while (rs.next()) { 
				Integer NumeroScatole = rs.getInt("NumeroScatole");
				String Cialda = rs.getString("TipoCialda");
				Long Data = rs.getLong("Data");
				//mag.put(TipoCialda.fromString(Cialda), Ammontare);
				rifornimenti.add(new Rifornimento(
  					  NumeroScatole, 
  					  TipoCialda.fromString(Cialda), 
  					  new Date(Long.valueOf(Data)))
  					  );
				
			}
			
		 } 
			catch (SQLException ex) {
		 }	
		finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
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