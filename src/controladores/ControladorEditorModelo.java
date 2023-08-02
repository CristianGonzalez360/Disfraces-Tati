package controladores;

import java.util.ArrayList;

import interfacesVista.VistaEditorModelo;
import negocio.Material;
import negocio.Modelo;
import servicios.ServiciosMaterial;
import servicios.ServiciosModelo;

public class ControladorEditorModelo {

	private VistaEditorModelo vista;
	private ServiciosModelo serviciosModelo;
	private ServiciosMaterial serviciosMaterial;
	private ArrayList<Modelo> todosModelos;
	private ArrayList<Material> todosMateriales;
	private ArrayList<Modelo> nuevosModelos;
	private ArrayList<Material> nuevosMateriales;
	private Modelo modelo;

	public void setVista(VistaEditorModelo vistaEditorModelo) {
		this.vista = vistaEditorModelo;
		vista.mostrarModelo(modelo);
		vista.mostrarMateriales(todosMateriales);
		vista.mostrarModelos(todosModelos);
	}

	public ControladorEditorModelo(Modelo modelo) {
		this.serviciosMaterial = new ServiciosMaterial();
		this.serviciosModelo = new ServiciosModelo();
		this.modelo = modelo;
		this.todosMateriales = this.serviciosMaterial.getAll();
		this.todosModelos = this.serviciosModelo.getAll();
		this.todosModelos.remove(modelo);
		this.nuevosMateriales = new ArrayList<>();
		this.nuevosModelos = new ArrayList<>();
	}

	public void guardar() {
		modelo.setMateriales(nuevosMateriales);
		modelo.setModelos(nuevosModelos);
		if (modelo.getId() == 0) {
			serviciosModelo.guardar(modelo);
			ControladorListas.getInstance().refrescar();
			ControladorModelos.getInstance().seleccionarModelo(modelo);
		} else
			actualizar();
	}

	public void actualizar() {
		this.serviciosModelo.actualizar(modelo);
		ControladorListas.getInstance().refrescar();
		ControladorModelos.getInstance().seleccionarModelo(modelo);
	}

	public void agregarMaterial(int indice) {
		nuevosMateriales.add(todosMateriales.get(indice));
	}

	public void agregarModelo(int indice) {
		nuevosModelos.add(todosModelos.get(indice));
	}

}
