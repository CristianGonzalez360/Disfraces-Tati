package controladores;

import java.util.ArrayList;

import interfacesVista.VistaEditorMaterial;
import negocio.Material;
import negocio.Talle;
import negocio.UnidadMedida;
import servicios.ServiciosMaterial;
import servicios.ServiciosUnidadMedida;

public class ControladorEditorMaterial {

	private Material material;
	private ServiciosMaterial serviciosMaterial;
	private ServiciosUnidadMedida serviciosUnidadMedida;
	private VistaEditorMaterial vistaEditorMaterial;
	private ArrayList<UnidadMedida> todasUnidades;

	public void setVista(VistaEditorMaterial vistaEditorMaterial) {
		this.vistaEditorMaterial = vistaEditorMaterial;
	}

	public ControladorEditorMaterial(Material material, ServiciosMaterial serviciosMaterial,
			ServiciosUnidadMedida serviciosUnidadMedida) {
		this.serviciosMaterial = serviciosMaterial;
		this.serviciosUnidadMedida = serviciosUnidadMedida;
		this.todasUnidades = this.serviciosUnidadMedida.getAll();
		this.material = material;
	}

	public void mostrarMaterial() {
		vistaEditorMaterial.mostrarUnidadesDeMedida(serviciosUnidadMedida.getAll());
		vistaEditorMaterial.mostrarMaterial(material);
	}

	public void guardar() {
		if (material.getId() == 0) {
			this.serviciosMaterial.guardar(material);
			ControladorMateriales.getInstance().seleccionarMaterial(material);
			ControladorMateriales.getInstance().mostrarMateriales();
		} else
			actualizar();
	}

	public void actualizar() {
		this.serviciosMaterial.actualizar(material);
		ControladorMateriales.getInstance().refrescar();
		ControladorListas.getInstance().refrescar();
		ControladorModelos.getInstance().mostrarModelos();
	}

	public void nuevaUnidad(String nombre) {
		UnidadMedida nueva = new UnidadMedida(nombre);
		if (todasUnidades.contains(nueva))
			nueva = todasUnidades.get(todasUnidades.indexOf(nueva));
		material.setUnidadMedida(nueva);
	}

	public void seleccionarUnidad(int indice) {
		if (indice > -1 && indice < todasUnidades.size()) {
			material.setUnidadMedida(todasUnidades.get(indice));
		}
	}
}
