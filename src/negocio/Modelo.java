package negocio;

import java.util.ArrayList;

public class Modelo implements Comparable<Modelo> {
	private int id;
	private String nombre;
	private String descripcion;
	private Double ganancia;
	private ArrayList<Material> materiales;
	private ArrayList<Modelo> modelosUsados;

	public Double getGanancia() {
		return ganancia;
	}

	public void setGanancia(Double ganancia) {
		this.ganancia = ganancia;
	}

	public Modelo(String nombre, String descripcion, double ganancia) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.ganancia = ganancia;
		this.materiales = new ArrayList<Material>();
		this.modelosUsados = new ArrayList<Modelo>();
	}

	public Modelo() {
		this.materiales = new ArrayList<Material>();
		this.modelosUsados = new ArrayList<Modelo>();
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<Material> getMateriales() {
		return materiales;
	}

	public void setMateriales(ArrayList<Material> materiales) {
		this.materiales = materiales;
	}

	public void agregarMaterial(Material material) {
		materiales.add(material);
	}

	@Override
	public int compareTo(Modelo o) {
		return this.getNombre().toUpperCase().compareTo(o.getNombre().toUpperCase());
	}

	public boolean usa(Material material) {
		boolean ret = false;
		for (Material m : materiales) {
			if (m.getId() == material.getId())
				ret = true;
		}
		return ret;
	}

	@Override
	public boolean equals(Object o) {
		boolean ret = false;
		if (o instanceof Modelo) {
			Modelo m = (Modelo) o;
			if (this.getId() == m.getId())
				ret = true;
		}
		return ret;
	}

	public ArrayList<Modelo> getModelosUsados() {
		return modelosUsados;
	}

	public void setModelos(ArrayList<Modelo> modelosUsados) {
		this.modelosUsados = modelosUsados;
	}

	public boolean usa(Modelo modelo) {
		return modelosUsados.contains(modelo);
	}
	
	public void reemplazarMaterial(int actual, Material nuevo){
		if(!materiales.contains(nuevo)){
			materiales.set(actual, nuevo);
		}
	}
}
