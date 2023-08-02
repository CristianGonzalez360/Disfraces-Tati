package persistencia;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import negocio.Contacto;

public class Serializador {

	public static void serializar(Contacto contacto) {
		try {
			ObjectOutputStream output = new ObjectOutputStream(
					new FileOutputStream(System.getProperty("user.dir") + "/InfoContacto.dat"));
			output.writeObject(contacto);
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static Contacto deserializar() {
		Contacto ret = null;
		try {
			ObjectInputStream input = new ObjectInputStream(
					new FileInputStream(System.getProperty("user.dir") + "/InfoContacto.dat"));
			ret = (Contacto) input.readObject();
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
			ret = new Contacto("", "", "");
		}

		return ret;
	}

}
