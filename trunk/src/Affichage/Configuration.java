package Affichage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;



public class Configuration extends JDialog implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JOptionPane jOptionPan1 = new JOptionPane();
	JPanel panOngletExport = new JPanel();
	JPanel panOngletSourceDeDonnees = new JPanel();

	// Export
	JLabel labelExportModele = new JLabel("Modèle : ");
	JTextField textExportModele = new JTextField(GestionChateau.propertiesMetier.getProperty("export.modele"));
	JLabel labelExportFacture = new JLabel("Exports : ");
	JTextField textExportFacture = new JTextField(GestionChateau.propertiesMetier.getProperty("export.facture"));
	// Source de données
	JLabel labelUrl = new JLabel("Url : ");
	JTextField textUrl = new JTextField(GestionChateau.propertiesMetier.getProperty("bdd.url"));
	JLabel labelUser = new JLabel("User : ");
	JTextField textUser = new JTextField(GestionChateau.propertiesMetier.getProperty("bdd.user"));
	JLabel labelPassword = new JLabel("Password : ");
	JTextField textPassword = new JTextField(GestionChateau.propertiesMetier.getProperty("bdd.password"));

	JButton butonOkExport = new JButton("OK");
	JButton butonOkSourceDeDonnees = new JButton("OK");

	public Configuration(){

		this.setTitle(GestionChateau.propertiesGeneral.getProperty("configuration.titre"));
		this.setSize(500, 300);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setResizable(false);

		this.butonOkExport.addActionListener(this);
		this.butonOkSourceDeDonnees.addActionListener(this);

		this.textUrl.setPreferredSize(new Dimension(200,20));

		//Création de notre conteneur d'onglets
		JTabbedPane ongletConfig = new JTabbedPane();

		this.panOngletExport.setLayout(new BorderLayout());
		this.panOngletSourceDeDonnees.setLayout(new BorderLayout());

		// Export
		this.panOngletExport.setPreferredSize(new Dimension(500,300));
		this.panOngletExport.setLayout(null);
		this.labelExportModele.setBounds(50, 50, 100, 20);
		this.textExportModele.setBounds(130, 50 , 320, 20);
		this.labelExportFacture.setBounds(50, 70, 100, 20);
		this.textExportFacture.setBounds(130, 70 ,320, 20);
		this.butonOkExport.setBounds(220, 150, 55, 25);
		this.panOngletExport.add(this.labelExportModele);
		this.panOngletExport.add(this.textExportModele);
		this.panOngletExport.add(this.labelExportFacture);
		this.panOngletExport.add(this.textExportFacture);
		this.panOngletExport.add(this.butonOkExport);
		// Sources de données
		this.panOngletSourceDeDonnees.setPreferredSize(new Dimension(500,300));
		this.panOngletSourceDeDonnees.setLayout(null);
		this.labelUrl.setBounds(110, 50, 100, 20);
		this.textUrl.setBounds(220, 50 , 200, 20);
		this.labelUser.setBounds(110, 70, 100, 20);
		this.textUser.setBounds(220, 70 ,100, 20);
		this.labelPassword.setBounds(110, 90, 100, 20);
		this.textPassword.setBounds(220, 90, 100, 20);
		this.butonOkSourceDeDonnees.setBounds(220, 150, 55, 25);
		this.panOngletSourceDeDonnees.add(this.labelUrl);
		this.panOngletSourceDeDonnees.add(this.textUrl);
		this.panOngletSourceDeDonnees.add(this.labelUser);
		this.panOngletSourceDeDonnees.add(this.textUser);
		this.panOngletSourceDeDonnees.add(this.labelPassword);
		this.panOngletSourceDeDonnees.add(this.textPassword);
		this.panOngletSourceDeDonnees.add(this.butonOkSourceDeDonnees);

		ongletConfig.add("Export", this.panOngletExport);
		ongletConfig.add("Sources de données", this.panOngletSourceDeDonnees);

		//on passe ensuite les onglets au contentPane
		this.getContentPane().add(ongletConfig);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource() == this.butonOkExport){
			GestionChateau.propertiesMetier.setProperty("export.modele", this.textExportModele.getText());
			GestionChateau.propertiesMetier.setProperty("export.facture",this.textExportFacture.getText());
			this.dispose();
			FileOutputStream outMetier;
			try {
				outMetier = new FileOutputStream("conf/conf_metiers.properties");
				GestionChateau.propertiesMetier.save(outMetier,"MAJ");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch ( IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.dispose();
		}
		if(arg0.getSource() == this.butonOkSourceDeDonnees){
			GestionChateau.propertiesMetier.setProperty("bdd.url", this.textUrl.getText());
			GestionChateau.propertiesMetier.setProperty("bdd.user", this.textUser.getText());
			GestionChateau.propertiesMetier.setProperty("bdd.password",this.textPassword.getText());
			this.dispose();
			FileOutputStream outMetier;
			try {
				outMetier = new FileOutputStream("conf/conf_metiers.properties");
				GestionChateau.propertiesMetier.save(outMetier,"MAJ");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch ( IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	class StateListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			/*if(e.getSource() == jrGrille){

                }*/

		}

	}

}


