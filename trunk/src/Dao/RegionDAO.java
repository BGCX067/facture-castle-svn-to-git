package Dao;

import java.sql.Connection;
import java.sql.SQLException;

import Bean.Region;

import com.mysql.jdbc.ResultSet;

public class RegionDAO extends DAO<Region>{


	public RegionDAO(Connection conn) {
		super(conn);
	}

	@Override
	public Region find(int id) {
		Region region = new Region();
		try {
			ResultSet result = (ResultSet) this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery(
							"SELECT * FROM arn_region WHERE id_region = "
									+ id);
			if (result.first()) {
				region = new Region(id, result
						.getString("regions"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return region;
	}

	@Override
	public boolean create(Region obj) {
		System.out
		.println("Insertion dans la BDD d'une nouvelle region");
		System.out
		.println("INSERT INTO arn_region (regions)  "
				+ "VALUES ('" + obj.getListeIdRegion() + "')");
		try {
			this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeUpdate(
							"INSERT INTO arn_region (regions)  "
									+ "VALUES ('" + obj.getListeIdRegion() + "')");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(Region obj) {
		System.out.println("suppression dans la BDD d'une region");
		System.out
		.println("DELETE FROM arn_region WHERE id_region  = '"
				+ obj.getId() + "' ");

		try {
			this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeUpdate(
							"DELETE FROM arn_region WHERE id_region = '"
									+ obj.getId() + "' ");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(Region obj) {
		System.out.println("Modification dans la BDD d'une region");
		System.out
		.println("UPDATE arn_region SET regions = '"
				+ obj.getListeIdRegion()
				+ "'");

		try {
			this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeUpdate(
							"UPDATE arn_region SET regions = '"
									+ obj.getListeIdRegion()
									+ "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
