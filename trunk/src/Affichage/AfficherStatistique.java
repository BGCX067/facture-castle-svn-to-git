package Affichage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.IntervalXYDataset;

import Conf.Parametres_Appli;
import Dao.ListeDonneesBDD;
import Traitement.GenererGraphiquePdf;

import com.itextpdf.text.pdf.DefaultFontMapper;
import com.toedter.calendar.JDateChooser;

public class AfficherStatistique extends JPanel implements ActionListener {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static JPanel panRecherche = new JPanel();
	public static JPanel panStatistique = new JPanel();
	public static JLabel label_haut = new JLabel(GestionChateau.propertiesGeneral.getProperty("affichage.statistique"));

	JDateChooser dateMinimum;
	JDateChooser dateMaximum;
	JComboBox listMois = new JComboBox();
	JComboBox listAnnees = new JComboBox();

	int anneeEnCours = 2011;

	public JButton statParBouteilleButton = new JButton(GestionChateau.propertiesGeneral.getProperty("affichage.statistique.recherche.bouton.par.bouteille"));
	public JButton statParCAButton = new JButton(GestionChateau.propertiesGeneral.getProperty("affichage.statistique.recherche.bouton.par.ca"));
	public JButton effacerButton = new JButton(GestionChateau.propertiesGeneral.getProperty("affichage.statistique.recherche.bouton.effacer"));

	ImageIcon icon = new ImageIcon("img/logopdfTailleRed.png");

	public JButton genererPdfButton = new JButton (this.icon);

	JFreeChart chart = null;
	DefaultPieDataset dataset = new DefaultPieDataset();
	PieDataset dataset2 = null;
	JLabel periodeJlabel = new JLabel(GestionChateau.propertiesGeneral.getProperty("affichage.statistique.recherche.label.periode"));
	JLabel periodeEntreJlabel = new JLabel(GestionChateau.propertiesGeneral.getProperty("affichage.statistique.recherche.label.tiret"));
	JLabel moisJlabel = new JLabel(GestionChateau.propertiesGeneral.getProperty("affichage.statistique.recherche.label.mensuel"));
	JLabel aucuneDonnees = new JLabel(GestionChateau.propertiesGeneral.getProperty("affichage.statistique.recherche.label.aucune.donnee"));

