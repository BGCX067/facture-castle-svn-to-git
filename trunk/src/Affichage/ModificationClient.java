package Affichage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
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

import Affichage.Tableaux.TableModifierClient;
import Bean.Client;
import Conf.Parametres_Appli;
import Dao.ClientDAO;
import Dao.DAO;
import Dao.ListeDonneesBDD;
import bdd.Connect;

public class ModificationClient extends JPanel implements ActionListener {

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;

	public static JLabel label_haut = new JLabel(GestionChateau.propertiesGeneral.getProperty("modifier.client.titre"));
	public static JPanel panTableauClient = new JPanel();
	public static JPanel panModificationClient = new JPanel();

	public JButton modifierClient = new JButton(GestionChateau.propertiesGeneral.getProperty("modifier.client.bouton.modifier"));
	public JButton supprimerClient = new JButton(GestionChateau.propertiesGeneral.getProperty("modifier.client.bouton.supprimer"));

	/**
	 * Variables concernant le tableau des clients
	 */
	// La liste des clients
	List<Client> listeClient;

	// Le tableau qui va permettre d'afficher la liste des clients
	private JTable tableau = new JTable();

	// Le tableau de données
	Object[][] data;

	// Le tableau de titre des colonnes
	String title[] = { "Num Client", "Nom du Client", "Prenom du Client",
	"Ville" };

	// Le tableau que l'on insère dans le JTable
	TableModifierClient tableModifierClient;

	JTextField nomClientTextField = new JTextField();
	JTextField prenomClientTextField = new JTextField();
	JTextField adresseClientTextField = new JTextField();
	JTextField villeClientTextField = new JTextField();
	JTextField cpClientTextField = new JTextField();
	JTextField mailClientTextField = new JTextField();
	JTextField telephoneClientTextField = new JTextField();
	JTextField ddnClientTextField = new JTextField();
	JTextArea infosClientTextField = new JTextArea();

	public static JLabel txtIdClientHidden = new JLabel("");

	/**
	 * Mise en place du panel qui permet d'afficher le tableau des clients
	 */
	public ModificationClient() {

		// Initialisation des panels
		panModificationClient.removeAll();
		panTableauClient.removeAll();

		// Méthode qui permet de mettre en place le panel
		miseEnFormePanel();

		miseEnPlaceTableau();

		misePlacePartieModification();

	}

	/**
	 * Méthode qui permet de mettre en place le panel principal
	 */
	private void miseEnFormePanel() {

		// Enrichissement du panel général
		this.setPreferredSize(new Dimension(Parametres_Appli.tailleLargeurPanelApplication,Parametres_Appli.tailleHauteurPanelApplication));
		this.setBackground(Parametres_Appli.couleurFond);
		// ============================================
		// == Label haut ==
		// ============================================
		label_haut.setHorizontalAlignment(SwingConstants.CENTER);
		label_haut.setFont(Parametres_Appli.titreFont);
		label_haut.setPreferredSize(new Dimension(Parametres_Appli.tailleLargeurPanelApplication,25));
		// ============================================
		// == Liste Produits ==
		// ============================================
		panTableauClient.setPreferredSize(new Dimension(700, 400));
		panTableauClient.setLayout(null);
		panTableauClient.setVisible(true);
		panTableauClient.setBackground(Parametres_Appli.couleurFond);

		this.add(label_haut, BorderLayout.NORTH);
	}

	/**
	 * Méthode qui permet de mettre en place le textfield
	 */
	private Font Police() {

		// Police par défaut des textfield
		Font fontTextfield = new Font("Arial", Font.LAYOUT_LEFT_TO_RIGHT, 12);
		return fontTextfield;
	}

