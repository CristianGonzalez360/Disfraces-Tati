package negocio;

public class UnidadMedida {
	private int id;
	private String nombre;

	public UnidadMedida(String nombre) {
		this.setNombre(nombre);
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return getNombre();
	}

	@Override
	public boolean equals(Object o) {
		UnidadMedida m;
		boolean b = false;
		if (o instanceof UnidadMedida) {
			m = (UnidadMedida) o;
			if (this.getNombre().toUpperCase().equals(m.nombre.toUpperCase()))
				b = true;
		}
		return b;
	}
}
