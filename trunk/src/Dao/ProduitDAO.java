package Dao;

import java.sql.SQLException;

import Bean.Produit;
import Bean.Tva;
import bdd.Connect;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;

public class ProduitDAO extends DAO<Produit> {

	public ProduitDAO(Connection conn) {
		super(conn);
	}

	@Override
	public Produit find(int id) {
		Produit produit = new Produit();
		try {ResultSet result = (ResultSet) this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery(
				"SELECT * FROM arn_produits WHERE id_produit = " + id);
		DAO<Tva> tvaDao = new TvaDAO(Connect.getInstance());
		if(result.first()) {
			produit = new Produit(id, result.getString("nom_client"), result.getDouble("prix_produit"), tvaDao.find(result.getInt("id_tva_fk")), result.getBoolean("actif"), result.getInt("stock"));
		}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return produit;
	}



	@Override
	public boolean create(Produit obj) {

		System.out.println("Insertion dans la BDD d'un produit");
		System.out.println("INSERT INTO arn_produits ( denomination_produit,prix_produit,id_tva_fk )  " +
				"VALUES ('"+obj.getLibelleProduit()+"','"+obj.getPrixProduit()+"',"+obj.getTva().getId()+")");
		try {this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeUpdate(
				"INSERT INTO arn_produits ( denomination_produit,prix_produit,actif,id_tva_fk )  " +
						"VALUES ('"+obj.getLibelleProduit()+"','"+obj.getPrixProduit()+"','1',"+obj.getTva().getId()+")");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(Produit obj) {
		System.out.println("suppression dans la BDD d'un produit");
		System.out.println("DELETE FROM arn_produits WHERE id_produit = '"+obj.getId()+"' ");

		try {this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeUpdate(
				"DELETE FROM arn_produits WHERE id_produit = '"+obj.getId()+"' ");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(Produit obj) {
		System.out.println("Modification dans la BDD d'un produit");
		System.out.println("UPDATE arn_produits SET denomination_produit = '"+obj.getLibelleProduit()+"' ,prix_produit = '"+obj.getPrixProduit()+"',id_tva_fk = "+obj.getTva().getId()+",actif = "+obj.getActif()+" WHERE id_produit = '"+obj.getId()+"' ");

		try {this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeUpdate(
				"UPDATE arn_produits SET denomination_produit = '"+obj.getLibelleProduit()+"' ,prix_produit = '"+obj.getPrixProduit()+"',id_tva_fk = "+obj.getTva().getId()+",actif = "+obj.getActif()+" WHERE id_produit = '"+obj.getId()+"' ");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}


	public boolean updateStock(Produit obj) {
		System.out.println("Modification dans la BDD des stocks d'un produit");
		System.out.println("UPDATE arn_produits SET stock = '"+obj.getNbStock()+"' WHERE id_produit = '"+obj.getId()+"' ");

		try {this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeUpdate(
				"UPDATE arn_produits SET stock = '"+obj.getNbStock()+"' WHERE id_produit = '"+obj.getId()+"' ");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public int recupStock(int idProduit){
		int idProd = 0;
		try {ResultSet result = (ResultSet) this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery(
				"SELECT * FROM arn_produits WHERE id_produit = " + idProduit);
		DAO<Tva> tvaDao = new TvaDAO(Connect.getInstance());
		if(result.first()) {
			idProd = result.getInt("stock");
		}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return idProd;

	}

}
