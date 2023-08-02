package persistencia;

import negocio.Disfraz;
import negocio.Material;

public class Usado {
	private Disfraz disfraz;
	private Material material;
	private double cantidad;
	private int id;

	public Usado(Disfraz disfraz, Material material, double cantidad) {
		this.disfraz = disfraz;
		this.material = material;
		this.cantidad = cantidad;
	}

	public double getCantidad() {
		return cantidad;
	}

	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public Disfraz getDisfraz() {
		return disfraz;
	}

	public void setDisfraz(Disfraz disfraz) {
		this.disfraz = disfraz;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
