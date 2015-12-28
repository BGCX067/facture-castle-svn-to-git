package Dao;

import java.sql.SQLException;
import java.util.Calendar;

import Bean.Bilan;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;

public class BilanDAO extends DAO<Bilan>{


	public BilanDAO(Connection conn) {
		super(conn);
	}


	@Override
	public boolean create(Bilan obj) {
		System.out.println("Insertion dans la BDD d'un client");

		int annee = Calendar.getInstance().get(Calendar.YEAR);
		int mois =Calendar.getInstance().get(Calendar.MONTH)+1;
		int jour = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

		System.out.println("INSERT INTO arn_bilan (date_bilan, nomSociete, nomGerantUn, nomGerantDeux, nomGerantTrois, titreBilan, titrePremierePartie, contenuPremierePartie, titreDeuxiemePartie, contenuDeuxiemePartie, titreTroisiemePartie, contenuTroisiemePartie, titreQuatriemePartie, contenuQuatriemePartie)  "
				+ "VALUES ('"  + annee +'-'+mois+'-'+jour + "','" + obj.getNomSociete() + "','" + obj.getNomGerantUn() + "','" + obj.getNomGerantDeux() + "','" + obj.getNomGerantTrois() + "','" + obj.getTitreBilan() + "','" + obj.getTitrePremierePartie() + "','" + obj.getContenuPremierePartie() + "','" + obj.getTitreDeuxiemePartie() + "','" + obj.getContenuDeuxiemePartie() + "','" + obj.getTitreTroisiemePartie() + "','" + obj.getContenuTroisiemePartie() + "','" + obj.getTitreQuatriemePartie() + "','" + obj.getContenuQuatriemePartie().replaceAll("'", "''") + "')");
		try {
			this.connect.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE, java.sql.ResultSet.CONCUR_READ_ONLY).executeUpdate(
					"INSERT INTO arn_bilan (date_bilan, nomSociete, nomGerantUn, nomGerantDeux, nomGerantTrois, titreBilan, titrePremierePartie, contenuPremierePartie, titreDeuxiemePartie, contenuDeuxiemePartie, titreTroisiemePartie, contenuTroisiemePartie, titreQuatriemePartie, contenuQuatriemePartie)  "
							+ "VALUES ('" + annee +'-'+mois+'-'+jour + "','" + obj.getNomSociete() + "','" + obj.getNomGerantUn() + "','" + obj.getNomGerantDeux() + "','" + obj.getNomGerantTrois() + "','" + obj.getTitreBilan() + "','" + obj.getTitrePremierePartie() + "','" + obj.getContenuPremierePartie() + "','" + obj.getTitreDeuxiemePartie() + "','" + obj.getContenuDeuxiemePartie() + "','" + obj.getTitreTroisiemePartie() + "','" + obj.getContenuTroisiemePartie() + "','" + obj.getTitreQuatriemePartie() + "','" + obj.getContenuQuatriemePartie().replaceAll("'", "''") + "')");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Insertion dans la BDD OK");
		return false;
	}

	@Override
	public boolean delete(Bilan obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Bilan obj) {

		int annee = Calendar.getInstance().get(Calendar.YEAR);
		int mois =Calendar.getInstance().get(Calendar.MONTH)+1;
		int jour = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

		System.out.println("UPDATE arn_bilan SET nomSociete = '"+obj.getNomSociete()+"'," +
				"nomGerantUn = '"+obj.getNomGerantUn()+"'," +
				"nomGerantDeux = '"+obj.getNomGerantDeux()+"'," +
				"nomGerantTrois = '"+obj.getNomGerantTrois()+"'," +
				"titreBilan = '"+obj.getTitreBilan()+"'," +
				"titrePremierePartie = '"+obj.getTitrePremierePartie()+"'," +
				"contenuPremierePartie = '"+obj.getContenuPremierePartie()+"'," +
				"titreDeuxiemePartie = '"+obj.getTitreDeuxiemePartie()+"'," +
				"contenuDeuxiemePartie = '"+obj.getContenuDeuxiemePartie()+"'," +
				"titreTroisiemePartie = '"+obj.getTitreTroisiemePartie()+"'," +
				"contenuTroisiemePartie = '"+obj.getContenuTroisiemePartie()+"'," +
				"titreQuatriemePartie = '"+obj.getTitreQuatriemePartie()+"'," +
				"contenuQuatriemePartie = '"+obj.getContenuQuatriemePartie()+"'," +
				"date_bilan = '" + annee +'-'+mois+'-'+jour + "' WHERE MONTH(date_bilan) = " +  (Calendar.getInstance().get(Calendar.MONTH)+1)+ " AND YEAR(date_bilan) = " +  (Calendar.getInstance().get(Calendar.YEAR)));

		try {this.connect.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,java.sql.ResultSet.CONCUR_READ_ONLY).executeUpdate(
				"UPDATE arn_bilan SET nomSociete = '"+obj.getNomSociete()+"'," +
						"nomGerantUn = '"+obj.getNomGerantUn()+"'," +
						"nomGerantDeux = '"+obj.getNomGerantDeux()+"'," +
						"nomGerantTrois = '"+obj.getNomGerantTrois()+"'," +
						"titreBilan = '"+obj.getTitreBilan()+"'," +
						"titrePremierePartie = '"+obj.getTitrePremierePartie()+"'," +
						"contenuPremierePartie = '"+obj.getContenuPremierePartie()+"'," +
						"titreDeuxiemePartie = '"+obj.getTitreDeuxiemePartie()+"'," +
						"contenuDeuxiemePartie = '"+obj.getContenuDeuxiemePartie()+"'," +
						"titreTroisiemePartie = '"+obj.getTitreTroisiemePartie()+"'," +
						"contenuTroisiemePartie = '"+obj.getContenuTroisiemePartie()+"'," +
						"titreQuatriemePartie = '"+obj.getTitreQuatriemePartie()+"'," +
						"contenuQuatriemePartie = '"+obj.getContenuQuatriemePartie()+"'," +
						"date_bilan = '" + annee +'-'+mois+'-'+jour + "' WHERE MONTH(date_bilan) = " +  (Calendar.getInstance().get(Calendar.MONTH)+1) + " AND YEAR(date_bilan) = " +  (Calendar.getInstance().get(Calendar.YEAR)));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Bilan find(int id) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean save(Bilan obj){
		//On select en base, si aucun bilan à la date du jour, on insert, sinon on update

		System.out.println("SELECT * FROM arn_bilan WHERE MONTH(date_bilan) = '" + (Calendar.getInstance().get(Calendar.MONTH)+1) + "'");
		Integer id_Bilan = null;
		try {
			ResultSet result = (ResultSet) this.connect.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
					java.sql.ResultSet.CONCUR_READ_ONLY).executeQuery(
							"SELECT * FROM arn_bilan WHERE MONTH(date_bilan) = '" +  (Calendar.getInstance().get(Calendar.MONTH)+1) + "'");
			if (result.first()) {
				id_Bilan = result.getInt("id_bilan");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		if(id_Bilan == null){
			create(obj);
		}else{
			update(obj);
		}

		return false;
	}


}
