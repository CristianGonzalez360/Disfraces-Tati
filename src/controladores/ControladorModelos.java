package controladores;

import interfacesVista.VistaModelos;
import negocio.Lista;
import negocio.Modelo;
import servicios.ServiciosModelo;

public class ControladorModelos {

	private VistaModelos vistaModelos;
	private ServiciosModelo serviciosModelos;
	private Lista lista;
	private Modelo seleccionado;

	private static ControladorModelos instancia;

	static ControladorModelos getInstance() {
		return instancia;
	}

	public void setVista(VistaModelos vistaModelos) {
		this.vistaModelos = vistaModelos;
	}

	public ControladorModelos() {
		this.serviciosModelos = new ServiciosModelo();
		instancia = this;
	}

	public void nuevoModelo() {
		vistaModelos.editarModelo(new Modelo());
	}

	public void editarModelo() {
		if (estaSeleccionado())
			vistaModelos.editarModelo(seleccionado);
	}

	public void mostrarModelos() {
		if (lista == null)
			vistaModelos.mostrarModelos(serviciosModelos.getAll(), lista.getListado().indexOf(seleccionado));
		else
			vistaModelos.mostrarModelos(lista.getListado(), lista.getListado().indexOf(seleccionado));
	}

	public void eliminar() {
		if (estaSeleccionado()) {
			serviciosModelos.eliminar(seleccionado);
			lista.getListado().remove(seleccionado);
			deseleccionar();
			vistaModelos.mostrarModelos(lista.getListado(), lista.getListado().indexOf(seleccionado));
		}
	}

	public void mostrarDetallesModelo() {
		if (estaSeleccionado())
			vistaModelos.mostrarModelo(seleccionado);
	}

	public void seleccionar(int id) {
		if (id >= 0) {
			seleccionado = serviciosModelos.getModelo(lista.getListado().get(id).getId());
			ControladorDisfraces.getInstance().mostrarDisfraces(seleccionado);
		}
	}

	public void deseleccionar() {
		this.seleccionado = null;
		ControladorDisfraces.getInstance().mostrarDisfraces(seleccionado);
	}

	public boolean estaSeleccionado() {
		boolean ret = false;
		if (this.seleccionado != null)
			ret = true;
		return ret;
	}

	public void mostrarModelos(Lista lista) {
		this.lista = lista;
		deseleccionar();
		mostrarModelos();
	}

	void seleccionarModelo(Modelo modelo) {
		int indice = lista.getListado().indexOf(modelo);
		seleccionado = lista.getListado().get(indice);
		vistaModelos.mostrarModelos(lista.getListado(), indice);
	}

	public void exportar() {
		vistaModelos.exportar(lista.getListado());
	}

	public void copiar() {
		if(estaSeleccionado()){
			vistaModelos.copiarModelo(seleccionado);
		}
	}
}
