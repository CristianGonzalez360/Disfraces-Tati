package servicios;

import java.util.ArrayList;
import java.util.Collections;

import negocio.Lista;
import negocio.Modelo;
import persistencia.DAOLista;

public class ServiciosListas {

	private DAOLista daoLista;
	
	public ServiciosListas(DAOLista dao) {
		this.daoLista = dao;
	}
	
	public ArrayList<Lista> getAll()
	{
		ArrayList<Lista> ret= daoLista.getAll();
		
		return ret;
	}

	public void guardar(Lista lista) {
		daoLista.guardar(lista);
	}

	public void eliminar(Lista lista) {
		daoLista.eliminar(lista);
	}

	public void actualizar(Lista lista) {
		daoLista.actualizar(lista);
	}

	public Lista getLista(int id){
		return daoLista.getLista(id);
		
	}

	public ArrayList<Modelo> getListado(Lista lista) {
		return daoLista.getListado(lista);
	}
	
	public ArrayList<Modelo> buscar(String palabra, Lista lista){
		return daoLista.buscar(palabra, lista);
	}
}
