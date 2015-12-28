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
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Affichage.Tableaux.TableModifierRegions;
import Bean.Region;
import Conf.Parametres_Appli;
import Dao.DAO;
import Dao.ListeDonneesBDD;
import Dao.RegionDAO;
import bdd.Connect;

public class ModificationRegions extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static JLabel label_haut = new JLabel(
			GestionChateau.propertiesGeneral
			.getProperty("modifier.region.titre"));
	public static JPanel panTableauRegion = new JPanel();
	public static JPanel panModification = new JPanel();

	List<Region> listeRegions;

	private JTable tableau = new JTable();
	Object[][] data;
	String title[] = { "Zone Région", "Numéros départements" };
	Object[][] dataPartiel;
	TableModifierRegions tableModifierRegions;

	JTextField libelleRegionTextField = new JTextField();

	public JButton modifierRegion = new JButton(
			GestionChateau.propertiesGeneral
			.getProperty("modifier.region.bouton.modifier"));

	public static JLabel txtIdRegionHidden = new JLabel("");

	JOptionPane jop3 = new JOptionPane();

	public ModificationRegions() {

		// Initialisation des panels
		panModification.removeAll();
		panTableauRegion.removeAll();

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
		// == Liste des regions ==
		// ============================================
		// Police par défaut des textfield
		Font fontTextfield = new Font("Arial", Font.LAYOUT_LEFT_TO_RIGHT, 12);

		panTableauRegion.setPreferredSize(new Dimension(700, 400));
		panTableauRegion.setLayout(null);
		panTableauRegion.setVisible(true);
		panTableauRegion.setBackground(Parametres_Appli.couleurFond);

		this.data = new Object[ListeDonneesBDD.recupRegion().size()][this.title.length];
		this.listeRegions = ListeDonneesBDD.recupRegion();
		Iterator<Region> itRegion = this.listeRegions.iterator();

		int i = 0;
		while (itRegion.hasNext()) {
			Region region = itRegion.next();
			this.data[i][0] = region.getId();
			this.data[i][1] = region.getListeIdRegion();
			i++;
		}

		this.tableModifierRegions = new TableModifierRegions(this.data,
				this.title);
		this.tableau = new JTable(this.tableModifierRegions);

		// Mise en place d'un menu contextuel
		this.tableau.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent event) {

				if (event.getButton() == MouseEvent.BUTTON3) {

					JMenuItem modifier = new JMenuItem(
							GestionChateau.propertiesGeneral
							.getProperty("affichage.recherche.tableau.modifier.ligne"));

					modifier.addActionListener(new AddListener(
							ModificationRegions.this.data[ModificationRegions.this.tableau
							                              .getSelectedRow()][0].toString()));
					JPopupMenu menu = new JPopupMenu();
					menu.add(modifier);
					menu.show(ModificationRegions.this.tableau, event.getX(),
							event.getY());
				}
			}
		});

		// Création d'un scroll
		JScrollPane myScrollPane = new JScrollPane(this.tableau);
		myScrollPane.setBounds(0, 0, 700, 400);

		panTableauRegion.add(myScrollPane);

		// ============================================
		// == Panel Modification ==
		// ============================================

		// Nom de la cuvée
		JLabel listeDepartementsJlabel = new JLabel(
				GestionChateau.propertiesGeneral
				.getProperty("modifier.region.liste.departement"));
		this.libelleRegionTextField.setFont(fontTextfield);

		panModification.setPreferredSize(new Dimension(500, 250));
		panModification.setBackground(Parametres_Appli.couleurFond);
		// panInfosClients.setBorder(BorderFactory.createLineBorder(Color.black));
		panModification.setLayout(null);

		// On ajout le label nom cuvee au panel
		listeDepartementsJlabel.setBounds(80, 5, 190, 25);
		panModification.add(listeDepartementsJlabel);

		// On ajout le textfield nom cuvee au panel
		this.libelleRegionTextField.setBounds(225, 5, 200, 25);
		panModification.add(this.libelleRegionTextField);

		panModification.setPreferredSize(new Dimension(500, 250));
		panModification.setLayout(null);

		// On ajout le bouton au panel
		this.modifierRegion.setBounds(200, 70, 175, 25);
		panModification.add(this.modifierRegion);

		txtIdRegionHidden.setVisible(false);
		panModification.add(txtIdRegionHidden);

		panModification.setVisible(false);

		this.add(label_haut, BorderLayout.NORTH);
		this.add(panTableauRegion, BorderLayout.CENTER);
		this.add(panModification, BorderLayout.CENTER);

	}

	/**
	 * Permet de modifier une ligne du tableau
	 * 
	 * @author BPEY
	 */
	class ModifierListener implements ActionListener {

		private final String idRegion;

		public ModifierListener(String idRegion) {
			this.idRegion = idRegion;
		}

		@Override
		public void actionPerformed(ActionEvent event) {
			System.out.println("id region : " + this.idRegion);
			// si c'est un update car 1 égal le mode de paiement de création
			if (!this.idRegion.equals("1")) {
				Region region = ListeDonneesBDD.recupUneRegion(this.idRegion);
				region.setListeIdRegion(ModificationRegions.this.libelleRegionTextField
						.getText());

				DAO<Region> regionDao = new RegionDAO(Connect.getInstance());
				regionDao.update(region);
			} else {
				Region region = new Region();
				region.setListeIdRegion(ModificationRegions.this.libelleRegionTextField
						.getText());

				DAO<Region> regionDao = new RegionDAO(Connect.getInstance());
				regionDao.create(region);
			}

			reload();

		}

	}

	/**
	 * Méthode qui permet de supprimer
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {

	}

	/**
	 * Permet d'ajouter une ligne au tableau
	 * 
	 * @author BPEY
	 */
	class AddListener implements ActionListener {

		private final String idRegion;

		public AddListener(String idRegion) {

			this.idRegion = idRegion;
		}

		@Override
		public void actionPerformed(ActionEvent event) {

			// Ouverture vers un nouveau jpanel

			Region region = new Region();
			region = ListeDonneesBDD.recupUneRegion(this.idRegion);

			panModification.repaint();

			ModificationRegions.this.libelleRegionTextField.setText(region
					.getListeIdRegion());

			txtIdRegionHidden.setText(this.idRegion);
			txtIdRegionHidden.setVisible(false);

			panModification.setVisible(true);
			ModificationRegions.this.modifierRegion
			.addActionListener(new ModifierListener(
					ModificationRegions.this.data[ModificationRegions.this.tableau
					                              .getSelectedRow()][0].toString()));
		}
	}

	public void reload() {
		this.add(new ModificationRegions());
	}

}
