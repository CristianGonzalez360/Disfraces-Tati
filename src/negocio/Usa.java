package negocio;

public class Usa {
	private int id;
	private Modelo modelo;
	private Material material;

	public Usa(Modelo modelo, Material material) {
		this.modelo = modelo;
		this.material = material;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Modelo getModelo() {
		return modelo;
	}

	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

}
