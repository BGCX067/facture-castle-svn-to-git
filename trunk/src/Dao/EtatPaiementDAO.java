package Dao;

import java.sql.Connection;
import java.sql.SQLException;

import Bean.EtatPaiement;

import com.mysql.jdbc.ResultSet;

public class EtatPaiementDAO extends DAO<EtatPaiement> {

	public EtatPaiementDAO(Connection conn) {
		super(conn);
	}

	@Override
	public EtatPaiement find(int id) {
		EtatPaiement etatPaiement = new EtatPaiement();
		try {
			ResultSet result = (ResultSet) this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery(
					"SELECT * FROM arn_etat_paiement WHERE id_etat_paiement = "
							+ id);
			if (result.first())
				etatPaiement = new EtatPaiement(id, result
						.getString("libelle_etat_paiement "), result
						.getBoolean("actif"));

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return etatPaiement;
	}

	@Override
	public boolean create(EtatPaiement obj) {
		System.out
				.println("Insertion dans la BDD d'un nouvel etat de paiement");
		System.out
				.println("INSERT INTO arn_etat_paiement ( libelle_etat_paiement,Actif)  "
						+ "VALUES ('"
						+ obj.getLibelleEtatPaiement()
						+ "',"
						+ obj.getActif() + ")");
		try {
			this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeUpdate(
					"INSERT INTO arn_etat_paiement ( libelle_etat_paiement,Actif)  "
							+ "VALUES ('" + obj.getLibelleEtatPaiement() + "',"
							+ obj.getActif() + ")");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(EtatPaiement obj) {
		System.out.println("suppression dans la BDD d'un etat de paiement");
		System.out
				.println("DELETE FROM arn_etat_paiement WHERE id_etat_paiement  = '"
						+ obj.getId() + "' ");

		try {
			this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeUpdate(
					"DELETE FROM arn_etat_paiement WHERE id_etat_paiement = '"
							+ obj.getId() + "' ");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(EtatPaiement obj) {
		System.out.println("Modification dans la BDD d'un etat de paiement");
		System.out
				.println("UPDATE arn_etat_paiement SET libelle_etat_paiement = '"
						+ obj.getLibelleEtatPaiement()
						+ "' ,actif = "
						+ obj.getActif()
						+ " WHERE id_etat_paiement = '"
						+ obj.getId() + "' ");

		try {
			this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeUpdate(
					"UPDATE arn_etat_paiement SET libelle_etat_paiement = '"
							+ obj.getLibelleEtatPaiement() + "' ,actif = "
							+ obj.getActif() + " WHERE id_etat_paiement = '"
							+ obj.getId() + "' ");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
