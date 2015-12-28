package Dao;

import java.sql.SQLException;

import Bean.LigneDeProduit;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;

public class LigneDeProduitDAO extends DAO<LigneDeProduit> {

        public LigneDeProduitDAO(Connection conn) {
                super(conn);
        }

        public LigneDeProduit find(int id) {
        	LigneDeProduit ligneDeProduit = new LigneDeProduit();                  
            try {ResultSet result = (ResultSet) this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery(
                                                                                                    "SELECT * FROM arn_relation_facture_produits WHERE id_facture_fk = " + id);
                 if(result.first())ligneDeProduit = new LigneDeProduit(result.getInt("id"), result.getInt("id_facture_fk"), result.getInt("id_produit_fk"), result.getInt("quantite_produit"), result.getDouble("prix_unitaire"), result.getDouble("prix_total"));

            } catch (SQLException e) {
                    e.printStackTrace();
            }
            return ligneDeProduit;
        }
        
		@Override
		public boolean create(LigneDeProduit obj) {
			// TODO Auto-generated method stub
			System.out.println("Insertion dans la BDD d'une ligne de produit");
			try {this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeUpdate(
                    "INSERT INTO arn_relation_facture_produits ( id_facture_fk, id_produit_fk, quantite_produit, prix_unitaire, prix_total )  " +
                    "VALUES ("+obj.getId_facture_fk()+","+obj.getId_produit_fk()+","+obj.getQuantite()+",'"+obj.getPrixUnitaire()+"','"+obj.getPrixTotal()+"')");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return false;
		}

		@Override
		public boolean delete(LigneDeProduit obj) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean update(LigneDeProduit obj) {
			// TODO Auto-generated method stub
			return false;
		}



}
