package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

public class BDManager {
	private static final String driver = "org.postgresql.Driver";
	private static final String url = "jdbc:postgresql://localhost:5432/postgres";
	private static final String user = "postgres";
	private static final String pass = "postgres";
	private Connection conexion;
	private static BDManager singleton;

	private BDManager() {
		try {
			Class.forName(driver);
			conexion = DriverManager.getConnection(url, user, pass);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}

	public Connection getConexion() {
		return conexion;
	}

	public static BDManager getInstance() {
		if (singleton == null) {
			singleton = new BDManager();
		}
		return singleton;

	}
}
