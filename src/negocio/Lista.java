package negocio;

import java.util.ArrayList;

public class Lista implements Comparable<Lista>{
	private int id;
	private String nombre;
	private ArrayList<Modelo> listado;

	public Lista() {
		listado = new ArrayList<>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String toString() {
		return this.nombre;
	}

	@Override
	public boolean equals(Object o) {
		boolean ret = false;
		if (o instanceof Lista) {
			Lista l = (Lista) o;
			if (l.getId() == this.getId())
				ret = true;
		}
		return ret;
	}

	@Override
	public int compareTo(Lista o) {
		return this.getNombre().compareToIgnoreCase(o.getNombre());
	}
	
	public void setListado(ArrayList<Modelo> listado){
		this.listado = listado;
	}
	
	public ArrayList<Modelo> getListado(){
		return listado;
	}
	
	public boolean esta(Modelo modelo){
		boolean ret = false;
		if(listado.contains(modelo))
			ret = true;
		return ret;
	}
}
