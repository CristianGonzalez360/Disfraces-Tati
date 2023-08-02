package vista;

import javax.swing.JDialog;
import javax.swing.JButton;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JEditorPane;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import controladores.ControladorEditorModelo;
import interfacesVista.VistaEditorModelo;
import negocio.Material;
import negocio.Modelo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.ListSelectionModel;
import javax.swing.JTabbedPane;

@SuppressWarnings("serial")
public class EditarModelo extends JDialog implements VistaEditorModelo {
	protected JTextField txtNombre;
	protected JTable tablaMateriales;
	protected ArrayList<Material> materiales;
	protected JEditorPane editorPane_descripcion;
	protected JTextField txtGanancia;
	protected ControladorEditorModelo controlador;
	protected Modelo modelo;

	private JTable tablaModelos;

	public EditarModelo(Modelo modelo) {

		this.controlador = new ControladorEditorModelo(modelo);

		setSize(new Dimension(520, 450));
		setPreferredSize(new Dimension(495, 406));
		setResizable(false);
		getContentPane().setSize(new Dimension(50, 0));
		getContentPane().setLayout(null);
		setTitle("Editar Disfraz");

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNombre.setBounds(20, 11, 53, 14);
		getContentPane().add(lblNombre);

		txtNombre = new JTextField();
		txtNombre.setBounds(83, 8, 322, 20);
		getContentPane().add(txtNombre);
		txtNombre.setColumns(10);

		JLabel lblDescripcion = new JLabel("Descripcion:");
		lblDescripcion.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDescripcion.setBounds(-15, 41, 88, 14);
		getContentPane().add(lblDescripcion);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(83, 39, 322, 100);
		getContentPane().add(scrollPane);

		editorPane_descripcion = new JEditorPane();
		scrollPane.setViewportView(editorPane_descripcion);
		editorPane_descripcion.setBorder(new LineBorder(Color.LIGHT_GRAY));

		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guardar();
			}
		});
		btnGuardar.setBounds(415, 7, 89, 23);
		getContentPane().add(btnGuardar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancelar.setBounds(415, 37, 89, 23);
		getContentPane().add(btnCancelar);

		JScrollPane scrollPane_tabla = new JScrollPane();
		scrollPane_tabla.setBounds(10, 281, 494, 130);

		tablaMateriales = new JTable() {
			@Override
			// Para editar solo el checkbox
			public boolean isCellEditable(int row, int column) {
				boolean b = false;
				if (column == 3)
					b = true;
				return b;
			}
		};
		tablaMateriales.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaMateriales.setSelectionBackground(Color.GREEN);
		tablaMateriales.getTableHeader().setReorderingAllowed(false);
		scrollPane_tabla.setViewportView(tablaMateriales);

		JLabel lblGanancia = new JLabel("Ganancia:");
		lblGanancia.setHorizontalAlignment(SwingConstants.RIGHT);
		lblGanancia.setBounds(-15, 153, 88, 14);
		getContentPane().add(lblGanancia);

		txtGanancia = new JTextField();
		txtGanancia.setHorizontalAlignment(SwingConstants.RIGHT);
		txtGanancia.setText("100");
		txtGanancia.setBounds(83, 150, 41, 20);
		getContentPane().add(txtGanancia);
		txtGanancia.setColumns(10);

		JLabel label = new JLabel("%");
		label.setBounds(129, 153, 20, 14);
		getContentPane().add(label);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setPreferredSize(new Dimension(495, 5));
		tabbedPane.setBounds(10, 178, 494, 233);
		getContentPane().add(tabbedPane);
		setModal(true);

		JPanel panelMateriales = new JPanel(new BorderLayout());
		panelMateriales.add(scrollPane_tabla);
		tabbedPane.add("Materiales", panelMateriales);

		tablaModelos = new JTable();
		JScrollPane scrollTablaModelos = new JScrollPane(tablaModelos);

		JPanel panelModelos = new JPanel(new BorderLayout());
		panelModelos.add(scrollTablaModelos);
		tabbedPane.add("Prendas", panelModelos);
		
		this.controlador.setVista(this);
	}

	public void guardar() {
		modelo.setNombre(txtNombre.getText());
		modelo.setDescripcion(editorPane_descripcion.getText());
		modelo.setGanancia(Double.parseDouble(txtGanancia.getText()));

		for (int i = 0; i < tablaMateriales.getRowCount(); i++) {
			if ((boolean) tablaMateriales.getValueAt(i, 3)) {
				controlador.agregarMaterial(i);
			}
		}

		for (int i = 0; i < tablaModelos.getRowCount(); i++) {
			if ((boolean) tablaModelos.getValueAt(i, 2)) {
				controlador.agregarModelo(i);
			}
		}
		controlador.guardar();
		dispose();
	}

	public void mostrarMateriales(final ArrayList<Material> materiales) {
		this.materiales = materiales;
		SwingWorker<Boolean, Material> worker = new SwingWorker<Boolean, Material>() {
			Object[] nombres;
			DefaultTableModel tm;

			@Override
			protected Boolean doInBackground() throws Exception {
				nombres = new Object[] { "Código", "Nombre", "Precio x Unidad", "Usar" };
				tm = new DefaultTableModel()
				// Colocar checkbox en la columna 3.
				{
					@Override
					public Class<?> getColumnClass(int index) {
						if (index == 3) {
							return Boolean.class;
						} else
							return String.class;
					}
				};
				tm.setColumnIdentifiers(nombres);
				tablaMateriales.setModel(tm);
				for (Material m : materiales) {
					publish(m);
				}
				return null;
			}

			@Override
			protected void done() {
				tablaMateriales.getColumnModel().getColumn(0).setMaxWidth(50);
				tablaMateriales.getColumnModel().getColumn(0).setMinWidth(50);
				tablaMateriales.getColumnModel().getColumn(3).setMaxWidth(50);
				tablaMateriales.getColumnModel().getColumn(3).setMinWidth(50);
				tablaMateriales.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
			}

			@Override
			protected void process(List<Material> chunks) {
				for (Material m : chunks) {
					Object[] row = { m.getId(), m.getNombre(),
							"$ " + m.getPrecio() + " x " + m.getCantidad() + " " + m.getUnidadMedida().getNombre(),
							modelo.usa(m) };
					tm.addRow(row);
				}
			}

		};
		worker.execute();
	}

	@Override
	public void mostrarModelo(Modelo modelo) {
		this.modelo = modelo;
		if (modelo.getNombre() != null)
			txtNombre.setText(modelo.getNombre());
		if (modelo.getDescripcion() != null)
			editorPane_descripcion.setText(modelo.getDescripcion());
		if (modelo.getGanancia() != null)
			txtGanancia.setText("" + modelo.getGanancia());
		else
			txtGanancia.setText("100");
	}

	@Override
	public void mostrarModelos(ArrayList<Modelo> modelos) {
		DefaultTableModel tm = new DefaultTableModel() {
			@Override
			public Class<?> getColumnClass(int index) {
				if (index == 2) {
					return Boolean.class;
				} else
					return String.class;
			}
		};
		String[] identifiers = { "Código", "Nombre", "Usar" };
		tm.setColumnIdentifiers(identifiers);
		for (Modelo m : modelos) {
			Object[] row = { m.getId(), m.getNombre(), modelo.usa(m) };
			tm.addRow(row);
		}
		tablaModelos.setModel(tm);
	}
}
