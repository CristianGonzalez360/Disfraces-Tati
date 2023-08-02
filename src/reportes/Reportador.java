package reportes;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import negocio.Contacto;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class Reportador {
	
	private JasperReport reporte;
	private JasperViewer reporteViewer;
	private JasperPrint reporteLleno;
	
	public void reportar(Contacto contacto, List<ModeloParaExportar> listaModelos) {
		String reporteURL =  "/recursos/report1.jasper";
		String subreporteURL =  "/recursos/report1_subreport1.jasper" ;
		InputStream input = null;

		try {
			input = getClass().getResourceAsStream(subreporteURL);
			JasperReport subreporte = (JasperReport) JRLoader.loadObject(input);
			Map<String, Object> parametersMap = new HashMap<String, Object>();
			parametersMap.put("SUBREPORT", subreporte);
			parametersMap.put("Nombre", contacto.getNombre());
			parametersMap.put("Telefono", contacto.getTelefono());
			parametersMap.put("Email", contacto.getEmail());
			
			input = null;
			input = getClass().getResourceAsStream(reporteURL);

			this.reporte = (JasperReport) JRLoader.loadObject( input );
			this.reporteLleno = JasperFillManager.fillReport(this.reporte, parametersMap, 
					new JRBeanCollectionDataSource(listaModelos));
		}
		catch (Exception e) {
			e.printStackTrace();		
		}
		finally {
			try {
				input.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void mostrar() {
		this.reporteViewer = new JasperViewer(this.reporteLleno, false);
		this.reporteViewer.setVisible(true);
	}
}
