package interfacesVista;

import java.util.ArrayList;

import negocio.Material;

public interface VistaMateriales {

	public void mostrarMateriales(ArrayList<Material> materiales, int seleccionado);

	public void editar(Material material);

}
