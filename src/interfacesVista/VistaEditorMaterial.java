package interfacesVista;

import java.util.ArrayList;

import negocio.Material;
import negocio.UnidadMedida;

public interface VistaEditorMaterial {

	public void mostrarMaterial(Material material);

	public void mostrarUnidadesDeMedida(ArrayList<UnidadMedida> unidades);

}
