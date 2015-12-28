package Utils;

import Affichage.GestionChateau;

public class Utilitaires {

	/**
	 * Arrondi d'un double avec n �l�ments apr�s la virgule.
	 * 
	 * @param a
	 *            La valeur � convertir.
	 * @param n
	 *            Le nombre de d�cimales � conserver.
	 * @return La valeur arrondi � n d�cimales.
	 */
	public static double floor(double a, int n) {
		double p = Math.pow(10.0, n);
		return Math.floor((a * p) + 0.5) / p;
	}

	/**
	 * M�thode qui renvoi le nom du mois en cours
	 */
	public static String nomMois(int numeroMois) {

		String nomMois = "";

		switch (numeroMois) {
		case 1:
			nomMois = GestionChateau.propertiesGeneral
			.getProperty("mois.nom.un");
			break;
		case 2:
			nomMois = GestionChateau.propertiesGeneral
			.getProperty("mois.nom.deux");
			break;
		case 3:
			nomMois = GestionChateau.propertiesGeneral
			.getProperty("mois.nom.trois");
			break;
		case 4:
			nomMois = GestionChateau.propertiesGeneral
			.getProperty("mois.nom.quatre");
			break;
		case 5:
			nomMois = GestionChateau.propertiesGeneral
			.getProperty("mois.nom.cinq");;
			break;
		case 6:
			nomMois = GestionChateau.propertiesGeneral
			.getProperty("mois.nom.six");;
			break;
		case 7:
			nomMois = GestionChateau.propertiesGeneral
			.getProperty("mois.nom.sept");
			break;
		case 8:
			nomMois = GestionChateau.propertiesGeneral
			.getProperty("mois.nom.huit");
			break;
		case 9:
			nomMois = GestionChateau.propertiesGeneral
			.getProperty("mois.nom.neuf");
			break;
		case 10:
			nomMois = GestionChateau.propertiesGeneral
			.getProperty("mois.nom.dix");
			break;
		case 11:
			nomMois = GestionChateau.propertiesGeneral
			.getProperty("mois.nom.onze");
			break;
		case 12:
			nomMois = GestionChateau.propertiesGeneral
			.getProperty("mois.nom.douze");
			break;
		default:
			break;
		}
		return nomMois;
	}

}
