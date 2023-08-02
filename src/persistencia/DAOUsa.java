package persistencia;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import negocio.Material;
import negocio.Modelo;
import negocio.Usa;

public class DAOUsa {

	private Connection conexion;
	private static DAOUsa instancia = null;

	public DAOUsa() {
		conexion = BDManager.getInstance().getConexion();
	}

	public void guardar(Usa usa) {
		String query = "INSERT INTO usa(modelo, material) values (" + usa.getModelo().getId() + ","
				+ usa.getMaterial().getId() + ")";
		try {
			Statement s = conexion.createStatement();
			s.execute(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Usa> getUsaBy(Modelo modelo) {
		ArrayList<Usa> ret = new ArrayList<Usa>();
		ResultSet rs;
		String query = "SELECT * FROM usa WHERE modelo=" + modelo.getId();
		try {
			Statement s = conexion.createStatement();
			rs = s.executeQuery(query);
			Usa u;
			DAOMaterial dao = new DAOMaterial();
			while (rs.next()) {
				Material m = dao.getMaterial(rs.getInt(3));
				u = new Usa(modelo, m);
				u.setId(rs.getInt(1));
				ret.add(u);
			}
			s.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);

		}
		return ret;
	}

	public void borrar(Usa usa) {
		String query = "DELETE FROM usa WHERE id=" + usa.getId();
		try {
			Statement s = conexion.createStatement();
			s.execute(query);
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// Borra los usados.
		query = "DELETE FROM usado WHERE disfraz IN(SELECT id FROM disfraz WHERE modelo=" + usa.getModelo().getId()
				+ ") AND material=" + usa.getMaterial().getId();
		try {
			Statement s = conexion.createStatement();
			s.execute(query);
			s.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);

		}
	}
}
