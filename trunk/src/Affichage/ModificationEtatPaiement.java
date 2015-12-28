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
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Affichage.Tableaux.TableModifierEtatPaiement;
import Bean.EtatPaiement;
import Conf.Parametres_Appli;
import Dao.DAO;
import Dao.EtatPaiementDAO;
import Dao.ListeDonneesBDD;
import bdd.Connect;

public class ModificationEtatPaiement extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	public static JLabel label_haut = new JLabel(
			GestionChateau.propertiesGeneral
					.getProperty("modifier.etatPaiement.titre"));
	public static JPanel panTableauEtatPaiement = new JPanel();
	public static JPanel panModification = new JPanel();

	List<EtatPaiement> listeEtatPaiement;

	private JTable tableau = new JTable();
	Object[][] data;
	String title[] = { "Id Etat Paiement", "Etat Paiement" };
	Object[][] dataPartiel;
	TableModifierEtatPaiement tableModifierEtatPaiement;

	JTextField etatPaiementTextField = new JTextField();
	JCheckBox actifCbx = new JCheckBox();

	public JButton modifierEtatPaiement = new JButton(
			GestionChateau.propertiesGeneral
					.getProperty("modifier.produit.bouton.modifier"));
	public JButton supprimerEtatPaiement = new JButton(
			GestionChateau.propertiesGeneral
					.getProperty("modifier.produit.bouton.supprimer"));

	public static JLabel txtIdEtatPaiementHidden = new JLabel("");

	JOptionPane jop3 = new JOptionPane();

	public ModificationEtatPaiement() {

		// Initialisation des panels
		panModification.removeAll();
		panTableauEtatPaiement.removeAll();

		// Enrichissement du panel général
		this.setPreferredSize(new Dimension(
				Parametres_Appli.tailleLargeurPanelApplication,
				Parametres_Appli.tailleHauteurPanelApplication));
		this.setBackground(Parametres_Appli.couleurFond);
		// ============================================
		// == Label haut ==
		// ============================================

		label_haut.setHorizontalAlignment(SwingConstants.CENTER);
		label_haut.setFont(Parametres_Appli.titreFont);
		label_haut.setPreferredSize(new Dimension(
				Parametres_Appli.tailleLargeurPanelApplication, 25));

		// ============================================
		// == Liste Produits ==
		// ============================================
		// Police par défaut des textfield
		Font fontTextfield = new Font("Arial", Font.LAYOUT_LEFT_TO_RIGHT, 12);

		panTableauEtatPaiement.setPreferredSize(new Dimension(700, 400));
		panTableauEtatPaiement.setLayout(null);
		panTableauEtatPaiement.setVisible(true);
		panTableauEtatPaiement.setBackground(Parametres_Appli.couleurFond);

		this.data = new Object[ListeDonneesBDD.recupEtatPaiement().size()][this.title.length];
		this.listeEtatPaiement = ListeDonneesBDD.recupEtatPaiement();
		Iterator<EtatPaiement> itEtat = this.listeEtatPaiement.iterator();

		int i = 0;
		while (itEtat.hasNext()) {
			EtatPaiement etatPaiement = itEtat.next();
			this.data[i][0] = etatPaiement.getId();
			this.data[i][1] = etatPaiement.getLibelleEtatPaiement();
			i++;
		}

		this.tableModifierEtatPaiement = new TableModifierEtatPaiement(
				this.data, this.title);
		this.tableau = new JTable(this.tableModifierEtatPaiement);

		// Mise en place d'un menu contextuel
		this.tableau.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent event) {

				if (event.getButton() == MouseEvent.BUTTON3) {

					JMenuItem modifier = new JMenuItem(
							GestionChateau.propertiesGeneral
									.getProperty("affichage.recherche.tableau.modifier.ligne"));

					modifier
							.addActionListener(new AddListener(
									ModificationEtatPaiement.this.data[ModificationEtatPaiement.this.tableau
											.getSelectedRow()][0].toString()));
					JPopupMenu menu = new JPopupMenu();
					menu.add(modifier);
					menu.show(ModificationEtatPaiement.this.tableau, event
							.getX(), event.getY());
				}
			}
		});

		// Création d'un scroll
		JScrollPane myScrollPane = new JScrollPane(this.tableau);
		myScrollPane.setBounds(0, 0, 700, 400);

		panTableauEtatPaiement.add(myScrollPane);

		// ============================================
		// == Panel Modification ==
		// ============================================

		// Nom de la cuvée
		JLabel nomCuveeJlabel = new JLabel(GestionChateau.propertiesGeneral
				.getProperty("modifier.produit.informations.produit.nom"));
		this.etatPaiementTextField.setFont(fontTextfield);

		panModification.setPreferredSize(new Dimension(500, 250));
		panModification.setBackground(Parametres_Appli.couleurFond);
		// panInfosClients.setBorder(BorderFactory.createLineBorder(Color.black));
		panModification.setLayout(null);

		// On ajout le label nom cuvee au panel
		nomCuveeJlabel.setBounds(100, 5, 150, 25);
		panModification.add(nomCuveeJlabel);

		// On ajout le textfield nom cuvee au panel
		this.etatPaiementTextField.setBounds(225, 5, 175, 25);
		panModification.add(this.etatPaiementTextField);

		// Produit actif ou non
		JLabel actifCbxJlabel = new JLabel(GestionChateau.propertiesGeneral
				.getProperty("modifier.produit.informations.produit.actif"));

		panModification.setPreferredSize(new Dimension(500, 250));
		panModification.setLayout(null);

		// On ajout le label actif au panel
		actifCbxJlabel.setBounds(100, 30, 150, 25);
		panModification.add(actifCbxJlabel);

		// On ajout le checkbox actif au panel
		this.actifCbx.setBounds(225, 30, 175, 25);
		panModification.add(this.actifCbx);

		// On ajout le bouton au panel
		this.modifierEtatPaiement.setBounds(200, 70, 175, 25);
		panModification.add(this.modifierEtatPaiement);

		// On ajout le bouton au panel
		this.supprimerEtatPaiement.setBounds(200, 100, 175, 25);
		panModification.add(this.supprimerEtatPaiement);

		// Lance la modification
		this.supprimerEtatPaiement.addActionListener(this);

		txtIdEtatPaiementHidden.setVisible(false);
		panModification.add(txtIdEtatPaiementHidden);

		panModification.setVisible(false);

		this.add(label_haut, BorderLayout.NORTH);
		this.add(panTableauEtatPaiement, BorderLayout.CENTER);
		this.add(panModification, BorderLayout.CENTER);

	}

	/**
	 * Permet de modifier une ligne du tableau
	 * 
	 * @author BPEY
	 */
	class ModifierListener implements ActionListener {

		private final String idEtatPaiement;

		public ModifierListener(String idEtatPaiement) {
			this.idEtatPaiement = idEtatPaiement;
		}

		@Override
		public void actionPerformed(ActionEvent event) {
			System.out.println("id etat de paiement : " + this.idEtatPaiement);
			// si c'est un update car 1 égal le mode de paiement de création
			if (!this.idEtatPaiement.equals("1")) {
				EtatPaiement etatPaiement = ListeDonneesBDD
						.recupUnEtatPaiement(this.idEtatPaiement);
				etatPaiement
						.setLibelleEtatPaiement(ModificationEtatPaiement.this.etatPaiementTextField
								.getText());

				etatPaiement.setActif(ModificationEtatPaiement.this.actifCbx
						.isSelected());

				DAO<EtatPaiement> etatPaiementDao = new EtatPaiementDAO(Connect
						.getInstance());
				etatPaiementDao.update(etatPaiement);
			} else {
				EtatPaiement etatPaiement = new EtatPaiement();
				etatPaiement
						.setLibelleEtatPaiement(ModificationEtatPaiement.this.etatPaiementTextField
								.getText());

				etatPaiement.setActif(ModificationEtatPaiement.this.actifCbx
						.isSelected());

				DAO<EtatPaiement> etatPaiementDao = new EtatPaiementDAO(Connect
						.getInstance());
				etatPaiementDao.create(etatPaiement);
			}

			reload();

		}

	}

	/**
	 * Méthode qui permet de supprimer un produit de la base
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {

		if (arg0.getSource() == this.supprimerEtatPaiement) {

			EtatPaiement etatPaiement = ListeDonneesBDD
					.recupUnEtatPaiement(txtIdEtatPaiementHidden.getText());

			if (ListeDonneesBDD
					.verificationUtilisationEtatPaiement(etatPaiement)) {
				JOptionPane
						.showMessageDialog(
								null,
								GestionChateau.propertiesGeneral
										.getProperty("suppression.etatPaiement.existant"),
								GestionChateau.propertiesGeneral
										.getProperty("suppression.etatPaiement.titre.popup"),
								JOptionPane.ERROR_MESSAGE);
			} else {
				DAO<EtatPaiement> etatPaiementDao = new EtatPaiementDAO(Connect
						.getInstance());
				etatPaiementDao.delete(etatPaiement);
			}

			reload();
		}

	}

	/**
	 * Permet d'ajouter une ligne au tableau
	 * 
	 * @author BPEY
	 */
	class AddListener implements ActionListener {

		private final String idEtatPaiement;

		public AddListener(String idEtatPaiement) {

			this.idEtatPaiement = idEtatPaiement;
		}

		@Override
		public void actionPerformed(ActionEvent event) {

			// Ouverture vers un nouveau jpanel

			EtatPaiement etatPaiement = new EtatPaiement();
			etatPaiement = ListeDonneesBDD
					.recupUnEtatPaiement(this.idEtatPaiement);

			panModification.repaint();

			ModificationEtatPaiement.this.etatPaiementTextField
					.setText(etatPaiement.getLibelleEtatPaiement());

			ModificationEtatPaiement.this.actifCbx.setSelected(etatPaiement
					.getActif());

			txtIdEtatPaiementHidden.setText(this.idEtatPaiement);
			txtIdEtatPaiementHidden.setVisible(false);

			panModification.setVisible(true);
			ModificationEtatPaiement.this.modifierEtatPaiement
					.addActionListener(new ModifierListener(
							ModificationEtatPaiement.this.data[ModificationEtatPaiement.this.tableau
									.getSelectedRow()][0].toString()));
		}
	}

	public void reload() {
		this.add(new ModificationEtatPaiement());
	}

}
