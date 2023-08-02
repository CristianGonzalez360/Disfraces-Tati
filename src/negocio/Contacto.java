package negocio;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Contacto implements Serializable {

	private String nombre, telefono, email;

	public Contacto(String nombre, String telefono, String email) {
		super();
		this.setNombre(nombre);
		this.setTelefono(telefono);
		this.setEmail(email);
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


}