	public AfficherStatistique(){

		// Annee en cour
		this.anneeEnCours = Calendar.getInstance().get(Calendar.YEAR);

		// Initialisation des panels
		panRecherche.removeAll();
		panStatistique.removeAll();
		// Enrichissement du panel général
		this.setPreferredSize(new Dimension(Parametres_Appli.tailleLargeurPanelApplication,Parametres_Appli.tailleHauteurPanelApplication));
		this.setBackground(Parametres_Appli.couleurFond);
		//this.setBorder(BorderFactory.createLineBorder(Color.black));

		// ============================================
		// ==               Label haut               ==
		// ============================================

		label_haut.setHorizontalAlignment(SwingConstants.CENTER);
		label_haut.setFont(Parametres_Appli.titreFont);
		label_haut.setPreferredSize(new Dimension(Parametres_Appli.tailleLargeurPanelApplication,25));
		//label_haut.setBorder(BorderFactory.createLineBorder(Color.black));

		// ============================================
		// ==                Recherche               ==
		// ============================================

		panRecherche.setPreferredSize(new Dimension(Parametres_Appli.tailleLargeurPanelApplication/2,100));
		//panRecherche.setBorder(BorderFactory.createLineBorder(Color.black));
		panRecherche.setLayout(null);

		int margeLignePeriode = 50;
		int margeLigneMois = 50;

		this.periodeJlabel.setBounds(margeLignePeriode+20, 5, 80, 25);
		this.periodeEntreJlabel.setBounds(margeLignePeriode+220, 5, 30, 25);
		this.dateMinimum = new JDateChooser();
		this.dateMaximum = new JDateChooser();
		this.dateMinimum.setBounds(margeLignePeriode+100, 5, 110, 25);
		this.dateMaximum.setBounds(margeLignePeriode+240, 5, 110, 25);

		this.listMois.addItem("");
		this.listMois.addItem(GestionChateau.propertiesGeneral.getProperty("mois.nom.un"));
		this.listMois.addItem(GestionChateau.propertiesGeneral.getProperty("mois.nom.deux"));
		this.listMois.addItem(GestionChateau.propertiesGeneral.getProperty("mois.nom.trois"));
		this.listMois.addItem(GestionChateau.propertiesGeneral.getProperty("mois.nom.quatre"));
		this.listMois.addItem(GestionChateau.propertiesGeneral.getProperty("mois.nom.cinq"));
		this.listMois.addItem(GestionChateau.propertiesGeneral.getProperty("mois.nom.six"));
		this.listMois.addItem(GestionChateau.propertiesGeneral.getProperty("mois.nom.sept"));
		this.listMois.addItem(GestionChateau.propertiesGeneral.getProperty("mois.nom.huit"));
		this.listMois.addItem(GestionChateau.propertiesGeneral.getProperty("mois.nom.neuf"));
		this.listMois.addItem(GestionChateau.propertiesGeneral.getProperty("mois.nom.dix"));
		this.listMois.addItem(GestionChateau.propertiesGeneral.getProperty("mois.nom.onze"));
		this.listMois.addItem(GestionChateau.propertiesGeneral.getProperty("mois.nom.douze"));

		this.listAnnees.addItem("");
		this.listAnnees.addItem("2011");
		this.listAnnees.addItem("2012");

		this.statParBouteilleButton.addActionListener(this);
		this.statParCAButton.addActionListener(this);
		this.effacerButton.addActionListener(this);
		this.listMois.addActionListener(this);
		this.listAnnees.addActionListener(this);

		this.dateMinimum.addFocusListener(new FocusListener()
		{
			@Override
			public void focusGained(FocusEvent e) {
				dumpInfo(e);
			}

			@Override
			public void focusLost(FocusEvent e) {
				dumpInfo(e);
			}

			private void dumpInfo(FocusEvent e) {
			}
		});
		this.dateMinimum.getContainerListeners();
		System.out.println(this.dateMinimum.getContainerListeners());
		//dateMaximum.addMouseListener(this);

		this.moisJlabel.setBounds(margeLigneMois+100, 35, 60, 25);
		this.listMois.setBounds(margeLigneMois+170, 35, 100, 25);
		this.listAnnees.setBounds(margeLigneMois+280, 35, 60, 25);

		this.statParBouteilleButton.setBounds(50, 70, 160, 25);
		this.effacerButton.setBounds(220, 70, 80, 25);
		this.statParCAButton.setBounds(310, 70, 160, 25);

		this.genererPdfButton.setBounds((Parametres_Appli.tailleLargeurPanelApplication/2)-34, 66, 32, 32);
		this.genererPdfButton.setBorderPainted(false);
		this.genererPdfButton.addActionListener(this);
		this.genererPdfButton.setToolTipText(GestionChateau.propertiesGeneral.getProperty("afficher.statistique.export"));


		panRecherche.add(this.periodeJlabel);
		panRecherche.add(this.periodeEntreJlabel);
		panRecherche.add(this.dateMinimum);
		panRecherche.add(this.dateMaximum);
		panRecherche.add(this.moisJlabel);
		panRecherche.add(this.listMois);
		panRecherche.add(this.listAnnees);
		panRecherche.add(this.statParBouteilleButton);
		panRecherche.add(this.statParCAButton);
		panRecherche.add(this.effacerButton);
		panRecherche.add(this.genererPdfButton);

		// ============================================
		// ==               Statistique              ==
		// ============================================

		// Creation du graphique par defaut
		this.chart = createChart(createDataset(this.anneeEnCours));
		ChartPanel chartPanel = new ChartPanel(this.chart);

		//ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setBounds(0, 20, 800, 400);

		// TABLEAU
		panStatistique.setPreferredSize(new Dimension(800,500));
		//panStatistique.setBorder(BorderFactory.createLineBorder(Color.black));
		// Alimentation du panel d 'informations des commandes
		panStatistique.setLayout(null);
		// Creation du tableau principal
		panStatistique.add(chartPanel);

		panRecherche.setBackground(Parametres_Appli.couleurPanel);
		panStatistique.setBackground(Parametres_Appli.couleurFond);

		//Au nord
		this.add(label_haut, BorderLayout.NORTH);
		this.add(panRecherche, BorderLayout.CENTER);
		this.add(panStatistique, BorderLayout.CENTER);
		//this.add(panInfosTransaction, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//Bouton effacer
		if(e.getSource() == this.effacerButton){
			this.dateMinimum.setDate(null);
			this.dateMaximum.setDate(null);
			panStatistique.removeAll();
			panStatistique.repaint();
			ChartPanel chartPanel = new ChartPanel(createChart(createDataset(this.anneeEnCours)));
			chartPanel.setBounds(0, 20, 800, 400);
			this.listMois.setSelectedIndex(0);
			this.listAnnees.setSelectedIndex(0);
			panStatistique.add(chartPanel);
			//On modifie la couleur des labels
			this.periodeJlabel.setForeground(Color.BLACK);
			this.moisJlabel.setForeground(Color.BLACK);
		}
		// Stat par bouteille
		if(e.getSource() == this.statParBouteilleButton){
			// ============================
			//     Recherche par periode
			// ============================
			if ((this.dateMinimum.getDate() != null) && (this.dateMaximum.getDate() != null) ){
				DefaultPieDataset dataset = new DefaultPieDataset();

				// Liste des produits
				List<String> listeProduitRelation = ListeDonneesBDD.recupListeProduitsRelation();

				//String[] listeProduitRelationArray = new String[listeProduitRelation.size()];
				Iterator<String> itListeProduit = listeProduitRelation.iterator();

				// Flag permettant de verifier si on a plus d un enregistrement
				boolean donneesRenseignees = false;

				while(itListeProduit.hasNext())
				{
					String idProduitTmp = itListeProduit.next();
					if (ListeDonneesBDD.recupNbrBouteilleVenduWithCriteresDate(Integer.parseInt(idProduitTmp), this.dateMinimum.getDate(), this.dateMaximum.getDate()) > 0){
						dataset.setValue(ListeDonneesBDD.findLibelleProduit(Integer.parseInt(idProduitTmp)),ListeDonneesBDD.recupNbrBouteilleVenduWithCriteresDate(Integer.parseInt(idProduitTmp), this.dateMinimum.getDate(), this.dateMaximum.getDate()));
						donneesRenseignees = true;
					}
				}
				//On modifie la couleur des labels
				this.periodeJlabel.setForeground(Color.GREEN);
				this.moisJlabel.setForeground(Color.BLACK);
				if (donneesRenseignees){

					this.chart = ChartFactory.createPieChart(
							(GestionChateau.propertiesGeneral.getProperty("affichage.statistique.criteres.periode")),  // chart title
							dataset,             // data
							true,               // include legend
							true,
							false
							);

					// Modification des labels sur le schéma
					PiePlot plot = (PiePlot)this.chart.getPlot( );
					StandardPieSectionLabelGenerator standardPieSectionLabelGenerator = new StandardPieSectionLabelGenerator("{1} bouteilles");
					plot.setLabelGenerator(standardPieSectionLabelGenerator);

					this.chart.setBackgroundPaint(Parametres_Appli.couleurFond);
					ChartPanel chartPanelCA = new ChartPanel(this.chart);
					panStatistique.removeAll();
					panStatistique.repaint();
					chartPanelCA.setBounds(0, 20, 800, 400);
					panStatistique.add(chartPanelCA);
				}else{
					panStatistique.removeAll();
					panStatistique.repaint();
					this.chart=null;
					this.aucuneDonnees.setBounds(100, 100, 600, 80);
					panStatistique.add(this.aucuneDonnees);
				}
			}else if ((this.dateMinimum.getDate() == null) && (this.dateMaximum.getDate() != null) ){
				JOptionPane.showMessageDialog(this, (GestionChateau.propertiesGeneral.getProperty("affichage.statistique.fenetre.message.erreur.periode.debut")), (GestionChateau.propertiesGeneral.getProperty("affichage.statistique.fenetre.message.erreur.titre")), 1);
			}else if ((this.dateMaximum.getDate() == null) && (this.dateMinimum.getDate() != null) ){
				JOptionPane.showMessageDialog(this, (GestionChateau.propertiesGeneral.getProperty("affichage.statistique.fenetre.message.erreur.periode.fin")), (GestionChateau.propertiesGeneral.getProperty("affichage.statistique.fenetre.message.erreur.titre")), 1);
			}else if ((this.listMois.getSelectedIndex() != 0) && (this.listAnnees.getSelectedIndex() != 0)){

				// Recuperation des informations du formulaire
				int moisRequetetmp = this.listMois.getSelectedIndex();
				String moisRequete = "";
				// On affiche le mois en deux chiffres
				if (moisRequetetmp < 10){
					moisRequete = "0"+String.valueOf(moisRequetetmp);
				}else{
					moisRequete = String.valueOf(moisRequetetmp);
				}
				String anneesRequete = this.listAnnees.getSelectedItem().toString();

				DefaultPieDataset dataset = new DefaultPieDataset();
				// Liste des produits
				List<String> listeProduitRelation = ListeDonneesBDD.recupListeProduitsRelation();
				//String[] listeProduitRelationArray = new String[listeProduitRelation.size()];
				Iterator<String> itListeProduit = listeProduitRelation.iterator();

				// Flag permettant de verifier si on a plus d un enregistrement
				boolean donneesRenseignees = false;

				while(itListeProduit.hasNext())
				{
					String idProduitTmp = itListeProduit.next();
					if (ListeDonneesBDD.recupNbrBouteilleVenduWithCriteres(Integer.parseInt(idProduitTmp), moisRequete, anneesRequete) > 0){
						dataset.setValue(ListeDonneesBDD.findLibelleProduit(Integer.parseInt(idProduitTmp)),ListeDonneesBDD.recupNbrBouteilleVenduWithCriteres(Integer.parseInt(idProduitTmp), moisRequete, anneesRequete));
						donneesRenseignees = true;
					}
				}
				//On modifie la couleur des labels
				this.periodeJlabel.setForeground(Color.BLACK);
				this.moisJlabel.setForeground(Color.GREEN);
				if (donneesRenseignees){
					this.chart = ChartFactory.createPieChart(
							(GestionChateau.propertiesGeneral.getProperty("affichage.statistique.criteres.mois")),  // chart title
							dataset,             // data
							true,               // include legend
							true,
							false
							);

					// Modification des labels sur le schéma
					PiePlot plot = (PiePlot)this.chart.getPlot( );
					StandardPieSectionLabelGenerator standardPieSectionLabelGenerator = new StandardPieSectionLabelGenerator("{1} bouteilles");
					plot.setLabelGenerator(standardPieSectionLabelGenerator);

					this.chart.setBackgroundPaint(Parametres_Appli.couleurFond);
					ChartPanel chartPanelBouteille = new ChartPanel(this.chart);
					panStatistique.removeAll();
					panStatistique.repaint();
					chartPanelBouteille.setBounds(0, 20, 800, 400);
					panStatistique.add(chartPanelBouteille);
				}else{
					panStatistique.removeAll();
					panStatistique.repaint();
					this.chart=null;
					this.aucuneDonnees.setBounds(100, 100, 600, 80);
					panStatistique.add(this.aucuneDonnees);
				}
			}else if(this.listMois.getSelectedIndex() == 0){
				JOptionPane.showMessageDialog(this, (GestionChateau.propertiesGeneral.getProperty("affichage.statistique.fenetre.message.erreur.mois")), (GestionChateau.propertiesGeneral.getProperty("affichage.statistique.fenetre.message.erreur.titre")), 1);
			}else if(this.listAnnees.getSelectedIndex() == 0){
				JOptionPane.showMessageDialog(this, (GestionChateau.propertiesGeneral.getProperty("affichage.statistique.fenetre.message.erreur.annee")), (GestionChateau.propertiesGeneral.getProperty("affichage.statistique.fenetre.message.erreur.titre")), 1);
			}

		}
		// Stat par CA
		if(e.getSource() == this.statParCAButton){

			// ============================
			//     Recherche par periode
			// ============================
			if ((this.dateMinimum.getDate() != null) && (this.dateMaximum.getDate() != null) ){
				DefaultPieDataset dataset = new DefaultPieDataset();

				// Liste des produits
				List<String> listeProduitRelation = ListeDonneesBDD.recupListeProduitsRelation();

				//String[] listeProduitRelationArray = new String[listeProduitRelation.size()];
				Iterator<String> itListeProduit = listeProduitRelation.iterator();

				// Flag permettant de verifier si on a plus d un enregistrement
				boolean donneesRenseignees = false;

				while(itListeProduit.hasNext())
				{
					String idProduitTmp = itListeProduit.next();
					if (ListeDonneesBDD.recupPrixByBouteilleVenduWithCriteresDate(Integer.parseInt(idProduitTmp), this.dateMinimum.getDate(), this.dateMaximum.getDate()) > 0){
						dataset.setValue(ListeDonneesBDD.findLibelleProduit(Integer.parseInt(idProduitTmp)),ListeDonneesBDD.recupPrixByBouteilleVenduWithCriteresDate(Integer.parseInt(idProduitTmp), this.dateMinimum.getDate(), this.dateMaximum.getDate()));
						donneesRenseignees = true;
					}
				}
				//On modifie la couleur des labels
				this.periodeJlabel.setForeground(Color.GREEN);
				this.moisJlabel.setForeground(Color.BLACK);
				if (donneesRenseignees){

					this.chart = ChartFactory.createPieChart(
							(GestionChateau.propertiesGeneral.getProperty("affichage.statistique.criteres.periode")),  // chart title
							dataset,             // data
							true,               // include legend
							true,
							false
							);

					// Modification des labels sur le schéma
					PiePlot plot = (PiePlot)this.chart.getPlot( );
					StandardPieSectionLabelGenerator standardPieSectionLabelGenerator = new StandardPieSectionLabelGenerator("{1} €");
					plot.setLabelGenerator(standardPieSectionLabelGenerator);

					this.chart.setBackgroundPaint(Parametres_Appli.couleurFond);
					ChartPanel chartPanelCA = new ChartPanel(this.chart);
					panStatistique.removeAll();
					panStatistique.repaint();
					chartPanelCA.setBounds(0, 20, 800, 400);
					panStatistique.add(chartPanelCA);
				}else{
					panStatistique.removeAll();
					panStatistique.repaint();
					this.chart=null;
					this.aucuneDonnees.setBounds(100, 100, 600, 80);
					panStatistique.add(this.aucuneDonnees);
				}
			}else if ((this.dateMinimum.getDate() == null) && (this.dateMaximum.getDate() != null) ){
				JOptionPane.showMessageDialog(this, (GestionChateau.propertiesGeneral.getProperty("affichage.statistique.fenetre.message.erreur.periode.debut")), (GestionChateau.propertiesGeneral.getProperty("affichage.statistique.fenetre.message.erreur.titre")), 1);
			}else if ((this.dateMaximum.getDate() == null) && (this.dateMinimum.getDate() != null) ){
				JOptionPane.showMessageDialog(this, (GestionChateau.propertiesGeneral.getProperty("affichage.statistique.fenetre.message.erreur.periode.fin")), (GestionChateau.propertiesGeneral.getProperty("affichage.statistique.fenetre.message.erreur.titre")), 1);
			}else if ((this.listMois.getSelectedIndex() != 0) && (this.listAnnees.getSelectedIndex() != 0)){
				// ============================
				//     Recherche par mois
				// ============================
				// Recuperation des informations du formulaire
				int moisRequetetmp = this.listMois.getSelectedIndex();
				String moisRequete = "";
				// On affiche le mois en deux chiffres
				if (moisRequetetmp < 10){
					moisRequete = "0"+String.valueOf(moisRequetetmp);
				}else{
					moisRequete = String.valueOf(moisRequetetmp);
				}
				String anneesRequete = this.listAnnees.getSelectedItem().toString();

				DefaultPieDataset dataset = new DefaultPieDataset();

				// Liste des produits
				List<String> listeProduitRelation = ListeDonneesBDD.recupListeProduitsRelation();

				//String[] listeProduitRelationArray = new String[listeProduitRelation.size()];
				Iterator<String> itListeProduit = listeProduitRelation.iterator();

				// Flag permettant de verifier si on a plus d un enregistrement
				boolean donneesRenseignees = false;

				while(itListeProduit.hasNext())
				{
					String idProduitTmp = itListeProduit.next();
					if (ListeDonneesBDD.recupPrixByBouteilleVenduWithCriteres(Integer.parseInt(idProduitTmp), moisRequete, anneesRequete) > 0){
						dataset.setValue(ListeDonneesBDD.findLibelleProduit(Integer.parseInt(idProduitTmp)),ListeDonneesBDD.recupPrixByBouteilleVenduWithCriteres(Integer.parseInt(idProduitTmp), moisRequete, anneesRequete));
						donneesRenseignees = true;
					}
				}
				if (donneesRenseignees){

					this.chart = ChartFactory.createPieChart(
							(GestionChateau.propertiesGeneral.getProperty("affichage.statistique.criteres.mois")),  // chart title
							dataset,             // data
							true,               // include legend
							true,
							false
							);

					// Modification des labels sur le schéma
					PiePlot plot = (PiePlot)this.chart.getPlot( );
					StandardPieSectionLabelGenerator standardPieSectionLabelGenerator = new StandardPieSectionLabelGenerator("{1} €");
					plot.setLabelGenerator(standardPieSectionLabelGenerator);

					// ======

					this.chart.setBackgroundPaint(Parametres_Appli.couleurFond);
					ChartPanel chartPanelCA = new ChartPanel(this.chart);
					panStatistique.removeAll();
					panStatistique.repaint();
					chartPanelCA.setBounds(0, 20, 800, 400);
					panStatistique.add(chartPanelCA);
					//On modifie la couleur des labels
					this.periodeJlabel.setForeground(Color.BLACK);
					this.moisJlabel.setForeground(Color.GREEN);
				}else{
					panStatistique.removeAll();
					panStatistique.repaint();
					this.chart=null;
					this.aucuneDonnees.setBounds(100, 100, 600, 80);
					panStatistique.add(this.aucuneDonnees);
				}
			}else if((this.listMois.getSelectedIndex() == 0) && (this.listAnnees.getSelectedIndex() != 0)){
				JOptionPane.showMessageDialog(this, (GestionChateau.propertiesGeneral.getProperty("affichage.statistique.fenetre.message.erreur.mois")), (GestionChateau.propertiesGeneral.getProperty("affichage.statistique.fenetre.message.erreur.titre")), 1);
			}else if((this.listAnnees.getSelectedIndex() == 0) && (this.listMois.getSelectedIndex() != 0)){
				JOptionPane.showMessageDialog(this, (GestionChateau.propertiesGeneral.getProperty("affichage.statistique.fenetre.message.erreur.annee")), (GestionChateau.propertiesGeneral.getProperty("affichage.statistique.fenetre.message.erreur.titre")), 1);
			}

		}

		if(e.getSource() == this.listMois){
			this.dateMinimum.setDate(null);
			this.dateMaximum.setDate(null);
			//On modifie la couleur des labels
			this.periodeJlabel.setForeground(Color.BLACK);
		}

		if(e.getSource() == this.listAnnees){
			this.dateMinimum.setDate(null);
			this.dateMaximum.setDate(null);
			//On modifie la couleur des labels
			this.periodeJlabel.setForeground(Color.BLACK);
		}

		if(e.getSource() == this.genererPdfButton){
			//File fileName = new File("C:/work_svn_google/trunk/editions/testpdf.pdf");
			File fileName = new File(GestionChateau.propertiesMetier
					.getProperty("export.modele.statistique"));
			OutputStream out = null;
			if (this.chart != null) {
				try {
					out = new BufferedOutputStream(new FileOutputStream(fileName));
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				GenererGraphiquePdf.writeChartAsPDF(out,this.chart,800,600,new DefaultFontMapper());
				// Ouverture du PDF (facture)
				try {
					java.awt.Desktop.getDesktop().open(fileName);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}else{
				JOptionPane.showMessageDialog(this, (GestionChateau.propertiesGeneral.getProperty("affichage.statistique.fenetre.message.erreur.graphique.absent")), (GestionChateau.propertiesGeneral.getProperty("affichage.statistique.fenetre.message.erreur.titre")), 1);
			}
		}
	}

	public static IntervalXYDataset createDataset(int annee)
	{
		TimeSeries timeseries = new TimeSeries("Bouteilles", "Year", "Count", org.jfree.data.time.Month.class);
		try
		{
			timeseries.add(new Month(1,annee), new Integer(ListeDonneesBDD.recupNbrBouteilleVenduByMonthAndYear("01",String.valueOf(annee))));
			timeseries.add(new Month(2,annee), new Integer(ListeDonneesBDD.recupNbrBouteilleVenduByMonthAndYear("02",String.valueOf(annee))));
			timeseries.add(new Month(3,annee), new Integer(ListeDonneesBDD.recupNbrBouteilleVenduByMonthAndYear("03",String.valueOf(annee))));
			timeseries.add(new Month(4,annee), new Integer(ListeDonneesBDD.recupNbrBouteilleVenduByMonthAndYear("04",String.valueOf(annee))));
			timeseries.add(new Month(5,annee), new Integer(ListeDonneesBDD.recupNbrBouteilleVenduByMonthAndYear("05",String.valueOf(annee))));
			timeseries.add(new Month(6,annee), new Integer(ListeDonneesBDD.recupNbrBouteilleVenduByMonthAndYear("06",String.valueOf(annee))));
			timeseries.add(new Month(7,annee), new Integer(ListeDonneesBDD.recupNbrBouteilleVenduByMonthAndYear("07",String.valueOf(annee))));
			timeseries.add(new Month(8,annee), new Integer(ListeDonneesBDD.recupNbrBouteilleVenduByMonthAndYear("08",String.valueOf(annee))));
			timeseries.add(new Month(9,annee), new Integer(ListeDonneesBDD.recupNbrBouteilleVenduByMonthAndYear("09",String.valueOf(annee))));
			timeseries.add(new Month(10,annee), new Integer(ListeDonneesBDD.recupNbrBouteilleVenduByMonthAndYear("10",String.valueOf(annee))));
			timeseries.add(new Month(11,annee), new Integer(ListeDonneesBDD.recupNbrBouteilleVenduByMonthAndYear("11",String.valueOf(annee))));
			timeseries.add(new Month(12,annee), new Integer(ListeDonneesBDD.recupNbrBouteilleVenduByMonthAndYear("12",String.valueOf(annee))));
		}
		catch(Exception exception)
		{
			System.err.println(exception.getMessage());
		}
		TimeSeriesCollection timeseriescollection = new TimeSeriesCollection(timeseries);
		return timeseriescollection;
	}

	public static JFreeChart createChart(IntervalXYDataset intervalxydataset)
	{
		JFreeChart jfreechart = ChartFactory.createXYBarChart("Nombre de bouteilles vendus", "Mois", true, "Nombre de bouteilles", intervalxydataset, PlotOrientation.VERTICAL, false, false, false);
		jfreechart.setBackgroundPaint(Parametres_Appli.couleurFond);
		XYPlot xyplot = (XYPlot)jfreechart.getPlot();
		XYItemRenderer xyitemrenderer = xyplot.getRenderer();
		//StandardXYToolTipGenerator standardxytooltipgenerator = new StandardXYToolTipGenerator("{1} = {2}", new SimpleDateFormat("yyyy"), new DecimalFormat("0"));
		StandardXYToolTipGenerator standardxytooltipgenerator = new StandardXYToolTipGenerator("{1} = {2}", new SimpleDateFormat("MM-yyyy"), new DecimalFormat("0"));
		xyitemrenderer.setBaseToolTipGenerator(standardxytooltipgenerator);
		xyplot.setBackgroundPaint(Color.lightGray);
		xyplot.setRangeGridlinePaint(Color.white);
		/*DateAxis dateaxis = (DateAxis)xyplot.getDomainAxis();
	        dateaxis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);
	        dateaxis.setLowerMargin(0.01D);
	        dateaxis.setUpperMargin(0.01D);*/
		return jfreechart;
	}

}
