package interfacesVista;

import negocio.Contacto;

public interface VistaExportar {

	public void mostrarContacto(Contacto contacto);

	public void mostrarBarraProgreso(int size);

	public void mostrarProgreso(int i);

	public void salir();

}
