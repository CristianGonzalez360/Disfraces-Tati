package interfacesVista;

import java.util.ArrayList;

import negocio.Lista;
import negocio.Material;
import negocio.Modelo;

public interface VistaEditorLista {

	public void mostrarLista(Lista lista);

	public void mostrarModelos(ArrayList<Modelo> modelos);

	public void mostrarMateriales(ArrayList<Material> Materiales);

	public void mostrarListado(ArrayList<Modelo> listado);

	public void mostrarModelos(Modelo modelo);

}
