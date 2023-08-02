package controladores;

import java.util.ArrayList;

import interfacesVista.VistaListas;
import negocio.Lista;
import servicios.ServiciosListas;

public class ControladorListas {

	private ArrayList<Lista> listas;
	private Lista seleccionada;
	private VistaListas vistaListas;
	private ServiciosListas serviciosListas;

	private static ControladorListas instancia;

	static ControladorListas getInstance() {
		return instancia;
	}

	public void setVista(VistaListas vistaListas) {
		this.vistaListas = vistaListas;
		mostrarListas();
	}

	public ControladorListas(ServiciosListas serviciosListas) {
		this.serviciosListas = serviciosListas;
		this.instancia = this;
	}

	public void mostrarListas() {
		this.listas = serviciosListas.getAll();
		int indice = listas.indexOf(seleccionada);
		if (indice == -1) {
			this.vistaListas.mostrarListas(listas, 0);
		} else
			this.vistaListas.mostrarListas(listas, indice);

	}

	public void seleccionar(int indice) {
		this.seleccionada = serviciosListas.getLista(listas.get(indice).getId());
		ControladorModelos.getInstance().mostrarModelos(seleccionada);
	}

	public void nuevaLista() {
		vistaListas.editarLista(new Lista());
	}

	public void editarLista() {
		if (seleccionada != null && seleccionada.getId() != 0)
			vistaListas.editarLista(seleccionada);
	}

	public void eliminar() {
		if (seleccionada != null && seleccionada.getId() != 0) {
			serviciosListas.eliminar(seleccionada);
			listas.remove(seleccionada);
			seleccionada = listas.get(0);
			vistaListas.mostrarListas(listas, 0);
			ControladorModelos.getInstance().mostrarModelos(seleccionada);
		}
	}

	public void refrescar() {
		this.seleccionada.setListado(serviciosListas.getListado(seleccionada));
	}

	public void buscar(String palabra) {
		this.seleccionada.setListado(serviciosListas.buscar(palabra, seleccionada));
		ControladorModelos.getInstance().mostrarModelos(seleccionada);
	}
	
}
