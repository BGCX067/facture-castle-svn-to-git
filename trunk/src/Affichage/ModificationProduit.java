package Affichage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Affichage.Tableaux.TableModifierProduit;
import Bean.Produit;
import Bean.Tva;
import Conf.Parametres_Appli;
import Dao.DAO;
import Dao.ListeDonneesBDD;
import Dao.ProduitDAO;
import bdd.Connect;

public class ModificationProduit extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static JLabel label_haut = new JLabel(GestionChateau.propertiesGeneral.getProperty("modifier.produit.titre"));
	public static JPanel panTableauProd = new JPanel();
	public static JPanel panModification = new JPanel();

	List<Produit> listeProduit;

	private JTable tableau = new JTable();
	Object[][] data;
	String title[] = { "Num Produit", "Nom du Produit", "Prix Du Produit",
	"TVA" };
	Object[][] dataPartiel;
	TableModifierProduit tableModifierProduit;
	public String IdProduitSession;
	JTextField nomCuveeTextField = new JTextField();
	JTextField prixUnitaireTextField = new JTextField();
	JLabel etatEnregistrementLabel = new JLabel(
			GestionChateau.propertiesGeneral
			.getProperty("informations.produit.enregistrement"));
	JComboBox tauxTvaJComboBox;
	JCheckBox actifCbx = new JCheckBox();

	public JButton modifierProduit = new JButton(GestionChateau.propertiesGeneral.getProperty("modifier.produit.bouton.modifier"));
	public JButton supprimerProduit = new JButton(GestionChateau.propertiesGeneral.getProperty("modifier.produit.bouton.supprimer"));

	public static JLabel txtIdProdHidden = new JLabel("");
	public static JLabel txtLigneHidden = new JLabel("");

	JOptionPane jop3 = new JOptionPane();

	public ModificationProduit() {

		// Initialisation des panels
		panModification.removeAll();
		panTableauProd.removeAll();

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
		// Police par défaut des textfield
		Font fontTextfield = new Font("Arial", Font.LAYOUT_LEFT_TO_RIGHT, 12);

		panTableauProd.setPreferredSize(new Dimension(700, 400));
		panTableauProd.setLayout(null);
		panTableauProd.setVisible(true);
		panTableauProd.setBackground(Parametres_Appli.couleurFond);

		this.data = new Object[ListeDonneesBDD.getNbrProduit()][this.title.length];
		this.listeProduit = ListeDonneesBDD.recupProduit();
		Iterator<Produit> itProduit = this.listeProduit.iterator();

		int i = 0;
		while (itProduit.hasNext()) {
			Produit produit = itProduit.next();
			this.data[i][0] = produit.getId();
			this.data[i][1] = produit.getLibelleProduit();
			this.data[i][2] = produit.getPrixProduit();
			this.data[i][3] = produit.getTva().getTauxTva();
			i++;
		}

		this.tableModifierProduit = new TableModifierProduit(this.data, this.title);
		this.tableau = new JTable(this.tableModifierProduit);

		// Mise en place d'un menu contextuel
		this.tableau.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent event) {

				if (event.getButton() == MouseEvent.BUTTON3) {

					JMenuItem modifier = new JMenuItem(
							GestionChateau.propertiesGeneral
							.getProperty("affichage.recherche.tableau.modifier.ligne"));

					modifier.addActionListener(new AddListener(ModificationProduit.this.data[ModificationProduit.this.tableau
					                                                                         .getSelectedRow()][0].toString()));
					JPopupMenu menu = new JPopupMenu();
					menu.add(modifier);
					menu.show(ModificationProduit.this.tableau, event.getX(), event.getY());
				}
			}
		});

		// Création d'un scroll
		JScrollPane myScrollPane = new JScrollPane(this.tableau);
		myScrollPane.setBounds(0, 0, 700, 400);

		panTableauProd.add(myScrollPane);

		// ============================================
		// == Panel Modification ==
		// ============================================

		// Nom de la cuvée
		JLabel nomCuveeJlabel = new JLabel(
				GestionChateau.propertiesGeneral
				.getProperty("modifier.produit.informations.produit.nom"));
		this.nomCuveeTextField.setFont(fontTextfield);

		panModification.setPreferredSize(new Dimension(500, 250));
		panModification.setBackground(Parametres_Appli.couleurFond);
		// panInfosClients.setBorder(BorderFactory.createLineBorder(Color.black));
		panModification.setLayout(null);

		// On ajout le label nom cuvee au panel
		nomCuveeJlabel.setBounds(100, 5, 150, 25);
		panModification.add(nomCuveeJlabel);

		// On ajout le textfield nom cuvee au panel
		this.nomCuveeTextField.setBounds(225, 5, 175, 25);
		panModification.add(this.nomCuveeTextField);

		// On ajout le label prix cuvee au panel
		JLabel prixUnitaireLabel = new JLabel(
				GestionChateau.propertiesGeneral
				.getProperty("modifier.produit.informations.produit.prix"));
		prixUnitaireLabel.setBounds(100, 50, 150, 25);
		panModification.add(prixUnitaireLabel);

		// On ajout le textfield prix cuvee au panel
		this.prixUnitaireTextField.setBounds(225, 50, 175, 25);
		panModification.add(this.prixUnitaireTextField);

		// On ajout le label tva au panel
		JLabel tvaCuveeJlabel = new JLabel(
				GestionChateau.propertiesGeneral
				.getProperty("modifier.produit.informations.produit.tva"));
		tvaCuveeJlabel.setBounds(100, 95, 150, 25);
		panModification.add(tvaCuveeJlabel);

		// On ajout la liste deroulante de la tva au panel
		List<Tva> tauxTva = ListeDonneesBDD.recupTauxTva();
		String[] tauxTvaListe = new String[tauxTva.size()];
		Iterator<Tva> it = tauxTva.iterator();
		int j = 0;
		while (it.hasNext()) {
			Tva taux = it.next();
			tauxTvaListe[j] = Double.toString(taux.getTauxTva());
			j++;
		}

		this.tauxTvaJComboBox = new JComboBox(tauxTvaListe);
		this.tauxTvaJComboBox.setBounds(225, 95, 175, 25);
		panModification.add(this.tauxTvaJComboBox);

		// Produit actif ou non
		JLabel actifCbxJlabel = new JLabel(
				GestionChateau.propertiesGeneral
				.getProperty("modifier.produit.informations.produit.actif"));

		panModification.setPreferredSize(new Dimension(500, 250));
		panModification.setLayout(null);

		// On ajout le label actif au panel
		actifCbxJlabel.setBounds(100, 140, 150, 25);
		panModification.add(actifCbxJlabel);

		// On ajout le checkbox actif au panel
		this.actifCbx.setBounds(225, 140, 175, 25);
		panModification.add(this.actifCbx);

		// On ajout le bouton au panel
		this.modifierProduit.setBounds(200, 180, 175, 25);
		panModification.add(this.modifierProduit);

		// On ajout le bouton au panel
		this.supprimerProduit.setBounds(200, 210, 175, 25);
		panModification.add(this.supprimerProduit);

		// Lance la modification
		this.supprimerProduit.addActionListener(this);

		// Message d'erreur
		this.etatEnregistrementLabel.setVisible(false);
		this.etatEnregistrementLabel.setBounds(200, 400, 175, 25);
		Font fontLblSuivi = new Font("Arial", Font.LAYOUT_LEFT_TO_RIGHT, 15);
		this.etatEnregistrementLabel.setForeground(new Color(0, 255, 0));
		this.etatEnregistrementLabel.setFont(fontLblSuivi);
		panModification.add(this.etatEnregistrementLabel);

		txtIdProdHidden.setVisible(false);
		panModification.add(txtIdProdHidden);
		txtLigneHidden.setVisible(false);
		panModification.add(txtLigneHidden);

		panModification.setVisible(false);

		this.add(label_haut, BorderLayout.NORTH);
		this.add(panTableauProd, BorderLayout.CENTER);
		this.add(panModification, BorderLayout.CENTER);


	}

	/**
	 * Permet de modifier une ligne du tableau
	 * 
	 * @author BPEY
	 */
	class ModifierListener implements ActionListener {

		private final int ligne;

		private final String idProduit;

		public ModifierListener(int ligne, String idProduit) {

			this.ligne = ligne;
			this.idProduit = idProduit;
		}

		@Override
		public void actionPerformed(ActionEvent event) {

			Produit produit = ListeDonneesBDD.recupUnProduit(this.idProduit);
			produit.setLibelleProduit(ModificationProduit.this.nomCuveeTextField.getText());
			produit.setPrixProduit(Double.parseDouble(ModificationProduit.this.prixUnitaireTextField
					.getText()));
			int idTva = 0;
			System.out.println("POINT 2");
			idTva = ListeDonneesBDD.recupIdTauxTva(ModificationProduit.this.tauxTvaJComboBox
					.getSelectedItem().toString());
			System.out.print(ModificationProduit.this.tauxTvaJComboBox.getSelectedItem().toString());
			Tva tva = new Tva(idTva, Double.parseDouble(ModificationProduit.this.tauxTvaJComboBox
					.getSelectedItem().toString()));
			produit.setTva(tva);

			produit.setActif(ModificationProduit.this.actifCbx.isSelected());

			// produit.setTva(tva);

			DAO<Produit> produitDao = new ProduitDAO(Connect.getInstance());
			produitDao.update(produit);

			ModificationProduit.this.etatEnregistrementLabel.setVisible(true);

			reload();

		}



	}

	/**
	 * Méthode qui permet de supprimer un produit de la base
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {

		if (arg0.getSource() == this.supprimerProduit) {

			Produit produit = ListeDonneesBDD.recupUnProduit(txtIdProdHidden
					.getText());

			if (ListeDonneesBDD.verificationUtilisationProduit(produit)) {
				JOptionPane
				.showMessageDialog(
						null,
						GestionChateau.propertiesGeneral
						.getProperty("suppression.produit.existant"),
						GestionChateau.propertiesGeneral
						.getProperty("suppression.produit.titre.popup"),
						JOptionPane.ERROR_MESSAGE);
			} else {
				DAO<Produit> produitDao = new ProduitDAO(Connect.getInstance());
				produitDao.delete(produit);
			}

			this.add(new ModificationProduit());

		}

	}

	/**
	 * Permet d'ajouter une ligne au tableau
	 * 
	 * @author BPEY
	 */
	class AddListener implements ActionListener {

		private final String idProduit;

		public AddListener(String idProduit) {

			this.idProduit = idProduit;
		}

		@Override
		public void actionPerformed(ActionEvent event) {

			// Ouverture vers un nouveau jpanel

			Produit produit = new Produit();
			produit = ListeDonneesBDD.recupUnProduit(this.idProduit);

			panModification.repaint();

			ModificationProduit.this.nomCuveeTextField.setText(produit.getLibelleProduit());
			ModificationProduit.this.prixUnitaireTextField.setText(String.valueOf(produit
					.getPrixProduit()));
			ModificationProduit.this.actifCbx.setSelected(produit.getActif());

			txtIdProdHidden.setText(this.idProduit);
			txtIdProdHidden.setVisible(false);

			txtLigneHidden.setText(String.valueOf(ModificationProduit.this.tableau.getSelectedRow()));
			txtLigneHidden.setVisible(false);

			/*
			 * if(produit.getTva().getId() == 1){
			 * tauxTvaJComboBox.setSelectedIndex(0); }else{
			 * tauxTvaJComboBox.setSelectedIndex(1); }
			 */

			panModification.setVisible(true);
			ModificationProduit.this.modifierProduit.addActionListener(new ModifierListener(ModificationProduit.this.tableau
					.getSelectedRow(), ModificationProduit.this.data[ModificationProduit.this.tableau.getSelectedRow()][0]
					                                                                                                    .toString()));
		}
	}




	public void reload(){
		this.add(new ModificationProduit());
	}


}
