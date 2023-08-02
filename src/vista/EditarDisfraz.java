package vista;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import controladores.ControladorEditorDisfraz;
import interfacesVista.VistaEditorDisfraz;
import negocio.Disfraz;
import negocio.Material;
import negocio.Talle;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.ListSelectionModel;
import javax.swing.JTabbedPane;

@SuppressWarnings("serial")
public class EditarDisfraz extends JDialog implements VistaEditorDisfraz {
	protected JComboBox comboBoxTalles;
	protected JLabel lblPrecio;
	protected JTextField txtPrecio;
	protected JTextField txtNombre;
	protected JTable tabla_materiales;
	protected Disfraz disfraz;
	protected ControladorEditorDisfraz controlador;
	private TablaPrendas tablaDisfraces;

	public EditarDisfraz(Disfraz disfraz) {

		this.controlador = new ControladorEditorDisfraz(disfraz);

		setTitle("Nuevo Talle");
		setModal(true);
		setSize(new Dimension(502, 323));
		setPreferredSize(new Dimension(450, 300));
		setResizable(false);
		getContentPane().setLayout(null);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(10, 11, 51, 14);
		getContentPane().add(lblNombre);

		JLabel lblTalle = new JLabel("Talle:");
		lblTalle.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTalle.setBounds(10, 36, 46, 14);
		getContentPane().add(lblTalle);

		comboBoxTalles = new JComboBox();
		comboBoxTalles.setEditable(true);
		comboBoxTalles.setBounds(66, 33, 100, 20);
		getContentPane().add(comboBoxTalles);

		lblPrecio = new JLabel("Precio:");
		lblPrecio.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPrecio.setBounds(176, 36, 88, 14);
		getContentPane().add(lblPrecio);

		txtPrecio = new JTextField();
		txtPrecio.setHorizontalAlignment(SwingConstants.RIGHT);
		txtPrecio.setEditable(false);
		txtPrecio.setBounds(274, 33, 86, 20);
		getContentPane().add(txtPrecio);
		txtPrecio.setColumns(10);

		txtNombre = new JTextField();
		txtNombre.setEditable(false);
		txtNombre.setBounds(66, 8, 294, 20);
		getContentPane().add(txtNombre);
		txtNombre.setColumns(10);

		JScrollPane scrollPaneMateriales = new JScrollPane();
		scrollPaneMateriales.setBounds(10, 243, 476, 43);

		tabla_materiales = new JTable() {
			@Override
			public boolean isCellEditable(int row, int column) {
				boolean b = false;
				if (column == 4)
					b = true;
				return b;
			}

			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
				Component comp = super.prepareRenderer(renderer, row, col);
				if (col == 4) {
					comp.setBackground(Color.GREEN);
				} else {
					comp.setBackground(Color.white);
				}
				return comp;
			}
		};
		tabla_materiales.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabla_materiales.getTableHeader().setReorderingAllowed(false);
		scrollPaneMateriales.setViewportView(tabla_materiales);

		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				guardar();
			}
		});
		btnGuardar.setBounds(397, 7, 89, 23);
		getContentPane().add(btnGuardar);

		JButton btnEliminar = new JButton("Cancelar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnEliminar.setBounds(397, 32, 89, 23);
		getContentPane().add(btnEliminar);

		JLabel label = new JLabel("$");
		label.setBounds(370, 36, 17, 14);
		getContentPane().add(label);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 61, 496, 234);
		getContentPane().add(tabbedPane);

		tabbedPane.add("Materiales", scrollPaneMateriales);

		tablaDisfraces = new TablaPrendas(controlador);
		JScrollPane scrollDisfraces = new JScrollPane(tablaDisfraces);

		tabbedPane.add("Prendas", scrollDisfraces);

		this.controlador.setVista(this);
	}

	public void guardar() {
		detenerEdicion();

		seleccionarTalle();
		
		for (int i = 0; i < tabla_materiales.getRowCount(); i++) {
			double cantidad = getCantidadAtRow(i);
			controlador.agregarMaterial(i, cantidad);
		}
		for (int i = 0; i < tablaDisfraces.getRowCount(); i++) {
			controlador.agragarDisfraz(i, (int) tablaDisfraces.getValueAt(i, 3));
		}

		controlador.guardar();
		dispose();
	}

	@Override
	public void mostrarMaterialesUsados(ArrayList<Material> materiales) {
		DefaultTableModel tm = new DefaultTableModel();
		Object[] nombres = new Object[] { "Código", "Nombre", "Precio", "Unidad", "Cantidad" };
		tm.setColumnIdentifiers(nombres);
		tm.addTableModelListener(new TableModelListener() {
			@Override // Cuando se edita se actualiza el precio
			public void tableChanged(TableModelEvent tme) {
				if (tme.getType() == TableModelEvent.UPDATE && tme.getColumn() == 4) {
					previewPrecio();
				}
			}
		});
		for (Material m : materiales) {
			Object[] row = { m.getId(), m.getNombre(), "$ " + m.getPrecio(),
					m.getCantidad() + " " + m.getUnidadMedida(), disfraz.getCantidadDe(m) };
			tm.addRow(row);
		}

		tabla_materiales.setModel(tm);
		tabla_materiales.getColumnModel().getColumn(0).setMaxWidth(50);
		tabla_materiales.getColumnModel().getColumn(0).setMinWidth(50);
		tabla_materiales.getColumnModel().getColumn(2).setMaxWidth(60);
		tabla_materiales.getColumnModel().getColumn(2).setMinWidth(60);
		tabla_materiales.getColumnModel().getColumn(3).setMaxWidth(80);
		tabla_materiales.getColumnModel().getColumn(3).setMinWidth(80);
		tabla_materiales.getColumnModel().getColumn(4).setMaxWidth(60);
		tabla_materiales.getColumnModel().getColumn(4).setMinWidth(60);
		tabla_materiales.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
	}

	public void previewPrecio() {
		double precio = 0;
		for (int i = 0; i < tabla_materiales.getRowCount(); i++) {
			precio += getCantidadAtRow(i) * getPrecioAtRow(i);
		}
		for (int i = 0; i < tablaDisfraces.getRowCount(); i++) {
			precio += getCostoAtRow(i);
		}
		precio += precio * disfraz.getModelo().getGanancia() / 100;
		precio *= 100;
		precio = Math.round(precio);
		precio /= 100;
		txtPrecio.setText("" + precio);
	}

	private double getCantidadAtRow(int index) {
		double ret = 0;
		try {
			if (tabla_materiales.getValueAt(index, 4) != null) {
				String s = tabla_materiales.getValueAt(index, 4).toString();
				ret = Double.parseDouble(s);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return ret;
	}

	private double getCostoAtRow(int fila) {
		double ret = 0;
		try {
			if (tablaDisfraces.getValueAt(fila, 2) != null) {
				String s = tablaDisfraces.getValueAt(fila, 2).toString();
				s = s.substring(2);
				ret = Double.parseDouble(s);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return ret;
	}

	private double getPrecioAtRow(int fila) {
		double ret = 0;
		try {
			if (tabla_materiales.getValueAt(fila, 4) != null) {
				String s = tabla_materiales.getValueAt(fila, 2).toString();
				s = s.substring(2);
				ret = Double.parseDouble(s);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return ret;
	}

	public void seleccionarTalle() {
		int indice = comboBoxTalles.getSelectedIndex();
		if (indice > -1)
			controlador.seleccionarTalle(indice);
		else
			controlador.nuevoTalle((String) comboBoxTalles.getSelectedItem());
	}

	public void detenerEdicion() {
		TableCellEditor editor = tabla_materiales.getCellEditor();
		if (editor != null)
			editor.stopCellEditing();
	}

	@Override
	public void mostrarDisfraz(Disfraz disfraz) {
		this.disfraz = disfraz;
		txtNombre.setText(disfraz.getModelo().getNombre());
		if(disfraz.getTalle()!=null){
			comboBoxTalles.setSelectedItem(disfraz.getTalle());
		}
		List<Disfraz> disfraces = disfraz.getDisfraces();
		if(disfraces.size() == 0)
		{
			for (int i = 0; i < tablaDisfraces.getRowCount(); i++) {
				tablaDisfraces.setValueAt(comboBoxTalles.getSelectedItem(), i, 3);
			}
		}
		
		for (int i = 0; i < disfraces.size(); i++) {
			tablaDisfraces.setValueAt(disfraces.get(i).getTalle(), i, 3);
		}
		
	}

	@Override
	public void mostrarTalles(ArrayList<Talle> talles) {
		DefaultComboBoxModel<?> cm = new DefaultComboBoxModel<>(talles.toArray());
		comboBoxTalles.setModel(cm);
	}

	@Override
	public void mostrarDisfraces(ArrayList<Disfraz> disfraces) {
		tablaDisfraces.agregarFila(disfraces);
	}

	@Override
	public void mostrarCostoDisfraz(double costo, int fila) {
		tablaDisfraces.setValueAt("$ "+costo, fila, 2);
	}
}
