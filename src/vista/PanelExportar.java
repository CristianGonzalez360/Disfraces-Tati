package vista;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import controladores.ControladorExportar;
import interfacesVista.VistaExportar;
import negocio.Contacto;
import negocio.Modelo;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import javax.swing.JProgressBar;

@SuppressWarnings("serial")
public class PanelExportar extends JDialog implements VistaExportar{
	private JTextField txtNombre;
	private JTextField txtTelefono;
	private JTextField txtEmail;
	private JProgressBar progressBar;
	private JButton btnAceptar;
	private Contacto contacto;
	private ControladorExportar controladorExportar;

	public PanelExportar(List<Modelo> modelos) {
		
		this.controladorExportar = new ControladorExportar(modelos);
		
		setResizable(false);
		setSize(new Dimension(338, 208));
		setPreferredSize(new Dimension(300, 500));
		setTitle("Exportar");
		getContentPane().setLayout(null);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(10, 39, 86, 14);
		getContentPane().add(lblNombre);

		JLabel lblTelfono = new JLabel("Tel\u00E9fono:");
		lblTelfono.setBounds(10, 64, 86, 14);
		getContentPane().add(lblTelfono);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(10, 89, 86, 14);
		getContentPane().add(lblEmail);

		txtNombre = new JTextField();
		txtNombre.setBounds(106, 36, 219, 20);
		getContentPane().add(txtNombre);
		txtNombre.setColumns(10);

		txtTelefono = new JTextField();
		txtTelefono.setColumns(10);
		txtTelefono.setBounds(106, 61, 219, 20);
		getContentPane().add(txtTelefono);

		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBounds(106, 86, 219, 20);
		getContentPane().add(txtEmail);

		btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				exportar();
			}
		});
		btnAceptar.setBounds(236, 117, 89, 23);
		getContentPane().add(btnAceptar);

		progressBar = new JProgressBar();
		progressBar.setVisible(false);
		progressBar.setBounds(10, 151, 315, 20);
		getContentPane().add(progressBar);

		JLabel lblInformacinDeContacto = new JLabel("Informaci\u00F3n de contacto:");
		lblInformacinDeContacto.setBounds(10, 11, 180, 14);
		getContentPane().add(lblInformacinDeContacto);
		setModal(true);

		controladorExportar.setVista(this);
	}

	protected void exportar() {
		guardarContacto();
		btnAceptar.setEnabled(false);
		controladorExportar.reportar();
	}

	public void guardarContacto() {
		this.contacto.setNombre(txtNombre.getText());
		this.contacto.setEmail(txtEmail.getText());
		this.contacto.setTelefono(txtTelefono.getText());
		controladorExportar.guardar();
	}

	@Override
	public void mostrarContacto(Contacto contacto) {
		this.contacto = contacto;
		if (contacto != null) {
			txtNombre.setText(contacto.getNombre());
			txtEmail.setText(contacto.getEmail());
			txtTelefono.setText(contacto.getTelefono());
		}
	}

	@Override
	public void mostrarBarraProgreso(int size) {
		progressBar.setMaximum(size);
		progressBar.setStringPainted(true);
		progressBar.setString("Espere...");
		progressBar.setVisible(true);
	}

	@Override
	public void mostrarProgreso(int i) {
		progressBar.setValue(i);		
	}

	@Override
	public void salir() {
		dispose();
	}
}
