package negocio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Disfraz implements Comparable<Disfraz> {
	private int id;
	private ArrayList<MaterialUsado> materiales;
	private Talle talle;
	private Modelo modelo;
	private ArrayList<Disfraz> disfraces;

	public Disfraz(Talle talle, Modelo modelo) {
		this.talle = talle;
		this.modelo = modelo;
		this.materiales = new ArrayList<MaterialUsado>();
		this.disfraces = new ArrayList<Disfraz>();
	}

	public Disfraz() {
		this.materiales = new ArrayList<MaterialUsado>();
		this.disfraces = new ArrayList<Disfraz>();
	}

	private double calcularPrecio() {
		double precio = 0;
		for (Disfraz d : disfraces) {
			precio += d.getCosto();
		}
		for (MaterialUsado mu : materiales) {
			precio += mu.getMaterial().getPrecio() * mu.getCantidad();
		}
		precio += precio * modelo.getGanancia() / 100;
		precio = Math.ceil(precio);
		return precio;
	}

	public void agregarMaterial(Material m, double cant) {
		boolean yaEsta = false;
		for (MaterialUsado mu : materiales) {
			if (mu.getMaterial().getId() == m.getId()) {
				mu.setCantidad(cant);
				yaEsta = true;
			}
		}
		if (!yaEsta)
			this.materiales.add(new MaterialUsado(m, cant));
		
		Collections.sort(this.materiales);
	}
	public void reemplazarMaterial(int actual, Material material){
		MaterialUsado mu = materiales.get(actual);
		mu.setMaterial(material);
		Collections.sort(materiales);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getPrecio() {
		return calcularPrecio();
	}

	public double getCosto() {
		double costo = 0;
		for (Disfraz d : disfraces) {
			costo += d.getCosto();
		}
		for (MaterialUsado mu : materiales) {
			costo += mu.getMaterial().getPrecio() * mu.getCantidad();
		}
		costo *= 100;
		costo = Math.round(costo);
		costo /= 100;
		return costo;
	}

	public ArrayList<MaterialUsado> getMateriales() {
		return materiales;
	}

	public void setMateriales(ArrayList<MaterialUsado> materiales) {
		this.materiales = materiales;
		calcularPrecio();
	}

	public Talle getTalle() {
		return talle;
	}

	public void setTalle(Talle talle) {
		this.talle = talle;
	}

	public Modelo getModelo() {
		return modelo;
	}

	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

	public double getCantidadDe(Material material) {
		double ret = 0;
		for (MaterialUsado mu : materiales) {
			if (mu.getMaterial().getId() == material.getId())
				ret = mu.getCantidad();
		}
		return ret;
	}

	public ArrayList<Disfraz> getDisfraces() {
		return this.disfraces;
	}

	public void setDisfraces(ArrayList<Disfraz> disfraces) {
		this.disfraces = disfraces;
	}

	@Override
	public boolean equals(Object o) {
		boolean b = false;
		Disfraz d = null;
		if (o instanceof Disfraz) {
			d = (Disfraz) o;
			if (this.id == d.getId())
				b = true;
		}
		return b;
	}

	@Override
	public int compareTo(Disfraz disfraz) {
		int ret = 0;
		if (disfraz.getModelo().equals(this.getModelo())) {
			try {
				Double d1 = Double.parseDouble(this.getTalle().getValor());
				Double d2 = Double.parseDouble(disfraz.getTalle().getValor());
				ret = d1.compareTo(d2);
			} catch (Exception e) {
				ret = this.getTalle().getValor().toUpperCase().compareTo(disfraz.getTalle().getValor().toUpperCase());
			}
		} else
			ret = this.getModelo().compareTo(disfraz.getModelo());
		return ret;
	}
	
	public boolean usa(Material material){
		boolean ret = false;
		for(MaterialUsado mu :materiales){
			if(mu.getMaterial().equals(material))
				ret = true;
		}
		return ret;
		
	}

}
