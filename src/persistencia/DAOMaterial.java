package persistencia;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JOptionPane;

import negocio.Material;
import negocio.Modelo;
import negocio.UnidadMedida;

public class DAOMaterial {
	private Connection conexion;
	
	public DAOMaterial() {
		conexion = BDManager.getInstance().getConexion();
	}

	public void guardar(Material material) {
		ResultSet rs;
		String query = "SELECT guardarmaterial('" + material.getNombre() + "'," + material.getCantidad() + ","
				+ material.getUnidadMedida().getId() + "," + material.getPrecio() + ")";
		try {
			Statement s = conexion.createStatement();
			rs = s.executeQuery(query);
			rs.next();
			material.setId(rs.getInt(1));
			s.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);

		}
	}

	public Material getMaterial(int id) {
		ResultSet rs;
		Material ret = null;
		String query = "SELECT * From material WHERE id=" + id;
		try {
			Statement s = conexion.createStatement();
			rs = s.executeQuery(query);
			rs.next();
			UnidadMedida um = DAOUnidadMedida.getInstance().getUnidadMedida(rs.getInt(5));
			ret = new Material(rs.getString(4), rs.getDouble(3), rs.getDouble(2), um);
			ret.setId(id);
			s.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);

		}

		return ret;
	}

	public ArrayList<Material> getAll() {
		ResultSet rs;
		ArrayList<Material> ret = new ArrayList<Material>();
		String query = "SELECT * From material";
		try {
			Statement s = conexion.createStatement();
			rs = s.executeQuery(query);
			Material m;
			while (rs.next()) {
				UnidadMedida um = DAOUnidadMedida.getInstance().getUnidadMedida(rs.getInt(5));
				m = new Material(rs.getString(4), rs.getDouble(3), rs.getDouble(2), um);
				m.setId(rs.getInt(1));
				ret.add(m);
			}
			s.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);

		}

		return ret;
	}

	public void actualizar(Material m) {
		String query = "UPDATE material SET nombre='" + m.getNombre() + "',precio=" + m.getPrecio() + ",cantidad="
				+ m.getCantidad() + ",unidad=" + m.getUnidadMedida().getId() + " WHERE id =" + m.getId();
		try {
			Statement s = conexion.createStatement();
			s.execute(query);
			s.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);
		}

	}

	public ArrayList<Material> getMaterialBy(Modelo modelo) {
		ArrayList<Material> ret = new ArrayList<Material>();
		ResultSet rs;
		// Materiales que usa el modelo.
		String query = "SELECT * FROM material WHERE id IN (SELECT material FROM usa WHERE modelo =" + modelo.getId()
				+ ")";
		try {
			Statement s = conexion.createStatement();
			rs = s.executeQuery(query);
			Material m;
			while (rs.next()) {
				UnidadMedida um = DAOUnidadMedida.getInstance().getUnidadMedida(rs.getInt(5));
				m = new Material(rs.getString(4), rs.getDouble(3), rs.getDouble(2), um);
				m.setId(rs.getInt(1));
				ret.add(m);
			}
			s.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);

		}
		Collections.sort(ret);
		return ret;
	}

	public void borrar(Material material) {
		String query = "DELETE FROM usado WHERE material=" + material.getId();
		try {
			Statement s = conexion.createStatement();
			s.execute(query);
			s.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);

		}
		query = "DELETE FROM usa WHERE material=" + material.getId();
		try {
			Statement s = conexion.createStatement();
			s.execute(query);
			s.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);

		}
		query = "DELETE FROM material WHERE id=" + material.getId();
		try {
			Statement s = conexion.createStatement();
			s.execute(query);
			s.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);

		}
	}
}
