package controladores;

import java.util.ArrayList;
import java.util.Collections;

import interfacesVista.VistaMateriales;
import negocio.Material;
import servicios.ServiciosMaterial;

public class ControladorMateriales {

	private ArrayList<Material> materiales;
	private Material seleccionado;
	private static ControladorMateriales instancia;
	private VistaMateriales vistaMateriales;
	private ServiciosMaterial serviciosMaterial;

	public void setVista(VistaMateriales vistaMateriales) {
		this.vistaMateriales = vistaMateriales;
		mostrarMateriales();
	}

	public ControladorMateriales() {
		instancia = this;
		this.serviciosMaterial = new ServiciosMaterial();
		this.materiales = new ArrayList<Material>();
		this.seleccionado = null;
	}

	public void mostrarMateriales() {
		materiales = this.serviciosMaterial.getAll();
		Collections.sort(materiales);
		this.vistaMateriales.mostrarMateriales(materiales,materiales.indexOf(seleccionado));
	}

	public void nuevoMaterial() {
		vistaMateriales.editar(new Material());
	}

	public void editar() {
		this.vistaMateriales.editar(seleccionado);
	}

	public void deseleccionar() {
		this.seleccionado = null;
	}

	public void seleccionar(int id) {
		this.seleccionado = this.materiales.get(id);
	}

	public void eliminar() {
		this.serviciosMaterial.eliminar(seleccionado);
		materiales.remove(seleccionado);
		ControladorListas.getInstance().refrescar();
		ControladorModelos.getInstance().mostrarModelos();
		deseleccionar();
		refrescar();
	}

	public boolean estaSeleccionado() {
		boolean ret = false;
		if (seleccionado != null)
			ret = true;
		return ret;
	}

	void seleccionarMaterial(Material material){
		this.seleccionado = material;
	}
	
	public void refrescar() {
		vistaMateriales.mostrarMateriales(materiales,materiales.indexOf(seleccionado));
	}

	static ControladorMateriales getInstance() {
		return instancia;
	}
}
