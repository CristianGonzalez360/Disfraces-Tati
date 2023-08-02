package negocio;

public class MaterialUsado implements Comparable<MaterialUsado>{
	private int id;
	private Material material;
	private double cantidad;

	public MaterialUsado(Material material, double cantidad) {
		this.material = material;
		this.cantidad = cantidad;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public double getCantidad() {
		return cantidad;
	}

	public void setCantidad(double cantitdad) {
		this.cantidad = cantitdad;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getPrecio() {
		double precio = this.cantidad * this.material.getPrecio();
		precio *= 100;
		precio = Math.round(precio);
		precio /= 100;
		return precio;
	}
	
	@Override 
	public boolean equals(Object o){
		boolean ret = false;
		MaterialUsado usado;
		if(o instanceof MaterialUsado){
			usado = (MaterialUsado)o;
			ret = usado.getId()==this.getId();
		}
		return ret;
	}

	@Override
	public int compareTo(MaterialUsado o) {
		return this.getMaterial().compareTo(o.getMaterial());
	}
}