	/**
	 * Méthode qui permet de mettre en place le tableau
	 */
	private void miseEnPlaceTableau() {

		/**
		 * TABLEAU LISTE CLIENT
		 */
		// On met en place le tableau
		this.data = new Object[ListeDonneesBDD.getNbrClient()][this.title.length];
		this.listeClient = ListeDonneesBDD.recupClient();
		Iterator<Client> itClient = this.listeClient.iterator();

		int i = 0;
		while (itClient.hasNext()) {
			Client client = itClient.next();
			this.data[i][0] = client.getId();
			this.data[i][1] = client.getNom();
			this.data[i][2] = client.getPrenom();
			this.data[i][3] = client.getVille();
			i++;
		}

		this.tableModifierClient = new TableModifierClient(this.data, this.title);
		this.tableau = new JTable(this.tableModifierClient);

		// Création d'un scroll
		JScrollPane myScrollPane = new JScrollPane(this.tableau);
		myScrollPane.setBounds(0, 0, 700, 400);
		panTableauClient.add(myScrollPane);

		// Mise en place d'un menu contextuel
		this.tableau.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent event) {

				if (event.getButton() == MouseEvent.BUTTON3) {

					JMenuItem modifier = new JMenuItem(
							GestionChateau.propertiesGeneral
							.getProperty("affichage.recherche.tableau.modifier.ligne"));

					modifier.addActionListener(new AddListener(ModificationClient.this.data[ModificationClient.this.tableau
					                                                                        .getSelectedRow()][0].toString()));
					JPopupMenu menu = new JPopupMenu();
					menu.add(modifier);
					menu.show(ModificationClient.this.tableau, event.getX(), event.getY());
				}
			}
		});

		this.add(panTableauClient, BorderLayout.CENTER);
	}

	/**
	 * Méthode qui permet de mettre en place la partie de modification
	 */
	private void misePlacePartieModification() {

		// ============================================
		// == Panel Modification ==
		// ============================================
		panModificationClient.setPreferredSize(new Dimension(700, 400));
		panModificationClient.setLayout(null);
		panModificationClient.setBackground(Parametres_Appli.couleurFond);

		// Nom du client
		JLabel nomClientJlabel = new JLabel(GestionChateau.propertiesGeneral
				.getProperty("modifier.client.informations.client.nom"));
		// On ajout le label nom Client au panel
		nomClientJlabel.setBounds(100, 5, 150, 25);
		panModificationClient.add(nomClientJlabel);
		// On ajout le textfield nom client au panel
		this.nomClientTextField.setFont(this.Police());
		this.nomClientTextField.setBounds(225, 5, 175, 25);
		panModificationClient.add(this.nomClientTextField);

		// Prenom du client
		JLabel prenomClientJlabel = new JLabel(GestionChateau.propertiesGeneral
				.getProperty("modifier.client.informations.client.prenom"));
		// On ajout le label prenom Client au panel
		prenomClientJlabel.setBounds(100, 30, 150, 25);
		panModificationClient.add(prenomClientJlabel);
		// On ajout le textfield prenom client au panel
		this.prenomClientTextField.setFont(this.Police());
		this.prenomClientTextField.setBounds(225, 30, 175, 25);
		panModificationClient.add(this.prenomClientTextField);

		// Adresse du client
		JLabel adresseClientJlabel = new JLabel(
				GestionChateau.propertiesGeneral
				.getProperty("modifier.client.informations.client.adresse"));
		// On ajout le label prenom Client au panel
		adresseClientJlabel.setBounds(100, 55, 150, 25);
		panModificationClient.add(adresseClientJlabel);
		// On ajout le textfield prenom client au panel
		this.adresseClientTextField.setFont(this.Police());
		this.adresseClientTextField.setBounds(225, 55, 175, 25);
		panModificationClient.add(this.adresseClientTextField);

		// Ville du client
		JLabel villeClientJlabel = new JLabel(GestionChateau.propertiesGeneral
				.getProperty("modifier.client.informations.client.ville"));
		// On ajout le label prenom Client au panel
		villeClientJlabel.setBounds(100, 80, 150, 25);
		panModificationClient.add(villeClientJlabel);
		// On ajout le textfield prenom client au panel
		this.villeClientTextField.setFont(this.Police());
		this.villeClientTextField.setBounds(225, 80, 175, 25);
		panModificationClient.add(this.villeClientTextField);

		// Code Postal du client
		JLabel cpClientJlabel = new JLabel(GestionChateau.propertiesGeneral
				.getProperty("modifier.client.informations.client.cp"));
		// On ajout le label prenom Client au panel
		cpClientJlabel.setBounds(100, 105, 150, 25);
		panModificationClient.add(cpClientJlabel);
		// On ajout le textfield prenom client au panel
		this.cpClientTextField.setFont(this.Police());
		this.cpClientTextField.setBounds(225, 105, 175, 25);
		panModificationClient.add(this.cpClientTextField);

		// Mail du client
		JLabel mailClientJlabel = new JLabel(GestionChateau.propertiesGeneral
				.getProperty("modifier.client.informations.client.mail"));
		// On ajout le label prenom Client au panel
		mailClientJlabel.setBounds(100, 130, 150, 25);
		panModificationClient.add(mailClientJlabel);
		// On ajout le textfield prenom client au panel
		this.mailClientTextField.setFont(this.Police());
		this.mailClientTextField.setBounds(225, 130, 175, 25);
		panModificationClient.add(this.mailClientTextField);

		// Téléphone du client
		JLabel telephoneClientJlabel = new JLabel(
				GestionChateau.propertiesGeneral
				.getProperty("modifier.client.informations.client.telephone"));
		// On ajout le label prenom Client au panel
		telephoneClientJlabel.setBounds(100, 155, 150, 25);
		panModificationClient.add(telephoneClientJlabel);
		// On ajout le textfield prenom client au panel
		this.telephoneClientTextField.setFont(this.Police());
		this.telephoneClientTextField.setBounds(225, 155, 175, 25);
		panModificationClient.add(this.telephoneClientTextField);

		// Date de naissance du client
		JLabel ddnClientJlabel = new JLabel(GestionChateau.propertiesGeneral
				.getProperty("modifier.client.informations.client.ddn"));
		// On ajout le label prenom Client au panel
		ddnClientJlabel.setBounds(100, 180, 150, 25);
		panModificationClient.add(ddnClientJlabel);
		// On ajout le textfield prenom client au panel
		this.ddnClientTextField.setFont(this.Police());
		this.ddnClientTextField.setBounds(225, 180, 175, 25);
		panModificationClient.add(this.ddnClientTextField);

		// info du client
		JLabel infoClientJlabel = new JLabel(GestionChateau.propertiesGeneral
				.getProperty("modifier.client.informations.client.infos"));
		// On ajout le label prenom Client au panel
		infoClientJlabel.setBounds(100, 205, 150, 25);
		panModificationClient.add(infoClientJlabel);
		// On ajout le textfield prenom client au panel
		this.infosClientTextField.setFont(this.Police());
		this.infosClientTextField.setBounds(225, 205, 350, 50);
		panModificationClient.add(this.infosClientTextField);

		// On ajout le bouton de modification au panel
		this.modifierClient.setBounds(200, 300, 175, 25);
		panModificationClient.add(this.modifierClient);

		// On ajout le bouton de suppression au panel
		this.supprimerClient.setBounds(200, 325, 175, 25);
		panModificationClient.add(this.supprimerClient);

		// Lance la modification
		this.supprimerClient.addActionListener(this);

		txtIdClientHidden.setVisible(false);
		panModificationClient.add(txtIdClientHidden);

		panModificationClient.setVisible(false);

		this.add(panModificationClient, BorderLayout.CENTER);
	}

	/**
	 * Permet d'ajouter une ligne au tableau
	 * 
	 * @author BPEY
	 */
	class AddListener implements ActionListener {

		private final String idClient;

		public AddListener(String idClient) {

			this.idClient = idClient;
		}

		@Override
		public void actionPerformed(ActionEvent event) {

			// Ouverture vers un nouveau jpanel

			panTableauClient.setVisible(false);

			String idClient = ModificationClient.this.data[ModificationClient.this.tableau.getSelectedRow()][0].toString();
			Client lClient = ListeDonneesBDD.recupUnClient(idClient);

			ModificationClient.this.nomClientTextField.setText(lClient.getNom());
			ModificationClient.this.prenomClientTextField.setText(lClient.getPrenom());
			ModificationClient.this.adresseClientTextField.setText(lClient.getAdresse());
			ModificationClient.this.villeClientTextField.setText(lClient.getVille());
			ModificationClient.this.cpClientTextField.setText(lClient.getCp());
			ModificationClient.this.mailClientTextField.setText(lClient.getMail());
			ModificationClient.this.telephoneClientTextField.setText(lClient.getTelephone());
			ModificationClient.this.ddnClientTextField.setText(lClient.getDateNaissance());
			ModificationClient.this.infosClientTextField.setText(lClient.getInfos());

			txtIdClientHidden.setText(idClient);
			txtIdClientHidden.setVisible(false);

			panModificationClient.setVisible(true);

			ModificationClient.this.modifierClient.addActionListener(new ModifierListener(ModificationClient.this.tableau
					.getSelectedRow(), idClient));
		}
	}

	/**
	 * Permet de modifier une ligne du tableau
	 * 
	 * @author BPEY
	 */
	class ModifierListener implements ActionListener {

		private final int ligne;

		private final String idClient;

		public ModifierListener(int ligne, String idClient) {
			this.ligne = ligne;
			this.idClient = idClient;
		}

		@Override
		public void actionPerformed(ActionEvent event) {
			System.out.println("UPDATE");

			Client lClient = ListeDonneesBDD.recupUnClient(this.idClient);
			lClient.setNom(ModificationClient.this.nomClientTextField.getText());
			lClient.setPrenom(ModificationClient.this.prenomClientTextField.getText());
			lClient.setAdresse(ModificationClient.this.adresseClientTextField.getText());
			lClient.setVille(ModificationClient.this.villeClientTextField.getText());
			lClient.setCp(ModificationClient.this.cpClientTextField.getText());
			lClient.setMail(ModificationClient.this.mailClientTextField.getText());
			lClient.setTelephone(ModificationClient.this.telephoneClientTextField.getText());
			lClient.setDateNaissance(ModificationClient.this.ddnClientTextField.getText());
			lClient.setInfos(ModificationClient.this.infosClientTextField.getText());

			DAO<Client> clientDao = new ClientDAO(Connect.getInstance());
			clientDao.update(lClient);


		}
	}

	/**
	 * Méthode qui permet de supprimer un produit de la base
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {

		if (arg0.getSource() == this.supprimerClient) {
			System.out.println("DELETE");

			Client lClient = ListeDonneesBDD.recupUnClient(txtIdClientHidden
					.getText());

			if (ListeDonneesBDD.verificationUtilisationClient(lClient)) {
				JOptionPane
				.showMessageDialog(
						null,
						GestionChateau.propertiesGeneral
						.getProperty("suppression.produit.existant"),
						GestionChateau.propertiesGeneral
						.getProperty("suppression.produit.titre.popup"),
						JOptionPane.ERROR_MESSAGE);
			} else {
				DAO<Client> clientDao = new ClientDAO(Connect.getInstance());
				clientDao.delete(lClient);
			}

			// TODO - retourner au début de l'écran de liste des clients.

			this.add(new ModificationClient());
		}

	}

}
