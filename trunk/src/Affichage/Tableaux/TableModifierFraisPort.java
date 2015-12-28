package Affichage.Tableaux;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

public class TableModifierFraisPort extends AbstractTableModel implements TableModelListener {


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
	public TableModifierFraisPort(Object[][] data, String[] title){
		this.data = data;
		this.title = title;
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
		return true;
	}
	@Override
	public void tableChanged(TableModelEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void setData(Object[][] data) {
		this.data = data ;
		fireTableDataChanged();
	}

}
