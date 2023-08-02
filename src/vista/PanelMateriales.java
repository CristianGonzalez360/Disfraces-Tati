package vista;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

import controladores.ControladorDisfraces;
import controladores.ControladorEditorMaterial;
import controladores.ControladorMateriales;
import controladores.ControladorModelos;
import interfacesVista.VistaMateriales;
import negocio.Material;
import persistencia.DAOMaterial;
import persistencia.DAOUnidadMedida;
import servicios.ServiciosMaterial;
import servicios.ServiciosUnidadMedida;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.FlowLayout;
import javax.swing.border.EtchedBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JCheckBox;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

@SuppressWarnings("serial")
public class PanelMateriales extends JPanel implements VistaMateriales {
	private static JTable tabla_materiales;
	private PanelMateriales pm;
	private ControladorMateriales controladorMateriales;
	final DefaultTableModel tm;

	public PanelMateriales() {

		this.controladorMateriales = new ControladorMateriales();
		setPreferredSize(new Dimension(400, 300));
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		setLayout(new BorderLayout(0, 0));

		JLabel lblMateriales = new JLabel("Materiales");
		lblMateriales.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblMateriales, BorderLayout.NORTH);

		pm = this;

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);

		tabla_materiales = new JTable() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tabla_materiales.setSelectionBackground(Color.GREEN);
		tabla_materiales.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				seleccionar();
			}
		});
		tabla_materiales.getTableHeader().setReorderingAllowed(false);

		Object[] nombres = { "Código", "Nombre", "Precio x Unidad" };
		tm = new DefaultTableModel();
		tm.setColumnIdentifiers(nombres);
		tabla_materiales.setModel(tm);

		scrollPane.setViewportView(tabla_materiales);

		JPanel panel_botones = new JPanel();
		panel_botones.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		FlowLayout flowLayout = (FlowLayout) panel_botones.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		add(panel_botones, BorderLayout.SOUTH);

		JButton btnNuevo = new JButton("Nuevo");
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controladorMateriales.nuevoMaterial();
			}
		});
		panel_botones.add(btnNuevo);

		JButton btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (controladorMateriales.estaSeleccionado()) {
					controladorMateriales.editar();
				} else
					JOptionPane.showMessageDialog(null, "Seleccione un Material", null,
							JOptionPane.INFORMATION_MESSAGE);
			}
		});
		panel_botones.add(btnEditar);

		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (controladorMateriales.estaSeleccionado()) {
					if (JOptionPane.showConfirmDialog(null, "¿Está seguro que quiere eliminar este Material?",
							"Seleccione una opción", JOptionPane.YES_NO_OPTION) == 0) {
						controladorMateriales.eliminar();
					}
				} else
					JOptionPane.showMessageDialog(null, "Seleccione un Material", null,
							JOptionPane.INFORMATION_MESSAGE);
			}
		});
		panel_botones.add(btnEliminar);

		controladorMateriales.setVista(this);
	}

	public void seleccionar() {
		controladorMateriales.seleccionar(tabla_materiales.getSelectedRow());
	}

	@Override
	public void mostrarMateriales(final ArrayList<Material> materiales, final int seleccionado) {

		tm.setRowCount(0);
		
		SwingWorker<Boolean, Material> worker = new SwingWorker<Boolean, Material>() {

			@Override
			protected Boolean doInBackground() throws Exception {
				for (int i = 0; i < materiales.size(); i++) {
					publish(materiales.get(i));
				}
				return null;
			}

			@Override
			protected void done() {
				tabla_materiales.getColumnModel().getColumn(0).setMaxWidth(50);
				tabla_materiales.getColumnModel().getColumn(0).setMinWidth(50);
				tabla_materiales.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
				if (seleccionado >= 0) {
					tabla_materiales.getSelectionModel().setSelectionInterval(seleccionado, seleccionado);
					tabla_materiales.scrollRectToVisible(tabla_materiales.getCellRect(seleccionado, 0, true));
				}
			}

			@Override
			protected void process(List<Material> chunks) {
				for (int i = 0; i < chunks.size(); i++) {
					Object[] row = { chunks.get(i).getId(), chunks.get(i).getNombre(), "$ " + chunks.get(i).getPrecio()
							+ " x " + chunks.get(i).getCantidad() + " " + chunks.get(i).getUnidadMedida().getNombre() };
					tm.addRow(row);
				}
			}
		};
		worker.execute();
	}

	@Override
	public void editar(Material material) {
		ControladorEditorMaterial cem = new ControladorEditorMaterial(material,
				new ServiciosMaterial(), new ServiciosUnidadMedida(new DAOUnidadMedida()));
		EditarMaterial em = new EditarMaterial(cem);
		cem.mostrarMaterial();
		em.setLocationRelativeTo(pm);
		em.setVisible(true);
	}

	public void mostrarMateriales() {
		controladorMateriales.mostrarMateriales();
	}

}
