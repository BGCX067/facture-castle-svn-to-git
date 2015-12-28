package Affichage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Bean.Produit;
import Bean.Tva;
import Conf.Parametres_Appli;
import Dao.DAO;
import Dao.ListeDonneesBDD;
import Dao.ProduitDAO;
import bdd.Connect;

public class CreationProduit extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long	serialVersionUID		= 1L;
	public static JLabel label_haut = new JLabel(GestionChateau.propertiesGeneral.getProperty("creer.produit.titre"));
	public static JPanel panAjoutProduit = new JPanel();

	JTextField nomCuveeTextField = new JTextField();
	JTextField prixUnitaireTextField = new JTextField();
	JLabel etatEnregistrementLabel	= new JLabel(GestionChateau.propertiesGeneral.getProperty("creer.produit.informations.produit.enregistrement"));
	JComboBox tauxTvaJComboBox;
	public JButton ajouterProduit = new JButton(GestionChateau.propertiesGeneral.getProperty("creer.produit.bouton.creer"));

	public CreationProduit() {

		// Enrichissement du panel général
		this.setPreferredSize(new Dimension(Parametres_Appli.tailleLargeurPanelApplication,Parametres_Appli.tailleHauteurPanelApplication));
		this.setBackground(Parametres_Appli.couleurFond);
		// this.setBorder(BorderFactory.createLineBorder(Color.black));
		// ============================================
		// == Label haut ==
		// ============================================

		label_haut.setHorizontalAlignment(SwingConstants.CENTER);
		label_haut.setFont(Parametres_Appli.titreFont);
		label_haut.setPreferredSize(new Dimension(Parametres_Appli.tailleLargeurPanelApplication, 25));

		// ============================================
		// == Informations clients ==
		// ============================================
		// Police par défaut des textfield
		Font fontTextfield = new Font("Arial", Font.LAYOUT_LEFT_TO_RIGHT, 12);

		// Nom de la cuvée
		JLabel nomCuveeJlabel = new JLabel(GestionChateau.propertiesGeneral.getProperty("creer.produit.informations.produit.nom"));
		this.nomCuveeTextField.setFont(fontTextfield);

		panAjoutProduit.setPreferredSize(new Dimension(500, 250));
		// panInfosClients.setBorder(BorderFactory.createLineBorder(Color.black));
		panAjoutProduit.setLayout(null);
		panAjoutProduit.setBackground(Parametres_Appli.couleurFond);

		// On ajout le label nom cuvee au panel
		nomCuveeJlabel.setBounds(100, 5, 150, 25);
		panAjoutProduit.add(nomCuveeJlabel);

		// On ajout le textfield nom cuvee au panel
		this.nomCuveeTextField.setBounds(225, 5, 175, 25);
		panAjoutProduit.add(this.nomCuveeTextField);

		// On ajout le label prix cuvee au panel
		JLabel prixUnitaireLabel = new JLabel(GestionChateau.propertiesGeneral.getProperty("creer.produit.informations.produit.prix"));
		prixUnitaireLabel.setBounds(100, 50, 150, 25);
		panAjoutProduit.add(prixUnitaireLabel);

		// On ajout le textfield prix cuvee au panel
		this.prixUnitaireTextField.setBounds(225, 50, 175, 25);
		panAjoutProduit.add(this.prixUnitaireTextField);

		// On ajout le label tva au panel
		JLabel tvaCuveeJlabel = new JLabel(GestionChateau.propertiesGeneral.getProperty("creer.produit.informations.produit.tva"));
		tvaCuveeJlabel.setBounds(100, 95, 150, 25);
		panAjoutProduit.add(tvaCuveeJlabel);

		// On ajout la liste deroulante de la tva au panel
		List<Tva> tauxTva = ListeDonneesBDD.recupTauxTva();
		String[] tauxTvaListe = new String[tauxTva.size()];
		Iterator<Tva> it = tauxTva.iterator();
		int i = 0;
		while (it.hasNext()) {
			Tva taux = it.next();
			tauxTvaListe[i] = Double.toString(taux.getTauxTva());
			i++;
		}
		this.tauxTvaJComboBox = new JComboBox(tauxTvaListe);
		this.tauxTvaJComboBox.setBounds(225, 95, 175, 25);
		panAjoutProduit.add(this.tauxTvaJComboBox);

		// On ajout le bouton au panel
		this.ajouterProduit.setBounds(200, 140, 175, 25);
		panAjoutProduit.add(this.ajouterProduit);

		// LAnce l'ajout
		this.ajouterProduit.addActionListener(this);

		// Message d'erreur
		this.etatEnregistrementLabel.setVisible(false);
		this.etatEnregistrementLabel.setBounds(200, 175, 175, 25);

		Font fontLblSuivi = new Font("Arial", Font.LAYOUT_LEFT_TO_RIGHT, 15);
		this.etatEnregistrementLabel.setForeground(new Color(0, 255, 0));
		this.etatEnregistrementLabel.setFont(fontLblSuivi);
		panAjoutProduit.add(this.etatEnregistrementLabel);

		// Au nord
		this.add(label_haut, BorderLayout.NORTH);
		this.add(panAjoutProduit, BorderLayout.CENTER);

	}

	// Bouton de creation du produit
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == this.ajouterProduit) {

			Produit produit = new Produit();
			produit.setLibelleProduit(this.nomCuveeTextField.getText());
			produit.setPrixProduit(Double.parseDouble(this.prixUnitaireTextField.getText()));

			Tva tva = new Tva();
			tva.setId(ListeDonneesBDD.recupIdTauxTva(this.tauxTvaJComboBox.getSelectedItem().toString()));
			tva.setTauxTva(Double.parseDouble(this.tauxTvaJComboBox.getSelectedItem().toString()));

			produit.setTva(tva);

			DAO<Produit> produitDao = new ProduitDAO(Connect.getInstance());
			produitDao.create(produit);

			this.etatEnregistrementLabel.setVisible(true);


		}
	}
}
