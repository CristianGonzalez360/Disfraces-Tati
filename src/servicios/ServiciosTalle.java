package servicios;

import java.util.ArrayList;

import negocio.Talle;
import persistencia.DAOTalle;

public class ServiciosTalle {
	
	private DAOTalle talleDao;

	public ServiciosTalle() {
		this.talleDao = new DAOTalle();
	}
	
	public ArrayList<Talle> getAll() {

		return talleDao.getAll();
	}

	public void guardar(Talle t) {
		talleDao.guardar(t);
	}

}
