package negocio;

public class Material implements Comparable<Material> {
	private int id;
	private double cantidad;
	private double precio;
	private String nombre;
	private UnidadMedida unidadMedida;

	public Material(String nombre, double precio, double cantidad, UnidadMedida unidadMedida) {
		this.nombre = nombre;
		this.precio = precio;
		this.cantidad = cantidad;
		this.unidadMedida = unidadMedida;
	}

	public Material() {
	}

	public double getCantidad() {
		return cantidad;
	}

	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public UnidadMedida getUnidadMedida() {
		return unidadMedida;
	}

	public void setUnidadMedida(UnidadMedida unidadMedida) {
		this.unidadMedida = unidadMedida;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object o) {
		Material m;
		boolean b = false;
		if (o instanceof Material) {
			m = (Material) o;
			if (this.getId() == m.getId())
				b = true;
		}
		return b;
	}

	@Override
	public int compareTo(Material o) {
		return this.getNombre().compareToIgnoreCase(o.getNombre());
	}

	@Override
	public String toString() {
		return getNombre();
	}

}
