package Affichage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import Affichage.Tableaux.TableVoirFacture;
import Bean.Facture;
import Conf.Parametres_Appli;
import Dao.ListeDonneesBDD;

import com.toedter.calendar.JDateChooser;

public class VoirFacture extends JPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static JLabel label_haut = new JLabel(GestionChateau.propertiesGeneral.getProperty("affichage.facture"));
	public static JPanel panTable = new JPanel();
	public static JPanel panRecherche = new JPanel();

	private final JTable tableau;
	Object[][] data;
	String  title[] = {
			"Numéro facture",
			"Nom",
			"Prénom",
			"Ville",
			"Date",
	"Total"};
	Object[][] dataPartiel;
	TableVoirFacture tableVoirFacture;

	JTextField villeTextField;
	JTextField numeroFactureTextField;
	JTextField nomClientTextField;
	JTextField dateTextField;
	List<Facture> listeFacture;
	List<Facture> listeFactureCritere;
	JDateChooser calendrierDate;


	ImageIcon icon = new ImageIcon("img/search.png" );
	public JButton voirFacture = new JButton(this.icon);



	//public JButton voirFacture = new JButton(GestionChateau.propertiesGeneral.getProperty("voir.facture.rechercher"));

	public VoirFacture(){

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

		// RECHERCHE
		panRecherche.setPreferredSize(new Dimension(700,70));
		//panRecherche.setBorder(BorderFactory.createLineBorder(Color.black));
		panRecherche.setLayout(null);

		// Police par défaut des textfield
		Font fontTextfield = new Font("Arial", Font.LAYOUT_LEFT_TO_RIGHT, 12);
		// Numéro de la facture
		JLabel numeroFactureJlabel = new JLabel(GestionChateau.propertiesGeneral.getProperty("affichage.recherche.numero.facture"));
		this.numeroFactureTextField = new JTextField();
		this.numeroFactureTextField.setFont(fontTextfield);
		// Nom du client
		JLabel nomClientJlabel = new JLabel(GestionChateau.propertiesGeneral.getProperty("affichage.recherche.nom.client"));
		this.nomClientTextField = new JTextField();
		this.nomClientTextField.setFont(fontTextfield);
		// Date de la transaction
		JLabel dateJlabel = new JLabel(GestionChateau.propertiesGeneral.getProperty("affichage.recherche.date.transaction"));
		//dateTextField = new JTextField();
		//dateTextField.setFont(fontTextfield);
		this.calendrierDate = new JDateChooser();
		this.calendrierDate.setBounds(520, 5, 110, 25);
		// Numéro de la facture
		JLabel villeJlabel = new JLabel(GestionChateau.propertiesGeneral.getProperty("affichage.recherche.ville"));
		this.villeTextField = new JTextField();
		this.villeTextField.setFont(fontTextfield);
		// Afficher la facture
		this.voirFacture.addActionListener(this);

		numeroFactureJlabel.setBounds(5, 5, 75, 25);
		this.numeroFactureTextField.setBounds(90, 5, 75, 25);
		nomClientJlabel.setBounds(180, 5, 90, 25);
		this.nomClientTextField.setBounds(280, 5, 90, 25);
		dateJlabel.setBounds(380, 5, 140, 25);
		//dateTextField.setBounds(520, 5, 50, 25);
		villeJlabel.setBounds(5,30, 75, 25);
		this.villeTextField.setBounds(90, 30, 75, 25);


		this.voirFacture.setBounds(280, 40, 90, 27);
		this.voirFacture.setToolTipText(GestionChateau.propertiesGeneral.getProperty("voir.facture.rechercher"));


		panRecherche.add(numeroFactureJlabel);
		panRecherche.add(this.numeroFactureTextField);
		panRecherche.add(nomClientJlabel);
		panRecherche.add(this.nomClientTextField);
		panRecherche.add(dateJlabel);
		panRecherche.add(this.calendrierDate);
		panRecherche.add(villeJlabel);
		panRecherche.add(this.villeTextField);
		panRecherche.add(this.voirFacture);

		// TABLEAU
		panTable.setPreferredSize(new Dimension(800,400));
		panTable.setBorder(BorderFactory.createLineBorder(Color.black));
		// Alimentation du panel d 'informations des commandes
		panTable.setLayout(null);
		// Creation du tableau principal
		//Les titres des colonnes

		//Les données du tableau
		System.out.println(ListeDonneesBDD.getNbrFacture());
		this.data = new Object[ListeDonneesBDD.getNbrFacture()][this.title.length];
		this.listeFacture = ListeDonneesBDD.recupAllFacture();
		Iterator<Facture> itFacture = this.listeFacture.iterator();

		int i = 0;
		while(itFacture.hasNext())
		{
			Facture facture = itFacture.next();
			this.data[i][0]=facture.getNumeroFacture();
			this.data[i][1]=facture.getClient().getNom();
			this.data[i][2]=facture.getClient().getPrenom();
			this.data[i][3]=facture.getClient().getVille();
			this.data[i][4]=facture.getDateFactureFormate();
			this.data[i][5]=facture.getPrixTotalTTC();
			i++;
		}

		this.tableVoirFacture = new TableVoirFacture(this.data, this.title);

		this.tableau = new JTable(this.tableVoirFacture);
		DefaultTableCellRenderer custom = new DefaultTableCellRenderer();
		custom.setFont(new Font("Verdana", Font.BOLD, 12));
		this.tableau.getTableHeader().setFont(new Font("Verdana", Font.BOLD, 12));

		// Mise en place d'un menu contextuel
		this.tableau.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseReleased(MouseEvent event){
				if(event.getButton() == MouseEvent.BUTTON3){
					// Ajouter une colonne
					JMenuItem ajouter = new JMenuItem(GestionChateau.propertiesGeneral.getProperty("affichage.recherche.tableau.afficher.ligne"));
					ajouter.addActionListener(new AddListener(VoirFacture.this.data[VoirFacture.this.tableau.getSelectedRow()][0].toString()));
					//Ajout du menu contextuel
					JPopupMenu menu = new JPopupMenu();
					menu.add(ajouter);
					menu.show(VoirFacture.this.tableau, event.getX(), event.getY());
				}
			}
		});

		// Création d'un scroll
		JScrollPane myScrollPane = new JScrollPane(this.tableau);
		//myScrollPane.setBorder(BorderFactory.createLineBorder(Color.green));
		myScrollPane.setBounds(0, 0, 800, 400);
		panTable.add(myScrollPane);

		//Au nord
		this.add(label_haut, BorderLayout.NORTH);
		this.add(panRecherche, BorderLayout.CENTER);
		this.add(panTable, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String dateFactureText;
		if(e.getSource() == this.voirFacture){
			// On filtre sur les critères de recherche
			if (!this.numeroFactureTextField.getText().equals("")){
				System.out.println("numeroFactureTextField non vide");
			}
			if (!this.nomClientTextField.getText().equals("")){
				System.out.println("nomClientTextField non vide");
			}
			DateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
			if (!(this.calendrierDate.getDate() == null)){
				dateFactureText = formatDate.format(this.calendrierDate.getDate());
			}else{
				dateFactureText ="";
			}
			this.dataPartiel = new Object[ListeDonneesBDD.getNbrFactureCriteres(this.numeroFactureTextField.getText(), this.nomClientTextField.getText(), dateFactureText, this.villeTextField.getText())][this.title.length];
			this.listeFactureCritere = ListeDonneesBDD.recupAllFactureCriteres(this.numeroFactureTextField.getText(), this.nomClientTextField.getText(), dateFactureText,this.villeTextField.getText());
			Iterator<Facture> itFacture2 = this.listeFactureCritere.iterator();
			int i = 0;
			while(itFacture2.hasNext())
			{
				/*Facture facture = (Facture)itFacture2.next();
	        	dataPartiel[i][0]=facture.getNumeroFacture();
	        	dataPartiel[i][1]=facture.getClient().getNom();
	        	dataPartiel[i][2]=facture.getClient().getPrenom();
	        	dataPartiel[i][3]=facture.getDateFactureFormate();
	        	dataPartiel[i][4]=facture.getPrixTotalTTC();
	        	i++;*/
				Facture facture = itFacture2.next();
				this.dataPartiel[i][0]=facture.getNumeroFacture();
				this.dataPartiel[i][1]=facture.getClient().getNom();
				this.dataPartiel[i][2]=facture.getClient().getPrenom();
				this.dataPartiel[i][3]=facture.getClient().getVille();
				this.dataPartiel[i][4]=facture.getDateFactureFormate();
				this.dataPartiel[i][5]=facture.getPrixTotalTTC();
				i++;
			}
			this.tableVoirFacture.setData(this.dataPartiel);
		}

	}

	/**
	 * Permet d'ajouter une ligne au tableau
	 * @author AAUM
	 */
	class AddListener implements ActionListener{
		private final String numeroFacture;
		public AddListener(String numeroFacture){
			this.numeroFacture = numeroFacture;
		}
		@Override
		public void actionPerformed(ActionEvent event) {
			if(Desktop.isDesktopSupported()){
				if(Desktop.getDesktop().isSupported(java.awt.Desktop.Action.OPEN)){
					try {

						//java.awt.Desktop.getDesktop().open(new File("C:/work_svn_google/facture-castle/editions/facture_"+this.numeroFacture+".pdf"));
						java.awt.Desktop.getDesktop().open(new File(GestionChateau.propertiesMetier.getProperty("export.facture")+"facture_"+this.numeroFacture+".pdf"));
					} catch (IOException ex) {
						//Traitement de l'exception
					}
				} else {
					//La fonction n'est pas supportée par votre système d'exploitation
				}
			}else {
				//Desktop pas supportée par votre système d'exploitation
			}

		}
	}
}
