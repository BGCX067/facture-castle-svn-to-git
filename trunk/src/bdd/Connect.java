package bdd;

import java.sql.DriverManager;
import java.sql.SQLException;

import Affichage.GestionChateau;

import com.mysql.jdbc.Connection;

public class Connect{

	/**
	 * URL de connection
	 */
	private String url = GestionChateau.propertiesMetier.getProperty("bdd.url");
	/**
	 * Nom du user
	 */
	private String user = GestionChateau.propertiesMetier.getProperty("bdd.user");
	/**
	 * Mot de passe du user
	 */
	private String passwd = GestionChateau.propertiesMetier.getProperty("bdd.password");
	/**
	 * Objet Connection
	 */
	private static Connection connect;
	
	/**
	 * Constructeur privé
	 */
	private Connect(){
		try {
			connect = (Connection) DriverManager.getConnection(url, user, passwd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Méthode qui va nous retourner notre instance
	 * et la créer si elle n'existe pas...
	 * @return
	 */
	public static Connection getInstance(){
		if(connect == null){
			new Connect();
		}
		return connect;	
	}	
}
