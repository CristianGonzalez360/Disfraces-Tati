package servicios;

import java.util.ArrayList;
import java.util.Collections;

import negocio.Lista;
import negocio.Material;
import negocio.Modelo;
import persistencia.DAOModelo;

public class ServiciosModelo {
	
	private DAOModelo modeloDao;

	public ServiciosModelo() {
		this.modeloDao = new DAOModelo();
	}

	public void guardar(Modelo modelo) {
		modeloDao.guardar(modelo);
	}

	public Modelo getModelo(int id) {
		return modeloDao.getModelo(id);
	}

	public ArrayList<Modelo> getAll() {
		ArrayList<Modelo> modelos = modeloDao.getAll();
		Collections.sort(modelos);
		return modelos;
	}

	public void actualizar(Modelo modelo) {
		modeloDao.actualizar(modelo);
	}

	public void eliminar(Modelo m) {
		modeloDao.eliminar(m);
	}
	
	public ArrayList<Modelo> getModeloBy(Material material) {
		ArrayList<Modelo> modelos = modeloDao.getModeloBy(material);
		Collections.sort(modelos);
		return modelos;
	}
		
}
