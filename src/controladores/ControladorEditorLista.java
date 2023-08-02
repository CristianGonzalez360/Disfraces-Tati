package controladores;

import java.util.ArrayList;

import interfacesVista.VistaEditorLista;
import negocio.Lista;
import negocio.Material;
import negocio.Modelo;
import servicios.ServiciosListas;
import servicios.ServiciosMaterial;
import servicios.ServiciosModelo;

public class ControladorEditorLista {

	private Lista lista;
	private VistaEditorLista vistaEditorLista;
	private ServiciosListas serviciosListas;
	private ServiciosMaterial serviciosMaterial;
	private ServiciosModelo serviciosModelo;
	private ArrayList<Material> todosMateriales;
	private ArrayList<Modelo> todosModelos;
	private ArrayList<Modelo> listado;

	public void setVista(VistaEditorLista vistaEditorLista) {
		this.vistaEditorLista = vistaEditorLista;
		vistaEditorLista.mostrarLista(lista);
		vistaEditorLista.mostrarModelos(todosModelos);
		vistaEditorLista.mostrarMateriales(serviciosMaterial.getAll());
	}

	public ControladorEditorLista(Lista lista, ServiciosListas serviciosListas, ServiciosModelo serviciosModelo,
			ServiciosMaterial serviciosMaterial) {
		this.serviciosListas = serviciosListas;
		this.serviciosMaterial = serviciosMaterial;
		this.serviciosModelo = serviciosModelo;
		this.todosMateriales = serviciosMaterial.getAll();
		this.todosModelos = serviciosModelo.getAll();
		this.listado = new ArrayList<>(lista.getListado());
		this.lista = lista;
	}

	public void guardar() {
		if (lista.getId() == 0) {
			lista.setListado(listado);
			serviciosListas.guardar(lista);
		} else
			actualizar();
		ControladorListas.getInstance().mostrarListas();
	}

	private void actualizar() {
		lista.setListado(listado);
		serviciosListas.actualizar(lista);
	}

	public void agregar(int indice) {
		if (indice >= 0 && indice < todosModelos.size() && !listado.contains(todosModelos.get(indice))) {
			listado.add(todosModelos.get(indice));
			vistaEditorLista.mostrarListado(listado);
		}
	}

	public void quitar(int indice) {
		if (indice >= 0 && indice < listado.size()) {
			listado.remove(listado.get(indice));
			vistaEditorLista.mostrarListado(listado);
		}
	}

	public void seleccionarMaterial(int indice) {
		todosModelos = this.serviciosModelo.getModeloBy(todosMateriales.get(indice));
		vistaEditorLista.mostrarModelos(todosModelos);
	}

	public void agregarTodo() {
		listado.addAll(todosModelos);
		vistaEditorLista.mostrarListado(listado);
	}

	public void seleccionarDeTodos(int indice) {
		vistaEditorLista.mostrarModelos(todosModelos.get(indice));

	}

	public void seleccionarDeListado(int indice) {
		vistaEditorLista.mostrarModelos(todosModelos.get(indice));
	}

}
