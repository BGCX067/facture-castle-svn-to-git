package Affichage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import Affichage.Tableaux.TableModifierStock;
import Bean.Produit;
import Conf.Parametres_Appli;
import Dao.DAO;
import Dao.ListeDonneesBDD;
import Dao.ProduitDAO;
import bdd.Connect;

public class ModificationsStocksProduits extends JPanel implements ActionListener {


	/**
	 * 
	 */
	private static final long serialVersionUID = -207424483693141042L;

	public static JLabel label_haut = new JLabel(
			GestionChateau.propertiesGeneral
			.getProperty("modifier.stocks.titre"));
	public static JPanel panTableauStocks = new JPanel();

	List<Produit> listeProduit;

	private JTable tableau = new JTable();
	Object[][] data;
	String title[] = { "Id Produit","Nom Produit", "Prix HT", "Prix TTC", "Chiffre d'affaire",
	"Nb Bouteilles" };

	TableModifierStock tableModifierFraisPort;

	public JButton modifierFraisPort = new JButton(
			GestionChateau.propertiesGeneral
			.getProperty("modifier.frais.port.bouton.modifier"));

	JOptionPane jop3 = new JOptionPane();

	public ModificationsStocksProduits() {

		// Initialisation des panels
		panTableauStocks.removeAll();

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

		panTableauStocks.setPreferredSize(new Dimension(1000, 550));
		panTableauStocks.setLayout(null);
		panTableauStocks.setVisible(true);
		panTableauStocks.setBackground(Parametres_Appli.couleurFond);

		this.data = new Object[ListeDonneesBDD.recupProduit().size()][this.title.length];
		this.listeProduit = ListeDonneesBDD.recupProduit();
		Iterator<Produit> itProduit = this.listeProduit.iterator();

		int i = 0;
		while (itProduit.hasNext()) {
			Produit fraisPort = itProduit.next();
			this.data[i][0] = fraisPort.getId();
			this.data[i][1] = fraisPort.getLibelleProduit();
			this.data[i][2] = arrondir(fraisPort.getPrixProduit(),2);
			this.data[i][3] = arrondir((fraisPort.getPrixProduit() * 1.05),2);
			this.data[i][4] = arrondir(fraisPort.getNbStock() * fraisPort.getPrixProduit(),2);
			this.data[i][5] = fraisPort.getNbStock();
			i++;
		}

		this.tableModifierFraisPort = new TableModifierStock(this.data,
				this.title);
		this.tableau = new JTable(this.tableModifierFraisPort);

		this.tableau.getColumnModel().getColumn(0).setPreferredWidth(20);
		this.tableau.getColumnModel().getColumn(1).setPreferredWidth(350);
		this.tableau.getColumnModel().getColumn(2).setPreferredWidth(90);
		this.tableau.getColumnModel().getColumn(3).setPreferredWidth(90);
		this.tableau.getColumnModel().getColumn(4).setPreferredWidth(90);
		this.tableau.getColumnModel().getColumn(5).setPreferredWidth(90);

		// Création d'un scroll
		JScrollPane myScrollPane = new JScrollPane(this.tableau);
		myScrollPane.setBounds(0, 0, 1000, 400);

		// On ajout le bouton au panel
		this.modifierFraisPort.setBounds(275, 425, 175, 25);
		panTableauStocks.add(this.modifierFraisPort);

		this.modifierFraisPort.addActionListener(this);

		panTableauStocks.add(myScrollPane);

		this.add(label_haut, BorderLayout.NORTH);
		this.add(panTableauStocks, BorderLayout.CENTER);

	}

	/**
	 * Méthode qui permet de mettre à jour le tableau
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.out.println("On met à jour le tableau");

		for (int i = 0; i < this.data.length; i++) {
			Produit lProduit = new Produit();
			lProduit.setId(Integer.valueOf(this.tableModifierFraisPort
					.getValueAt(i, 0).toString()));
			lProduit.setNbStock(Integer.valueOf(this.tableModifierFraisPort.getValueAt(i, 5)
					.toString()));

			DAO<Produit> produitDao = new ProduitDAO(Connect.getInstance());
			((ProduitDAO) produitDao).updateStock(lProduit);
		}

		this.reload();

	}

	public void reload() {
		this.add(new ModificationTranches());
	}

	static public Double arrondir(Double value, int n) {
		double r = (Math.round(value.doubleValue() * Math.pow(10, n))) / (Math.pow(10, n));
		return new Double(r);
	}

}
