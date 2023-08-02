package interfacesVista;

import java.util.ArrayList;

import negocio.Lista;
import negocio.Modelo;

public interface VistaListas {

	public void mostrarListas(ArrayList<Lista> listas, int seleccionado);

	public void editarLista(Lista lista);

}
