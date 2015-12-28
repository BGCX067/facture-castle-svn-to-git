package Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Bean.Client;
import bdd.Connect;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;

public class ClientDAO extends DAO<Client> {

	public ClientDAO(Connection conn) {
		super(conn);
	}

	@Override
	public Client find(int id) {
		Client client = new Client();
		try {
			ResultSet result = (ResultSet) this.connect.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
					java.sql.ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM arn_clients WHERE id_client = " + id);
			if (result.first()) {
				client = new Client(id, result.getString("nom_client"), result.getString("prenom_client"), result
						.getString("adresse_client"), result.getString("cp_client"), result.getString("ville_client"),
						result.getString("infos_client"), result.getString("mail_client"), result
						.getString("tel_client"), result.getString("ddn_client"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return client;
	}

	@Override
	public int findId(Client client) {
		int idClient = 0;
		try {
			ResultSet result = (ResultSet) this.connect.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
					java.sql.ResultSet.CONCUR_READ_ONLY).executeQuery(
							"SELECT * FROM arn_clients WHERE nom_client = '" + client.getNom() + "' and prenom_client ='"
									+ client.getPrenom() + "'");
			if (result.first()) {
				idClient = result.getInt("id_client");
			} else {
				// Nouveau Client
				ClientDAO clientDao = new ClientDAO(Connect.getInstance());
				clientDao.create(client);
				idClient = findId(client);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return idClient;
	}

	@Override
	public int findIdByNom(String nomClient) {
		int idClient = 0;
		try {
			ResultSet result = (ResultSet) this.connect.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
					java.sql.ResultSet.CONCUR_READ_ONLY).executeQuery(
							"SELECT * FROM arn_clients WHERE nom_client LIKE '%" + nomClient + "%'");
			System.out.println("SELECT * FROM arn_clients WHERE nom_client LIKE '%" + nomClient + "%'");
			if (result.first()) {
				idClient = result.getInt("id_client");
			} else {
				idClient = 0;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return idClient;
	}

	/* Retourne tous les clients */
	public static List<Client> recupAllIdClientByNom(String nomClient, String ville) {

		List<Client> allClient = new ArrayList<Client>();
		String clause = "";
		if ((!nomClient.equals("")) && (ville.equals(""))){
			clause =" WHERE nom_client LIKE '%" + nomClient + "%'";
		}else if ((nomClient.equals("")) && (!ville.equals(""))){
			clause =" WHERE ville_client LIKE '%" + ville + "%'";
		}else if ((!nomClient.equals("")) && (!ville.equals(""))){
			clause =" WHERE nom_client LIKE '%" + nomClient + "%' and ville_client LIKE '%" + ville + "%'";
		}else{
			clause ="";
		}
		try {
			ResultSet result = (ResultSet) Connect
					.getInstance()
					.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
							java.sql.ResultSet.CONCUR_READ_ONLY)
							.executeQuery("SELECT * FROM arn_clients "+clause);
			while (result.next()) {
				allClient.add(new Client(result.getInt("id_client"),result.getString("ville_client")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return allClient;
	}

	@Override
	public boolean create(Client obj) {
		System.out.println("Insertion dans la BDD d'un client");
		try {
			this.connect.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE, java.sql.ResultSet.CONCUR_READ_ONLY).executeUpdate(
					"INSERT INTO arn_clients ( nom_client, prenom_client, adresse_client, cp_client, ville_client, infos_client)  "
							+ "VALUES ('" + obj.getNom() + "','" + obj.getPrenom() + "','" + obj.getAdresse() + "','"
							+ obj.getCp() + "','" + obj.getVille() + "','" + obj.getInfos() + "')");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Insertion dans la BDD OK");
		return false;
	}

	@Override
	public boolean delete(Client obj) {
		System.out.println("suppression dans la BDD d'un client");
		System.out.println("DELETE FROM arn_clients WHERE id_client = '"+obj.getId()+"' ");

		try {this.connect.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,java.sql.ResultSet.CONCUR_READ_ONLY).executeUpdate(
				"DELETE FROM arn_clients WHERE id_client = '"+obj.getId()+"' ");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(Client obj) {

		System.out.println("UPDATE arn_clients SET nom_client = '"+obj.getNom()+"' ,prenom_client = '"+obj.getPrenom()+"',adresse_client = '"+obj.getAdresse()+"',cp_client = '"+obj.getCp()+"',ville_client = '"+obj.getVille()+"',infos_client = '"+obj.getInfos()+"',mail_client = '"+obj.getMail()+"',tel_client = '"+obj.getTelephone()+"',ddn_client = '"+obj.getDateNaissance()+"' WHERE id_client = '"+obj.getId()+"' ");

		try {this.connect.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,java.sql.ResultSet.CONCUR_READ_ONLY).executeUpdate(
				"UPDATE arn_clients SET nom_client = '"+obj.getNom()+"' ,prenom_client = '"+obj.getPrenom()+"',adresse_client = '"+obj.getAdresse()+"',cp_client = '"+obj.getCp()+"',ville_client = '"+obj.getVille()+"',infos_client = '"+obj.getInfos()+"',mail_client = '"+obj.getMail()+"',tel_client = '"+obj.getTelephone()+"',ddn_client = '"+obj.getDateNaissance()+"' WHERE id_client = '"+obj.getId()+"' ");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}



}
