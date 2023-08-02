package controladores;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingWorker;

import interfacesVista.VistaExportar;
import negocio.Contacto;
import negocio.Disfraz;
import negocio.Modelo;
import persistencia.Serializador;
import reportes.DisfrazParaExportar;
import reportes.ModeloParaExportar;
import reportes.Reportador;
import servicios.ServiciosDisfraz;

public class ControladorExportar {

	private ServiciosDisfraz serviciosDisfraz;
	private List<Modelo> modelos;
	private VistaExportar vista;
	private Contacto contacto;

	public void setVista(VistaExportar vistaExportar) {
		vista = vistaExportar;
		vista.mostrarContacto(contacto);
	}

	public ControladorExportar(List<Modelo> modelos) {
		this.serviciosDisfraz = new ServiciosDisfraz();
		this.modelos = modelos;
		contacto = Serializador.deserializar();
	}

	public void guardar() {
		Serializador.serializar(contacto);
	}

	public void reportar() {

		final ArrayList<ModeloParaExportar> listaModelos = new ArrayList<>();
		vista.mostrarBarraProgreso(modelos.size());

		SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {

			@Override
			protected Boolean doInBackground() throws Exception {
				int i = 0;
				for (Modelo modelo : modelos) {
					ModeloParaExportar mpe = new ModeloParaExportar(modelo.getNombre(), modelo.getDescripcion());
					for (Disfraz disfraz : serviciosDisfraz.getDisfrazBy(modelo)) {
						mpe.addDisfraz(new DisfrazParaExportar(disfraz.getId(), disfraz.getTalle().getValor(),
								disfraz.getPrecio()));
						for (int e = 0; e < 5000; e++) {

						}
					}
					listaModelos.add(mpe);
					publish(++i);
				}
				return null;
			}

			@Override
			protected void process(List<Integer> chunks) {
				for (Integer i : chunks) {
					vista.mostrarProgreso(i);
				}
			}

			@Override
			protected void done() {
				Reportador r = new Reportador();
				r.reportar(contacto, listaModelos);
				vista.salir();
				r.mostrar();
			}
		};
		worker.execute();
	}

}
