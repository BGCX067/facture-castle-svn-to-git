package Affichage.Tableaux;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import Affichage.CreerFacture;
import Conf.Parametres_Appli;
import Dao.ListeDonneesBDD;
import Utils.Utilitaires;

//CLASSE MODÈLE PERSONNALISÉE
public class TableModel extends AbstractTableModel implements TableModelListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Object[][] data;
	private final String[] title;
	/**
	 * Constructeur
	 * @param data
	 * @param title
	 */
	public TableModel(Object[][] data, String[] title){
		this.data = data;
		this.title = title;
		addTableModelListener(this);
	}
	/**
	 * Retourne le titre de la colonne à l'indice spécifé
	 */
	@Override
	public String getColumnName(int col) {
		return this.title[col];
	}

	/**
	 * Retourne le nombre de colonnes
	 */
	@Override
	public int getColumnCount() {
		return this.title.length;
	}

	/**
	 * Retourne le nombre de lignes
	 */
	@Override
	public int getRowCount() {
		return this.data.length;
	}

	/**
	 * Retourne la valeur à l'emplacement spécifié
	 */
	@Override
	public Object getValueAt(int row, int col) {
		return this.data[row][col];
	}

	/**
	 * Défini la valeur à l'emplacement spécifié
	 */
	@Override
	public void setValueAt(Object value, int row, int col) {
		//On interdit la modification sur certaine colonne !
		this.data[row][col] = value;
		if (col==1 ) {
			if(!this.data[row][1].toString().equals("")){
				this.data[row][3] = (Double.parseDouble(this.data[row][1].toString())*Double.parseDouble(this.data[row][2].toString()));
			}else{
				this.data[row][3] = 0;
			}

			// On parcours toutes les lignes de la colone prix
			this.fireTableDataChanged();
			enrichissementTableauTotaux(this.data);
		}
		if (col==0 ){
			majChampQuantite(value,row,col);
		}
		this.fireTableDataChanged();
	}

	/**
	 * Retourne la classe de la donnée de la colonne
	 * @param col
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Class getColumnClass(int col){
		//On retourne le type de la cellule à la colonne demandée
		//On se moque de la ligne puisque les données sur chaque ligne sont les mêmes
		//On choisit donc la première ligne
		return this.data[0][col].getClass();
	}

	/**
	 * Méthode permettant de retirer une ligne du tableau
	 * @param position
	 */
	public void removeRow(int position){

		int indice = 0, indice2 = 0, nbRow = this.getRowCount()-1, nbCol = this.getColumnCount();
		Object temp[][] = new Object[nbRow][nbCol];

		for(Object[] value : this.data){
			if(indice != position){
				temp[indice2++] = value;
			}
			indice++;
		}
		this.data = temp;
		temp = null;
		//Cette méthode permet d'avertir le tableau que les données ont été modifiées
		//Ce qui permet une mise à jours complète du tableau
		this.fireTableDataChanged();
	}

	/**
	 * Permet d'ajouter une ligne dans le tableau
	 * @param data
	 */
	public void addRow(Object[] data){
		int indice = 0, nbRow = this.getRowCount(), nbCol = this.getColumnCount();

		Object temp[][] = this.data;
		this.data = new Object[nbRow+1][nbCol];

		for(Object[] value : temp) {
			this.data[indice++] = value;
		}


		this.data[indice] = data;
		temp = null;
		//Cette méthode permet d'avertir le tableau que les données ont été modifiées
		//Ce qui permet une mise à jours complète du tableau
		this.fireTableDataChanged();
	}


	@Override
	public boolean isCellEditable(int row, int col){
		if((col == 3) || (col ==2)){
			return false;
		}else{
			return true;
		}
	}

	@Override
	public void tableChanged(TableModelEvent e) {
		System.out.println("Point123");
		//setValueAt(e, int row, int col);
		//System.out.println(e.getFirstRow()

		/*  GestionChateau.propertiesGeneral.getProperty("informations.commande.tableau.entete.total") =+
			   GestionChateau.propertiesGeneral.getProperty("informations.commande.tableau.entete.prixunitaire");*/

	}

	public void majChampQuantite(Object value, int row, int col) {
		System.out.println("Point12333");
		this.data[row][2]=ListeDonneesBDD.getPrixProduit((String) this.data[row][col]);
	}

	public static void enrichissementTableauTotaux(Object[][] data){
		Double totalHT = 0.0;
		Double remise = 0.0;
		int nbBouteille = 0;
		Double port = 0.0;
		Double totalPrixTVA = 0.0;
		int i = 0;
		for (i=0; i<data.length; i++){
			if (data[i][3].toString()!= ""){
				totalHT = Utilitaires.floor(totalHT+Double.parseDouble(data[i][3].toString()),2);
			}
			if (!data[i][1].toString().equals("")){
				nbBouteille = nbBouteille+Integer.parseInt(data[i][1].toString());
			}
		}
		// Alimentation du champ Total HT
		CreerFacture.totalHTTextField.setText(totalHT.toString());

		if(CreerFacture.codePostalTextFieldDestinataire.getText().equals("")){
			port = 0.0;
		}else{
			String region = CreerFacture.codePostalTextFieldDestinataire.getText().substring(0, 2);
			int zone = ListeDonneesBDD.recupzone(region);
			port = ListeDonneesBDD.recupeFraisPortQantiteCodePostal(nbBouteille,zone);
		}

		CreerFacture.portTextField.setText(port.toString());


		if (CreerFacture.remiseTextField.getText().equals("") ){
			CreerFacture.remiseTextField.setText("0.0");
		}
		port = Double.parseDouble(CreerFacture.portTextField.getText());
		remise = Double.parseDouble(CreerFacture.remiseTextField.getText());
		totalPrixTVA = Utilitaires.floor(((totalHT*(Parametres_Appli.TVA/100))+totalHT+port)-remise,2);
		CreerFacture.portTextField.setText(port.toString());
		CreerFacture.remiseTextField.setText(remise.toString());
		CreerFacture.tauxTVATextField.setText(Parametres_Appli.TVA.toString());
		CreerFacture.totalTTCTextField.setText(totalPrixTVA.toString());
	}


}



