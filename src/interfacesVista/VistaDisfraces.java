package interfacesVista;

import java.util.ArrayList;

import negocio.Disfraz;

public interface VistaDisfraces {

	public void editarDisfraz(Disfraz disfraz);

	public void mostrarDisfraces(ArrayList<Disfraz> disfraces,int seleccionado);

}
