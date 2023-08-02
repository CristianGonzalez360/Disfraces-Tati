package interfacesVista;

import java.util.ArrayList;

import negocio.Disfraz;
import negocio.Material;
import negocio.Talle;

public interface VistaEditorDisfraz {

	public void mostrarDisfraz(Disfraz disfraz);

	public void mostrarMaterialesUsados(ArrayList<Material> materiales);

	public void mostrarTalles(ArrayList<Talle> talles);

	public void mostrarDisfraces(ArrayList<Disfraz> disfraces);

	public void mostrarCostoDisfraz(double costo, int indice);

	public void previewPrecio();
	
}
