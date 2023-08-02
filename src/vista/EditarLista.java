package vista;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import controladores.ControladorEditorLista;
import interfacesVista.VistaEditorLista;
import negocio.Lista;
import negocio.Material;
import negocio.Modelo;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JEditorPane;
import javax.swing.JComboBox;

public class EditarLista extends JDialog implements VistaEditorLista {
	protected JTextField tfNombre;
	private JTable tablaTodosModelos;
	private JScrollPane scrollTodosModelos;
	private JButton btnCancelar;
	protected Lista lista;

	protected ControladorEditorLista controladorEditorLista;
	private JButton btnGuardar;
	private JComboBox comboMateriales;
	private JTable tablaListado;
	private JEditorPane editorPaneDescripcion;

	public EditarLista(Lista lista, ControladorEditorLista controlador) {
		setResizable(false);
		this.controladorEditorLista = controlador;
		this.lista = lista;

		setModal(true);
		setSize(new Dimension(500, 450));
		setTitle("Editar Lista");
		getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panelPrincipal = new JPanel();
		getContentPane().add(panelPrincipal);
		panelPrincipal.setLayout(null);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(10, 11, 60, 20);
		panelPrincipal.add(lblNombre);

		tfNombre = new JTextField();
		tfNombre.setBounds(70, 11, 135, 20);
		panelPrincipal.add(tfNombre);
		tfNombre.setColumns(10);
		scrollTodosModelos = new JScrollPane();
		scrollTodosModelos.setBounds(10, 76, 195, 235);
		panelPrincipal.add(scrollTodosModelos);

		tablaTodosModelos = new JTable() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tablaTodosModelos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaTodosModelos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				controladorEditorLista.seleccionarDeTodos(tablaTodosModelos.getSelectedRow());
			}
		});
		tablaTodosModelos.setSelectionBackground(Color.GREEN);
		scrollTodosModelos.setViewportView(tablaTodosModelos);

		btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(409, 10, 75, 23);
		panelPrincipal.add(btnGuardar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(409, 38, 75, 23);
		panelPrincipal.add(btnCancelar);

		JLabel lblMateriales = new JLabel("Materiales:");
		lblMateriales.setBounds(10, 42, 60, 14);
		panelPrincipal.add(lblMateriales);

		comboMateriales = new JComboBox();
		comboMateriales.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				filtrarModelos();
			}
		});
		comboMateriales.setBounds(70, 42, 135, 20);
		panelPrincipal.add(comboMateriales);
		
		JScrollPane scrollListado = new JScrollPane();
		scrollListado.setBounds(289, 76, 195, 235);
		panelPrincipal.add(scrollListado);
		
		tablaListado = new JTable() {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tablaListado.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				controladorEditorLista.seleccionarDeListado(tablaListado.getSelectedRow());
			}
		});
		tablaListado.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaListado.setSelectionBackground(Color.GREEN);
		scrollListado.setViewportView(tablaListado);
		
		JButton buttonAgregar = new JButton(">>");
		buttonAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controladorEditorLista.agregar(tablaTodosModelos.getSelectedRow());
			}
		});
		buttonAgregar.setBounds(215, 148, 64, 23);
		panelPrincipal.add(buttonAgregar);
		
		JButton buttonQuitar = new JButton("<<");
		buttonQuitar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controladorEditorLista.quitar(tablaListado.getSelectedRow());
			}
		});
		buttonQuitar.setBounds(215, 182, 64, 23);
		panelPrincipal.add(buttonQuitar);
		
		JButton buttonAgregarTodos = new JButton(">>>>");
		buttonAgregarTodos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controladorEditorLista.agregarTodo();
			}
		});
		buttonAgregarTodos.setBounds(215, 216, 64, 23);
		panelPrincipal.add(buttonAgregarTodos);
		
		JScrollPane scrollDescripcion = new JScrollPane();
		scrollDescripcion.setBounds(10, 322, 474, 89);
		panelPrincipal.add(scrollDescripcion);
		
		editorPaneDescripcion = new JEditorPane();
		scrollDescripcion.setViewportView(editorPaneDescripcion);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				guardar();
				dispose();
			}
		});

		controladorEditorLista.setVista(this);
		setLocationRelativeTo(null);
	}

	protected void filtrarModelos() {
		controladorEditorLista.seleccionarMaterial(comboMateriales.getSelectedIndex());
	}

	protected void guardar() {
		lista.setNombre(tfNombre.getText());
		controladorEditorLista.guardar();
	}

	@Override
	public void mostrarLista(Lista lista) {
		this.tfNombre.setText(lista.getNombre());
		DefaultTableModel tm = new DefaultTableModel();
		Object[] identifiers = {"Listado"};
		tm.setColumnIdentifiers(identifiers);
		for(Modelo m : lista.getListado()){
			Object[] row = {m.getNombre()};
			tm.addRow(row);
		}
		tablaListado.setModel(tm);
	}

	@Override
	public void mostrarModelos(ArrayList<Modelo> modelos) {
		DefaultTableModel tm = new DefaultTableModel();
		Object[] identifiers = {"Todos"};
		tm.setColumnIdentifiers(identifiers);
		tablaTodosModelos.setModel(tm);
		for (Modelo m : modelos) {
			Object[] nombre = { m.getNombre()};
			tm.addRow(nombre);
		}
	}

	@Override
	public void mostrarMateriales(ArrayList<Material> materiales) {
		DefaultComboBoxModel<?> cm = new DefaultComboBoxModel<>(materiales.toArray());
		comboMateriales.setModel(cm);
	}

	@Override
	public void mostrarListado(ArrayList<Modelo> listado) {
		DefaultTableModel tm = new DefaultTableModel();
		Object[] identifiers = {"Listado"};
		tm.setColumnIdentifiers(identifiers);
		for(Modelo m : listado){
			Object[] row = {m.getNombre()};
			tm.addRow(row);
		}
		tablaListado.setModel(tm);
		
	}

	@Override
	public void mostrarModelos(Modelo modelo) {
		editorPaneDescripcion.setText(modelo.getDescripcion());		
	}
}