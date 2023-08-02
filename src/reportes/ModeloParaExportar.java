package reportes;

import java.util.ArrayList;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class ModeloParaExportar {
	
	private String nombre;
	private String descripcion;
	private ArrayList<DisfrazParaExportar> disfraces;

	public ModeloParaExportar() {
		// TODO Auto-generated constructor stub
	}

	public ModeloParaExportar(String nombre, String descripcion) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.disfraces = new ArrayList<>();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public JRDataSource getDisfraces() {
		return new JRBeanCollectionDataSource(disfraces);
	}

	public void setDisfraces(ArrayList<DisfrazParaExportar> disfraces) {
		this.disfraces = disfraces;
	}
	
	public void addDisfraz(DisfrazParaExportar disfraz){
		this.disfraces.add(disfraz);
	}

}
