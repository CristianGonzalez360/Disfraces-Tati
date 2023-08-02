package interfacesVista;

import java.util.ArrayList;

import negocio.Lista;
import negocio.Modelo;

public interface VistaModelos {

	public void editarModelo(Modelo modelo);

	public void mostrarModelo(Modelo modelo);
	
	public void exportar(ArrayList<Modelo> listado);

	public void mostrarModelos(ArrayList<Modelo> modelos, int seleccionado);

	public void copiarModelo(Modelo modelo);
}
