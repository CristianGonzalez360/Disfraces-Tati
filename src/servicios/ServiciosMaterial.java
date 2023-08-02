package servicios;

import java.util.ArrayList;
import java.util.Collections;

import negocio.Material;
import negocio.Modelo;
import persistencia.DAOMaterial;

public class ServiciosMaterial {

	private DAOMaterial materialDao;

	public ServiciosMaterial() {
		this.materialDao = new DAOMaterial();
	}

	public void guardar(Material m) {
		materialDao.guardar(m);
	}

	public ArrayList<Material> getAll() {
		ArrayList<Material> ret = materialDao.getAll();
		Collections.sort(ret);
		return ret;
	}

	public Material getMaterial(int id) {
		return materialDao.getMaterial(id);
	}

	public void actualizar(Material m) {
		materialDao.actualizar(m);
	}

	public ArrayList<Material> getMaterialBy(Modelo modelo) {

		return materialDao.getMaterialBy(modelo);
	}

	public void eliminar(Material m) {
		materialDao.borrar(m);
	}

}
