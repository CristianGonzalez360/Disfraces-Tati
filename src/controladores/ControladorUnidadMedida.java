package controladores;

import java.util.ArrayList;

import negocio.UnidadMedida;
import persistencia.DAOUnidadMedida;

public class ControladorUnidadMedida {

	public static void guardar(UnidadMedida um) {
		DAOUnidadMedida.getInstance().guardar(um);
	}

	public static ArrayList<UnidadMedida> getAll() {
		return DAOUnidadMedida.getInstance().getAll();
	}
}
