package Dao;

import java.sql.Connection;
import java.sql.SQLException;

import bdd.Connect;

import com.mysql.jdbc.ResultSet;

import Bean.MoyenPaiement;
import Bean.Produit;
import Bean.Tva;

public class MoyenPaiementDAO extends DAO<MoyenPaiement> {

	public MoyenPaiementDAO(Connection conn) {
		super(conn);
	}
	

	@Override
	public MoyenPaiement find(int id) {
		MoyenPaiement moyenPaiement = new MoyenPaiement();                  
        try {ResultSet result = (ResultSet) this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery(
                                                                                                "SELECT * FROM arn_moyen_paiement WHERE id_moyen_paiement = " + id);
             if(result.first())moyenPaiement = new MoyenPaiement(id, result.getString("libelle_moyen_paiement "), result.getBoolean("actif"));

        } catch (SQLException e) {
                e.printStackTrace();
        }
        return moyenPaiement;
	}
	

	@Override
	public boolean create(MoyenPaiement obj) {
		System.out.println("Insertion dans la BDD d'un nouveau mode de paiement");
		System.out.println("INSERT INTO arn_moyen_paiement ( libelle_moyen_paiement,Actif)  " +
                "VALUES ('"+obj.getLibelleMoyenPaiement()+"',"+obj.getActif()+")");
		try {this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeUpdate(
				"INSERT INTO arn_moyen_paiement ( libelle_moyen_paiement,Actif)  " +
                "VALUES ('"+obj.getLibelleMoyenPaiement()+"',"+obj.getActif()+")");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(MoyenPaiement obj) {
		System.out.println("suppression dans la BDD d'un mode de paiement");
		System.out.println("DELETE FROM arn_moyen_paiement WHERE id_moyen_paiement = '"+obj.getId()+"' ");
              
		try {this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeUpdate(
				"DELETE FROM arn_moyen_paiement WHERE id_moyen_paiement = '"+obj.getId()+"' ");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(MoyenPaiement obj) {
		System.out.println("Modification dans la BDD d'un mode paiement");
		System.out.println("UPDATE arn_moyen_paiement SET libelle_moyen_paiement = '"+obj.getLibelleMoyenPaiement()+"' ,actif = "+obj.getActif()+" WHERE id_moyen_paiement = '"+obj.getId()+"' ");
              
		try {this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeUpdate(
				"UPDATE arn_moyen_paiement SET libelle_moyen_paiement = '"+obj.getLibelleMoyenPaiement()+"' ,actif = "+obj.getActif()+" WHERE id_moyen_paiement = '"+obj.getId()+"' ");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
