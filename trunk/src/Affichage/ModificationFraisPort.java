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
import javax.swing.SwingConstants;

import Affichage.Tableaux.TableModifierFraisPort;
import Bean.FraisPort;
import Conf.Parametres_Appli;
import Dao.DAO;
import Dao.FraisPortDAO;
import Dao.ListeDonneesBDD;
import bdd.Connect;

public class ModificationFraisPort extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static JLabel label_haut = new JLabel(
			GestionChateau.propertiesGeneral
			.getProperty("modifier.Frais.Port.titre"));
	public static JPanel panTableauFraisPort = new JPanel();

	List<FraisPort> listeFraisPort;

	private JTable tableau = new JTable();
	Object[][] data;
	String title[] = { "Id_Tranche", "Tranche 1", "Tranche 2", "Tranche 3",
			"Tranche 4", "Tranche 5", "Tranche 6", "Tranche 7", "Tranche 8",
			"Tranche 9", "Tranche 10", "Tranche 11", "Tranche 12", "Région" };

	TableModifierFraisPort tableModifierFraisPort;

	public JButton modifierFraisPort = new JButton(
			GestionChateau.propertiesGeneral
			.getProperty("modifier.frais.port.bouton.modifier"));

	JOptionPane jop3 = new JOptionPane();

	public ModificationFraisPort() {

		// Initialisation des panels
		panTableauFraisPort.removeAll();

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

		panTableauFraisPort.setPreferredSize(new Dimension(1000, 550));
		panTableauFraisPort.setLayout(null);
		panTableauFraisPort.setVisible(true);
		panTableauFraisPort.setBackground(Parametres_Appli.couleurFond);

		this.data = new Object[ListeDonneesBDD.recupFraisPort().size()][this.title.length];
		this.listeFraisPort = ListeDonneesBDD.recupFraisPort();
		Iterator<FraisPort> itFraisPort = this.listeFraisPort.iterator();

		int i = 0;
		while (itFraisPort.hasNext()) {
			FraisPort fraisPort = itFraisPort.next();
			this.data[i][0] = fraisPort.getId_frais_port();
			this.data[i][1] = fraisPort.getTranche1();
			this.data[i][2] = fraisPort.getTranche2();
			this.data[i][3] = fraisPort.getTranche3();
			this.data[i][4] = fraisPort.getTranche4();
			this.data[i][5] = fraisPort.getTranche5();
			this.data[i][6] = fraisPort.getTranche6();
			this.data[i][7] = fraisPort.getTranche7();
			this.data[i][8] = fraisPort.getTranche8();
			this.data[i][9] = fraisPort.getTranche9();
			this.data[i][10] = fraisPort.getTranche10();
			this.data[i][11] = fraisPort.getTranche11();
			this.data[i][12] = fraisPort.getTranche12();
			this.data[i][13] = fraisPort.getId_region();
			i++;
		}

		this.tableModifierFraisPort = new TableModifierFraisPort(this.data,
				this.title);
		this.tableau = new JTable(this.tableModifierFraisPort);

		// Création d'un scroll
		JScrollPane myScrollPane = new JScrollPane(this.tableau);
		myScrollPane.setBounds(0, 0, 1000, 400);

		// On ajout le bouton au panel
		this.modifierFraisPort.setBounds(275, 425, 175, 25);
		panTableauFraisPort.add(this.modifierFraisPort);

		this.modifierFraisPort.addActionListener(this);

		panTableauFraisPort.add(myScrollPane);

		this.add(label_haut, BorderLayout.NORTH);
		this.add(panTableauFraisPort, BorderLayout.CENTER);

	}

	/**
	 * Méthode qui permet de mettre à jour le tableau
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.out.println("On met à jour le tableau");

		for (int i = 0; i < this.data.length; i++) {
			FraisPort lFraisPort = new FraisPort();
			lFraisPort.setId_frais_port(Integer.valueOf(this.tableModifierFraisPort
					.getValueAt(i, 0).toString()));
			lFraisPort.setTranche1(this.tableModifierFraisPort.getValueAt(i, 1)
					.toString());
			lFraisPort.setTranche2(this.tableModifierFraisPort.getValueAt(i, 2)
					.toString());
			lFraisPort.setTranche3(this.tableModifierFraisPort.getValueAt(i, 3)
					.toString());
			lFraisPort.setTranche4(this.tableModifierFraisPort.getValueAt(i, 4)
					.toString());
			lFraisPort.setTranche5(this.tableModifierFraisPort.getValueAt(i, 5)
					.toString());
			lFraisPort.setTranche6(this.tableModifierFraisPort.getValueAt(i, 6)
					.toString());
			lFraisPort.setTranche7(this.tableModifierFraisPort.getValueAt(i, 7)
					.toString());
			lFraisPort.setTranche8(this.tableModifierFraisPort.getValueAt(i, 8)
					.toString());
			lFraisPort.setTranche9(this.tableModifierFraisPort.getValueAt(i, 9)
					.toString());
			lFraisPort.setTranche10(this.tableModifierFraisPort.getValueAt(i, 10)
					.toString());
			lFraisPort.setTranche11(this.tableModifierFraisPort.getValueAt(i, 11)
					.toString());
			lFraisPort.setTranche12(this.tableModifierFraisPort.getValueAt(i, 12)
					.toString());
			lFraisPort.setId_region(Integer.valueOf(this.tableModifierFraisPort
					.getValueAt(i, 13).toString()));


			DAO<FraisPort> fraisPortDao = new FraisPortDAO(Connect.getInstance());
			fraisPortDao.update(lFraisPort);

		}

		this.reload();

	}

	public void reload() {
		this.add(new ModificationTranches());
	}

}
