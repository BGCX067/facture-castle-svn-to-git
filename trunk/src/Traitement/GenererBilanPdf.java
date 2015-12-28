package Traitement;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import org.jfree.chart.JFreeChart;

import Affichage.AfficherStatistique;
import Affichage.GestionChateau;
import Bean.Bilan;
import Utils.Utilitaires;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.DefaultFontMapper;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

public class GenererBilanPdf {

	/** Path to the resulting PDF file. */
	public static final String MODELE = GestionChateau.propertiesMetier
			.getProperty("export.modele.bilan");
	public static final String RESULT = GestionChateau.propertiesMetier
			.getProperty("export.bilan");

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
	public static void manipulatePdf(Bilan bilan) throws IOException,
	DocumentException {

		int numeroPage = 1;

		Calendar date = Calendar.getInstance();
		String nomMois = Utilitaires.nomMois(date.get(Calendar.MONTH) + 1);
		int annee = date.get(Calendar.YEAR);
		PdfReader reader = new PdfReader(MODELE);
		PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(RESULT
				+ "Bilan_" + annee + "_" + nomMois + ".pdf"), '\0', true);


		PdfContentByte cb = stamper.getUnderContent(numeroPage);
		cb.beginText();

		//Encadrement de la page
		cb.rectangle(25, 25, 545, 792);

		cb.setFontAndSize(
				BaseFont.createFont(BaseFont.TIMES_BOLD, BaseFont.CP1250, true),
				12);
		// Nom du chateau
		cb.showTextAligned(Element.ALIGN_LEFT, bilan.getNomSociete(), 28, 805,
				0);
		// Aux actionnaires
		cb.showTextAligned(Element.ALIGN_RIGHT,
				GestionChateau.propertiesGeneral
				.getProperty("creer.bilan.actionnaire"), 560, 805, 0);
		// aux cogerants
		cb.showTextAligned(Element.ALIGN_LEFT, GestionChateau.propertiesGeneral
				.getProperty("creer.bilan.cogerant"), 28, 785, 0);

		// Premier gerant
		cb.showTextAligned(Element.ALIGN_LEFT, GestionChateau.propertiesGeneral
				.getProperty("creer.bilan.generation.gerant.un"), 98, 785, 0);


		String secondgerant = GestionChateau.propertiesGeneral
				.getProperty("creer.bilan.generation.gerant.deux");
		// Separation
		if(!secondgerant.equals("")){
			cb.showTextAligned(Element.ALIGN_LEFT, " - ", 208, 785, 0);

			// Deuxieme gerant
			cb.showTextAligned(Element.ALIGN_LEFT, secondgerant, 218, 785, 0);

		}

		// Titre
		cb.showTextAligned(
				Element.ALIGN_LEFT,
				GestionChateau.propertiesGeneral
				.getProperty("creer.bilan.titre.fichier")
				+ " "
				+ nomMois + " " + annee, 65, 755, 0);

		/* Rectangle Titre */
		cb.saveState();
		cb.setColorStroke(BaseColor.BLACK);
		cb.rectangle(55, 745, 500, 25);
		cb.stroke();
		cb.saveState();

		// Titre premiere partie
		cb.setFontAndSize(
				BaseFont.createFont(BaseFont.TIMES_BOLD, BaseFont.CP1250, true),
				10);

		cb.showTextAligned(Element.ALIGN_LEFT, GestionChateau.propertiesGeneral
				.getProperty("creer.bilan.premiere.partie"), 130, 710, 0);

		// Contenu premiere partie
		// Une ligne = 180 caracteres
		String[] contenuPremierePartie = bilan.getContenuPremierePartie()
				.split("\n");

		int y = 690;
		cb.setFontAndSize(BaseFont.createFont(BaseFont.TIMES_ROMAN,
				BaseFont.CP1250, true), 7);
		for (String ligne : contenuPremierePartie) {
			if (ligne.length() > 180) {

				int deb = 0;
				int fin = 180;
				double nbBoucle = ligne.length() / 180;

				for (int i = 0; i <= nbBoucle; i++) {
					String texte = "";
					if (ligne.substring(deb).length() < 181) {
						texte = ligne.substring(deb);
					} else {
						texte = ligne.substring(deb, fin);
					}

					//SI y est inférieur a 150 pixel, on crée une nouvelle page
					if((y <= 150)){
						numeroPage += 1;
						cb = testChangementPage(stamper, cb, numeroPage);

						y = 800;
					}

					cb.showTextAligned(Element.ALIGN_LEFT, texte, 40, y, 0);
					y -= 9;
					deb += 181;
					fin += 181;
				}
			} else {

				//SI y est inférieur a 150 pixel, on crée une nouvelle page
				if((y <= 150)){
					numeroPage += 1;
					cb = testChangementPage(stamper, cb, numeroPage);

					y = 800;
				}
				cb.showTextAligned(Element.ALIGN_LEFT, ligne, 40, y, 0);
				y -= 9;
			}
		}

		// Pour faire une petite marge
		y -= 20;


		// Titre deuxieme partie
		cb.setFontAndSize(
				BaseFont.createFont(BaseFont.TIMES_BOLD, BaseFont.CP1250, true),
				10);
		cb.showTextAligned(Element.ALIGN_LEFT, GestionChateau.propertiesGeneral
				.getProperty("creer.bilan.deuxieme.partie"), 130, y, 0);

		// Pour faire une petite marge
		y -= 20;
		// Contenu deuxieme partie
		// Une ligne = 180 caracteres
		String[] contenuDeuxiemePartie = bilan.getContenuDeuxiemePartie()
				.split("\n");

