package vista;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JSplitPane;
import javax.swing.UIManager;

import controladores.ControladorListas;
import persistencia.DAOLista;
import servicios.ServiciosListas;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.util.List;

public class VPrincipal {

	private JFrame frame;
	private JSplitPane splitPane_derecho;
	private JPanel panel_izquierdo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VPrincipal window = new VPrincipal();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public VPrincipal() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();

		List<Image> iconos = new ArrayList<>();
		iconos.add(new ImageIcon(getClass().getResource("/recursos/icono-16x16.png")).getImage());
		iconos.add(new ImageIcon(getClass().getResource("/recursos/icono-32x32.png")).getImage());
		iconos.add(new ImageIcon(getClass().getResource("/recursos/icono-20x20.png")).getImage());
		iconos.add(new ImageIcon(getClass().getResource("/recursos/icono-40x40.png")).getImage());

		frame.setIconImages(iconos);
		frame.setBounds((int) Component.LEFT_ALIGNMENT, (int) Component.TOP_ALIGNMENT, 1280, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		frame.setTitle("Disfraces Tati");

		JSplitPane splitPane_horizontal = new JSplitPane();
		frame.getContentPane().add(splitPane_horizontal, BorderLayout.CENTER);

		splitPane_derecho = new JSplitPane();
		splitPane_derecho.setOrientation(JSplitPane.VERTICAL_SPLIT);
		PanelDisfraces pd = new PanelDisfraces();
		splitPane_derecho.setLeftComponent(pd);
		PanelMateriales pm2 = new PanelMateriales();
		//pm2.mostrarMateriales();
		splitPane_derecho.setRightComponent(pm2);
		splitPane_horizontal.setRightComponent(splitPane_derecho);
		
		panel_izquierdo = new JPanel();
		splitPane_horizontal.setLeftComponent(panel_izquierdo);
		panel_izquierdo.setLayout(new BorderLayout(0, 0));
		PanelModelos pm= new PanelModelos();
		panel_izquierdo.add(pm, BorderLayout.CENTER);
		
		ControladorListas cl = new ControladorListas(new ServiciosListas(new DAOLista()));
		PanelListas pl = new PanelListas(cl);
		cl.setVista(pl);
		panel_izquierdo.add(pl, BorderLayout.NORTH);
		
		splitPane_horizontal.setResizeWeight(0.5);
		splitPane_derecho.setResizeWeight(0.5);
		frame.setLocationRelativeTo(null);
	}

}
