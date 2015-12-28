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

import Affichage.Tableaux.TableModifierModePaiement;
import Bean.MoyenPaiement;
import Conf.Parametres_Appli;
import Dao.DAO;
import Dao.ListeDonneesBDD;
import Dao.MoyenPaiementDAO;
import bdd.Connect;

public class ModificationModePaiement extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	public static JLabel label_haut = new JLabel(
			GestionChateau.propertiesGeneral
					.getProperty("modifier.modePaiement.titre"));
	public static JPanel panTableauModePaiement = new JPanel();
	public static JPanel panModification = new JPanel();

	List<MoyenPaiement> listeMoyenPaiement;

	private JTable tableau = new JTable();
	Object[][] data;
	String title[] = { "Id Mode Paiement", "Mode Paiement" };
	Object[][] dataPartiel;
	TableModifierModePaiement tableModifierModePaiement;

	JTextField modePaiementTextField = new JTextField();
	JCheckBox actifCbx = new JCheckBox();

	public JButton modifierModePaiement = new JButton(
			GestionChateau.propertiesGeneral
					.getProperty("modifier.produit.bouton.modifier"));
	public JButton supprimerModePaiement = new JButton(
			GestionChateau.propertiesGeneral
					.getProperty("modifier.produit.bouton.supprimer"));

	public static JLabel txtIdMoyenPaiementHidden = new JLabel("");

	JOptionPane jop3 = new JOptionPane();

	public ModificationModePaiement() {

		// Initialisation des panels
		panModification.removeAll();
		panTableauModePaiement.removeAll();

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

		panTableauModePaiement.setPreferredSize(new Dimension(700, 400));
		panTableauModePaiement.setLayout(null);
		panTableauModePaiement.setVisible(true);
		panTableauModePaiement.setBackground(Parametres_Appli.couleurFond);

		this.data = new Object[ListeDonneesBDD.recupMoyenPaiement().size()][this.title.length];
		this.listeMoyenPaiement = ListeDonneesBDD.recupMoyenPaiement();
		Iterator<MoyenPaiement> itMoyen = this.listeMoyenPaiement.iterator();

		int i = 0;
		while (itMoyen.hasNext()) {
			MoyenPaiement moyenPaiement = itMoyen.next();
			this.data[i][0] = moyenPaiement.getId();
			this.data[i][1] = moyenPaiement.getLibelleMoyenPaiement();
			i++;
		}

		this.tableModifierModePaiement = new TableModifierModePaiement(
				this.data, this.title);
		this.tableau = new JTable(this.tableModifierModePaiement);

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
									ModificationModePaiement.this.data[ModificationModePaiement.this.tableau
											.getSelectedRow()][0].toString()));
					JPopupMenu menu = new JPopupMenu();
					menu.add(modifier);
					menu.show(ModificationModePaiement.this.tableau, event
							.getX(), event.getY());
				}
			}
		});

		// Création d'un scroll
		JScrollPane myScrollPane = new JScrollPane(this.tableau);
		myScrollPane.setBounds(0, 0, 700, 400);

		panTableauModePaiement.add(myScrollPane);

		// ============================================
		// == Panel Modification ==
		// ============================================

		// Nom de la cuvée
		JLabel nomCuveeJlabel = new JLabel(GestionChateau.propertiesGeneral
				.getProperty("modifier.produit.informations.produit.nom"));
		this.modePaiementTextField.setFont(fontTextfield);

		panModification.setPreferredSize(new Dimension(500, 250));
		panModification.setBackground(Parametres_Appli.couleurFond);
		// panInfosClients.setBorder(BorderFactory.createLineBorder(Color.black));
		panModification.setLayout(null);

		// On ajout le label nom cuvee au panel
		nomCuveeJlabel.setBounds(100, 5, 150, 25);
		panModification.add(nomCuveeJlabel);

		// On ajout le textfield nom cuvee au panel
		this.modePaiementTextField.setBounds(225, 5, 175, 25);
		panModification.add(this.modePaiementTextField);

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
		this.modifierModePaiement.setBounds(200, 70, 175, 25);
		panModification.add(this.modifierModePaiement);

		// On ajout le bouton au panel
		this.supprimerModePaiement.setBounds(200, 100, 175, 25);
		panModification.add(this.supprimerModePaiement);

		// Lance la modification
		this.supprimerModePaiement.addActionListener(this);

		txtIdMoyenPaiementHidden.setVisible(false);
		panModification.add(txtIdMoyenPaiementHidden);

		panModification.setVisible(false);

		this.add(label_haut, BorderLayout.NORTH);
		this.add(panTableauModePaiement, BorderLayout.CENTER);
		this.add(panModification, BorderLayout.CENTER);

	}

	/**
	 * Permet de modifier une ligne du tableau
	 * 
	 * @author BPEY
	 */
	class ModifierListener implements ActionListener {

		private final String idMoyenPaiement;

		public ModifierListener(String idMoyenPaiement) {
			this.idMoyenPaiement = idMoyenPaiement;
		}

		@Override
		public void actionPerformed(ActionEvent event) {
			System.out.println("id moyen de paiement : " +this.idMoyenPaiement);
			// si c'est un update car 1 égal le mode de paiement de création
			if (!this.idMoyenPaiement.equals("1")) {
				MoyenPaiement moyenPaiement = ListeDonneesBDD
						.recupUnMoyenPaiement(this.idMoyenPaiement);
				moyenPaiement
						.setLibelleMoyenPaiement(ModificationModePaiement.this.modePaiementTextField
								.getText());

				moyenPaiement.setActif(ModificationModePaiement.this.actifCbx
						.isSelected());

				DAO<MoyenPaiement> moyenPaiementDao = new MoyenPaiementDAO(
						Connect.getInstance());
				moyenPaiementDao.update(moyenPaiement);
			} else {
				MoyenPaiement moyenPaiement = new MoyenPaiement();
				moyenPaiement
						.setLibelleMoyenPaiement(ModificationModePaiement.this.modePaiementTextField
								.getText());

				moyenPaiement.setActif(ModificationModePaiement.this.actifCbx
						.isSelected());

				DAO<MoyenPaiement> moyenPaiementDao = new MoyenPaiementDAO(
						Connect.getInstance());
				moyenPaiementDao.create(moyenPaiement);
			}

			reload();

		}

	}

	/**
	 * Méthode qui permet de supprimer un produit de la base
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {

		if (arg0.getSource() == this.supprimerModePaiement) {

			MoyenPaiement moyenPaiement = ListeDonneesBDD
					.recupUnMoyenPaiement(txtIdMoyenPaiementHidden.getText());

			if (ListeDonneesBDD
					.verificationUtilisationMoyenPaiement(moyenPaiement)) {
				JOptionPane
						.showMessageDialog(
								null,
								GestionChateau.propertiesGeneral
										.getProperty("suppression.moyenPaiement.existant"),
								GestionChateau.propertiesGeneral
										.getProperty("suppression.moyenPaiement.titre.popup"),
								JOptionPane.ERROR_MESSAGE);
			} else {
				DAO<MoyenPaiement> moyenPaiementDao = new MoyenPaiementDAO(
						Connect.getInstance());
				moyenPaiementDao.delete(moyenPaiement);
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

		private final String idMoyenPaiement;

		public AddListener(String idMoyenPaiement) {

			this.idMoyenPaiement = idMoyenPaiement;
		}

		@Override
		public void actionPerformed(ActionEvent event) {

			// Ouverture vers un nouveau jpanel

			MoyenPaiement moyenPaiement = new MoyenPaiement();
			moyenPaiement = ListeDonneesBDD
					.recupUnMoyenPaiement(this.idMoyenPaiement);

			panModification.repaint();

			ModificationModePaiement.this.modePaiementTextField
					.setText(moyenPaiement.getLibelleMoyenPaiement());

			ModificationModePaiement.this.actifCbx.setSelected(moyenPaiement
					.getActif());

			txtIdMoyenPaiementHidden.setText(this.idMoyenPaiement);
			txtIdMoyenPaiementHidden.setVisible(false);

			panModification.setVisible(true);
			ModificationModePaiement.this.modifierModePaiement
					.addActionListener(new ModifierListener(
							ModificationModePaiement.this.data[ModificationModePaiement.this.tableau
									.getSelectedRow()][0].toString()));
		}
	}

	public void reload() {
		this.add(new ModificationModePaiement());
	}

}
