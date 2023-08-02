package servicios;

import java.util.ArrayList;

import negocio.UnidadMedida;
import persistencia.DAOUnidadMedida;

public class ServiciosUnidadMedida {

	private DAOUnidadMedida unidadMedidaDao;

	public ServiciosUnidadMedida(DAOUnidadMedida unidadMedidaDao) {
		this.unidadMedidaDao = unidadMedidaDao;
	}

	public void guardar(UnidadMedida um) {
		unidadMedidaDao.guardar(um);
	}

	public ArrayList<UnidadMedida> getAll() {
		return unidadMedidaDao.getAll();
	}

}
