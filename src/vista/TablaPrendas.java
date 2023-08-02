package vista;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import controladores.ControladorEditorDisfraz;
import negocio.Disfraz;
import negocio.Talle;

public class TablaPrendas extends JTable {

	private ArrayList<TableCellRenderer> renderers;
	private ArrayList<DefaultCellEditor> editors;
	private DefaultTableModel modelo;
	private ArrayList<JComboBox<Talle>> combos;
	private ControladorEditorDisfraz controlador;

	public TablaPrendas(ControladorEditorDisfraz controladorEditorDisfraz) {
		controlador = controladorEditorDisfraz;
		renderers = new ArrayList<>();
		editors = new ArrayList<>();
		combos = new ArrayList<>();
		String[] identifiers = { "Código", "Nombre", "Precio", "Talle" };
		modelo = new DefaultTableModel();
		modelo.setColumnIdentifiers(identifiers);
		setModel(modelo);
		getTableHeader().setReorderingAllowed(false);
		getColumnModel().getColumn(0).setMaxWidth(50);
		getColumnModel().getColumn(0).setMinWidth(50);
		setSelectionBackground(Color.GREEN);
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		boolean ret = false;
		if (column == 3)
			ret = true;
		return ret;
	}

	@Override
	public TableCellEditor getCellEditor(int row, int column) {
		TableCellEditor ret;

		if (column == 3 && row < getRowCount())
			ret = editors.get(row);
		else
			ret = super.getCellEditor(row, column);

		return ret;
	}

	@Override
	public TableCellRenderer getCellRenderer(int row, int column) {
		TableCellRenderer ret;

		if (column == 3 && row < getRowCount())
			ret = renderers.get(row);
		else
			ret = super.getCellRenderer(row, column);

		return ret;
	}

	public void agregarFila(ArrayList<Disfraz> disfraces) {
		if (!disfraces.isEmpty()) {
			String[] row = { disfraces.get(0).getModelo().getId() + "", disfraces.get(0).getModelo().getNombre() };
			modelo.addRow(row);
			final JComboBox<Talle> combo = new JComboBox<>();
			combo.setEditable(false);
			for (Disfraz d : disfraces) {
				combo.addItem(d.getTalle());
			}
			TableCellRenderer render = new TableCellRenderer() {

				@Override
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
						boolean hasFocus, int row, int column) {
					combo.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
					combo.setForeground(isSelected ? table.getSelectionForeground() : table.getForeground());
					return combo;
				}
			};
			renderers.add(render);
			DefaultCellEditor editor = new DefaultCellEditor(combo);
			editors.add(editor);
			combos.add(combo);
			combo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					controlador.seleccionarDisfraz(combos.indexOf(combo), combo.getSelectedIndex());
				}
			});
		} else {
			
		}
	}

	@Override
	public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
		Component comp = super.prepareRenderer(renderer, row, col);
		if (col == 3) {
			comp.setBackground(Color.GREEN);
		} else {
			comp.setBackground(Color.white);
		}
		return comp;
	}

	@Override
	public void setValueAt(Object aValue, int row, int column) {
		if (column == 3)
			combos.get(row).setSelectedItem(aValue);
		else
			super.setValueAt(aValue, row, column);
	}

	@Override
	public Object getValueAt(int row, int column) {
		Object ret = null;

		if (column == 3) {
			ret = combos.get(row).getSelectedIndex();
		} else
			ret = super.getValueAt(row, column);

		return ret;
	}

	public int getDisfrazSeleccionado(int fila) {
		return combos.get(fila).getSelectedIndex();
	}

	public TablaPrendas(TableModel dm) {
		super(dm);
		// TODO Auto-generated constructor stub
	}

	public TablaPrendas(TableModel dm, TableColumnModel cm) {
		super(dm, cm);
		// TODO Auto-generated constructor stub
	}

	public TablaPrendas(int numRows, int numColumns) {
		super(numRows, numColumns);
		// TODO Auto-generated constructor stub
	}

	public TablaPrendas(Vector rowData, Vector columnNames) {
		super(rowData, columnNames);
		// TODO Auto-generated constructor stub
	}

	public TablaPrendas(Object[][] rowData, Object[] columnNames) {
		super(rowData, columnNames);
		// TODO Auto-generated constructor stub
	}

	public TablaPrendas(TableModel dm, TableColumnModel cm, ListSelectionModel sm) {
		super(dm, cm, sm);
		// TODO Auto-generated constructor stub
	}

}
