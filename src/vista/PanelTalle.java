package vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import negocio.Disfraz;
import negocio.MaterialUsado;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.ListSelectionModel;

@SuppressWarnings("serial")
public class PanelTalle extends JPanel {

	public PanelTalle(Disfraz disfraz) {
		setLayout(new BorderLayout(0, 0));
		setName(disfraz.getTalle().getValor());

		JTable tabla_materiales = new JTable() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tabla_materiales.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabla_materiales.setSelectionBackground(Color.GREEN);
		tabla_materiales.getTableHeader().setReorderingAllowed(false);

		// Llenar tablas con los materiales usados por el disfraz
		Object[] nombres = { "Código", "Nombre", "Cantidad", "Precio" };
		DefaultTableModel tm = new DefaultTableModel();
		tm.setColumnIdentifiers(nombres);
		for (MaterialUsado m : disfraz.getMateriales()) {
			Object[] row = { m.getMaterial().getId(), m.getMaterial().getNombre(),
					m.getCantidad() + " " + m.getMaterial().getUnidadMedida(), "$ " + m.getPrecio() };
			tm.addRow(row);
		}
		tabla_materiales.setModel(tm);
		tabla_materiales.getColumnModel().getColumn(0).setMinWidth(70);
		tabla_materiales.getColumnModel().getColumn(0).setMaxWidth(70);
		tabla_materiales.getColumnModel().getColumn(2).setMaxWidth(100);
		tabla_materiales.getColumnModel().getColumn(2).setMinWidth(100);
		tabla_materiales.getColumnModel().getColumn(3).setMaxWidth(70);
		tabla_materiales.getColumnModel().getColumn(3).setMinWidth(70);

		JTable tabla_disfraces = new JTable() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		tabla_disfraces.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabla_disfraces.setSelectionBackground(Color.GREEN);
		tabla_disfraces.getTableHeader().setReorderingAllowed(false);

		Object[] identifiers = { "Código", "Nombre", "Talle", "Precio" };
		DefaultTableModel tm2 = new DefaultTableModel();
		tm2.setColumnIdentifiers(identifiers);
		for (Disfraz d : disfraz.getDisfraces()) {
			Object[] row = { d.getId(), d.getModelo().getNombre(), d.getTalle().getValor(), "$ " + d.getPrecio() };
			tm2.addRow(row);
		}
		tabla_disfraces.setModel(tm2);
		tabla_disfraces.getColumnModel().getColumn(0).setMaxWidth(70);
		tabla_disfraces.getColumnModel().getColumn(0).setMinWidth(70);
		tabla_disfraces.getColumnModel().getColumn(2).setMaxWidth(100);
		tabla_disfraces.getColumnModel().getColumn(2).setMinWidth(100);
		tabla_disfraces.getColumnModel().getColumn(3).setMaxWidth(70);
		tabla_disfraces.getColumnModel().getColumn(3).setMinWidth(70);

		JScrollPane scrollDisfraces = new JScrollPane(tabla_disfraces);

		JScrollPane scrollMateriales = new JScrollPane(tabla_materiales);

		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.add("Materiales", scrollMateriales);
		tabbedPane.addTab("Prendas", scrollDisfraces);

		add(tabbedPane, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));

		JPanel panel_codigo = new JPanel();
		panel.add(panel_codigo, BorderLayout.WEST);

		JLabel lblCdigo = new JLabel("C\u00F3digo: ");
		panel_codigo.add(lblCdigo);

		JLabel lblId = new JLabel();
		lblId.setText(disfraz.getId() + "");
		panel_codigo.add(lblId);

		JPanel panel_precio = new JPanel();
		panel.add(panel_precio, BorderLayout.EAST);

		JLabel lblCosto = new JLabel("Costo:");
		panel_precio.add(lblCosto);

		JLabel lblMostrarcosto = new JLabel("$ " + disfraz.getCosto());
		lblMostrarcosto.setHorizontalAlignment(SwingConstants.CENTER);
		lblMostrarcosto.setPreferredSize(new Dimension(50, 14));
		panel_precio.add(lblMostrarcosto);

		JSeparator separator = new JSeparator();
		separator.setPreferredSize(new Dimension(2, 14));
		separator.setOrientation(SwingConstants.VERTICAL);
		panel_precio.add(separator);

		JLabel lblPrecio = new JLabel("Precio:");
		panel_precio.add(lblPrecio);

		JLabel labelMostrarPrecio = new JLabel("$ " + disfraz.getPrecio());
		labelMostrarPrecio.setForeground(Color.BLACK);
		labelMostrarPrecio.setBackground(Color.WHITE);
		labelMostrarPrecio.setHorizontalAlignment(SwingConstants.CENTER);
		labelMostrarPrecio.setPreferredSize(new Dimension(50, 14));
		panel_precio.add(labelMostrarPrecio);
	}
}
