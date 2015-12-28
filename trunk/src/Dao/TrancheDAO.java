package Dao;

import java.sql.Connection;
import java.sql.SQLException;

import Bean.Tranche;

import com.mysql.jdbc.ResultSet;

public class TrancheDAO extends DAO<Tranche>{



	public TrancheDAO(Connection conn) {
		super(conn);
	}

	@Override
	public Tranche find(int id) {
		Tranche tranche = new Tranche();
		try {
			ResultSet result = (ResultSet) this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery(
							"SELECT * FROM arn_tranche_frais_port WHERE id_region = "
									+ id);
			if (result.first()) {
				tranche = new Tranche(id, result
						.getString("nom_tranche"), result.getInt("tranche_min"), result.getInt("tranche_max"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tranche;
	}

	@Override
	public boolean create(Tranche obj) {
		System.out
		.println("Insertion dans la BDD d'une nouvelle tranche");
		System.out
		.println("INSERT INTO arn_tranche_frais_port (nom_tranche,tranche_min,tranche_max)  "
				+ "VALUES ('"+obj.getNomTranche()+"',"+obj.getMinimum()+","+obj.getMaximum()+")");
		try {
			this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeUpdate(
							"INSERT INTO arn_tranche_frais_port (nom_tranche,tranche_min,tranche_max)  "
									+ "VALUES ('"+obj.getNomTranche()+"',"+obj.getMinimum()+","+obj.getMaximum()+")");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(Tranche obj) {
		System.out.println("suppression dans la BDD d'une tranche");
		System.out
		.println("DELETE FROM arn_tranche_frais_port WHERE id_tranche_frais_port  = '"
				+ obj.getId_tranche() + "' ");

		try {
			this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeUpdate(
							"DELETE FROM arn_tranche_frais_port WHERE id_tranche_frais_port  = '"
									+ obj.getId_tranche() + "' ");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(Tranche obj) {
		System.out.println("Modification dans la BDD d'une Tranche");
		System.out
		.println("UPDATE arn_tranche_frais_port SET tranche_min = '"+ obj.getMinimum()+ "',tranche_max = '"+ obj.getMaximum()+ "' WHERE id_tranche_frais_port="+obj.getId_tranche());

		try {
			this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeUpdate(
							"UPDATE arn_tranche_frais_port SET tranche_min = '"+ obj.getMinimum()+ "',tranche_max = '"+ obj.getMaximum()+ "' WHERE id_tranche_frais_port="+obj.getId_tranche());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}


}
