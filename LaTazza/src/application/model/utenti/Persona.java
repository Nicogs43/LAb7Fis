package application.model.utenti;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;

import application.utils.Euro;
import connectionDB.ConnectionFactory;

public class Persona extends Cliente {

	private Euro debito;

	public static final Comparator<Persona> SortListPersona = (Persona p1, Persona p2) -> p1.getNome()
			.compareToIgnoreCase(p2.getNome());

	public Persona(String nome) {
		super(nome);
		debito = new Euro(0);
	}

	public Persona(String nome, Euro debito) {
		super(nome);
		this.debito = debito;
	}

	public String getNome() {
		return super.getNome();
	}

	public Euro getDebito() {
		return debito;
	}

	public boolean aumentaDebito(Euro euro) {
		if (euro.getValore() < 0)
			return false;
		debito.somma(euro);
		Connection connection = ConnectionFactory.getConnection();
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM Persona WHERE Nome =?");
			ps.setString(1, this.getNome());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Integer Debito = rs.getInt("Debito");
				Euro newDebito = new Euro(0, Debito);
				newDebito.somma(euro);
				PreparedStatement ps1 = connection.prepareStatement("UPDATE Persona SET Debito =? WHERE Nome = ?");
				ps1.setString(2, this.getNome());
				ps1.setInt(1, (int) newDebito.getValore());
				int i = ps1.executeUpdate();
				if (i == 1)
					return true;
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return false;
	}

	public boolean diminuisciDebito(Euro euro) {
		Connection connection = ConnectionFactory.getConnection();
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM Persona WHERE Nome =?");
			ps.setString(1, this.getNome());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Integer Debito = rs.getInt("Debito");
				if (euro.getValore() < 0 || euro.getValore() > Debito) {
					debito.sottrai(euro);
					PreparedStatement ps1 = connection.prepareStatement("UPDATE Persona SET Debito =? WHERE Nome = ?");
					ps1.setString(2, this.getNome());
					ps1.setInt(1, (int) debito.getValore());
					int i = ps1.executeUpdate();
					if (i == 1) {
						return true;
					}

				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;

	}

	@Override
	public String toString() {
		return super.getNome();
	}

}