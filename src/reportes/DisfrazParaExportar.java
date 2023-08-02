package reportes;

import java.util.ArrayList;

import negocio.Disfraz;
import negocio.MaterialUsado;
import negocio.Modelo;
import negocio.Talle;

public class DisfrazParaExportar {
	
	private int id;
	private String talle;
	private double precio;

	public DisfrazParaExportar() {
		// TODO Auto-generated constructor stub
	}

	public DisfrazParaExportar(int id, String talle, double precio) {
		super();
		this.id = id;
		this.talle = talle;
		this.precio = precio;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTalle() {
		return talle;
	}

	public void setTalle(String talle) {
		this.talle = talle;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

}