		cb.setFontAndSize(BaseFont.createFont(BaseFont.TIMES_ROMAN,
				BaseFont.CP1250, true), 7);
		for (String ligne : contenuDeuxiemePartie) {
			if (ligne.length() > 180) {

				int deb = 0;
				int fin = 180;
				double nbBoucle = ligne.length() / 180;

				for (int i = 0; i <= nbBoucle; i++) {
					String texte = "";
					if (ligne.substring(deb).length() < 181) {
						texte = ligne.substring(deb);
					} else {
						texte = ligne.substring(deb, fin);
					}

					if((y <= 150)){
						numeroPage += 1;
						cb = testChangementPage(stamper, cb, numeroPage);
						y = 800;
					}

					cb.showTextAligned(Element.ALIGN_LEFT, texte, 40, y, 0);
					y -= 9;
					deb += 181;
					fin += 181;
				}
			} else {

				if((y <= 150)){
					numeroPage += 1;
					cb = testChangementPage(stamper, cb, numeroPage);

					y = 800;
				}


				cb.showTextAligned(Element.ALIGN_LEFT, ligne, 40, y, 0);
				y -= 9;
			}
		}

		// Pour faire une petite marge
		y -= 20;


		// Titre troisieme partie
		cb.setFontAndSize(
				BaseFont.createFont(BaseFont.TIMES_BOLD, BaseFont.CP1250, true),
				10);
		cb.showTextAligned(Element.ALIGN_LEFT, GestionChateau.propertiesGeneral
				.getProperty("creer.bilan.troisieme.partie"), 130, y, 0);

		// Pour faire une petite marge
		y -= 20;
		// Contenu troisieme partie
		// Une ligne = 180 caracteres
		String[] contenuTroisiemePartie = bilan.getContenuTroisiemePartie()
				.split("\n");

		cb.setFontAndSize(BaseFont.createFont(BaseFont.TIMES_ROMAN,
				BaseFont.CP1250, true), 7);
		for (String ligne : contenuTroisiemePartie) {
			if (ligne.length() > 180) {

				int deb = 0;
				int fin = 180;
				double nbBoucle = ligne.length() / 180;

				for (int i = 0; i <= nbBoucle; i++) {
					String texte = "";
					if (ligne.substring(deb).length() < 181) {
						texte = ligne.substring(deb);
					} else {
						texte = ligne.substring(deb, fin);
					}

					if((y <= 150)){
						numeroPage += 1;
						cb = testChangementPage(stamper, cb, numeroPage);

						y = 800;
					}

					cb.showTextAligned(Element.ALIGN_LEFT, texte, 40, y, 0);
					y -= 9;
					deb += 181;
					fin += 181;
				}
			} else {

				if((y <= 150)){
					numeroPage += 1;
					cb = testChangementPage(stamper, cb, numeroPage);

					y = 800;
				}

				cb.showTextAligned(Element.ALIGN_LEFT, ligne, 40, y, 0);
				y -= 9;
			}
		}

		// Pour faire une petite marge
		y -= 20;

		// Titre quatrieme partie
		cb.setFontAndSize(
				BaseFont.createFont(BaseFont.TIMES_BOLD, BaseFont.CP1250, true),
				10);
		cb.showTextAligned(Element.ALIGN_LEFT, GestionChateau.propertiesGeneral
				.getProperty("creer.bilan.quatrieme.partie"), 130, y, 0);

		// Pour faire une petite marge
		y -= 20;
		// Contenu quatrieme partie
		// Une ligne = 180 caracteres
		String[] contenuQuatriemePartie = bilan.getContenuQuatriemePartie()
				.split("\n");

		cb.setFontAndSize(BaseFont.createFont(BaseFont.TIMES_ROMAN,
				BaseFont.CP1250, true), 7);
		for (String ligne : contenuQuatriemePartie) {
			if (ligne.length() > 180) {

				int deb = 0;
				int fin = 180;
				double nbBoucle = ligne.length() / 180;

				for (int i = 0; i <= nbBoucle; i++) {
					String texte = "";
					if (ligne.substring(deb).length() < 181) {
						texte = ligne.substring(deb);
					} else {
						texte = ligne.substring(deb, fin);
					}

					if((y <= 150)){
						numeroPage += 1;
						cb = testChangementPage(stamper, cb, numeroPage);

						y = 800;
					}

					cb.showTextAligned(Element.ALIGN_LEFT, texte, 40, y, 0);
					y -= 9;
					deb += 181;
					fin += 181;
				}
			} else {

				if((y <= 150)){
					numeroPage += 1;
					cb = testChangementPage(stamper, cb, numeroPage);

					y = 800;
				}


				cb.showTextAligned(Element.ALIGN_LEFT, ligne, 40, y, 0);
				y -= 9;
			}
		}

		cb.stroke();
		cb.saveState();
		cb.endText();
		y -=20;

		int margeHautGraphique = y-200;
		JFreeChart chart = AfficherStatistique.createChart(AfficherStatistique.createDataset(Calendar.getInstance().get(Calendar.YEAR)));
		GenererGraphiquePdf.addChartAsPDF(cb,chart,400,200,new DefaultFontMapper(),100,margeHautGraphique);

		cb.stroke();
		cb.saveState();

		y -=20;

		stamper.close();
	}


	private static PdfContentByte testChangementPage(PdfStamper stamper, PdfContentByte cb, int numeroPage) throws DocumentException, IOException{

		cb.stroke();
		cb.saveState();
		cb.endText();

		// Creation de la seconde page

		stamper.insertPage(numeroPage,new Rectangle (PageSize.A4));

		cb = stamper.getUnderContent(numeroPage);


		cb.rectangle(25, 25, 545, 800);
		cb.stroke();
		cb.saveState();



		cb.beginText();
		cb.setFontAndSize(BaseFont.createFont(BaseFont.TIMES_ROMAN,
				BaseFont.CP1250, true), 7);
		return cb;
	}
}
