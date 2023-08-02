package vista;

import java.awt.BorderLayout;
import java.awt.Label;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import negocio.Disfraz;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.border.EtchedBorder;

import controladores.ControladorDisfraces;
import interfacesVista.VistaDisfraces;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Dimension;

@SuppressWarnings("serial")
public class PanelDisfraces extends JPanel implements VistaDisfraces {

	private static JTabbedPane tabbedPane_talles;
	private ControladorDisfraces controlador;

	public PanelDisfraces() {

		this.controlador = new ControladorDisfraces();

		setPreferredSize(new Dimension(400, 300));
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		setLayout(new BorderLayout(0, 0));

		JLabel lblTalles = new JLabel("Talles");
		lblTalles.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblTalles, BorderLayout.NORTH);

		tabbedPane_talles = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_talles.setPreferredSize(new Dimension(5, 300));
		tabbedPane_talles.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				if (tabbedPane_talles.getSelectedComponent() instanceof PanelTalle)
					controlador.seleccionar(tabbedPane_talles.getSelectedIndex());
			}
		});
		tabbedPane_talles.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		JButton btnNuevo = new JButton("Nuevo");
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controlador.nuevoDisfraz();
			}
		});

		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (tabbedPane_talles.getSelectedIndex() >= 0) {
					if (JOptionPane.showConfirmDialog(null, "¿Está seguro que quiere eliminar este Talle?",
							"Seleccione una opción", JOptionPane.YES_NO_OPTION) == 0) {
						controlador.eliminar();
					}
				} else
					JOptionPane.showMessageDialog(null, "Seleccione un Talle", "No seleccionó un Talle",
							JOptionPane.INFORMATION_MESSAGE);
			}
		});

		JButton btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controlador.editarDisfraz();
			}
		});
		panel.add(btnNuevo);
		panel.add(btnEditar);
		panel.add(btnEliminar);
		add(tabbedPane_talles, BorderLayout.CENTER);

		this.controlador.setVista(this);
	}

	@Override
	public void mostrarDisfraces(final ArrayList<Disfraz> disfraces, final int seleccionado) {
		tabbedPane_talles.removeAll();

		SwingWorker<Boolean, Disfraz> worker = new SwingWorker<Boolean, Disfraz>() {

			@Override
			protected Boolean doInBackground() throws Exception {
				boolean ret = false;
				for (Disfraz d : disfraces) {
					publish(d);
				}
				ret = disfraces.isEmpty();

				return ret;
			}

			@Override
			protected void done() {
				try {
					if (get() == true) {
						Label l = new Label("No Hay Ningun Talle");
						l.setAlignment(Label.CENTER);
						tabbedPane_talles.addTab("", l);
					} else {
						tabbedPane_talles.setSelectedIndex(seleccionado);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			protected void process(List<Disfraz> chunks) {
				for (Disfraz d : chunks) {
					tabbedPane_talles.add(new PanelTalle(d));
				}
			}
		};
		worker.execute();
	}

	@Override
	public void editarDisfraz(Disfraz disfraz) {
		if (disfraz != null) {
			EditarDisfraz ed = new EditarDisfraz(disfraz);
			ed.setLocationRelativeTo(null);
			ed.setVisible(true);
		} else {
			JOptionPane.showMessageDialog(null, "Seleccione un Talle", "No seleccionó un Talle",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
