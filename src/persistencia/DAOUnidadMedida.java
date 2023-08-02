package persistencia;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import negocio.UnidadMedida;

public class DAOUnidadMedida {
	private Connection conexion;
	private static DAOUnidadMedida instancia = null;

	public DAOUnidadMedida() {
		conexion = BDManager.getInstance().getConexion();
	}

	public static DAOUnidadMedida getInstance() {
		if (instancia == null) {
			instancia = new DAOUnidadMedida();
		}
		return instancia;
	}

	public void guardar(UnidadMedida unidad) {
		ResultSet rs;
		String query = "SELECT guardarunidad('" + unidad.getNombre() + "')";
		try {
			Statement s = conexion.createStatement();
			rs = s.executeQuery(query);
			rs.next();
			unidad.setId(rs.getInt(1));
			s.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);

		}
	}

	public UnidadMedida getUnidadMedida(int id) {
		UnidadMedida ret = null;
		ResultSet rs;
		String query = "SELECT * From unidad WHERE id=" + id;
		try {
			Statement s = conexion.createStatement();
			rs = s.executeQuery(query);
			rs.next();
			ret = new UnidadMedida(rs.getString(1));
			ret.setId(id);
			s.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);

		}

		return ret;
	}

	public ArrayList<UnidadMedida> getAll() {
		ResultSet rs;
		ArrayList<UnidadMedida> ret = new ArrayList<UnidadMedida>();
		String query = "SELECT * From unidad";
		try {
			Statement s = conexion.createStatement();
			rs = s.executeQuery(query);
			UnidadMedida um;
			while (rs.next()) {
				um = new UnidadMedida(rs.getString(1));
				um.setId(rs.getInt(2));
				ret.add(um);
			}
			s.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);

		}

		return ret;
	}
}
