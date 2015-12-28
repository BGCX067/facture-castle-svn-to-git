package Traitement;

import java.io.FileOutputStream;
import java.io.IOException;

import Affichage.GestionChateau;
import Bean.Facture;
import Bean.LigneDeProduit;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

public class GenererFacturePdf {
	/** Path to the resulting PDF file. */
	//public static final String RESULT = "C:/Documents and Settings/Administrateur/workspace/TestRTF/modele.pdf";
	public static final String MODELE = GestionChateau.propertiesMetier.getProperty("export.modele");
	public static final String RESULT = GestionChateau.propertiesMetier.getProperty("export.facture");


	/**
	 * Manipulates a PDF file src with the file dest as result
	 * @param src the original PDF
	 * @param dest the resulting PDF
	 * @throws IOException
	 * @throws DocumentException
	 */
	public void manipulatePdf(Facture facture) throws IOException, DocumentException {
		PdfReader reader = new PdfReader(MODELE);
		PdfStamper stamper =
				new PdfStamper(reader, new FileOutputStream(RESULT+"Facture_"+facture.getNumeroFacture()+".pdf"), '\0', true);

		PdfContentByte cb = stamper.getUnderContent(1);
		cb.beginText();
		/* Client */
		//Si le client est le même que le destinataire, on n'affiche pas la partie de gauche
		if(!facture.getClient().getAdresse().equals(facture.getDestAdresse())){
			cb.setFontAndSize(BaseFont.createFont(BaseFont.TIMES_BOLD,BaseFont.CP1250,true), 12);
			cb.showTextAligned(Element.ALIGN_LEFT, facture.getClient().getNom().toUpperCase()+" "+facture.getClient().getPrenom().toUpperCase(), 81, 642, 0);
			cb.showTextAligned(Element.ALIGN_LEFT, facture.getClient().getAdresse(), 81, 620, 0);
			cb.showTextAligned(Element.ALIGN_LEFT, facture.getClient().getCp()+" "+facture.getClient().getVille().toUpperCase(), 81, 598, 0);
		}
		/* Informations chateau */
		//cb.setFontAndSize(BaseFont.createFont(BaseFont.TIMES_ROMAN,BaseFont.CP1250,true), 12);
		//cb.showTextAligned(Element.ALIGN_LEFT, GestionChateau.propertiesGeneral.getProperty("generation.facture.chateau.nom"), 50, 736, 0);
		//cb.showTextAligned(Element.ALIGN_LEFT, GestionChateau.propertiesGeneral.getProperty("generation.facture.chateau.cp")+" "+GestionChateau.propertiesGeneral.getProperty("generation.facture.chateau.ville"), 50, 716, 0);
		//cb.showTextAligned(Element.ALIGN_LEFT, GestionChateau.propertiesGeneral.getProperty("generation.facture.chateau.telephone"), 50, 696, 0);
		//cb.showTextAligned(Element.ALIGN_LEFT, GestionChateau.propertiesGeneral.getProperty("generation.facture.chateau.email"), 50, 676, 0);
		/* Destinataire */
		cb.setFontAndSize(BaseFont.createFont(BaseFont.TIMES_BOLD,BaseFont.CP1250,true), 12);
		cb.showTextAligned(Element.ALIGN_LEFT, facture.getDestNom().toUpperCase()+" "+facture.getDestPrenom().toUpperCase(), 372, 642, 0);
		cb.showTextAligned(Element.ALIGN_LEFT, facture.getDestAdresse(), 372, 620, 0);
		cb.showTextAligned(Element.ALIGN_LEFT, facture.getDestCodePostal()+" "+facture.getDestVille().toUpperCase(), 372, 598, 0);
		/* Informations commandes */
		cb.setFontAndSize(BaseFont.createFont(BaseFont.TIMES_ROMAN,BaseFont.CP1250,true), 11);
		cb.showTextAligned(Element.ALIGN_LEFT, facture.getLieuTransaction()+", "+GestionChateau.propertiesGeneral.getProperty("generation.facture.commande.date.fature")+" "+facture.getDateFacture(), 50, 565, 0);
		cb.showTextAligned(Element.ALIGN_LEFT, GestionChateau.propertiesGeneral.getProperty("generation.facture.commande.numero")+" "+facture.getNumeroFacture(), 50, 545, 0);
		cb.showTextAligned(Element.ALIGN_LEFT, GestionChateau.propertiesGeneral.getProperty("generation.facture.commande.devise"), 375, 545, 0);


		/* Rectangle Client*/
		//Si le client est le même que le destinataire, on n'affiche pas la partie de gauche
		if(!facture.getClient().getAdresse().equals(facture.getDestAdresse())){
			cb.saveState();
			cb.setColorStroke(BaseColor.BLACK);
			cb.rectangle(35,581,235,82);
			cb.stroke();
			cb.saveState();
		}

		/* Rectangle Destinataire*/
		cb.saveState();
		cb.setColorStroke(BaseColor.BLACK);
		cb.rectangle(326,581,235,82);
		cb.stroke();
		cb.saveState();

		/* Tableau des produits */
		/* On crée un tableau de 4 colonnes */
		float[] colsWidth = {2f, 1f, 1f, 1f}; // Code 1
		PdfPTable table = new PdfPTable(colsWidth);
		/* On crée les cellules d'en-tête */
		PdfPCell cellEnTete1 = new PdfPCell(new Paragraph(GestionChateau.propertiesGeneral.getProperty("generation.facture.commande.tableau.designation"),new Font(BaseFont.createFont(BaseFont.TIMES_BOLD,BaseFont.CP1250,true), 11)));
		PdfPCell cellEnTete2 = new PdfPCell(new Paragraph(GestionChateau.propertiesGeneral.getProperty("generation.facture.commande.tableau.quantite"),new Font(BaseFont.createFont(BaseFont.TIMES_BOLD,BaseFont.CP1250,true), 11)));
		PdfPCell cellEnTete3 = new PdfPCell(new Paragraph(GestionChateau.propertiesGeneral.getProperty("generation.facture.commande.tableau.prix.unitaire"),new Font(BaseFont.createFont(BaseFont.TIMES_BOLD,BaseFont.CP1250,true), 11)));
		PdfPCell cellEnTete4 = new PdfPCell(new Paragraph(GestionChateau.propertiesGeneral.getProperty("generation.facture.commande.tableau.total"),new Font(BaseFont.createFont(BaseFont.TIMES_BOLD,BaseFont.CP1250,true), 11)));
		/* On centre les libellés */
		cellEnTete1.setHorizontalAlignment(1);
		cellEnTete2.setHorizontalAlignment(1);
		cellEnTete3.setHorizontalAlignment(1);
		cellEnTete4.setHorizontalAlignment(1);

		table.addCell(cellEnTete1);
		table.addCell(cellEnTete2);
		table.addCell(cellEnTete3);
		table.addCell(cellEnTete4);

		int i =0;
		for(i=0;i<facture.getLigneDeProduit().length;i++){
			LigneDeProduit ligneDeProduit = facture.listeLigneDeProduit(i);
			/* On crée les cellules de données */
			PdfPCell cell1 = new PdfPCell(new Paragraph(ligneDeProduit.getProduit().getLibelleProduit(),new Font(BaseFont.createFont(BaseFont.TIMES_ROMAN,BaseFont.CP1250,true), 11)));
			PdfPCell cell2 = new PdfPCell(new Paragraph(String.valueOf(ligneDeProduit.getQuantite()),new Font(BaseFont.createFont(BaseFont.TIMES_ROMAN,BaseFont.CP1250,true), 11)));
			PdfPCell cell3 = new PdfPCell(new Paragraph(String.valueOf(ligneDeProduit.getPrixUnitaire()),new Font(BaseFont.createFont(BaseFont.TIMES_ROMAN,BaseFont.CP1250,true), 11)));
			PdfPCell cell4 = new PdfPCell(new Paragraph(String.valueOf(ligneDeProduit.getPrixTotal()),new Font(BaseFont.createFont(BaseFont.TIMES_ROMAN,BaseFont.CP1250,true), 11)));
			/* On centre les libellés */
			cell1.setHorizontalAlignment(0);
			cell2.setHorizontalAlignment(1);
			cell3.setHorizontalAlignment(1);
			cell4.setHorizontalAlignment(1);

			table.addCell(cell1);
			table.addCell(cell2);
			table.addCell(cell3);
			table.addCell(cell4);
		}
		/* Définition de la largeur du tableau */
		table.setTotalWidth(500);
		/* Nombre de ligne sous total tableau */
		int nbrLigneSousTotalTableau = 5;
		/* Sous tableau */
		/* Cellule vide */
		PdfPCell cellCelluleVide = new PdfPCell(new Paragraph("",new Font(BaseFont.createFont(BaseFont.TIMES_BOLD,BaseFont.CP1250,true), 11)));
		cellCelluleVide.setBorder(0);
		cellCelluleVide.setBackgroundColor(BaseColor.WHITE);
		/* Total HT */
		table.addCell(cellCelluleVide);
		table.addCell(cellCelluleVide);
		PdfPCell cellTotalHt = new PdfPCell(new Paragraph(GestionChateau.propertiesGeneral.getProperty("generation.facture.commande.tableau.soustableau.totalHT"),new Font(BaseFont.createFont(BaseFont.TIMES_BOLD,BaseFont.CP1250,true), 11)));
		cellTotalHt.setHorizontalAlignment(1);
		table.addCell(cellTotalHt);
		PdfPCell cellTotalHtValeur = new PdfPCell(new Paragraph(facture.getPrixTotalHT().toString(),new Font(BaseFont.createFont(BaseFont.TIMES_ROMAN,BaseFont.CP1250,true), 11)));
		cellTotalHtValeur.setHorizontalAlignment(1);
		table.addCell(cellTotalHtValeur);
		/* Remise */
		if (facture.getRemise() != 0.00){
			table.addCell(cellCelluleVide);
			table.addCell(cellCelluleVide);
			PdfPCell cellRemise = new PdfPCell(new Paragraph(GestionChateau.propertiesGeneral.getProperty("generation.facture.commande.tableau.soustableau.remise"),new Font(BaseFont.createFont(BaseFont.TIMES_BOLD,BaseFont.CP1250,true), 11)));
			cellRemise.setHorizontalAlignment(1);
			table.addCell(cellRemise);
			PdfPCell cellRemiseValeur = new PdfPCell(new Paragraph(facture.getRemise().toString(),new Font(BaseFont.createFont(BaseFont.TIMES_ROMAN,BaseFont.CP1250,true), 11)));
			cellRemiseValeur.setHorizontalAlignment(1);
			table.addCell(cellRemiseValeur);
			nbrLigneSousTotalTableau ++;
		}
		/* Port */
		table.addCell(cellCelluleVide);
		table.addCell(cellCelluleVide);
		PdfPCell cellPort = new PdfPCell(new Paragraph(GestionChateau.propertiesGeneral.getProperty("generation.facture.commande.tableau.soustableau.port"),new Font(BaseFont.createFont(BaseFont.TIMES_BOLD,BaseFont.CP1250,true), 11)));
		cellPort.setHorizontalAlignment(1);
		table.addCell(cellPort);
		PdfPCell cellPortValeur = new PdfPCell(new Paragraph(facture.getPort().toString(),new Font(BaseFont.createFont(BaseFont.TIMES_ROMAN,BaseFont.CP1250,true), 11)));
		cellPortValeur.setHorizontalAlignment(1);
		table.addCell(cellPortValeur);
		/* TVA */
		table.addCell(cellCelluleVide);
		table.addCell(cellCelluleVide);
		PdfPCell cellTva = new PdfPCell(new Paragraph(GestionChateau.propertiesGeneral.getProperty("generation.facture.commande.tableau.soustableau.tva"),new Font(BaseFont.createFont(BaseFont.TIMES_BOLD,BaseFont.CP1250,true), 11)));
		cellTva.setHorizontalAlignment(1);
		table.addCell(cellTva);
		PdfPCell cellTvaValeur = new PdfPCell(new Paragraph(facture.getTxTVA().toString(),new Font(BaseFont.createFont(BaseFont.TIMES_ROMAN,BaseFont.CP1250,true), 11)));
		cellTvaValeur.setHorizontalAlignment(1);
		table.addCell(cellTvaValeur);
		/* Total TTC */
		table.addCell(cellCelluleVide);
		table.addCell(cellCelluleVide);
		PdfPCell cellTotalTtc = new PdfPCell(new Paragraph(GestionChateau.propertiesGeneral.getProperty("generation.facture.commande.tableau.soustableau.totalTTC"),new Font(BaseFont.createFont(BaseFont.TIMES_BOLD,BaseFont.CP1250,true), 11)));
		cellTotalTtc.setHorizontalAlignment(1);
		table.addCell(cellTotalTtc);
		PdfPCell cellTotalTtcValeur = new PdfPCell(new Paragraph(facture.getPrixTotalTTC().toString(),new Font(BaseFont.createFont(BaseFont.TIMES_ROMAN,BaseFont.CP1250,true), 11)));
		cellTotalTtcValeur.setHorizontalAlignment(1);
		table.addCell(cellTotalTtcValeur);

		/* On ecrit le tableau */
		table.writeSelectedRows(0, facture.getLigneDeProduit().length+nbrLigneSousTotalTableau, 50, 480, cb);

		cb.stroke();
		cb.saveState();
		cb.endText();
		stamper.close();
	}
}
