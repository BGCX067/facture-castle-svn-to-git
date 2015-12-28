package Dao;


import java.sql.SQLException;

import Bean.Client;
import Bean.Facture;
import bdd.Connect;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;

public class FactureDAO extends DAO<Facture> {

	public FactureDAO(Connection conn) {
		super(conn);
	}

	@Override
	public Facture find(int id) {
		Facture facture = new Facture();
		try {ResultSet result = (ResultSet) this.connect.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,java.sql.ResultSet.CONCUR_READ_ONLY).executeQuery(
				"SELECT * FROM arn_facture WHERE id_facture = " + id);
		DAO<Client> clientDao = new ClientDAO(Connect.getInstance());
		/*if(result.first())facture = new Facture(id, result.getString("num_facture"),
                		 result.getString("lieu_facture"),clientDao.find(result.getInt("id_client_fk")),
                		 result.getDouble("prix_total_HT"),result.getDouble("prix_total_TTC"), result.getString("date_facture"),
                		 result.getInt("idEtatPaiement"),result.getInt("idMoyenPaiement"));*/
		if(result.first()) {
			facture = new Facture(id, result.getString("num_facture"), result.getString("lieu_facture"), clientDao.find(result.getInt("id_client_fk")),
					result.getDouble("remise_facture"),result.getDouble("port_facture"), result.getDouble("tva_facture"), result.getDouble("prix_total_HT"), result.getDouble("prix_total_TTC"),
					result.getString("date_facture"), result.getInt("idEtatPaiement"), result.getInt("idMoyenPaiement"),
					result.getString("dest_nom"), result.getString("dest_prenom"), result.getString("dest_adresse"),
					result.getString("dest_code_postal"), result.getString("dest_ville"), result.getString("dest_mail"), result.getString("dest_telephone"));
		}



		} catch (SQLException e) {
			e.printStackTrace();
		}
		return facture;
	}

	@Override
	public boolean create(Facture obj) {
		// TODO Auto-generated method stub
		System.out.println("Insertion dans la BDD d'une facture");

		try {this.connect.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,java.sql.ResultSet.CONCUR_READ_ONLY).executeUpdate(
				"INSERT INTO arn_facture ( num_facture, id_client_fk, tva_facture, prix_total_HT, prix_total_TTC, remise_facture, port_facture, id_etat_paiement_fk, id_moyen_paiement_fk, date_facture, lieu_facture, dest_nom, dest_prenom, dest_adresse, dest_code_postal, dest_ville, dest_mail, dest_telephone)  " +
						"VALUES ('"+obj.getNumeroFacture()+"',"+obj.getClient().getId()+",'"+obj.getTxTVA()+"','"+obj.getPrixTotalHT()+"','"+obj.getPrixTotalTTC()+"','"+obj.getRemise()+"','"+obj.getPort()+"',"+obj.getIdEtatPaiement()+","+obj.getIdMoyenPaiement()+", '"+obj.getDateFacture()+"', '"+obj.getLieuTransaction().replaceAll("'", "''")+"', '"+obj.getDestNom().replaceAll("'", "''")+"', '"+obj.getDestPrenom().replaceAll("'", "''")+"', '"+obj.getDestAdresse().replaceAll("'", "''")+"', '"+obj.getDestCodePostal()+"', '"+obj.getDestVille().replaceAll("'", "''")+"', '"+obj.getDestMail()+"', '"+obj.getDestTelephone()+"')");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(Facture obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Facture obj) {
		// TODO Auto-generated method stub
		return false;
	}



}

