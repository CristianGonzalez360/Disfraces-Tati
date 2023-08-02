package persistencia;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JOptionPane;

import negocio.Talle;

public class DAOTalle {
	private Connection conexion;
	private static DAOTalle instancia = null;

	public DAOTalle() {
		conexion = BDManager.getInstance().getConexion();
	}

	/**
	 * Guarda un Talle y setea el id que le corresponde.
	 * 
	 * @param talle
	 *            El Talle a guardar.
	 */
	public void guardar(Talle talle) {
		ResultSet rs;
		String query = "SELECT guardarTalle('" + talle.getValor() + "')";
		try {
			Statement s = conexion.createStatement();
			rs = s.executeQuery(query);
			rs.next();
			talle.setId(rs.getInt(1));
			s.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Obter un Talle por id
	 * 
	 * @param id
	 *            El id del Talle requerido.
	 * @return El Talle pedido, null si no existe.
	 */
	public Talle getTalle(int id) {
		ResultSet rs;
		Talle ret = null;
		String query = "SELECT * From talle WHERE id=" + id;
		try {
			Statement s = conexion.createStatement();
			rs = s.executeQuery(query);
			rs.next();
			ret = new Talle(rs.getString(2));
			ret.setId(id);
			s.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);

		}

		return ret;
	}

	/**
	 * Obtener todos los Talles guardados.
	 * 
	 * @return Un Array con todos los Talles.
	 */
	public ArrayList<Talle> getAll() {
		ResultSet rs;
		ArrayList<Talle> ret = new ArrayList<Talle>();
		String query = "SELECT * From talle";
		try {
			Statement s = conexion.createStatement();
			rs = s.executeQuery(query);
			Talle t;
			while (rs.next()) {
				t = new Talle(rs.getString(2));
				t.setId(rs.getInt(1));
				ret.add(t);
			}
			s.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);

		}
		Collections.sort(ret);
		return ret;
	}

}
