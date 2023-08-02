package interfacesVista;

import java.util.ArrayList;

import negocio.Material;
import negocio.Modelo;

public interface VistaEditorModelo {

	public void mostrarMateriales(ArrayList<Material> todos);

	public void mostrarModelo(Modelo modelo);

	public void mostrarModelos(ArrayList<Modelo> modelos);

}
