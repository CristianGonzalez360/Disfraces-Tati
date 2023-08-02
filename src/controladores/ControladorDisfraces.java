package controladores;

import java.util.ArrayList;

import interfacesVista.VistaDisfraces;
import negocio.Disfraz;
import negocio.Modelo;
import servicios.ServiciosDisfraz;

public class ControladorDisfraces {

	private static ControladorDisfraces instancia;

	
	private ArrayList<Disfraz> disfraces;
	private Disfraz seleccionado;
	private Modelo modelo;
	private VistaDisfraces vistaDisfraces;

	private ServiciosDisfraz serviciosDisfraces;

	public void setVista(VistaDisfraces vd) {
		this.vistaDisfraces = vd;
	}

	public ControladorDisfraces() {
		this.serviciosDisfraces = new ServiciosDisfraz();
		this.disfraces = new ArrayList<Disfraz>();
		instancia = this;
	}

	static ControladorDisfraces getInstance() {
		return instancia;
	}

	public void deseleccionar() {
		if (disfraces.size() > 0)
			seleccionado = disfraces.get(0);
		else
			seleccionado = null;
	}

	public void editarDisfraz() {
		vistaDisfraces.editarDisfraz(seleccionado);
	}

	public void eliminar() {
		if (estaSeleccionado()) {
			serviciosDisfraces.eliminar(seleccionado);
			disfraces.remove(seleccionado);
			deseleccionar();
			vistaDisfraces.mostrarDisfraces(disfraces, disfraces.indexOf(seleccionado));
		}
	}

	public boolean estaSeleccionado() {
		boolean ret = false;
		if (seleccionado != null)
			ret = true;
		return ret;
	}

	public void guardar() {
		serviciosDisfraces.guardar(seleccionado);
		mostrarDisfraces(modelo);
	}

	public void mostrarDisfraces(Modelo modelo) {
		this.modelo = modelo;
		if (modelo != null) {
			this.disfraces = serviciosDisfraces.getDisfrazBy(modelo);
			if (!disfraces.isEmpty()) {
				this.seleccionado = disfraces.get(0);
				vistaDisfraces.mostrarDisfraces(disfraces, 0);
			}
			else {
				deseleccionar();
				vistaDisfraces.mostrarDisfraces(new ArrayList<Disfraz>(), disfraces.indexOf(seleccionado));
			}
		}
	}

	public void nuevoDisfraz() {
		Disfraz nuevo = null;
		if (modelo != null) {
			nuevo = new Disfraz();
			nuevo.setModelo(modelo);
		}
		vistaDisfraces.editarDisfraz(nuevo);
	}

	public void refrescar() {
		vistaDisfraces.mostrarDisfraces(disfraces, disfraces.indexOf(seleccionado));
	}

	public void recargar() {
		this.disfraces = serviciosDisfraces.getDisfrazBy(modelo);
	}

	public void seleccionar(int indice) {
		this.seleccionado = disfraces.get(indice);
	}

	void seleccionarDisfraz(Disfraz disfraz) {
		int indice = disfraces.indexOf(disfraz);
		seleccionado = disfraces.get(indice);
		vistaDisfraces.mostrarDisfraces(disfraces, indice);
	}

}
