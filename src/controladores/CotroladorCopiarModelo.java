package controladores;

import java.util.ArrayList;
import interfacesVista.VistaCopiarModelo;
import negocio.Disfraz;
import negocio.Material;
import negocio.Modelo;
import servicios.ServiciosDisfraz;
import servicios.ServiciosMaterial;
import servicios.ServiciosModelo;

public class CotroladorCopiarModelo {

	private ServiciosModelo serviciosModelo;
	private ServiciosDisfraz serviciosDisfraz;
	private ServiciosMaterial serviciosMaterial;
	private ArrayList<Material> todosMateriales;
	private ArrayList<Disfraz> disfraces;
	private Modelo modelo;
	private VistaCopiarModelo vista;

	public void setVista(VistaCopiarModelo vistaCopiarModelo) {
		vista = vistaCopiarModelo;
		vista.mostrarModelo(modelo);
		vista.mostrarMateriales(todosMateriales);
	}

	public CotroladorCopiarModelo(Modelo modelo) {
		this.serviciosModelo = new ServiciosModelo();
		this.serviciosDisfraz = new ServiciosDisfraz();
		this.serviciosMaterial = new ServiciosMaterial();
		todosMateriales = serviciosMaterial.getAll();
		for (Material m : modelo.getMateriales()) {
			todosMateriales.remove(m);
		}
		disfraces = serviciosDisfraz.getDisfrazBy(modelo);
		for (Disfraz d : disfraces) {
			d.setId(0);
		}
		this.modelo = new Modelo(modelo.getNombre(), modelo.getDescripcion(), modelo.getGanancia());

		for (Material m : modelo.getMateriales()) {
			Material copiaMaterial = new Material(m.getNombre(), m.getPrecio(), m.getCantidad(), m.getUnidadMedida());
			copiaMaterial.setId(m.getId());
			this.modelo.agregarMaterial(copiaMaterial);
		}
	}

	public void reemplazarMaterial(int actual, int nuevo) {
		modelo.reemplazarMaterial(actual, todosMateriales.get(nuevo));
		for (Disfraz d : disfraces) {
			d.reemplazarMaterial(actual, todosMateriales.get(nuevo));
		}
	}

	public void guardar() {
		serviciosModelo.guardar(modelo);
		for (Disfraz d : disfraces) {
			d.setModelo(modelo);
			serviciosDisfraz.guardar(d);
		}
		ControladorListas.getInstance().refrescar();
		ControladorModelos.getInstance().seleccionarModelo(modelo);
	}
}
