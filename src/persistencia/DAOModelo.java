package persistencia;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JOptionPane;

import negocio.Disfraz;
import negocio.Lista;
import negocio.Material;
import negocio.MaterialUsado;
import negocio.Modelo;
import negocio.Usa;

public class DAOModelo {

	private Connection conexion;

	public DAOModelo() {
		conexion = BDManager.getInstance().getConexion();
	}

	public void guardar(Modelo modelo) {
		ResultSet rs;
		String query = "SELECT guardarmodelo('" + modelo.getNombre() + "','" + modelo.getDescripcion() + "',"
				+ modelo.getGanancia() + ")";
		try {
			Statement s = conexion.createStatement();
			rs = s.executeQuery(query);
			rs.next();
			modelo.setId(rs.getInt(1));
			s.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		DAOUsa dao = new DAOUsa();
		for (Material m : modelo.getMateriales()) {
			dao.guardar(new Usa(modelo, m));
		}

		for (Modelo m : modelo.getModelosUsados()) {
			try {
				Statement s = conexion.createStatement();
				query = "INSERT INTO modelousado(modelo, modelousado)VALUES (" + modelo.getId() + "," + m.getId() + ")";
				s.execute(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public Modelo getModelo(int id) {
		ResultSet rs;
		Modelo ret = null;
		String query = "SELECT * From modelo WHERE id=" + id;
		try {
			Statement s = conexion.createStatement();
			rs = s.executeQuery(query);
			rs.next();
			ret = new Modelo(rs.getString(2), rs.getString(3), rs.getDouble(4));
			ret.setId(rs.getInt(1));
			s.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		DAOMaterial dao = new DAOMaterial();
		ret.setMateriales(dao.getMaterialBy(ret));

		DAOMaterial daomodelo = new DAOMaterial();
		ret.setMateriales(daomodelo.getMaterialBy(ret));

		ret.setModelos(getModelosUsados(ret));

		return ret;

	}

	public ArrayList<Modelo> getAll() {
		ResultSet rs;
		ArrayList<Modelo> ret = new ArrayList<Modelo>();
		String query = "SELECT * From modelo";
		try {
			Statement s = conexion.createStatement();
			rs = s.executeQuery(query);
			Modelo m;
			while (rs.next()) {
				m = new Modelo(rs.getString(2), rs.getString(3), rs.getDouble(4));
				m.setId(rs.getInt(1));
				ret.add(m);
			}
			s.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);
		}

		Collections.sort(ret);
		return ret;
	}

	public void actualizar(Modelo modelo) {
		String query = "UPDATE modelo SET nombre='" + modelo.getNombre() + "',descripcion='" + modelo.getDescripcion()
				+ "',ganancia=" + modelo.getGanancia() + " WHERE id =" + modelo.getId();
		try {
			Statement s = conexion.createStatement();
			s.execute(query);
			s.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);

		}
		DAOUsa dao = new DAOUsa();
		ArrayList<Usa> usadosActuales = dao.getUsaBy(modelo);
		// cuales hay que agregar?
		for (Material m : modelo.getMateriales()) {
			boolean esta = false;// Ya esta guardado?
			for (Usa u : usadosActuales) {
				if (m.getId() == u.getMaterial().getId()) {
					esta = true;// Ya esta guardado.
				}
			}
			if (!esta)// No esta, Hay que guardarlo.
			{
				dao.guardar(new Usa(modelo, m));
			}
		}
		// Cuales hay que borrar?
		for (Usa u : usadosActuales) {
			boolean usado = false;// Todavia es usado?
			for (Material m : modelo.getMateriales()) {
				if (m.getId() == u.getMaterial().getId()) {
					usado = true;// Si, no hay que borrarlo
				}
			}
			if (!usado)// No se usa mas, Hay que borrarlo.
			{
				dao.borrar(u);
			}
		}

		// Actualizar modelos usados
		ArrayList<Modelo> modelosUsados = getModelosUsados(modelo);

		for (Modelo m : modelo.getModelosUsados()) {
			if (!modelosUsados.contains(m)) {
				agregarUsado(modelo, m);
			}
		}
		for (Modelo m : modelosUsados) {
			if (!modelo.getModelosUsados().contains(m)) {
				quitarUsado(modelo, m);
			}
		}

	}

	private void agregarUsado(Modelo modelo, Modelo modeloUsado) {
		String query = "INSERT INTO modelousado(modelo, modelousado) VALUES (" + modelo.getId() + ", "
				+ modeloUsado.getId() + ")";
		try {
			Statement s = conexion.createStatement();
			s.execute(query);
			s.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void quitarUsado(Modelo modelo, Modelo modeloUsado) {
		String query = "DELETE FROM disfrazusado WHERE disfrazusado IN(SELECT id FROM disfraz WHERE modelo= "
				+ modeloUsado.getId() + ") AND disfraz IN(SELECT id FROM disfraz WHERE modelo = " + modelo.getId()
				+ ")";
		try {
			Statement s = conexion.createStatement();
			s.execute(query);
			s.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		query = "DELETE FROM modelousado WHERE modelo =" + modelo.getId() + "AND modelousado=" + modeloUsado.getId();
		try {
			Statement s = conexion.createStatement();
			s.execute(query);
			s.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private ArrayList<Modelo> getModelosUsados(Modelo modelo) {
		ArrayList<Modelo> ret = new ArrayList<>();
		String query = "SELECT * FROM modelo WHERE id IN(SELECT modelousado FROM modelousado WHERE modelo="
				+ modelo.getId() + ")";
		ResultSet rs;
		try {
			Statement s = conexion.createStatement();
			rs = s.executeQuery(query);
			while (rs.next()) {
				Modelo m = new Modelo(rs.getString(2), rs.getString(3), rs.getDouble(4));
				m.setId(rs.getInt(1));
				ret.add(m);
			}
			s.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Collections.sort(ret);
		return ret;
	}

	public void eliminar(Modelo modelo) {
		// Eliminar usa
		DAOUsa dao3 = new DAOUsa();
		ArrayList<Usa> usados = dao3.getUsaBy(modelo);
		for (Usa u : usados) {
			dao3.borrar(u);
		}

		// Eliminar de las listas
		String query = "DELETE FROM listado WHERE id_modelo=" + modelo.getId();
		try {
			Statement s = conexion.createStatement();
			s.execute(query);
			s.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);
		}

		// Eliminar disfraces
		DAODisfraz dao = new DAODisfraz();
		ArrayList<Disfraz> disfraces = dao.getDisfrazByModelo(modelo);
		for (Disfraz d : disfraces) {
			dao.borrar(d);
		}

		query = "DELETE FROM modelousado WHERE modelousado=" + modelo.getId();
		try {
			Statement s = conexion.createStatement();
			s.execute(query);
			s.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);
		}

		query = "DELETE FROM modelousado WHERE modelo=" + modelo.getId();

		try {
			Statement s = conexion.createStatement();
			s.execute(query);
			s.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);
		}

		query = "DELETE FROM modelo WHERE id=" + modelo.getId();
		try {
			Statement s = conexion.createStatement();
			s.execute(query);
			s.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);
		}

	}

	public ArrayList<Modelo> getModeloBy(Material material) {
		ResultSet rs;
		ArrayList<Modelo> ret = new ArrayList<Modelo>();
		String query = "SELECT * FROM modelo WHERE id IN (SELECT modelo FROM usa WHERE material =" + material.getId()
				+ ")";
		try {
			Statement s = conexion.createStatement();
			rs = s.executeQuery(query);
			Modelo modelo;
			while (rs.next()) {
				modelo = new Modelo(rs.getString(2), rs.getString(3), rs.getDouble(4));
				modelo.setId(rs.getInt(1));
				DAOMaterial dao = new DAOMaterial();
				modelo.setMateriales(dao.getMaterialBy(modelo));
				ret.add(modelo);
			}
			s.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		Collections.sort(ret);
		return ret;
	}

}
