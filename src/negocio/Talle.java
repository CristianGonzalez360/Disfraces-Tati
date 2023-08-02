package negocio;

public class Talle implements Comparable<Talle>{
	private int id;
	private String valor;

	public Talle(String valor) {
		this.valor = valor.toUpperCase();
	}

	public String getValor() {
		return valor;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return this.valor;
	}

	@Override
	public boolean equals(Object o) {
		String s;
		boolean b = false;
		if (o instanceof Talle) {
			s = ((Talle) o).getValor();
			if (s.toUpperCase().equals(this.getValor().toUpperCase()))
				b = true;
		}
		return b;
	}
	
	@Override
	public int compareTo(Talle o) {
		int ret = 0;
		try {
			Double d1 = Double.parseDouble(this.getValor());
			Double d2 = Double.parseDouble(o.getValor());
			ret = d1.compareTo(d2);
		} catch (Exception e) {
			ret = this.getValor().toUpperCase().compareTo(o.getValor().toUpperCase());
		}

		return ret;
	}
}
