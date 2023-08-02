package vista;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import negocio.Material;
import negocio.UnidadMedida;
import javax.swing.JComboBox;

import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

import controladores.ControladorEditorMaterial;
import interfacesVista.VistaEditorMaterial;

@SuppressWarnings("serial")
public class EditarMaterial extends JDialog implements VistaEditorMaterial {
	protected JTextField txtNombre;
	protected JTextField txtPrecio;
	protected JTextField txtCantidad;
	protected JComboBox comboBoxUnidades;
	protected Material material;
	protected ControladorEditorMaterial controlador;

	public EditarMaterial(ControladorEditorMaterial controladorEditorMaterial) {

		this.controlador = controladorEditorMaterial;
		this.controlador.setVista(this);
		setModal(true);
		setPreferredSize(new Dimension(370, 140));
		setSize(new Dimension(370, 114));
		setResizable(false);
		getContentPane().setLayout(null);
		setTitle("Nuevo Material");

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(10, 11, 54, 14);
		getContentPane().add(lblNombre);

		txtNombre = new JTextField();
		txtNombre.setBounds(66, 8, 194, 20);
		getContentPane().add(txtNombre);
		txtNombre.setColumns(10);

		JLabel lblPrecio = new JLabel("Precio:");
		lblPrecio.setBounds(10, 36, 46, 14);
		getContentPane().add(lblPrecio);

		txtPrecio = new JTextField();
		txtPrecio.setHorizontalAlignment(SwingConstants.RIGHT);
		txtPrecio.setBounds(66, 33, 73, 20);
		getContentPane().add(txtPrecio);
		txtPrecio.setColumns(10);

		JLabel lblCantidad = new JLabel("Cantidad:");
		lblCantidad.setBounds(10, 61, 54, 14);
		getContentPane().add(lblCantidad);

		txtCantidad = new JTextField();
		txtCantidad.setHorizontalAlignment(SwingConstants.RIGHT);
		txtCantidad.setBounds(66, 58, 73, 20);
		getContentPane().add(txtCantidad);
		txtCantidad.setColumns(10);

		comboBoxUnidades = new JComboBox();
		comboBoxUnidades.setEditable(true);
		comboBoxUnidades.setBounds(142, 58, 118, 20);
		getContentPane().add(comboBoxUnidades);

		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				guardar();
			}
		});
		btnGuardar.setBounds(270, 7, 86, 23);
		getContentPane().add(btnGuardar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnCancelar.setBounds(270, 32, 86, 23);
		getContentPane().add(btnCancelar);

		JLabel label = new JLabel("$");
		label.setBounds(142, 36, 14, 14);
		getContentPane().add(label);
	}

	public void guardar() {

		try {
			material.setNombre(txtNombre.getText());
			material.setCantidad(Double.parseDouble(txtCantidad.getText()));
			material.setPrecio(Double.parseDouble(txtPrecio.getText()));
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Ingresó un valor inválido", getTitle(), JOptionPane.ERROR_MESSAGE);
		}
		if (seleccionarUnidadMedida()) {
			controlador.guardar();
			dispose();
		}
		else
			JOptionPane.showMessageDialog(null, "Seleccione una Unidad de Medida", getTitle(), JOptionPane.ERROR_MESSAGE);
	}

	public boolean seleccionarUnidadMedida() {
		boolean ret = false;
		if (comboBoxUnidades.getSelectedItem() != null) {
			ret = true;
			int id = comboBoxUnidades.getSelectedIndex();
			if (id == -1) {
				controlador.nuevaUnidad((String) comboBoxUnidades.getSelectedItem());
			} else
				controlador.seleccionarUnidad(comboBoxUnidades.getSelectedIndex());
		}
		return ret;
	}

	@Override
	public void mostrarMaterial(Material material) {
		this.material = material;
		this.txtNombre.setText(material.getNombre());
		this.txtPrecio.setText(material.getPrecio() + "");
		this.txtCantidad.setText(material.getCantidad() + "");
		this.comboBoxUnidades.setSelectedItem(material.getUnidadMedida());
	}

	@Override
	public void mostrarUnidadesDeMedida(ArrayList<UnidadMedida> unidades) {
		DefaultComboBoxModel<?> cm = new DefaultComboBoxModel<>(unidades.toArray());
		comboBoxUnidades.setModel(cm);
	}
}
