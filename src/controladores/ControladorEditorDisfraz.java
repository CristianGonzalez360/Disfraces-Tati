package controladores;

import java.util.ArrayList;

import interfacesVista.VistaEditorDisfraz;
import negocio.Disfraz;
import negocio.Material;
import negocio.MaterialUsado;
import negocio.Modelo;
import negocio.Talle;
import persistencia.DAOTalle;
import servicios.ServiciosDisfraz;
import servicios.ServiciosMaterial;
import servicios.ServiciosTalle;

public class ControladorEditorDisfraz {

	private Disfraz disfraz;
	private ServiciosDisfraz serviciosDisfraz;
	private ServiciosMaterial serviciosMaterial;
	private VistaEditorDisfraz vistaEditorDisfraz;
	private ServiciosTalle serviciosTalle;

	private Talle talleSeleccionado;
	private ArrayList<Talle> todosTalles;

	private ArrayList<ArrayList<Disfraz>> todosDisfraces;
	private ArrayList<Disfraz> disfracesSeleccionados;

	private ArrayList<Material> todosMateriales;
	private ArrayList<MaterialUsado> nuevosMateriales;

	public void setVista(VistaEditorDisfraz vistaEditorDisfraz) {
		this.vistaEditorDisfraz = vistaEditorDisfraz;
		vistaEditorDisfraz.mostrarTalles(serviciosTalle.getAll());
		for (ArrayList<Disfraz> disfraces : todosDisfraces) {
			vistaEditorDisfraz.mostrarDisfraces(disfraces);
		}
		vistaEditorDisfraz.mostrarDisfraz(disfraz);
		vistaEditorDisfraz.mostrarMaterialesUsados(serviciosMaterial.getMaterialBy(disfraz.getModelo()));
		vistaEditorDisfraz.previewPrecio();
	}

	public ControladorEditorDisfraz(Disfraz disfraz) {
		this.disfraz = disfraz;
		this.serviciosTalle = new ServiciosTalle();
		this.todosTalles = this.serviciosTalle.getAll();

		this.serviciosDisfraz = new ServiciosDisfraz();
		todosDisfraces = new ArrayList<>();
		disfracesSeleccionados = new ArrayList<>();
		for (Modelo usado : this.disfraz.getModelo().getModelosUsados()) {
			todosDisfraces.add(this.serviciosDisfraz.getDisfrazBy(usado));
		}

		this.serviciosMaterial = new ServiciosMaterial();
		todosMateriales = this.serviciosMaterial.getMaterialBy(disfraz.getModelo());
		nuevosMateriales = this.disfraz.getMateriales();
	}

	public void guardar() {
		disfraz.setDisfraces(disfracesSeleccionados);
		disfraz.setTalle(talleSeleccionado);
		if (disfraz.getId() == 0) {
			serviciosDisfraz.guardar(disfraz);
			ControladorDisfraces.getInstance().recargar();
			ControladorDisfraces.getInstance().seleccionarDisfraz(disfraz);
		} else
			actualizar();
	}

	public void actualizar() {
		serviciosDisfraz.actualizar(disfraz);
		ControladorDisfraces.getInstance().refrescar();
	}

	public void agregarMaterial(int indice, double cantidad) {
		if (disfraz.getId() == 0)
			nuevosMateriales.add(new MaterialUsado(todosMateriales.get(indice), cantidad));
		else
			nuevosMateriales.get(indice).setCantidad(cantidad);
	}

	public void seleccionarTalle(int indice) {
		if (indice > -1 && indice < todosTalles.size()) {
			talleSeleccionado = todosTalles.get(indice);
		}
	}

	public void nuevoTalle(String valorTalle) {
		talleSeleccionado = new Talle(valorTalle);
		if (todosTalles.contains(talleSeleccionado)) {
			talleSeleccionado = todosTalles.get(todosTalles.indexOf(talleSeleccionado));
		} else {
			serviciosTalle.guardar(talleSeleccionado);
		}
	}

	public void agragarDisfraz(int indice, int indice2) {
		disfracesSeleccionados.add(todosDisfraces.get(indice).get(indice2));
	}

	public void seleccionarDisfraz(int indice, int indice2) {
		vistaEditorDisfraz.mostrarCostoDisfraz(todosDisfraces.get(indice).get(indice2).getCosto(), indice);
		vistaEditorDisfraz.previewPrecio();
	}

}
