package Affichage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class Menu {

	public JMenuBar menuBar = new JMenuBar();

	private final JMenu m_fichier = new JMenu(GestionChateau.propertiesGeneral.getProperty("menu.fichier"));
	private final JMenu m_action = new JMenu(GestionChateau.propertiesGeneral.getProperty("menu.actions"));
	//private final JMenu m_edition = new JMenu(GestionChateau.propertiesGeneral.getProperty("menu.edition"));
	private final JMenu m_a_propos = new JMenu(GestionChateau.propertiesGeneral.getProperty("menu.apropos"));
	private final JMenu m_administration = new JMenu(GestionChateau.propertiesGeneral.getProperty("menu.administration"));

	private final JMenuItem i_ouvrir = new JMenuItem(GestionChateau.propertiesGeneral.getProperty("menu.fichier.ouvrir"));
	private final JMenuItem i_quitter = new JMenuItem(GestionChateau.propertiesGeneral.getProperty("menu.fichier.quitter"));
	private final JMenuItem i_nouvelleFacture = new JMenuItem(GestionChateau.propertiesGeneral
			.getProperty("menu.action.creer.facture"));
	private final JMenuItem i_voirFacture = new JMenuItem(GestionChateau.propertiesGeneral
			.getProperty("menu.action.afficher.facture"));
	private final JMenuItem i_voirStatistique = new JMenuItem(GestionChateau.propertiesGeneral
			.getProperty("menu.action.afficher.statistique"));
	private final JMenuItem i_gererStock = new JMenuItem(GestionChateau.propertiesGeneral
			.getProperty("menu.action.gerer.stock"));
	private final JMenuItem i_genererBilan = new JMenuItem(GestionChateau.propertiesGeneral
			.getProperty("menu.action.generer.bilan"));


	private final JMenuItem i_config = new JMenuItem(GestionChateau.propertiesGeneral
			.getProperty("menu.action.edition.configuration"));
	private final JMenuItem i_version = new JMenuItem(GestionChateau.propertiesGeneral
			.getProperty("menu.action.apropos.version"));

	private final JMenu m_produit = new JMenu(GestionChateau.propertiesGeneral.getProperty("menu.produit"));
	private final JMenuItem i_creationProduit = new JMenuItem(GestionChateau.propertiesGeneral
			.getProperty("menu.action.creation.produit"));
	private final JMenuItem i_modificationProduit = new JMenuItem(GestionChateau.propertiesGeneral
			.getProperty("menu.action.modification.produit"));
	private final JMenuItem i_modificationClient = new JMenuItem(GestionChateau.propertiesGeneral
			.getProperty("menu.action.modification.client"));

	private final JMenu m_facturation = new JMenu(GestionChateau.propertiesGeneral.getProperty("menu.facturation"));
	private final JMenuItem i_modificationModePaiement = new JMenuItem(GestionChateau.propertiesGeneral
			.getProperty("menu.action.modification.mode.paiement"));
	private final JMenuItem i_modificationEtatPaiement = new JMenuItem(GestionChateau.propertiesGeneral
			.getProperty("menu.action.modification.etat.paiement"));

	private final JMenu m_frais_port = new JMenu(GestionChateau.propertiesGeneral.getProperty("menu.livraison"));
	private final JMenuItem i_region = new JMenuItem(GestionChateau.propertiesGeneral
			.getProperty("menu.action.gestion_region"));
	private final JMenuItem i_tranche_frais_port = new JMenuItem(GestionChateau.propertiesGeneral
			.getProperty("menu.action.gestion_tranche_port"));
	private final JMenuItem i_frais_port = new JMenuItem(GestionChateau.propertiesGeneral
			.getProperty("menu.action.frais.port"));


	JFileChooser chooseLots = new JFileChooser();

	public Menu() {

		// On initialise nos menus
		// --------------------------

		this.i_ouvrir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (Menu.this.chooseLots.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
					// Loto2.fileLots = chooseLots.getSelectedFile();
					// Loto2.showLot();
				}
			}
		});
		// this.m_fichier.add(i_ouvrir);

		// Nouvelle Facture
		this.i_nouvelleFacture.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				GestionChateau.panAll.removeAll();
				GestionChateau.panAll.add(new CreerFacture());
				//GestionChateau.panAll.repaint();
				GestionChateau.panAll.revalidate();
			}
		});
		this.m_action.add(this.i_nouvelleFacture);

		// Affichage des factures
		this.i_voirFacture.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				GestionChateau.panAll.removeAll();
				GestionChateau.panAll.add(new VoirFacture());
				// GestionChateau.panAll.repaint();
				GestionChateau.panAll.revalidate();
			}
		});
		this.m_action.add(this.i_voirFacture);

		// Affichage des statisttiques
		this.i_voirStatistique.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				GestionChateau.panAll.removeAll();
				GestionChateau.panAll.add(new AfficherStatistique());
				// GestionChateau.panAll.repaint();
				GestionChateau.panAll.revalidate();
			}
		});
		this.m_action.add(this.i_voirStatistique);


		// Gestion des stocks
		this.i_gererStock.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				GestionChateau.panAll.removeAll();
				GestionChateau.panAll.add(new ModificationsStocksProduits());
				//GestionChateau.panAll.repaint();
				GestionChateau.panAll.revalidate();
			}
		});
		this.m_action.add(this.i_gererStock);


		// Génération du bilan
		this.i_genererBilan.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				GestionChateau.panAll.removeAll();
				GestionChateau.panAll.add(new CreerBilan());
				//GestionChateau.panAll.repaint();
				GestionChateau.panAll.revalidate();
			}
		});
		this.m_action.add(this.i_genererBilan);



		// Quitter l'application
		this.i_quitter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		this.m_fichier.add(this.i_quitter);

		// Version de l'application
		this.i_version.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				/*
				 * String mess = "Loto v1.0 \nAAUM Edition \n"; mess +=
				 * "Merci d'avoir choisi notre logiciel\n"; mess +=
				 * "\n Support : antho33@gmail.com";
				 */
				String mess = GestionChateau.propertiesGeneral.getProperty("menu.action.apropos.version.message");
				System.out.println(this.getClass().getClassLoader());
				Icon image = new ImageIcon(getClass().getResource("/img/logo_chateau.png"));
				JOptionPane.showMessageDialog(null, mess, GestionChateau.propertiesGeneral
						.getProperty("menu.action.apropos.version.message.icone"), JOptionPane.INFORMATION_MESSAGE,
						image);
			}
		});
		this.m_a_propos.add(this.i_version);

		// Nouveau produit
		this.i_creationProduit.addActionListener(new ActionListener() {
			@Override
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				/*
				 * if (!GestionChateau.visiblePanelCreerFacture){ if
				 * (GestionChateau.panAll.countComponents() > 0){
				 * GestionChateau.panAll.remove(0); }
				 */
				GestionChateau.panAll.removeAll();
				System.out.println(GestionChateau.panAll.countComponents());
				// GestionChateau.visiblePanelCreerFacture = true;
				// GestionChateau.panAll.add(new CreerFacture(),
				// BorderLayout.CENTER);
				GestionChateau.panAll.add(new CreationProduit());
				// GestionChateau.panAll.repaint();
				GestionChateau.panAll.revalidate();
			}
		});
		this.m_produit.add(this.i_creationProduit);

		// Modification produit
		this.i_modificationProduit.addActionListener(new ActionListener() {
			@Override
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				/*
				 * if (!GestionChateau.visiblePanelCreerFacture){ if
				 * (GestionChateau.panAll.countComponents() > 0){
				 * GestionChateau.panAll.remove(0); }
				 */
				GestionChateau.panAll.removeAll();
				System.out.println(GestionChateau.panAll.countComponents());
				// GestionChateau.visiblePanelCreerFacture = true;
				// GestionChateau.panAll.add(new CreerFacture(),
				// BorderLayout.CENTER);
				GestionChateau.panAll.add(new ModificationProduit());
				// GestionChateau.panAll.repaint();
				GestionChateau.panAll.revalidate();
			}
		});
		this.m_produit.add(this.i_modificationProduit);

		this.m_administration.add(this.m_produit);





		// Modification mode paiement
		this.i_modificationModePaiement.addActionListener(new ActionListener() {
			@Override
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				GestionChateau.panAll.removeAll();
				System.out.println(GestionChateau.panAll.countComponents());
				GestionChateau.panAll.add(new ModificationModePaiement());
				GestionChateau.panAll.revalidate();
			}
		});
		this.m_facturation.add(this.i_modificationModePaiement);

		// Modification etat paiement
		this.i_modificationEtatPaiement.addActionListener(new ActionListener() {
			@Override
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				GestionChateau.panAll.removeAll();
				System.out.println(GestionChateau.panAll.countComponents());
				GestionChateau.panAll.add(new ModificationEtatPaiement());
				GestionChateau.panAll.revalidate();
			}
		});
		this.m_facturation.add(this.i_modificationEtatPaiement);

		this.m_administration.add(this.m_facturation);






		// Gestion des regions pour les frais de port
		this.i_region.addActionListener(new ActionListener() {
			@Override
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				GestionChateau.panAll.removeAll();
				System.out.println(GestionChateau.panAll.countComponents());
				GestionChateau.panAll.add(new ModificationRegions());
				GestionChateau.panAll.revalidate();
			}
		});
		this.m_frais_port.add(this.i_region);

		// Gestion des tranches de qqté de bouteilles
		this.i_tranche_frais_port.addActionListener(new ActionListener() {
			@Override
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				GestionChateau.panAll.removeAll();
				System.out.println(GestionChateau.panAll.countComponents());
				GestionChateau.panAll.add(new ModificationTranches());
				GestionChateau.panAll.revalidate();
			}
		});
		this.m_frais_port.add(this.i_tranche_frais_port);
		// Gestion des frais de port
		this.i_frais_port.addActionListener(new ActionListener() {
			@Override
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				GestionChateau.panAll.removeAll();
				System.out.println(GestionChateau.panAll.countComponents());
				GestionChateau.panAll.add(new ModificationFraisPort());
				GestionChateau.panAll.revalidate();
			}
		});
		this.m_frais_port.add(this.i_frais_port);

		this.m_administration.add(this.m_frais_port);








		// Fiche client
		this.i_modificationClient.addActionListener(new ActionListener() {
			@Override
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {

				GestionChateau.panAll.removeAll();
				System.out.println(GestionChateau.panAll.countComponents());
				GestionChateau.panAll.add(new ModificationClient());
				GestionChateau.panAll.revalidate();
			}
		});
		this.m_administration.add(this.i_modificationClient);

		// Configuration
		this.i_config.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new Configuration();
			}
		});
		this.m_administration.add(this.i_config);

		// On ajoute dans la barre des menus, les différents menus
		this.menuBar.add(this.m_fichier);
		this.menuBar.add(this.m_action);
		//this.menuBar.add(this.m_edition);
		this.menuBar.add(this.m_administration);
		this.menuBar.add(this.m_a_propos);
	}
}
