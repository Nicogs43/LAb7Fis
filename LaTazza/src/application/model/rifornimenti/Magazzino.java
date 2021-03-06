package application.model.rifornimenti;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;


import application.utils.TipoCialda;
import connectionDB.ConnectionFactory;

public class Magazzino {

	private Rifornimenti rifornimenti;
	HashMap<TipoCialda, Integer> mag = new HashMap<TipoCialda, Integer>();

	public Magazzino() {
		mag.put(TipoCialda.caff?, 0);
		mag.put(TipoCialda.caff?Arabica, 0);
		mag.put(TipoCialda.th?, 0);
		mag.put(TipoCialda.th?Limone, 0);
		mag.put(TipoCialda.cioccolata, 0);
		mag.put(TipoCialda.camomilla, 0);
		rifornimenti = new Rifornimenti();
	}

	public ArrayList<Rifornimento> getRifornimenti() {
		return rifornimenti.getRifornimenti();
	}

	public void load() {
		Connection connection = ConnectionFactory.getConnection();
		try {

			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Magazzino");
			while (rs.next()) {
				String Cialda = rs.getString("TipoCialda");
				Integer Ammontare = rs.getInt("Ammontare");
				mag.put(TipoCialda.fromString(Cialda), Ammontare);

			}
			rifornimenti.load();
		} catch (SQLException ex) {
		} finally {
			ConnectionFactory.closeConnection(connection);
		}

	}

	public int numeroCialdeDisponibili(TipoCialda tipoCialda) {
		return mag.get(tipoCialda);
	}

	public boolean aggiungiRifornimento(int numScatole, TipoCialda tipoCialda) {
		if (numScatole < 1)
			return false;
		Connection connection = ConnectionFactory.getConnection();
		try {
			PreparedStatement ps = connection.prepareStatement("UPDATE Magazzino SET Ammontare=? WHERE TipoCialda=?");
			ps.setInt(1, mag.get(tipoCialda) + 50 * numScatole);
			ps.setString(2, tipoCialda.toString());
			int i = ps.executeUpdate();
			if (i == 1) {
				mag.put(tipoCialda, mag.get(tipoCialda) + 50 * numScatole);
				rifornimenti.addRifornimento(numScatole, tipoCialda);
				return true;
			}
		} catch (SQLException ex) {

		}

		return false;
	}

	public boolean rimuoviCialde(int numeroCialde, TipoCialda tipoCialda) {
		if (numeroCialde < 1)
			return false;
		Connection connection = ConnectionFactory.getConnection();
		try {
			PreparedStatement ps = connection.prepareStatement("UPDATE Magazzino SET Ammontare=? WHERE TipoCialda=?");
			ps.setInt(1, mag.get(tipoCialda) - numeroCialde);
			ps.setString(2, tipoCialda.toString());
			int i = ps.executeUpdate();
			if (i == 1) {
				mag.put(tipoCialda, mag.get(tipoCialda) - numeroCialde);
				return true;
			}
		} catch (SQLException ex) {

		} finally {
			ConnectionFactory.closeConnection(connection);
		}

		return true;
	}

	public String print() {
		String magString = "MAGAZZINO\n";
		for (TipoCialda tipoCialda : mag.keySet())
			magString += tipoCialda.toString() + " " + mag.get(tipoCialda) + '\n';
		return magString += "\n\n" + rifornimenti.print();
	}

}
