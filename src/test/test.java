package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class test {

	public static void main(String[] args) {
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		try {
			System.out.println(new Date());
			System.out.println(format.parse("5/31/2021 11:49").after(new Date()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//test t = new test();
		//t.BD_backup("C:/Users/gonza/Desktop");
	}

	private Process proceso;
	private ProcessBuilder constructor;

	private final String host = "localhost";
	private final String puerto = "5432";
	private final String usuario = "postgres";
	private final String clave = "postgres";
	private final String bd = "postgres";
	private final String formato = "custom";
	private final String ruta = "F:/Program Files/PostgreSQL/10/bin\\pg_dump.exe";

	public boolean BD_backup(String path) {
		boolean hecho = false;
		try {
			File pgdump = new File(ruta);
			if (pgdump.exists()) {
				if (!path.equalsIgnoreCase("")) {
					constructor = new ProcessBuilder(ruta, "--verbose",
							"--format", formato, "-f", path);
				} else {
					constructor = new ProcessBuilder(ruta, "--verbose",
							"--inserts", "--column-inserts", "-f", path);
					System.out.println("ERROR");
				}
				constructor.environment().put("PGHOST", host);
				constructor.environment().put("PGPORT", puerto);
				constructor.environment().put("PGUSER", usuario);
				constructor.environment().put("PGPASSWORD", clave);
				constructor.environment().put("PGDATABASE", bd);
				constructor.redirectErrorStream(true);
				proceso = constructor.start();
				// escribirProcess(proceso);
				 final BufferedReader r = new BufferedReader(
			                new InputStreamReader(proceso.getErrorStream()));
			        String line = r.readLine();
			        while (line != null) {
			            System.err.println(line);
			            line = r.readLine();
			        }
				System.out.println("terminado backup " + path);
				
				hecho = true;
			} else {
				if (!path.equalsIgnoreCase("")) {
					constructor = new ProcessBuilder("/opt/PostgreSQL/10/bin/pg_dump", "--verbose", "--format",
							formato, "-f", path);
				} else {
					constructor = new ProcessBuilder("/opt/PostgreSQL/10/bin/pg_dump", "--verbose", "--inserts",
							"--column-inserts", "-f", path);
					System.out.println("ERROR");
				}
				constructor.environment().put("PGHOST", host);
				constructor.environment().put("PGPORT", puerto);
				constructor.environment().put("PGUSER", usuario);
				constructor.environment().put("PGPASSWORD", clave);
				constructor.environment().put("PGDATABASE", bd);
				constructor.redirectErrorStream(true);
				proceso = constructor.start();
				// escribirProcess(proceso);
				System.out.println("terminado backup " + path);
				hecho = true;
			}
		} catch (Exception ex) {
			System.err.println(ex.getMessage() + "Error de backup");
			hecho = false;
		}
		return hecho;
	}

	public boolean restaurarBackup(String path) {
		boolean hecho = false;
		try {
			File pgrestore = new File("C:/Program Files/PostgreSQL/9.4/bin\\pg_restore.exe");
			if (pgrestore.exists()) {
				constructor = new ProcessBuilder("C:/Program Files/PostgreSQL/9.4/bin\\pg_restore.exe", "-i", "-h",
						host, "-p", puerto, "-U", usuario, "-d", bd, "-v", path);
				constructor.environment().put("PGPASSWORD", clave);
				constructor.redirectErrorStream(true);
				proceso = constructor.start();
				// escribirProcess(proceso);
				hecho = true;
			} else {
				constructor = new ProcessBuilder("/opt/PostgreSQL/9.4/bin/pg_restore", "-i", "-h", host, "-p", puerto,
						"-U", usuario, "-d", bd, "-v", path);
				constructor.environment().put("PGPASSWORD", clave);
				constructor.redirectErrorStream(true);
				proceso = constructor.start();
				// escribirProcess(proceso);
				hecho = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			hecho = false;
		}
		return hecho;
	}

}
