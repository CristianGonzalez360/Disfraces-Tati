package vista;

import javax.swing.JPanel;

import controladores.ControladorEditorLista;
import controladores.ControladorListas;
import interfacesVista.VistaListas;
import negocio.Lista;
import persistencia.DAOLista;
import persistencia.DAOMaterial;
import persistencia.DAOModelo;
import servicios.ServiciosListas;
import servicios.ServiciosMaterial;
import servicios.ServiciosModelo;

import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PanelListas extends JPanel implements VistaListas {

	private ControladorListas controladorListas;
	private JComboBox comboListas;
	private JTextField buscar;

	public PanelListas(ControladorListas controlador) {

		this.controladorListas = controlador;

		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblListas = new JLabel("Listas");
		add(lblListas);

		comboListas = new JComboBox();
		comboListas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controladorListas.seleccionar(comboListas.getSelectedIndex());
			}
		});
		add(comboListas);

		JButton btnNueva = new JButton("Nueva");
		btnNueva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controladorListas.nuevaLista();
			}
		});
		add(btnNueva);

		JButton btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controladorListas.editarLista();
			}
		});
		add(btnEditar);

		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (JOptionPane.showConfirmDialog(null, "¿Está seguro que quiere eliminar esta Lista?",
						"Seleccione una opción", JOptionPane.YES_NO_OPTION) == 0) {
					controladorListas.eliminar();
				}
			}
		});
		add(btnEliminar);
		
		buscar = new JTextField();
		buscar.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0),"buscar");
		buscar.getActionMap().put("buscar", new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controlador.buscar(buscar.getText());
				
			}
		});
		add(buscar);
		buscar.setColumns(10);
	}

	@Override
	public void mostrarListas(ArrayList<Lista> listas, int seleccionado) {
		DefaultComboBoxModel<?> cm = new DefaultComboBoxModel<>(listas.toArray());
		comboListas.setModel(cm);
		comboListas.setSelectedIndex(seleccionado);
	}

	@Override
	public void editarLista(Lista lista) {
		EditarLista editarLista = new EditarLista(lista,
				new ControladorEditorLista(lista, new ServiciosListas(new DAOLista()),
						new ServiciosModelo(), new ServiciosMaterial()));
		editarLista.setVisible(true);
	}
}
