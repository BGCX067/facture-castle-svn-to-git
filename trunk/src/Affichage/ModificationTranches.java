package Affichage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
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
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Affichage.Tableaux.TableModifierTranche;
import Bean.Tranche;
import Conf.Parametres_Appli;
import Dao.DAO;
import Dao.ListeDonneesBDD;
import Dao.TrancheDAO;
import bdd.Connect;

public class ModificationTranches extends JPanel implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static JLabel label_haut = new JLabel(
			GestionChateau.propertiesGeneral
			.getProperty("modifier.region.tranche"));
	public static JPanel panTableauTranche = new JPanel();

	List<Tranche> listeTranche;

	private JTable tableau = new JTable();
	Object[][] data;
	String title[] = { "Id_Tranche","Nom Tranche", "Minimum", "Maximum" };
	TableModifierTranche tableModifierTranche;

	JTextField libelleTrancheMinTextField = new JTextField();
	JTextField libelleTrancheMaxTextField = new JTextField();

	public JButton modifierRegion = new JButton(
			GestionChateau.propertiesGeneral
			.getProperty("modifier.tranche.bouton.modifier"));

	public static JLabel txtIdTrancheHidden = new JLabel("");

	JOptionPane jop3 = new JOptionPane();

	public ModificationTranches() {

		// Initialisation des panels
		panTableauTranche.removeAll();

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

		panTableauTranche.setPreferredSize(new Dimension(700, 550));
		panTableauTranche.setLayout(null);
		panTableauTranche.setVisible(true);
		panTableauTranche.setBackground(Parametres_Appli.couleurFond);

		this.data = new Object[ListeDonneesBDD.recupTranche().size()][this.title.length];
		this.listeTranche = ListeDonneesBDD.recupTranche();
		Iterator<Tranche> itTranche = this.listeTranche.iterator();

		int i = 0;
		while (itTranche.hasNext()) {
			Tranche tranche = itTranche.next();
			this.data[i][0] = tranche.getId_tranche();
			this.data[i][1] = tranche.getNomTranche();
			this.data[i][2] = tranche.getMinimum();
			this.data[i][3] = tranche.getMaximum();
			i++;
		}

		this.tableModifierTranche = new TableModifierTranche(this.data,
				this.title);
		this.tableau = new JTable(this.tableModifierTranche);

		// Création d'un scroll
		JScrollPane myScrollPane = new JScrollPane(this.tableau);
		myScrollPane.setBounds(0, 0, 700, 400);

		// On ajout le bouton au panel
		this.modifierRegion.setBounds(275,425, 175, 25);
		panTableauTranche.add(this.modifierRegion);

		this.modifierRegion.addActionListener(this);

		panTableauTranche.add(myScrollPane);

		this.add(label_haut, BorderLayout.NORTH);
		this.add(panTableauTranche, BorderLayout.CENTER);


	}


	/**
	 * Méthode qui permet de mettre à jour le tableau
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.out.println("On met à jour le tableau");

		for (int i = 0; i < this.data.length; i++) {
			Tranche lTranche = new Tranche();
			lTranche.setId_tranche(Integer.valueOf(this.tableModifierTranche.getValueAt(i, 0).toString()));
			lTranche.setNomTranche(this.tableModifierTranche.getValueAt(i, 1).toString());
			lTranche.setMinimum(Integer.valueOf(this.tableModifierTranche.getValueAt(i, 2).toString()));
			lTranche.setMaximum(Integer.valueOf(this.tableModifierTranche.getValueAt(i, 3).toString()));

			DAO<Tranche> trancheDao = new TrancheDAO(Connect
					.getInstance());
			trancheDao.update(lTranche);

		}

		this.reload();

	}


	public void reload() {
		this.add(new ModificationTranches());
	}


}
