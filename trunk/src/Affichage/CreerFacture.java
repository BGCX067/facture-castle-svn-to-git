package Affichage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import Affichage.Tableaux.TableModel;
import Bean.Client;
import Bean.EtatPaiement;
import Bean.Facture;
import Bean.LigneDeProduit;
import Bean.MoyenPaiement;
import Bean.Produit;
import Conf.Parametres_Appli;
import Dao.ClientDAO;
import Dao.DAO;
import Dao.FactureDAO;
import Dao.LigneDeProduitDAO;
import Dao.ListeDonneesBDD;
import Dao.ProduitDAO;
import Traitement.GenererFacturePdf;
import Utils.AutoCompleteDocument;
import bdd.Connect;

import com.itextpdf.text.DocumentException;
import com.toedter.calendar.JDateChooser;

public class CreerFacture extends JPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static JLabel label_haut = new JLabel(GestionChateau.propertiesGeneral.getProperty("nouvelle.facture"));
	public static JPanel panInfosClients = new JPanel();
	public static JPanel panInfosClientsDestinataire = new JPanel();
	public static JPanel panInfosCommandes = new JPanel();
	public static JPanel panInfosTransaction = new JPanel();

	public static JTextField totalHTTextField = new JTextField();
	public static JTextField remiseTextField = new JTextField();
	public static JTextField portTextField = new JTextField();
	public static JTextField tauxTVATextField = new JTextField();
	public static JTextField totalTTCTextField = new JTextField();

	JTextField nomTextField = new JTextField();
	JTextField prenomTextField = new JTextField();
	JTextField emailTextField = new JTextField();
	JTextField numTelephoneTextField = new JTextField();
	JTextField adresseTextField = new JTextField();
	JTextField codePostalTextField = new JTextField();
	JTextField villeTextField = new JTextField();
	JTextField lieuTransactionTextField = new JTextField();
	JTextArea infosComplementairesTextField = new JTextArea();

	JTextField nomTextFieldDestinataire = new JTextField();
	JTextField prenomTextFieldDestinataire = new JTextField();
	JTextField emailTextFieldDestinataire = new JTextField();
	JTextField numTelephoneTextFieldDestinataire = new JTextField();
	JTextField adresseTextFieldDestinataire = new JTextField();
	public static JTextField codePostalTextFieldDestinataire = new JTextField();
	JTextField villeTextFieldDestinataire = new JTextField();
	JTextField lieuTransactionTextFieldDestinataire = new JTextField();
	JTextArea infosComplementairesTextFieldDestinataire = new JTextArea();

	TableModel tableModel;

	JDateChooser dateNaissanceCalendrier;
	JComboBox moyenPaiementJComboBox;
	JComboBox etatPaiementJComboBox;
	private final JTable tableau;
	public JButton genererFacture = new JButton(GestionChateau.propertiesGeneral.getProperty("generer.facture"));

	Object[][] data = {	{"", "", "",""},
			{"", "", "",""},
			{"", "", "",""},
			{"", "", "",""}
	};

	public CreerFacture(){

		// Initialisation des panels
		panInfosClients.removeAll();
		panInfosClientsDestinataire.removeAll();
		panInfosCommandes.removeAll();
		panInfosTransaction.removeAll();

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

		// ============================================
		// ==          Informations clients          ==
		// ============================================
		// Police par défaut des textfield
		Font fontTextfield = new Font("Arial", Font.LAYOUT_LEFT_TO_RIGHT, 12);
		// Police par défaut des titres
		Font fontTitreTextfield = new Font("Arial", Font.BOLD, 16);

		// Titre
		JLabel infosClientTitreJlabel = new JLabel(GestionChateau.propertiesGeneral.getProperty("informations.client.titre"));
		infosClientTitreJlabel.setFont(fontTitreTextfield);

		// Nom
		JLabel nomJlabel = new JLabel(GestionChateau.propertiesGeneral.getProperty("informations.client.nom"));
		this.nomTextField.setFont(fontTextfield);
		this.nomTextField.addFocusListener(new focusListenertTextField());
		List<String> listeNom = ListeDonneesBDD.recupNomClient();
		String[] listeNomAuto = new String[listeNom.size()];
		Iterator<String> itNom = listeNom.iterator();
		int i = 0;
		while(itNom.hasNext())
		{
			listeNomAuto[i] = itNom.next();
			i++;
		}
		AutoCompleteDocument nomAuto = new AutoCompleteDocument(this.nomTextField, listeNomAuto);
		this.nomTextField.setDocument(nomAuto);
		this.nomTextField.addFocusListener(new FocusListener()
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
				if (CreerFacture.this.prenomTextField.getText().equals("")){

					List<String> listePrenom = ListeDonneesBDD.recupPrenomClient(CreerFacture.this.nomTextField.getText());
					String[] listePrenomAuto = new String[listePrenom.size()];
					Iterator<String> itPrenom = listePrenom.iterator();
					int i = 0;
					while(itPrenom.hasNext())
					{
						listePrenomAuto[i] = itPrenom.next();
						System.out.print(listePrenomAuto[i]);
						i++;
					}
					AutoCompleteDocument prenomAuto = new AutoCompleteDocument(CreerFacture.this.prenomTextField, listePrenomAuto);
					CreerFacture.this.prenomTextField.setDocument(prenomAuto);
				}
			}
		});

		// Prénom
		JLabel prenomJlabel = new JLabel(GestionChateau.propertiesGeneral.getProperty("informations.client.prenom"));
		this.prenomTextField.setFont(fontTextfield);
		this.prenomTextField.addFocusListener(new focusListenertTextField());

		// Email
		JLabel emailJlabel = new JLabel(GestionChateau.propertiesGeneral.getProperty("informations.client.email"));
		this.emailTextField.setFont(fontTextfield);
		this.emailTextField.addFocusListener(new focusListenertTextField());

		// Numero de telephone
		JLabel numTelephoneJlabel = new JLabel(GestionChateau.propertiesGeneral.getProperty("informations.client.numero.telephone"));
		this.numTelephoneTextField.setFont(fontTextfield);
		this.numTelephoneTextField.addFocusListener(new focusListenertTextField());

		// Date de naissance
		JLabel dateNaissanceJlabel = new JLabel(GestionChateau.propertiesGeneral.getProperty("informations.client.date.naissance"));
		//dateTextField = new JTextField();
		//dateTextField.setFont(fontTextfield);
		this.dateNaissanceCalendrier = new JDateChooser();

		// Adresse
		JLabel adresseJlabel = new JLabel(GestionChateau.propertiesGeneral.getProperty("informations.client.adresse"));
		this.adresseTextField.setFont(fontTextfield);
		this.adresseTextField.addFocusListener(new focusListenertTextField());

		// Code postal
		JLabel codePostalJlabel = new JLabel(GestionChateau.propertiesGeneral.getProperty("informations.client.codepostal"));
		this.codePostalTextField.setFont(fontTextfield);
		this.codePostalTextField.addFocusListener(new focusListenertTextField());

		// Ville
		JLabel villeJlabel = new JLabel(GestionChateau.propertiesGeneral.getProperty("informations.client.ville"));
		this.villeTextField.setFont(fontTextfield);
		List<String> listeVille = ListeDonneesBDD.recupVilleClient();
		String[] listeVilleAuto = new String[listeVille.size()];
		Iterator<String> itVille = listeVille.iterator();
		i = 0;
		while(itVille.hasNext())
		{
			listeVilleAuto[i] = itVille.next();
			i++;
		}
		AutoCompleteDocument villeAuto = new AutoCompleteDocument(this.villeTextField, listeVilleAuto);
		this.villeTextField.setDocument(villeAuto);
		this.villeTextField.addFocusListener(new focusListenertTextField());

		// Informations complémentaires
		JLabel infosComplementairesJlabel = new JLabel(GestionChateau.propertiesGeneral.getProperty("informations.client.infoscomplementaires"));
		this.infosComplementairesTextField.setFont(fontTextfield);

		panInfosClients.setPreferredSize(new Dimension(500,380));
		//panInfosClients.setBorder(BorderFactory.createLineBorder(Color.black));
		panInfosClients.setLayout(null);

		int margeGaucheInfosClientsLibelle = 100;
		int margeHautInfosClients = 40;
		int margeGaucheInfosClientsChamp = 225;
		int tailleHautInfosClients = 25;
		int tailleLargeurInfosClientsLibelle = 120;
		int tailleLargeurInfosClientsChamp = 125;

		// Titre
		infosClientTitreJlabel.setBounds(200, 0, tailleLargeurInfosClientsLibelle, tailleHautInfosClients);

		nomJlabel.setBounds(margeGaucheInfosClientsLibelle, margeHautInfosClients, tailleLargeurInfosClientsLibelle, tailleHautInfosClients);
		this.nomTextField.setBounds(margeGaucheInfosClientsChamp, margeHautInfosClients, tailleLargeurInfosClientsChamp, tailleHautInfosClients);
		prenomJlabel.setBounds(margeGaucheInfosClientsLibelle, margeHautInfosClients + 30, tailleLargeurInfosClientsLibelle, tailleHautInfosClients);
		this.prenomTextField.setBounds(margeGaucheInfosClientsChamp, margeHautInfosClients + 30, tailleLargeurInfosClientsChamp, tailleHautInfosClients);
		emailJlabel.setBounds(margeGaucheInfosClientsLibelle, margeHautInfosClients + 60, tailleLargeurInfosClientsLibelle, tailleHautInfosClients);
		this.emailTextField.setBounds(margeGaucheInfosClientsChamp, margeHautInfosClients + 60, tailleLargeurInfosClientsChamp+50, tailleHautInfosClients);
		numTelephoneJlabel.setBounds(margeGaucheInfosClientsLibelle, margeHautInfosClients + 90, tailleLargeurInfosClientsLibelle, tailleHautInfosClients);
		this.numTelephoneTextField.setBounds(margeGaucheInfosClientsChamp, margeHautInfosClients + 90, tailleLargeurInfosClientsChamp, tailleHautInfosClients);
		dateNaissanceJlabel.setBounds(margeGaucheInfosClientsLibelle, margeHautInfosClients + 120, tailleLargeurInfosClientsLibelle, tailleHautInfosClients);
		this.dateNaissanceCalendrier.setBounds(margeGaucheInfosClientsChamp, margeHautInfosClients + 120, 110, 25);
		codePostalJlabel.setBounds(margeGaucheInfosClientsLibelle, margeHautInfosClients + 150, tailleLargeurInfosClientsLibelle, tailleHautInfosClients);
		this.codePostalTextField.setBounds(margeGaucheInfosClientsChamp, margeHautInfosClients + 150, tailleLargeurInfosClientsChamp-80, tailleHautInfosClients);
		villeJlabel.setBounds(margeGaucheInfosClientsLibelle, margeHautInfosClients + 180, tailleLargeurInfosClientsLibelle, tailleHautInfosClients);
		this.villeTextField.setBounds(margeGaucheInfosClientsChamp, margeHautInfosClients + 180, tailleLargeurInfosClientsChamp, tailleHautInfosClients);
		adresseJlabel.setBounds(margeGaucheInfosClientsLibelle, margeHautInfosClients + 210, tailleLargeurInfosClientsLibelle, tailleHautInfosClients);
		this.adresseTextField.setBounds(margeGaucheInfosClientsChamp, margeHautInfosClients + 210, tailleLargeurInfosClientsChamp+50, tailleHautInfosClients);
		infosComplementairesJlabel.setBounds(175, margeHautInfosClients + 240, 200, tailleHautInfosClients);
		this.infosComplementairesTextField.setBounds(75, margeHautInfosClients + 265, 350, 50);

		panInfosClients.add(infosClientTitreJlabel);
		panInfosClients.add(dateNaissanceJlabel);
		panInfosClients.add(this.dateNaissanceCalendrier);
		panInfosClients.add(nomJlabel);
		panInfosClients.add(this.nomTextField);
		panInfosClients.add(prenomJlabel);
		panInfosClients.add(this.prenomTextField);
		panInfosClients.add(emailJlabel);
		panInfosClients.add(this.emailTextField);
		panInfosClients.add(numTelephoneJlabel);
		panInfosClients.add(this.numTelephoneTextField);
		panInfosClients.add(codePostalJlabel);
		panInfosClients.add(this.codePostalTextField);
		panInfosClients.add(villeJlabel);
		panInfosClients.add(this.villeTextField);
		panInfosClients.add(adresseJlabel);
		panInfosClients.add(this.adresseTextField);
		panInfosClients.add(infosComplementairesJlabel);
		panInfosClients.add(this.infosComplementairesTextField);

		// ============================================
		// ==          Informations destinataires    ==
		// ============================================

		// Titre
		JLabel infosDestinataireTitreJlabel = new JLabel(GestionChateau.propertiesGeneral.getProperty("informations.destinataire.titre"));
		infosDestinataireTitreJlabel.setFont(fontTitreTextfield);

		// Nom destinataire
		JLabel nomJlabelDestinataire = new JLabel(GestionChateau.propertiesGeneral.getProperty("informations.destinataire.nom"));
		this.nomTextFieldDestinataire.setFont(fontTextfield);

		// Prénom
		JLabel prenomJlabelDestinataire = new JLabel(GestionChateau.propertiesGeneral.getProperty("informations.destinataire.prenom"));
		this.prenomTextFieldDestinataire.setFont(fontTextfield);

		// Email
		JLabel emailJlabelDestinataire = new JLabel(GestionChateau.propertiesGeneral.getProperty("informations.destinataire.email"));
		this.emailTextFieldDestinataire.setFont(fontTextfield);

		// Numero de telephone
		JLabel numTelephoneJlabelDestinataire = new JLabel(GestionChateau.propertiesGeneral.getProperty("informations.destinataire.numero.telephone"));
		this.numTelephoneTextFieldDestinataire.setFont(fontTextfield);

		// Adresse
		JLabel adresseJlabelDestinataire = new JLabel(GestionChateau.propertiesGeneral.getProperty("informations.destinataire.adresse"));
		this.adresseTextFieldDestinataire.setFont(fontTextfield);

		// Code postal
		JLabel codePostalJlabelDestinataire = new JLabel(GestionChateau.propertiesGeneral.getProperty("informations.destinataire.codepostal"));
		CreerFacture.codePostalTextFieldDestinataire.setFont(fontTextfield);
		CreerFacture.codePostalTextFieldDestinataire.addFocusListener(new focusListenertTextField());

		// Ville
		JLabel villeJlabelDestinataire = new JLabel(GestionChateau.propertiesGeneral.getProperty("informations.destinataire.ville"));
		this.villeTextFieldDestinataire.setFont(fontTextfield);

		// Informations complémentaires
		JLabel infosComplementairesJlabelDestinataire = new JLabel(GestionChateau.propertiesGeneral.getProperty("informations.destinataire.infoscomplementaires"));
		this.infosComplementairesTextFieldDestinataire.setFont(fontTextfield);

		panInfosClientsDestinataire.setPreferredSize(new Dimension(500,380));
		//panInfosClientsDestinataire.setBorder(BorderFactory.createLineBorder(Color.black));
		panInfosClientsDestinataire.setLayout(null);

		infosDestinataireTitreJlabel.setBounds(200, 0, tailleLargeurInfosClientsLibelle, tailleHautInfosClients);
		nomJlabelDestinataire.setBounds(margeGaucheInfosClientsLibelle, margeHautInfosClients, tailleLargeurInfosClientsLibelle, tailleHautInfosClients);
		this.nomTextFieldDestinataire.setBounds(margeGaucheInfosClientsChamp, margeHautInfosClients, tailleLargeurInfosClientsChamp, tailleHautInfosClients);
		prenomJlabelDestinataire.setBounds(margeGaucheInfosClientsLibelle, margeHautInfosClients + 30, tailleLargeurInfosClientsLibelle, tailleHautInfosClients);
		this.prenomTextFieldDestinataire.setBounds(margeGaucheInfosClientsChamp, margeHautInfosClients + 30, tailleLargeurInfosClientsChamp, tailleHautInfosClients);
		emailJlabelDestinataire.setBounds(margeGaucheInfosClientsLibelle, margeHautInfosClients + 60, tailleLargeurInfosClientsLibelle, tailleHautInfosClients);
		this.emailTextFieldDestinataire.setBounds(margeGaucheInfosClientsChamp, margeHautInfosClients + 60, tailleLargeurInfosClientsChamp+50, tailleHautInfosClients);
		numTelephoneJlabelDestinataire.setBounds(margeGaucheInfosClientsLibelle, margeHautInfosClients + 90, tailleLargeurInfosClientsLibelle, tailleHautInfosClients);
		this.numTelephoneTextFieldDestinataire.setBounds(margeGaucheInfosClientsChamp, margeHautInfosClients + 90, tailleLargeurInfosClientsChamp, tailleHautInfosClients);
		codePostalJlabelDestinataire.setBounds(margeGaucheInfosClientsLibelle, margeHautInfosClients + 120, tailleLargeurInfosClientsLibelle, tailleHautInfosClients);
		CreerFacture.codePostalTextFieldDestinataire.setBounds(margeGaucheInfosClientsChamp, margeHautInfosClients + 120, tailleLargeurInfosClientsChamp-80, tailleHautInfosClients);
		villeJlabelDestinataire.setBounds(margeGaucheInfosClientsLibelle, margeHautInfosClients + 150, tailleLargeurInfosClientsLibelle, tailleHautInfosClients);
		this.villeTextFieldDestinataire.setBounds(margeGaucheInfosClientsChamp, margeHautInfosClients + 150, tailleLargeurInfosClientsChamp, tailleHautInfosClients);
		adresseJlabelDestinataire.setBounds(margeGaucheInfosClientsLibelle, margeHautInfosClients + 180, tailleLargeurInfosClientsLibelle, tailleHautInfosClients);
		this.adresseTextFieldDestinataire.setBounds(margeGaucheInfosClientsChamp, margeHautInfosClients + 180, tailleLargeurInfosClientsChamp+50, tailleHautInfosClients);
		//infosComplementairesJlabelDestinataire.setBounds(175, margeHautInfosClients + 210, 200, tailleHautInfosClients);
		infosComplementairesJlabelDestinataire.setBounds(175, margeHautInfosClients + 240, 200, tailleHautInfosClients);
		//this.infosComplementairesTextFieldDestinataire.setBounds(75, margeHautInfosClients + 235, 350, 50);
		this.infosComplementairesTextFieldDestinataire.setBounds(75, margeHautInfosClients + 265, 350, 50);

		panInfosClientsDestinataire.add(infosDestinataireTitreJlabel);
		panInfosClientsDestinataire.add(nomJlabelDestinataire);
		panInfosClientsDestinataire.add(this.nomTextFieldDestinataire);
		panInfosClientsDestinataire.add(prenomJlabelDestinataire);
		panInfosClientsDestinataire.add(this.prenomTextFieldDestinataire);
		panInfosClientsDestinataire.add(emailJlabelDestinataire);
		panInfosClientsDestinataire.add(this.emailTextFieldDestinataire);
		panInfosClientsDestinataire.add(numTelephoneJlabelDestinataire);
		panInfosClientsDestinataire.add(this.numTelephoneTextFieldDestinataire);
		panInfosClientsDestinataire.add(codePostalJlabelDestinataire);
		panInfosClientsDestinataire.add(CreerFacture.codePostalTextFieldDestinataire);
		panInfosClientsDestinataire.add(villeJlabelDestinataire);
		panInfosClientsDestinataire.add(this.villeTextFieldDestinataire);
		panInfosClientsDestinataire.add(adresseJlabelDestinataire);
		panInfosClientsDestinataire.add(this.adresseTextFieldDestinataire);
		panInfosClientsDestinataire.add(infosComplementairesJlabelDestinataire);
		panInfosClientsDestinataire.add(this.infosComplementairesTextFieldDestinataire);

		// ============================================
		// ==         Informations commandes         ==
		// ============================================

		panInfosCommandes.setPreferredSize(new Dimension(600,200));
		//panInfosCommandes.setBorder(BorderFactory.createLineBorder(Color.black));


		// Creation du tableau principal
		// Les titres des colonnes
		String  title[] = {GestionChateau.propertiesGeneral.getProperty("informations.commande.tableau.entete.designation"),
				GestionChateau.propertiesGeneral.getProperty("informations.commande.tableau.entete.quantite"),
				GestionChateau.propertiesGeneral.getProperty("informations.commande.tableau.entete.prixunitaire"),
				GestionChateau.propertiesGeneral.getProperty("informations.commande.tableau.entete.total")};

		// Les données du tableau
		List<Produit> produit = ListeDonneesBDD.recupProduitActif();
		String[] comboData = new String[produit.size()];
		Iterator<Produit> it3 = produit.iterator();
		i = 0;
		while(it3.hasNext())
		{
			Produit prod= it3.next();
			comboData[i] = prod.getLibelleProduit();
			i++;
		}

		this.tableModel = new TableModel(this.data, title);
		this.tableau = new JTable(this.tableModel);
		this.tableau.getColumn(GestionChateau.propertiesGeneral.getProperty("informations.commande.tableau.entete.designation")).setCellEditor(new DefaultCellEditor(new JComboBox(comboData)));
		centrerTable(this.tableau);

		// Mise en place d'un menu contextuel
		this.tableau.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseReleased(MouseEvent event){
				if(event.getButton() == MouseEvent.BUTTON3){

					// Ajouter une colonne
					JMenuItem ajouter = new JMenuItem(GestionChateau.propertiesGeneral.getProperty("informations.commande.tableau.ajouter.ligne"));
					ajouter.addActionListener(new AddListener());

					// Supprimer une ligne
					JMenuItem erase = new JMenuItem(GestionChateau.propertiesGeneral.getProperty("informations.commande.tableau.supprimer.ligne"));
					erase.addActionListener(new RemoveListener(CreerFacture.this.tableau.getSelectedRow()));

					//Ajout du menu contextuel
					JPopupMenu menu = new JPopupMenu();
					menu.add(ajouter);
					menu.add(erase);
					menu.show(CreerFacture.this.tableau, event.getX(), event.getY());
				}
			}
		});

		// Creation du bas du tableau
		// Total HT
		JLabel totalHTJlabel = new JLabel(GestionChateau.propertiesGeneral.getProperty("informations.commande.tableau.contenu.totalHT"));
		totalHTTextField.setFont(fontTextfield);
		totalHTTextField.setEditable(false);
		totalHTTextField.setHorizontalAlignment(SwingConstants.CENTER);

		// Remise
		JLabel remiseJlabel = new JLabel(GestionChateau.propertiesGeneral.getProperty("informations.commande.tableau.contenu.remise"));
		remiseTextField.setFont(fontTextfield);
		remiseTextField.addFocusListener(this.focusAdapter);
		remiseTextField.setHorizontalAlignment(SwingConstants.CENTER);

		// Port
		JLabel portJlabel = new JLabel(GestionChateau.propertiesGeneral.getProperty("informations.commande.tableau.contenu.port"));
		portTextField.setFont(fontTextfield);
		portTextField.addFocusListener(this.focusAdapter);
		portTextField.setHorizontalAlignment(SwingConstants.CENTER);

		// TVA
		JLabel tauxTVAJlabel = new JLabel(GestionChateau.propertiesGeneral.getProperty("informations.commande.tableau.contenu.tauxTVA"));
		tauxTVATextField.setFont(fontTextfield);
		tauxTVATextField.setEditable(false);
		tauxTVATextField.setHorizontalAlignment(SwingConstants.CENTER);

		// Total TTC
		JLabel totalTTCJlabel = new JLabel(GestionChateau.propertiesGeneral.getProperty("informations.commande.tableau.contenu.totalTTC"));
		totalTTCTextField.setFont(fontTextfield);
		totalTTCTextField.setEditable(false);
		totalTTCTextField.setHorizontalAlignment(SwingConstants.CENTER);


		// Alimentation du panel d 'informations des commandes
		panInfosCommandes.setLayout(null);

		// Taille de la colonne Désignation
		this.tableau.getColumnModel().getColumn(0).setPreferredWidth(330);
		this.tableau.getColumnModel().getColumn(1).setPreferredWidth(90);
		this.tableau.getColumnModel().getColumn(2).setPreferredWidth(90);
		this.tableau.getColumnModel().getColumn(3).setPreferredWidth(90);

		JScrollPane jScroll = new JScrollPane(this.tableau);
		//jScroll.setBorder(BorderFactory.createLineBorder(Color.green));

		jScroll.setBounds(0, 0, 600, 100);
		panInfosCommandes.add(jScroll);

		totalHTJlabel.setBounds(430, 100, 75, 20);
		totalHTTextField.setBounds(510, 100, 90, 20);
		panInfosCommandes.add(totalHTJlabel);
		panInfosCommandes.add(totalHTTextField);
		remiseJlabel.setBounds(430, 120, 75, 20);
		remiseTextField.setBounds(510, 120, 90, 20);
		panInfosCommandes.add(remiseJlabel);
		panInfosCommandes.add(remiseTextField);
		portJlabel.setBounds(430, 140, 75, 20);
		portTextField.setBounds(510, 140, 90, 20);
		panInfosCommandes.add(portJlabel);
		panInfosCommandes.add(portTextField);
		tauxTVAJlabel.setBounds(430, 160, 75, 20);
		tauxTVATextField.setBounds(510, 160, 90, 20);
		panInfosCommandes.add(tauxTVAJlabel);
		panInfosCommandes.add(tauxTVATextField);
		totalTTCJlabel.setBounds(430, 180, 75, 20);
		totalTTCTextField.setBounds(510, 180, 90, 20);
		panInfosCommandes.add(totalTTCJlabel);
		panInfosCommandes.add(totalTTCTextField);

		// ============================================
		// ==       Informations transactions        ==
		// ============================================

		panInfosTransaction.setPreferredSize(new Dimension(750,60));
		//panInfosTransaction.setBorder(BorderFactory.createLineBorder(Color.black));
		System.out.println(panInfosTransaction.getLayout());
		panInfosTransaction.setLayout(null);

		// Lieu de la transaction
		JLabel lieuTransactionJlabel = new JLabel(GestionChateau.propertiesGeneral.getProperty("informations.transaction.lieu.transaction"));
		this.lieuTransactionTextField.setFont(fontTextfield);
		// Valeur par defaut du lieu de la transaction
		this.lieuTransactionTextField.setText(GestionChateau.propertiesMetier.getProperty("creer.facture.lieu.de.transaction.defaut"));
		// Moyen de paiement
		JLabel moyenPaiementJlabel = new JLabel(GestionChateau.propertiesGeneral.getProperty("informations.transaction.moyen.paiement"));
		List<MoyenPaiement> moyenPaiement = ListeDonneesBDD.recupMoyenPaiementActif();
		String[] moyenPaiementListe = new String[moyenPaiement.size()];
		Iterator<MoyenPaiement> it = moyenPaiement.iterator();
		i = 0;
		while(it.hasNext())
		{
			MoyenPaiement moyen= it.next();
			moyenPaiementListe[i] = moyen.getLibelleMoyenPaiement();
			i++;
		}
		this.moyenPaiementJComboBox = new JComboBox(moyenPaiementListe);
		// Etat du paiement
		JLabel etatPaiementJlabel = new JLabel(GestionChateau.propertiesGeneral.getProperty("informations.transaction.etat.paiement"));
		List<EtatPaiement> etatPaiement = ListeDonneesBDD.recupEtatPaiementActif();
		String[] etatPaiementListe = new String[etatPaiement.size()];
		Iterator<EtatPaiement> it2 = etatPaiement.iterator();
		i = 0;
		while(it2.hasNext())
		{
			EtatPaiement etat = it2.next();
			etatPaiementListe[i] = etat.getLibelleEtatPaiement();
			i++;
		}
		this.etatPaiementJComboBox = new JComboBox(etatPaiementListe);
		// Générer la facture
		this.genererFacture.addActionListener(this);

		lieuTransactionJlabel.setBounds(0, 0, 130, 20);
		this.lieuTransactionTextField.setBounds(lieuTransactionJlabel.getWidth()+5, 0, 125, 20);
		panInfosTransaction.add(lieuTransactionJlabel);
		panInfosTransaction.add(this.lieuTransactionTextField);
		moyenPaiementJlabel.setBounds(270, 0, 130, 20);
		this.moyenPaiementJComboBox.setBounds(395, 0, 125, 20);
		panInfosTransaction.add(moyenPaiementJlabel);
		panInfosTransaction.add(this.moyenPaiementJComboBox);
		etatPaiementJlabel.setBounds(530, 0, 100, 20);
		this.etatPaiementJComboBox.setBounds(620, 0, 125, 20);
		panInfosTransaction.add(etatPaiementJlabel);
		panInfosTransaction.add(this.etatPaiementJComboBox);
		this.genererFacture.setBounds(300, 30, 150, 20);
		panInfosTransaction.add(this.genererFacture);

		panInfosClients.setBackground(Parametres_Appli.couleurPanel);
		panInfosClientsDestinataire.setBackground(Parametres_Appli.couleurPanel);
		panInfosCommandes.setBackground(Parametres_Appli.couleurFond);
		panInfosTransaction.setBackground(Parametres_Appli.couleurFond);
		//Au nord
		this.add(label_haut, BorderLayout.NORTH);
		this.add(panInfosClients, BorderLayout.CENTER);
		this.add(panInfosClientsDestinataire, BorderLayout.CENTER);
		this.add(panInfosCommandes, BorderLayout.CENTER);
		this.add(panInfosTransaction, BorderLayout.CENTER);
		//Au sud
		//this.add(new JButton("coucou"), BorderLayout.SOUTH);
		//À l'ouest
		//this.add(new JButton("coucou"), BorderLayout.WEST);
		//À l'est
		//this.add(new JButton("coucou"), BorderLayout.EAST);
	}


	/**
	 * Permet d'ajouter une ligne au tableau
	 * @author AAUM
	 */
	class AddListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent event) {
			Object[] donnee = new Object[]{"", "", "",""};
			((TableModel)CreerFacture.this.tableau.getModel()).addRow(donnee);
		}

	}

	/**
	 * Permet de supprimer une ligne du tableau
	 * @author AAUM
	 */
	class RemoveListener implements ActionListener{
		private final int ligne;
		public RemoveListener(int ligne){
			this.ligne = ligne;
		}
		@Override
		public void actionPerformed(ActionEvent event) {
			((TableModel)CreerFacture.this.tableau.getModel()).removeRow(this.ligne);
		}
	}

	// Bouton de génération de la facture
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == this.genererFacture){
			// Vérification que les champs nom et prenom ne sont pas vides
			Boolean validiteFormulaire = true;
			String messageErreur = "Champ(s) vide(s) :\n";
			if (this.nomTextField.getText().equals("")){
				validiteFormulaire=false;
				messageErreur+=" - Nom\n";
			}
			if (this.prenomTextField.getText().equals("")){
				validiteFormulaire=false;
				messageErreur+=" - Prénom\n";
			}
			if (!validiteFormulaire){
				JOptionPane.showMessageDialog(null, messageErreur,"Erreur",JOptionPane.ERROR_MESSAGE);
			}else{
				if (!CreerFacture.totalTTCTextField.getText().equals("")){
					int intGenererFacture = 0;
					intGenererFacture = JOptionPane.showConfirmDialog(null,GestionChateau.propertiesGeneral.getProperty("generer.facture.message.confirmation.contenu"),
							GestionChateau.propertiesGeneral.getProperty("generer.facture.message.confirmation.titre"),2,2);
					if (intGenererFacture == 0){
						GenererFacturePdf genererFacturePdf = new GenererFacturePdf();
						try {
							Client client = new Client();
							Facture facture = new Facture();
							DateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
							String dateNaissanceText = "";

							facture.setClient(client);
							client.setNom(this.nomTextField.getText());
							client.setPrenom(this.prenomTextField.getText());
							client.setAdresse(this.adresseTextField.getText());
							client.setVille(this.villeTextField.getText());
							client.setCp(this.codePostalTextField.getText());
							if (!(this.dateNaissanceCalendrier.getDate() == null)){
								dateNaissanceText = formatDate.format(this.dateNaissanceCalendrier.getDate());
							}
							client.setDateNaissance(dateNaissanceText);
							client.setMail(this.emailTextField.getText());
							client.setTelephone(this.numTelephoneTextField.getText());
							// Récupération de l id du client
							DAO<Client> clientDao = new ClientDAO(Connect.getInstance());
							client.setId(clientDao.findId(client));

							facture.setLieuTransaction(this.lieuTransactionTextField.getText());
							this.tableau.getModel().getRowCount();
							//System.out.println(tableau.getModel().getRowCount());
							int i;
							String libelleProduit = "";
							String quantite = "";
							String prixUnitaire = "";
							String prixTotal = "";

							int ligneTableauRenseignee = 0;
							for(i=0;i<this.tableau.getModel().getRowCount();i++){
								// Vérification que le champ quantité n est pas null
								if (this.tableau.getValueAt(i, 0).toString() != "" ){
									ligneTableauRenseignee++;
								}
							}
							LigneDeProduit[] ligneDeProduit = new LigneDeProduit[ligneTableauRenseignee];
							for(i=0;i<ligneTableauRenseignee;i++){
								// Vérification que le champ quantité n est pas null
								if (this.tableau.getValueAt(i, 0).toString() == "" ){
									libelleProduit = "NULL";
								}else{
									libelleProduit = this.tableau.getValueAt(i, 0).toString();
									ligneTableauRenseignee++;
								}
								// Vérification que le champ quantité n est pas null
								if (this.tableau.getValueAt(i, 1).toString() == ""){
									quantite = "0";
								}else{
									quantite = this.tableau.getValueAt(i, 1).toString();
								}
								// Vérification que le champ prix unitaire n est pas null
								if (this.tableau.getValueAt(i, 2).toString() == ""){
									prixUnitaire = "0";
								}else{
									prixUnitaire = this.tableau.getValueAt(i, 2).toString();
								}
								// Vérification que le champ prix total n est pas null
								if (this.tableau.getValueAt(i, 3).toString() == ""){
									prixTotal = "0";
								}else{
									prixTotal = this.tableau.getValueAt(i, 3).toString();
								}
								System.out.println("CA PLANTE PAS ICI");
								if(libelleProduit != "NULL"){
									ligneDeProduit[i] = new LigneDeProduit(new Produit(libelleProduit.toString()), Integer.parseInt(quantite), Double.parseDouble(prixUnitaire),Double.parseDouble(prixTotal));
								}
							}

							facture.setLigneDeProduit(ligneDeProduit);
							facture.setPrixTotalHT(Double.parseDouble(CreerFacture.totalHTTextField.getText()));
							facture.setRemise(Double.parseDouble(CreerFacture.remiseTextField.getText()));
							facture.setPort(Double.parseDouble(CreerFacture.portTextField.getText()));
							facture.setTxTVA(Double.parseDouble(CreerFacture.tauxTVATextField.getText()));
							facture.setPrixTotalTTC(Double.parseDouble(CreerFacture.totalTTCTextField.getText()));
							facture.setLieuTransaction(this.lieuTransactionTextField.getText());
							facture.setIdEtatPaiement(ListeDonneesBDD.recupIdEtatPaiement(this.etatPaiementJComboBox.getSelectedItem().toString()));
							facture.setIdMoyenPaiement(ListeDonneesBDD.recupIdMoyenPaiement(this.moyenPaiementJComboBox.getSelectedItem().toString()));
							//System.out.println(etatPaiementJComboBox.getSelectedItem().toString());
							facture.setNumeroFacture(ListeDonneesBDD.getNumeroFacutre());
							facture.setDateFacture(formatDate.format(new Date()));

							// Destinataire
							facture.setDestNom(this.nomTextFieldDestinataire.getText());
							facture.setDestPrenom(this.prenomTextFieldDestinataire.getText());
							facture.setDestAdresse(this.adresseTextFieldDestinataire.getText());
							facture.setDestVille(this.villeTextFieldDestinataire.getText());
							facture.setDestCodePostal(CreerFacture.codePostalTextFieldDestinataire.getText());
							facture.setDestMail(this.emailTextFieldDestinataire.getText());
							facture.setDestTelephone(this.numTelephoneTextFieldDestinataire.getText());

							genererFacturePdf.manipulatePdf(facture);
							JOptionPane.showMessageDialog(null, "Génération OK");

							// Ouverture du PDF (facture)
							java.awt.Desktop.getDesktop().open(new File(GestionChateau.propertiesMetier.getProperty("export.facture")+"facture_"+facture.getNumeroFacture()+".pdf"));

							DAO<Facture> factureDao = new FactureDAO(Connect.getInstance());
							factureDao.create(facture);
							// Itérer sur les lignes de produits
							LigneDeProduit[] ligneDeProduitList = facture.getLigneDeProduit();
							i = 0;
							for(i=0;i<ligneDeProduitList.length;i++)
							{
								if (ligneDeProduit[i].getProduit().getLibelleProduit() != "NULL"){
									ligneDeProduit[i].setId_produit_fk(ListeDonneesBDD.findIdProduit(ligneDeProduit[i].getProduit()));
									ligneDeProduit[i].setId_facture_fk(ListeDonneesBDD.findIdFacture(facture));
									DAO<LigneDeProduit> ligneDeProduitDao = new LigneDeProduitDAO(Connect.getInstance());
									ligneDeProduitDao.create(ligneDeProduit[i]);
									System.out.println(ligneDeProduit[i].getId_facture_fk());
									System.out.println(facture.getNumeroFacture());


									//On enleve du stock les produits sur la facture
									//Pour cela on recup le stock en base et on soustrait les produits en commandes
									DAO<Produit> produitDao = new ProduitDAO(Connect.getInstance());
									int nbStock = ((ProduitDAO) produitDao).recupStock(ligneDeProduit[i].getId_produit_fk());
									System.out.println("Voici le stock actuel : " + nbStock);
									int nbNewStock = nbStock - ligneDeProduit[i].getQuantite();
									System.out.println("Voici le nouveau stock : " + nbNewStock);
									//On met a jour en base
									Produit lProduit = new Produit();
									lProduit.setId(ligneDeProduit[i].getId_produit_fk());
									lProduit.setNbStock(nbNewStock);
									((ProduitDAO) produitDao).updateStock(lProduit);
								}
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (DocumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}else{
					JOptionPane.showMessageDialog(null, "Problème lors de la génération de la facture");
				}
			}
		}
	}

	FocusAdapter focusAdapter = new FocusAdapter() {
		@Override
		public void focusGained( FocusEvent e) {

		}

		@Override
		public void focusLost( FocusEvent e) {
			Double valeurTempTTC = (Double.parseDouble(CreerFacture.totalTTCTextField.getText())+Double.parseDouble(CreerFacture.portTextField.getText()))-Double.parseDouble(CreerFacture.remiseTextField.getText());
			CreerFacture.totalTTCTextField.setText(valeurTempTTC.toString());
		}
	};


	public void centrerTable(JTable table) {
		DefaultTableCellRenderer custom = new DefaultTableCellRenderer();
		custom.setHorizontalAlignment(SwingConstants.CENTER);
		for (int i=0 ; i<table.getColumnCount() ; i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(custom);
		}
	}

	// Classe permettant une action en perdant le focus d un textfield
	private class focusListenertTextField implements FocusListener
	{
		@Override
		public void focusLost(FocusEvent e)
		{
			Object Donnees = e.getSource(); // On récupère l'objet appelant
			JTextField champ = (JTextField)Donnees; // On le caste en JTextField
			// Alimentation des informations destinataires a partir des informations clients
			if (e.getSource() == CreerFacture.this.nomTextField){
				CreerFacture.this.nomTextFieldDestinataire.setText(CreerFacture.this.nomTextField.getText());
			}
			if (e.getSource() == CreerFacture.this.prenomTextField){
				CreerFacture.this.prenomTextFieldDestinataire.setText(CreerFacture.this.prenomTextField.getText());
			}
			if (e.getSource() == CreerFacture.this.emailTextField){
				CreerFacture.this.emailTextFieldDestinataire.setText(CreerFacture.this.emailTextField.getText());
			}
			if (e.getSource() == CreerFacture.this.numTelephoneTextField){
				CreerFacture.this.numTelephoneTextFieldDestinataire.setText(CreerFacture.this.numTelephoneTextField.getText());
			}
			if (e.getSource() == CreerFacture.this.codePostalTextField){
				CreerFacture.codePostalTextFieldDestinataire.setText(CreerFacture.this.codePostalTextField.getText());
			}
			if (e.getSource() == CreerFacture.this.villeTextField){
				CreerFacture.this.villeTextFieldDestinataire.setText(CreerFacture.this.villeTextField.getText());
			}
			if (e.getSource() == CreerFacture.this.adresseTextField){
				CreerFacture.this.adresseTextFieldDestinataire.setText(CreerFacture.this.adresseTextField.getText());
			}
			if ((e.getSource() == CreerFacture.codePostalTextFieldDestinataire) && !("").equals(totalTTCTextField.getText())){
				//CreerFacture.this.tableau.getModel().notify();
				TableModel.enrichissementTableauTotaux(CreerFacture.this.data);
			}
		}
		@Override
		public void focusGained(FocusEvent e)
		{
		}
	}


}
