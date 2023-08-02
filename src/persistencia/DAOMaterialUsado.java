package persistencia;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JOptionPane;

import negocio.Disfraz;
import negocio.Material;
import negocio.MaterialUsado;

public class DAOMaterialUsado {
	private Connection conexion;

	public DAOMaterialUsado() {
		conexion = BDManager.getInstance().getConexion();
	}

	public void guardar(Usado materialUsado) {
		String query = "INSERT INTO usado(disfraz, material, cantidad)VALUES (" + materialUsado.getDisfraz().getId()
				+ "," + materialUsado.getMaterial().getId() + "," + materialUsado.getCantidad() + ")";
		try {
			Statement s = conexion.createStatement();
			s.execute(query);
			s.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);

		}
	}

	public ArrayList<MaterialUsado> getMaterialUsadoBy(Disfraz d) {
		ArrayList<MaterialUsado> ret = new ArrayList<MaterialUsado>();
		ResultSet rs;
		String query = "SELECT * FROM usado WHERE disfraz=" + d.getId();
		try {
			Statement s = conexion.createStatement();
			rs = s.executeQuery(query);
			MaterialUsado mu;
			while (rs.next()) {
				DAOMaterial dao = new DAOMaterial();
				Material m = dao.getMaterial(rs.getInt(3));
				mu = new MaterialUsado(m, rs.getDouble(4));
				mu.setId(rs.getInt(1));
				ret.add(mu);
			}
			s.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		Collections.sort(ret);
		return ret;
	}

	public void actualizar(MaterialUsado mu) {
		String query = "UPDATE usado SET cantidad=" + mu.getCantidad() + " WHERE id=" + mu.getId();
		try {
			Statement s = conexion.createStatement();
			s.execute(query);
			s.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void borrar(MaterialUsado mu) {
		String query = "DELETE FROM usado WHERE id=" + mu.getId();
		try {
			Statement s = conexion.createStatement();
			s.execute(query);
			s.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);

		}
	}
}
