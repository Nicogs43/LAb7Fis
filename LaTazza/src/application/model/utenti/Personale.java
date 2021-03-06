package application.model.utenti;

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

	public void load() {

		Connection connection = ConnectionFactory.getConnection();
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Persona");
			while (rs.next()) {
				String Nome = rs.getString("Nome");
				Integer Debito = rs.getInt("Debito");
				personale.add(new Persona(Nome, new Euro(0, Debito)));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Pagamentodidebito");
			while (rs.next()) {
				String Nome = rs.getString("NomePersona");
				Integer Debito = rs.getInt("Ammontare");
				Long Data = rs.getLong("Data");
				pagamentiDebito.add(new PagamentoDebito(new Persona(Nome), new Euro(0, Debito), new Date(Data)));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		finally {
			ConnectionFactory.closeConnection(connection);
		}

	}

	public boolean addPersona(String p) {
		Connection connection = ConnectionFactory.getConnection();
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO Persona VALUES ( ?, 0)");
			ps.setString(1, p);
			int i = ps.executeUpdate();

			if (i == 1) {
				personale.add(new Persona(p));
				return true;
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			ConnectionFactory.closeConnection(connection);
		}
		return false;

	}

	public boolean removePersona(Persona p) {
		Connection connection = ConnectionFactory.getConnection();
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM Persona WHERE Nome = ?");
			ps.setString(1, p.getNome());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Integer Debito = rs.getInt("Debito");
				if (Debito > 0)
					return false;
				else {
					PreparedStatement ps1 = connection.prepareStatement("DELETE FROM Persona WHERE Nome= ?");
					ps1.setString(1, p.getNome());
					int i = ps1.executeUpdate();
					if (i == 1) {
						return personale.remove(p);

					}
				}

			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			ConnectionFactory.closeConnection(connection);
		}
		return false;
	}

	public Set<Persona> getIndebitati() {
		LinkedHashSet<Persona> personaleConDebiti = new LinkedHashSet<Persona>();
		Connection connection = ConnectionFactory.getConnection();
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Persona");
			while (rs.next()) {
				String Nome = rs.getString("Nome");
				Integer Debito = rs.getInt("Debito");
				if (Debito > 0)
					personaleConDebiti.add(new Persona(Nome, new Euro(0, Debito)));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			ConnectionFactory.closeConnection(connection);
		}
		return personaleConDebiti;
	}

	public boolean diminuisciDebito(Persona pers, Euro ammontare) {
		if (!pers.diminuisciDebito(ammontare))
			return false;
		Connection connection = ConnectionFactory.getConnection();
		try {
			PagamentoDebito pd = new PagamentoDebito(pers, ammontare);
			PreparedStatement ps = connection.prepareStatement("INSERT INTO PagamentoDiDebito VALUES ( ?, ? ,? )");
			ps.setString(1, pers.getNome());
			ps.setInt(2, (int) ammontare.getValore());
			ps.setLong(3, pd.getEpoch());
			int i = ps.executeUpdate();
			if (i == 1) {
				pagamentiDebito.add(pd);
				return true;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			ConnectionFactory.closeConnection(connection);
		}
		return false;
	}

	// Metodo usato solo per i test
	public boolean rimuoviPagamentoDiDebito(PagamentoDebito pd) {
		Connection connection = ConnectionFactory.getConnection();
		try {

			PreparedStatement ps = connection
					.prepareStatement("DELETE FROM PagamentoDiDebito WHERE data= ? AND NomePersona =?");
			ps.setLong(1, pd.getEpoch());
			ps.setString(2, pd.getPersona().getNome());
			int i = ps.executeUpdate();
			if (i == 1) {
				pagamentiDebito.remove(pd);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			ConnectionFactory.closeConnection(connection);
		}
		return false;

	}

	public String print() {
		String personaleString = "PERSONALE\n";
		for (Persona pers : personale)
			personaleString += pers.toString() + " " + pers.getDebito().getValore() + '\n';
		String pagamentiDebitiString = "PAGAMENTO\n";
		for (PagamentoDebito pagDeb : pagamentiDebito)
			pagamentiDebitiString += pagDeb.getEpoch() + " " + pagDeb.getPersona() + ' '
					+ pagDeb.getAmmontare().getValore() + '\n';
		return personaleString += '\n' + pagamentiDebitiString + '\n';
	}

}