package servicios;

import java.util.ArrayList;

import negocio.Disfraz;
import negocio.Modelo;
import persistencia.DAODisfraz;

public class ServiciosDisfraz {
	
	private DAODisfraz disfrazDao;

	public ServiciosDisfraz() {
		this.disfrazDao = new DAODisfraz();	}
	
	public Disfraz getDisfraz(int id) {
		return disfrazDao.getDisfraz(id);
	}

	public void guardar(Disfraz d) {
		disfrazDao.guardar(d);
	}

	public void actualizar(Disfraz d) {
		disfrazDao.actualizar(d);
	}

	public ArrayList<Disfraz> getDisfrazBy(Modelo m) {
		ArrayList<Disfraz> ret = disfrazDao.getDisfrazByModelo(m);
		return ret;
	}

	public void eliminar(Disfraz d) {
		disfrazDao.borrar(d);
	}

}
