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
import negocio.Modelo;
import negocio.Talle;

public class DAODisfraz {

	private Connection conexion;

	public DAODisfraz() {
		conexion = BDManager.getInstance().getConexion();
	}

	public void guardar(Disfraz disfraz) {
		ResultSet rs;
		String query = "SELECT guardardisfraz(" + disfraz.getTalle().getId() + "," + disfraz.getModelo().getId() + ")";
		try {
			Statement s = conexion.createStatement();
			rs = s.executeQuery(query);
			rs.next();
			disfraz.setId(rs.getInt(1));
			s.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		DAOMaterialUsado dao = new DAOMaterialUsado();
		for (MaterialUsado mu : disfraz.getMateriales()) {
			dao.guardar(new Usado(disfraz, mu.getMaterial(), mu.getCantidad()));
		}

		for (Disfraz d : disfraz.getDisfraces()) {
			query = "SELECT guardardisfrazusado(" + disfraz.getId() + "," + d.getId() + ")";
			Statement s;
			try {
				s = conexion.createStatement();
				rs = s.executeQuery(query);
				s.close();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);
			}

		}
	}

	public Disfraz getDisfraz(int id) {
		ResultSet rs;
		Disfraz ret = null;
		String query = "SELECT * From disfraz WHERE id=" + id;
		try {
			Statement s = conexion.createStatement();
			rs = s.executeQuery(query);
			rs.next();

			DAOTalle daot = new DAOTalle();
			Talle t = daot.getTalle(rs.getInt(2));

			DAOModelo dao = new DAOModelo();
			Modelo modelo = dao.getModelo(rs.getInt(3));

			ret = new Disfraz(t, modelo);
			ret.setId(id);
			
			DAOMaterialUsado dao2 = new DAOMaterialUsado();			
			ret.setMateriales(dao2.getMaterialUsadoBy(ret));
			
			DAOMaterial daoMaterial = new DAOMaterial();
			ArrayList<Material> materiales = daoMaterial.getMaterialBy(modelo);
			for(Material m : materiales){
				if(!ret.usa(m))
					ret.agregarMaterial(m, 0);
			}
			
			ret.setDisfraces(getDisfracesUsados(ret));

			s.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);
		}

		return ret;
	}
	
	private ArrayList<Disfraz> getDisfracesUsados(Disfraz disfraz) {
		ArrayList<Disfraz> ret = new ArrayList<>();
		String query = "SELECT id FROM disfraz WHERE id IN(SELECT disfrazusado FROM disfrazusado WHERE disfraz="
				+ disfraz.getId() + ")";
		ResultSet rs;
		try {
			Statement s = conexion.createStatement();
			rs = s.executeQuery(query);
			while (rs.next()) {
				Disfraz usado = getDisfraz(rs.getInt(1));
				ret.add(usado);
			}
			s.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Collections.sort(ret);
		return ret;
	}

	public ArrayList<Disfraz> getDisfrazByModelo(Modelo modelo) {
		ResultSet rs;
		ArrayList<Disfraz> ret = new ArrayList<Disfraz>();
		String query = "SELECT id FROM disfraz WHERE modelo=" + modelo.getId();
		try {
			Statement s = conexion.createStatement();
			rs = s.executeQuery(query);
			Disfraz disfraz;
			while (rs.next()) {
				disfraz = getDisfraz(rs.getInt(1));
				ret.add(disfraz);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		Collections.sort(ret);
		return ret;
	}

	public void actualizar(Disfraz disfraz) {
		String query = "UPDATE disfraz SET talle=" + disfraz.getTalle().getId() + " WHERE id=" + disfraz.getId();
		try {
			Statement s = conexion.createStatement();
			s.execute(query);
			s.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);

		}
		DAOMaterialUsado dao = new DAOMaterialUsado();
		ArrayList<MaterialUsado> materiales = dao.getMaterialUsadoBy(disfraz);
		for (MaterialUsado mu : disfraz.getMateriales()) {
			if (materiales.contains(mu))
				dao.actualizar(mu);
			else
				dao.guardar(new Usado(disfraz, mu.getMaterial(), mu.getCantidad()));
		}

		ArrayList<Disfraz> actuales = getDisfracesUsados(disfraz);
		for (Disfraz d : disfraz.getDisfraces()) {
			if (!actuales.contains(d))
				agregarDisfraz(disfraz, d);

		}

		for (Disfraz d : actuales) {
			if (!disfraz.getDisfraces().contains(d))
				quitarDisfraz(disfraz, d);
		}

	}

	private void agregarDisfraz(Disfraz disfraz, Disfraz disfrazUsado) {
		String query = "INSERT INTO disfrazusado(disfraz,disfrazusado) VALUES(" + disfraz.getId() + ","
				+ disfrazUsado.getId() + ") ";
		try {
			Statement s = conexion.createStatement();
			s.execute(query);
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void quitarDisfraz(Disfraz disfraz, Disfraz disfrazUsado) {
		String query = "DELETE FROM disfrazusado WHERE disfraz=" + disfraz.getId() + " AND disfrazusado="
				+ disfrazUsado.getId();
		try {
			Statement s = conexion.createStatement();
			s.execute(query);
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void borrar(Disfraz disfraz) {
		DAOMaterialUsado dao = new DAOMaterialUsado();
		for (MaterialUsado mu : disfraz.getMateriales()) {
			dao.borrar(mu);
		}

		String query = "DELETE FROM disfrazusado WHERE disfrazusado=" + disfraz.getId();
		try {
			Statement s = conexion.createStatement();
			s.execute(query);
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		query = "DELETE FROM disfrazusado WHERE disfraz=" + disfraz.getId();
		try {
			Statement s = conexion.createStatement();
			s.execute(query);
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		query = "DELETE FROM disfraz WHERE id=" + disfraz.getId();

		try {
			Statement s = conexion.createStatement();
			s.execute(query);
			s.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);
		}

	}

}
