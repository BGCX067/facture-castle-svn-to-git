package Traitement;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.OutputStream;

import org.jfree.chart.JFreeChart;

import Affichage.GestionChateau;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.FontMapper;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

public class GenererGraphiquePdf {

	/** Path to the resulting PDF file. */
	// public static final String RESULT =
	// "C:/Documents and Settings/Administrateur/workspace/TestRTF/modele.pdf";
	public static final String MODELE = GestionChateau.propertiesMetier
	.getProperty("export.modele");
	public static final String RESULT = GestionChateau.propertiesMetier
	.getProperty("export.facture");

	/**
	 * Manipulates a PDF file src with the file dest as result
	 * 
	 * @param src
	 *            the original PDF
	 * @param dest
	 *            the resulting PDF
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static void writeChartAsPDF(OutputStream out,
			JFreeChart chart,
			int width,int height,
			FontMapper mapper) {

		Rectangle pagesize = new Rectangle(width,height);
		Document document = new Document(pagesize,50,50,50,50);
		try{
			PdfWriter writer= PdfWriter.getInstance(document,out);
			document.addAuthor("JFreeChart");
			document.addSubject("Demonstration");
			document.open();
			PdfContentByte cb=writer.getDirectContent();
			PdfTemplate tp=cb.createTemplate(width,height);
			Graphics2D g2=tp.createGraphics(width,height,mapper);
			Rectangle2D r2D=new Rectangle2D.Double(0,0,width,height);
			chart.draw(g2,r2D,null);
			g2.dispose();
			cb.addTemplate(tp,0,0);
		}
		catch(DocumentException de){
			System.err.println(de.getMessage());
		}
		document.close();
	}

	/**
	 * Manipulates a PDF file src with the file dest as result
	 * 
	 * @param src
	 *            the original PDF
	 * @param dest
	 *            the resulting PDF
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static void addChartAsPDF(PdfContentByte cb,
			JFreeChart chart,
			int width,int height,
			FontMapper mapper, int margeGauche, int margeHaut) {

		PdfTemplate tp=cb.createTemplate(width,height);
		Graphics2D g2=tp.createGraphics(width,height,mapper);
		Rectangle2D r2D=new Rectangle2D.Double(0,0,width,height);
		chart.draw(g2,r2D,null);
		g2.dispose();
		cb.addTemplate(tp,margeGauche,margeHaut);

	}

}
