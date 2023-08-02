package reportes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class test {

	private JasperReport reporte;
	private JasperViewer reporteViewer;
	private JasperPrint reporteLleno;

	// Recibe la lista de personas para armar el reporte
	/*public ReporteAgenda(List<Generacion> generaciones)
	    {
	    	//Hardcodeado
			Map<String, Object> parametersMap = new HashMap<String, Object>();
			parametersMap.put("Fecha", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));		
	    	try		{
	    		JasperReport subreporte = (JasperReport) JRLoader.loadObjectFromFile( "reportes\\SubreporteGeneracin.jasper" );
	    		parametersMap.put("SUBREPORT", subreporte);
				this.reporte = (JasperReport) JRLoader.loadObjectFromFile( "reportes\\ReportePorGeneracin.jasper" );
				this.reporteLleno = JasperFillManager.fillReport(this.reporte, parametersMap, 
						new JRBeanCollectionDataSource(generaciones));
			}
			catch( JRException ex ) 
			{
				ex.printStackTrace();
			}
	    }*/

	public void mostrar() {
		this.reporteViewer = new JasperViewer(this.reporteLleno, false);
		this.reporteViewer.setVisible(true);
	}

}
