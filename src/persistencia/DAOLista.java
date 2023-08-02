package persistencia;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JOptionPane;

import org.w3c.dom.ls.LSInput;

import negocio.Lista;
import negocio.Modelo;

public class DAOLista {

	private Connection conexion;

	public DAOLista() {
		conexion = BDManager.getInstance().getConexion();
	}

	public ArrayList<Lista> getAll() {
		ArrayList<Lista> ret = new ArrayList<Lista>();
		ResultSet rs;
		String query = "SELECT * FROM lista";
		try {
			Statement s = conexion.createStatement();
			rs = s.executeQuery(query);
			while (rs.next()) {
				Lista l = new Lista();
				l.setId(rs.getInt(1));
				l.setNombre(rs.getString(2));
				ret.add(l);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		Collections.sort(ret);
		Lista todos = new Lista();
		todos.setNombre("Todos");
		ret.add(0, todos);

		return ret;
	}

	public void guardar(Lista lista) {
		ResultSet rs;
		String query = "SELECT guardarlista('" + lista.getNombre() + "')";
		try {
			Statement s = conexion.createStatement();
			rs = s.executeQuery(query);
			rs.next();
			lista.setId(rs.getInt(1));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);
		}

		for (Modelo m : lista.getListado()) {
			agregar(lista, m);
		}
	}

	public void eliminar(Lista lista) {
		String query = "DELETE FROM listado WHERE id_lista=" + lista.getId();
		try {
			Statement s = conexion.createStatement();
			s.execute(query);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);
		}

		query = "DELETE FROM lista WHERE id=" + lista.getId();

		try {
			Statement s = conexion.createStatement();
			s.execute(query);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void actualizar(Lista lista) {
		String query = "UPDATE lista SET nombre='" + lista.getNombre() + "' WHERE id=" + lista.getId();
		try {
			Statement s = conexion.createStatement();
			s.execute(query);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		ArrayList<Modelo> actuales = getListado(lista);
		ArrayList<Modelo> listado = lista.getListado();

		// Agregar los q nos estan en la base.
		for (Modelo m : listado) {
			if (!actuales.contains(m)) {
				agregar(lista, m);
			}
		}
		// Borrar los que no estan en el nuevo listado.
		for (Modelo m : actuales) {
			if (!listado.contains(m)) {
				quitar(lista, m);
			}
		}
	}

	private void agregar(Lista lista, Modelo modelo) {
		String query = "INSERT INTO listado(id_lista, id_modelo) VALUES (" + lista.getId() + "," + modelo.getId() + ")";
		try {
			Statement s = conexion.createStatement();
			s.execute(query);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void quitar(Lista lista, Modelo modelo) {
		String query = "DELETE FROM listado WHERE id_lista=" + lista.getId() + "AND id_modelo=" + modelo.getId();
		try {
			Statement s = conexion.createStatement();
			s.execute(query);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}

	public Lista getLista(int id) {
		Lista ret = null;
		if (id != 0) {
			ResultSet rs;
			String query = "SELECT * FROM lista WHERE id=" + id;
			try {
				Statement s = conexion.createStatement();
				rs = s.executeQuery(query);
				rs.next();
				ret = new Lista();
				ret.setId(rs.getInt(1));
				ret.setNombre(rs.getString(2));

			} catch (Exception e) {
				e.printStackTrace();
			}
			ret.setListado(getListado(ret));
		} else {
			DAOModelo daomodelo = new DAOModelo();
			ret = new Lista();
			ret.setNombre("Todos");
			ret.setListado(daomodelo.getAll());
		}
		return ret;
	}

	public ArrayList<Modelo> getListado(Lista lista) {
		ArrayList<Modelo> ret = new ArrayList<>();
		String query = "";
		if (lista.getId() != 0) {
			query = "SELECT * FROM modelo WHERE id IN(SELECT id_modelo FROM listado WHERE  id_lista=" + lista.getId()
					+ ")";
		} else
			query = "SELECT * FROM modelo";
		try {
			Statement s = conexion.createStatement();
			ResultSet rs;
			rs = s.executeQuery(query);
			Modelo m;
			while (rs.next()) {
				m = new Modelo(rs.getString(2), rs.getString(3), rs.getDouble(4));
				m.setId(rs.getInt(1));
				DAOMaterial dao = new DAOMaterial();
				m.setMateriales(dao.getMaterialBy(m));
				ret.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Collections.sort(ret);
		return ret;
	}
	
	public ArrayList<Modelo> buscar(String palabra, Lista lista) {
		ResultSet rs;
		ArrayList<Modelo> ret = new ArrayList<Modelo>();
		
		String query ;
		if(lista.getId()!=0)
			query = "SELECT * FROM modelo WHERE id IN (SELECT id_modelo FROM listado WHERE id_lista = " + lista.getId()+ ") AND nombre ~* '^" + palabra + "'";
		else
			query = "SELECT * FROM modelo WHERE nombre ~* '^" + palabra + "'";
		
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
