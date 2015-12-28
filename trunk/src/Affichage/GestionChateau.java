package Affichage;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GestionChateau extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// public static boolean visiblePanelCreerFacture = false;
	public static JPanel panAll = new JPanel();
	public static Properties propertiesGeneral = new Properties();
	public static Properties propertiesMetier = new Properties();

	public GestionChateau() {

		// Ajout du fichier de propriétés
		FileInputStream in;
		FileInputStream inMetier;
		try {
			in = new FileInputStream("conf/fr_langage.properties");
			inMetier = new FileInputStream("conf/conf_metiers.properties");
			propertiesGeneral.load(in);
			propertiesMetier.load(inMetier);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// this.setTitle(Parametres_Appli.NomChateau);
		this.setTitle(GestionChateau.propertiesGeneral
				.getProperty("titre.application"));
		Image icone = Toolkit.getDefaultToolkit().getImage(
				"./img/logo_chateau.png");
		this.setIconImage(icone);
		this.setSize(1024, 768);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addComponentListener(this.listener);
		this.setLocationRelativeTo(null);
		this.setResizable(true);
		Color couleurFond = (new Color(224, 224, 224));
		panAll.setBackground(couleurFond);
		this.setBackground(couleurFond);
		// this.panAll.add(new JButton("COUCOU"), BorderLayout.CENTER);
		// this.setContentPane(new CreerFacture());
		Menu menu = new Menu();
		this.setJMenuBar(menu.menuBar);
		panAll.add(new CreerFacture());

		this.setContentPane(panAll);
		this.setVisible(true);

		// Identification ident = new Identification();
	}

	ComponentListener listener = new ComponentAdapter() {

		@Override
		public void componentMoved(ComponentEvent evt) {
			/*
			 * Component c = (Component) evt.getSource(); Point newLoc =
			 * c.getLocation();
			 */
		}

		@Override
		public void componentResized(ComponentEvent evt) {
			/*
			 * Component c = (Component) evt.getSource(); Dimension newSize =
			 * c.getSize(); System.out.println("Get new size"+c.getSize());
			 */
		}
	};

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}
}
