package Dao;

import java.sql.Connection;

import Bean.Bilan;
import Bean.Client;

public abstract class DAO<T> {

	protected Connection connect = null;

	/**
	 * Constructeur
	 * @param conn
	 */
	public DAO(Connection conn){
		this.connect = conn;
	}

	/**
	 * Méthode de création
	 * @param obj
	 * @return
	 */
	public abstract boolean create(T obj);
	/**
	 * Méthode pour effacer
	 * @param obj
	 * @return
	 */
	public abstract boolean delete(T obj);
	/**
	 * Méthode de mise à jour
	 * @param obj
	 * @return
	 */
	public abstract boolean update(T obj);
	/**
	 * Méthode de recherche des informations
	 * @param id
	 * @return
	 */
	public abstract T find(int id);


	public int findId(Client client) {
		// TODO Auto-generated method stub
		return 0;
	}
	public int findIdByNom(String nomClient) {
		// TODO Auto-generated method stub
		return 0;
	}
	public int findByTaux(String tauxTva){
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Methode qui permet de gerer un update ou un create
	 * @param obj
	 * @return
	 */
	public boolean save(Bilan obj){
		return false;
	}

}
