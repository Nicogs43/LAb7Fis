package application.model.rifornimenti;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import application.model.rifornimenti.Rifornimento;
import application.model.utenti.PagamentoDebito;
import application.utils.TipoCialda;
import connectionDB.ConnectionFactory;

public class Rifornimenti {

	private ArrayList<Rifornimento> rifornimenti;

	public Rifornimenti() {
		rifornimenti = new ArrayList<Rifornimento>();
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
				rifornimenti.add(
						new Rifornimento(NumeroScatole, TipoCialda.fromString(Cialda), new Date(Long.valueOf(Data))));

			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			ConnectionFactory.closeConnection(connection);
		}

	}

	public boolean addRifornimento(int numeroScatole, TipoCialda tc) {
		Connection connection = ConnectionFactory.getConnection();
		try {
			Rifornimento rf = new Rifornimento(numeroScatole, tc);
			PreparedStatement ps = connection.prepareStatement("INSERT INTO Rifornimento VALUES ( ?, ? ,? )");
			ps.setInt(1, numeroScatole);
			ps.setString(2, tc.toString());
			ps.setLong(3, rf.getEpoch());
			int i = ps.executeUpdate();
			if (i == 1) {
				return rifornimenti.add(rf);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			ConnectionFactory.closeConnection(connection);
		}
		return false;

	}

	public String print() {
		String rifornimentoString = "RIFORNIMENTI\n";
		for (Rifornimento rif : rifornimenti)
			rifornimentoString += String.valueOf(rif.getEpoch()) + " " + rif.getNumeroScatole() + " "
					+ rif.getTipoCialda() + '\n';
		return rifornimentoString += '\n';
	}

}