package vista;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controladores.ControladorModelos;
import interfacesVista.VistaModelos;
import negocio.Modelo;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.ListSelectionModel;

@SuppressWarnings("serial")
public class PanelModelos extends JPanel implements VistaModelos {

	private JTable tabla_modelos;
	private JEditorPane textArea_descripcion;
	private JPanel panel_modelos;
	private JPanel panel_botones;
	private JButton btnNuevo;
	private JButton btnEliminar;
	private JButton btnEditar;
	private JScrollPane scrollPane_descripcion;
	private JButton btnExportar;
	private final DefaultTableModel tm;
	private JButton btnCopiar;
	private ControladorModelos controladorModelos;

	public PanelModelos() {

		this.controladorModelos = new ControladorModelos();

		setPreferredSize(new Dimension(400, 600));
		setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		setLayout(new BorderLayout(0, 0));
		JLabel lbl_disfraces = new JLabel("Disfraces");
		lbl_disfraces.setHorizontalAlignment(SwingConstants.CENTER);
		add(lbl_disfraces, BorderLayout.NORTH);

		panel_modelos = new JPanel();
		add(panel_modelos, BorderLayout.CENTER);

		tabla_modelos = new JTable() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tabla_modelos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabla_modelos.setSelectionBackground(Color.GREEN);
		Object[] nombres = { "Código", "Nombre", "Ganancia" };
		tm = new DefaultTableModel();
		tm.setColumnIdentifiers(nombres);
		tabla_modelos.setModel(tm);
		tabla_modelos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					controladorModelos.seleccionar(tabla_modelos.getSelectedRow());
					controladorModelos.mostrarDetallesModelo();
				}
			}
		});

		tabla_modelos.getTableHeader().setReorderingAllowed(false);

		panel_modelos.setLayout(new BorderLayout(0, 0));
		JScrollPane scrollPane_tabla = new JScrollPane(tabla_modelos);
		panel_modelos.add(scrollPane_tabla, BorderLayout.CENTER);

		textArea_descripcion = new JEditorPane();
		textArea_descripcion.setEditable(false);
		textArea_descripcion.setPreferredSize(new Dimension(322, 50));

		scrollPane_descripcion = new JScrollPane(textArea_descripcion);
		panel_modelos.add(scrollPane_descripcion, BorderLayout.SOUTH);

		panel_botones = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_botones.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		add(panel_botones, BorderLayout.SOUTH);

		btnNuevo = new JButton("Nuevo");
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controladorModelos.nuevoModelo();
			}
		});

		panel_botones.add(btnNuevo);

		btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (tabla_modelos.getSelectedRow() >= 0) {
					controladorModelos.editarModelo();
				} else {
					JOptionPane.showMessageDialog(null, "Seleccione un Disfraz", null, JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		panel_botones.add(btnEditar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (controladorModelos.estaSeleccionado()) {
					if (JOptionPane.showConfirmDialog(null, "¿Está seguro que quiere eliminar este Disfraz?",
							"Seleccione una opción", JOptionPane.YES_NO_OPTION) == 0) {
						controladorModelos.eliminar();
					}
				} else
					JOptionPane.showMessageDialog(null, "Seleccione un Disfraz", null, JOptionPane.INFORMATION_MESSAGE);
			}
		});
		panel_botones.add(btnEliminar);

		btnExportar = new JButton("Exportar");
		btnExportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controladorModelos.exportar();
			}
		});
		panel_botones.add(btnExportar);
		
		btnCopiar = new JButton("Copiar");
		btnCopiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (controladorModelos.estaSeleccionado())
					{
						controladorModelos.copiar();
					}
			 else
				JOptionPane.showMessageDialog(null, "Seleccione un Disfraz", null, JOptionPane.INFORMATION_MESSAGE);
			}
		});
		panel_botones.add(btnCopiar);

		this.controladorModelos.setVista(this);
	}

	@Override
	public void mostrarModelo(Modelo modelo) {
		textArea_descripcion.setText(modelo.getDescripcion());
	}

	@Override
	public void mostrarModelos(final ArrayList<Modelo> modelos, final int seleccionado) {

		tm.setRowCount(0);

		SwingWorker<Boolean, Modelo> worker = new SwingWorker<Boolean, Modelo>() {

			@Override
			protected Boolean doInBackground() throws Exception {

				for (int i = 0; i < modelos.size(); i++) {
					publish(modelos.get(i));
				}
				return null;
			}

			@Override
			protected void done() {
				tabla_modelos.getColumnModel().getColumn(0).setMaxWidth(50);
				tabla_modelos.getColumnModel().getColumn(0).setMinWidth(50);
				tabla_modelos.getColumnModel().getColumn(2).setMaxWidth(65);
				tabla_modelos.getColumnModel().getColumn(2).setMinWidth(65);
				tabla_modelos.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
				textArea_descripcion.setText("");
				if (seleccionado >= 0) {
					tabla_modelos.getSelectionModel().setSelectionInterval(seleccionado, seleccionado);
					tabla_modelos.scrollRectToVisible(tabla_modelos.getCellRect(seleccionado, 0, true));
				}
			}

			@Override
			protected void process(List<Modelo> chunks) {
				for (int i = 0; i < chunks.size(); i++) {
					Object[] row = { chunks.get(i).getId(), chunks.get(i).getNombre(),
							chunks.get(i).getGanancia() + "%" };
					tm.addRow(row);
				}
			}
		};
		worker.execute();
	}

	@Override
	public void editarModelo(Modelo modelo) {
		EditarModelo em = new EditarModelo(modelo);
		em.setLocationRelativeTo(null);
		em.setVisible(true);
	}

	@Override
	public void exportar(ArrayList<Modelo> listado) {
		PanelExportar pe = new PanelExportar(listado);
		pe.setLocationRelativeTo(null);
		pe.setVisible(true);
	}

	@Override
	public void copiarModelo(Modelo modelo) {
		CopiarModelo em = new CopiarModelo(modelo);
		em.setLocationRelativeTo(null);
		em.setVisible(true);
	
	}
}
