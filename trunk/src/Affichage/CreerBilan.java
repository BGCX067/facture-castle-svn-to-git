package Affichage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import Bean.Bilan;
import Bean.Produit;
import Conf.Parametres_Appli;
import Dao.BilanDAO;
import Dao.DAO;
import Dao.ListeDonneesBDD;
import Traitement.GenererBilanPdf;
import bdd.Connect;

import com.itextpdf.text.DocumentException;

public class CreerBilan extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static JLabel label_haut = new JLabel(
			GestionChateau.propertiesGeneral.getProperty("creer.bilan.titre"));
	public static JPanel panBilan = new JPanel();

	JTextArea premierePartieTextArea = new JTextArea();
	JScrollPane premierePartieScroll = new JScrollPane();

	JTextArea deuxiemePartieTextArea = new JTextArea();
	JScrollPane deuxiemePartieScroll = new JScrollPane();

	JTextArea troisiemePartieTextArea = new JTextArea();
	JScrollPane troisiemePartieScroll = new JScrollPane();

	JTextArea quatriemePartieTextArea = new JTextArea();
	JScrollPane quatriemePartieScroll = new JScrollPane();

	public JButton genererBilan = new JButton(
			GestionChateau.propertiesGeneral
			.getProperty("creer.bilan.bouton.generer"));

	public CreerBilan() {

		panBilan.removeAll();

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

		panBilan.setPreferredSize(new Dimension(700, 650));
		panBilan.setLayout(null);
		panBilan.setBackground(Parametres_Appli.couleurFond);

		// Police par défaut des textfield
		Font fontTextfield = new Font("Arial", Font.LAYOUT_LEFT_TO_RIGHT, 12);

		// Premiere partie
		JLabel premierePartieJlabel = new JLabel(GestionChateau.propertiesGeneral
				.getProperty("creer.bilan.generation.puce") + " " +
				GestionChateau.propertiesGeneral
				.getProperty("creer.bilan.premiere.partie"));
		this.premierePartieTextArea.setFont(fontTextfield);
		//this.premierePartieTextArea.setLineWrap(true);
		//this.premierePartieTextArea.setDocument(new LimitedStyleDocument(3));
		this.premierePartieScroll = new JScrollPane(this.premierePartieTextArea);
		// On ajout le label nom premiere partie au panel
		premierePartieJlabel.setBounds(40, 40, 150, 25);
		panBilan.add(premierePartieJlabel);
		// On ajout le textfield premiere partie au panel
		this.premierePartieScroll.setBounds(150, 5, 400, 100);
		panBilan.add(this.premierePartieScroll);

		// Deuxieme partie
		JLabel deuxiemePartieJlabel = new JLabel(GestionChateau.propertiesGeneral
				.getProperty("creer.bilan.generation.puce") + " " +
				GestionChateau.propertiesGeneral
				.getProperty("creer.bilan.deuxieme.partie"));
		this.deuxiemePartieTextArea.setFont(fontTextfield);
		this.deuxiemePartieScroll = new JScrollPane(this.deuxiemePartieTextArea);
		// On ajout le label nom deuxieme partie au panel
		deuxiemePartieJlabel.setBounds(40, 160, 150, 25);
		panBilan.add(deuxiemePartieJlabel);
		// On ajout le textfield deuxieme partie au panel
		this.deuxiemePartieScroll.setBounds(150, 125, 400, 100);
		panBilan.add(this.deuxiemePartieScroll);

		// Troisieme partie
		JLabel troisiemePartieJlabel = new JLabel(GestionChateau.propertiesGeneral
				.getProperty("creer.bilan.generation.puce") + " " +
				GestionChateau.propertiesGeneral
				.getProperty("creer.bilan.troisieme.partie"));
		this.troisiemePartieTextArea.setFont(fontTextfield);
		this.troisiemePartieScroll = new JScrollPane(
				this.troisiemePartieTextArea);
		// On ajout le label nom deuxieme partie au panel
		troisiemePartieJlabel.setBounds(40, 280, 150, 25);
		panBilan.add(troisiemePartieJlabel);
		// On ajout le textfield deuxieme partie au panel
		this.troisiemePartieScroll.setBounds(150, 245, 400, 100);
		panBilan.add(this.troisiemePartieScroll);

		// Quatrieme partie
		JLabel quatriemePartieJlabel = new JLabel(GestionChateau.propertiesGeneral
				.getProperty("creer.bilan.generation.puce") + " " +
				GestionChateau.propertiesGeneral
				.getProperty("creer.bilan.quatrieme.partie"));
		this.quatriemePartieTextArea.setFont(fontTextfield);
		this.quatriemePartieTextArea.setEnabled(false);
		this.quatriemePartieScroll = new JScrollPane(
				this.quatriemePartieTextArea);
		// On ajout le label nom deuxieme partie au panel
		quatriemePartieJlabel.setBounds(40, 400, 150, 25);
		panBilan.add(quatriemePartieJlabel);
		// On ajout le textfield deuxieme partie au panel
		this.quatriemePartieScroll.setBounds(150, 365, 400, 100);
		panBilan.add(this.quatriemePartieScroll);

		// On recupere les infos concernants les ventes de bouteilles
		Calendar date = Calendar.getInstance();

		List<Produit> listProduit = ListeDonneesBDD
				.recupProduitVendusMoisCourant(date.get(date.MONTH) + 1);
		String AfficherListeProduit = "";
		for (Produit produitEnCours : listProduit) {
			AfficherListeProduit += produitEnCours.getLibelleProduit() + " : "
					+ produitEnCours.getNbBouteillesVendu() + " " + "\n";
		}

		this.quatriemePartieTextArea.setText(AfficherListeProduit);
		// On ajout le bouton au panel
		this.genererBilan.setBounds(200, 550, 175, 25);
		panBilan.add(this.genererBilan);

		// Lance la generation
		this.genererBilan.addActionListener(this);

		// Au nord
		this.add(label_haut, BorderLayout.NORTH);
		this.add(panBilan, BorderLayout.CENTER);

	}

	// Bouton de generation du bilan
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == this.genererBilan) {

			Bilan lBilan = new Bilan(
					GestionChateau.propertiesGeneral
					.getProperty("creer.bilan.nomSociete"),
					GestionChateau.propertiesGeneral
					.getProperty("creer.bilan.generation.gerant.un"),
					GestionChateau.propertiesGeneral
					.getProperty("creer.bilan.generation.gerant.deux"),
					GestionChateau.propertiesGeneral
					.getProperty("creer.bilan.generation.gerant.trois"),
					GestionChateau.propertiesGeneral
					.getProperty("creer.bilan.titre.fichier"),
					GestionChateau.propertiesGeneral
					.getProperty("creer.bilan.premiere.partie"),
					this.premierePartieTextArea.getText(),
					GestionChateau.propertiesGeneral
					.getProperty("creer.bilan.deuxieme.partie"),
					this.deuxiemePartieTextArea.getText(),
					GestionChateau.propertiesGeneral
					.getProperty("creer.bilan.troisieme.partie"),
					this.troisiemePartieTextArea.getText(),
					GestionChateau.propertiesGeneral
					.getProperty("creer.bilan.quatrieme.partie"),
					this.quatriemePartieTextArea.getText(), Calendar.getInstance());

			try {
				GenererBilanPdf.manipulatePdf(lBilan);

				DAO<Bilan> bilanDao = new BilanDAO(Connect.getInstance());
				bilanDao.save(lBilan);

				JOptionPane.showMessageDialog(null,
						GestionChateau.propertiesGeneral
						.getProperty("creer.bilan.generation.ok"));

				// Ouverture du PDF (facture)
				java.awt.Desktop.getDesktop().open(new
						File(GestionChateau.propertiesMetier.getProperty("export.bilan")+"Bilan_2011_Octobre.pdf"));

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
