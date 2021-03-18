package application.model.vendite;


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
import java.util.ArrayList;
import java.util.Date;

import application.model.utenti.Cliente;
import application.model.utenti.PagamentoDebito;
import application.model.utenti.Persona;
import application.model.vendite.Vendita;
import application.utils.TipoCialda;
import connectionDB.ConnectionFactory;

public class Vendite {

	private ArrayList<Vendita> vendite;
	
	public Vendite() {
		vendite = new ArrayList<Vendita>();
	}
	
	
	public ArrayList<Vendita> getVendite() {
		return vendite;
	}
	
	
	public void load() {
		
		Connection connection = ConnectionFactory.getConnection();
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Vendita");
			while (rs.next()) {
				String Cliente =rs.getString("Cliente");
				String Cialda = rs.getString("TipoCialda");
				int quantita = rs.getInt("quantità");
				boolean incontanti = rs.getBoolean("incontanti");
				Long Data = rs.getLong("Data");
				
		    			  vendite.add(new Vendita(
		    					  new Persona(Cliente),
		    					  quantita,
		    					  TipoCialda.fromString(Cialda),     					  
		    					  incontanti,
		    					  new Date(Long.valueOf(Data))));
		    		  }
		    	  
		      

		    } catch (SQLException ex) {
				ex.printStackTrace();
			} finally {
				ConnectionFactory.closeConnection(connection);
			}
		    
	}
	
	
	public boolean addVendita (Cliente cl, int quantita, TipoCialda tipoCialda, boolean cont) {
		Connection connection = ConnectionFactory.getConnection();
		try {
			Vendita vd = new Vendita(cl, quantita, tipoCialda, cont);
			PreparedStatement ps = connection.prepareStatement("INSERT INTO Vendita VALUES ( ?, ? ,? ,?,?)");
			ps.setString(1,cl.getNome());
			ps.setString(2, tipoCialda.toString());
			ps.setInt(3, quantita);
			ps.setBoolean(4, cont);
			ps.setLong(5, vd.getEpoch());
			int i = ps.executeUpdate();
			if (i == 1) {
				return vendite.add(vd);
			}
		}catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			ConnectionFactory.closeConnection(connection);
		}
		return false;
	}
	
	public boolean addVendita (Cliente cl, int quantita, TipoCialda tipoCialda, boolean cont, Date date) {
		Connection connection = ConnectionFactory.getConnection();
		try {
			Vendita vd = new Vendita(cl, quantita, tipoCialda, cont,date);
			PreparedStatement ps = connection.prepareStatement("INSERT INTO Vendita VALUES ( ?, ? ,? ,?,?)");
			ps.setString(1,cl.getNome());
			ps.setString(2, tipoCialda.toString());
			ps.setInt(3, quantita);
			ps.setBoolean(4, cont);
			ps.setLong(5,vd.getEpoch() );
			int i = ps.executeUpdate();
			if (i == 1) {
				return vendite.add(vd);
			}
		}catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			ConnectionFactory.closeConnection(connection);
		}
		return false;	
	}	
	
	//METEDO PER I TEST
		public boolean deleteVendita (Vendita vd) {
			Connection connection = ConnectionFactory.getConnection();
			try {
				PreparedStatement ps = connection.prepareStatement("DELETE FROM Vendita WHERE Cliente=? AND Data=?");
				ps.setString(1,vd.getCliente().getNome());		
				ps.setLong(2, vd.getEpoch() );
				int i = ps.executeUpdate();
				if (i == 1) {
					vendite.remove(vendite.size()-1);
					return true;
				}
			}catch (SQLException ex) {
				ex.printStackTrace();
			} finally {
				ConnectionFactory.closeConnection(connection);
			}
			return false;
		}
		
	
	public String print() {
		String venditeString="VENDITE\n";
		for(Vendita vend : vendite)
			venditeString+=vend.getEpoch()+" "+vend.getCliente().getNome()+" "+vend.getQuantita()+" "+vend.getTipoCialda().toString()+" "+vend.isContanti()+'\n';
		return venditeString+='\n';
	}
	
}
