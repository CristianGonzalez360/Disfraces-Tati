package vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import controladores.CotroladorCopiarModelo;
import interfacesVista.VistaCopiarModelo;
import negocio.Material;
import negocio.Modelo;

import javax.swing.JComboBox;
import javax.swing.JSeparator;

public class CopiarModelo extends JDialog implements VistaCopiarModelo {

	private final JPanel contentPanel = new JPanel();
	private JEditorPane editorPane_descripcion;
	private JTextField txtNombre;
	private JTextField txtGanancia;
	private Modelo modelo;
	private CotroladorCopiarModelo controlador;
	private JComboBox comboMateriales;
	private JComboBox comboTodosMateriales;

	/**
	 * Create the dialog.
	 */
	public CopiarModelo(Modelo modelo) {

		this.controlador = new CotroladorCopiarModelo(modelo);

		setSize(new Dimension(520, 316));
		setPreferredSize(new Dimension(495, 406));
		setResizable(false);
		getContentPane().setSize(new Dimension(50, 0));
		getContentPane().setLayout(null);
		setTitle("Copiar Disfraz");
		setModal(true);

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

		comboMateriales = new JComboBox();
		comboMateriales.setBounds(83, 194, 322, 20);
		getContentPane().add(comboMateriales);

		comboTodosMateriales = new JComboBox();
		comboTodosMateriales.setBounds(83, 225, 322, 20);
		getContentPane().add(comboTodosMateriales);

		JButton btnCambiar = new JButton("Cambiar");
		btnCambiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cambiar();
			}
		});
		btnCambiar.setBounds(83, 256, 89, 23);
		getContentPane().add(btnCambiar);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 178, 494, 5);
		getContentPane().add(separator);

		JLabel lblCambiar = new JLabel("Cambiar");
		lblCambiar.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCambiar.setBounds(10, 197, 63, 14);
		getContentPane().add(lblCambiar);

		JLabel lblPor = new JLabel("Por");
		lblPor.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPor.setBounds(27, 228, 46, 14);
		getContentPane().add(lblPor);

		this.controlador.setVista(this);
	}

	protected void cambiar() {
		controlador.reemplazarMaterial(comboMateriales.getSelectedIndex(), comboTodosMateriales.getSelectedIndex());
		comboMateriales.setModel(new DefaultComboBoxModel<>(modelo.getMateriales().toArray()));
	}

	protected void guardar() {
		modelo.setNombre(txtNombre.getText());
		modelo.setDescripcion(editorPane_descripcion.getText());
		modelo.setGanancia(Double.parseDouble(txtGanancia.getText()));
		controlador.guardar();
		dispose();
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

		DefaultComboBoxModel<?> dcm = new DefaultComboBoxModel<>(modelo.getMateriales().toArray());
		this.comboMateriales.setModel(dcm);
	}

	@Override
	public void mostrarMateriales(ArrayList<Material> todosMateriales) {
		DefaultComboBoxModel<?> dcm = new DefaultComboBoxModel<>(todosMateriales.toArray());
		this.comboTodosMateriales.setModel(dcm);
	}
}
