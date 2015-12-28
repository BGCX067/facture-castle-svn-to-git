import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import Conf.Parametres_Appli;



public class Identification extends JDialog implements ActionListener {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*JLabel labelLogin = new JLabel(Parametres_Appli.Login);
    JLabel labelPass = new JLabel(Parametres_Appli.Pass);*/
    JTextField textLogin = new JTextField("");
    JTextField textPass = new JTextField("");
    
    JButton butonOkIdent = new JButton("OK");
	
	public Identification(){
		//this.setTitle(Parametres_Appli.TitreFenetreIdentification);
	    this.setSize(300, 200);
	    this.setLocationRelativeTo(null);
	    this.setResizable(false);
	    
	    /* Alimentation de fenetre */
	    /*this.add(labelLogin);
	    this.add(textLogin);
	    this.add(labelPass);
	    this.add(textPass);*/
	    this.add(butonOkIdent);
	    
	    this.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
