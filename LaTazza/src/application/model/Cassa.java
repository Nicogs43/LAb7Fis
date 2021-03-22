package application.model;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;

import application.utils.Euro;
import connectionDB.ConnectionFactory;

public class Cassa {

	private Euro disponibilita;

	public Cassa() {
		disponibilita = new Euro(0);
	}

	public void load() {

		Connection connection = ConnectionFactory.getConnection();
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM cassa");
			while (rs.next()) {
				Integer cassa = rs.getInt("Disponibilità");
				if (cassa == 0) {// se la cassa è vuota il default è 2000
					PreparedStatement ps = connection.prepareStatement("UPDATE Cassa SET Disponibilità =? ");
					ps.setInt(1, 2000);
					int i = ps.executeUpdate();
					if (i == 1) {
						disponibilita = new Euro(2000);
						return;
					}
				}
				disponibilita = new Euro(0, cassa);
				return;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			ConnectionFactory.closeConnection(connection);
		}
	}

	public boolean riceviPagamento(Euro euro) {
		if (euro.minoreDi(new Euro(0)))
			return false;
		Connection connection = ConnectionFactory.getConnection();
		try {
			PreparedStatement ps = connection.prepareStatement("UPDATE Cassa SET Disponibilità =? ");
			ps.setInt(1, (int) (euro.getValore() + disponibilita.getValore()));
			int i = ps.executeUpdate();
			if (i == 1) {
				disponibilita.somma(euro);
				return true;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			ConnectionFactory.closeConnection(connection);
		}
		return false;
	}

	public boolean effettuaPagamento(Euro euro) {
		if (euro.minoreDi(new Euro(0)) || disponibilita.minoreDi(euro))
			return false;
		Connection connection = ConnectionFactory.getConnection();
		try {
			PreparedStatement ps = connection.prepareStatement("UPDATE Cassa SET Disponibilità =? ");
			ps.setInt(1, (int) (disponibilita.getValore() - euro.getValore()));
			int i = ps.executeUpdate();
			if (i == 1) {
				disponibilita.sottrai(euro);
				return true;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			ConnectionFactory.closeConnection(connection);
		}

		return false;
	}

	public Euro getDisponibilita() {
		return disponibilita;
	}

	@Override
	public String toString() {
		return String.format(Locale.US, "%,.2f", (double) disponibilita.getValore() / 100);
	}

	public String print() {
		return "CASSA\n" + disponibilita.getValore() + "\n\n";
	}

}
