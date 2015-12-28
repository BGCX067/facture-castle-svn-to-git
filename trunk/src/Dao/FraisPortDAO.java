package Dao;

import java.sql.Connection;
import java.sql.SQLException;

import Bean.FraisPort;

import com.mysql.jdbc.ResultSet;

public class FraisPortDAO extends DAO<FraisPort> {

	public FraisPortDAO(Connection conn) {
		super(conn);
	}

	@Override
	public FraisPort find(int id) {
		FraisPort fraisPort = new FraisPort();
		try {
			ResultSet result = (ResultSet) this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery(
							"SELECT * FROM arn_frais_port WHERE id_frais_port = " + id);
			if (result.first()) {
				fraisPort = new FraisPort(id, result.getString("tranche1"),
						result.getString("tranche2"),
						result.getString("tranche3"),
						result.getString("tranche4"),
						result.getString("tranche5"),
						result.getString("tranche6"),
						result.getString("tranche7"),
						result.getString("tranche8"),
						result.getString("tranche9"),
						result.getString("tranche10"),
						result.getString("tranche11"),
						result.getString("tranche12"),
						result.getInt("id_region_fk"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return fraisPort;
	}

	@Override
	public boolean create(FraisPort obj) {
		System.out.println("Insertion dans la BDD d'une nouvelle ligne");
		System.out
		.println("INSERT INTO arn_frais_port (tranche1,tranche2,tranche3,tranche4,tranche5,tranche6,tranche7,tranche8,tranche9,tranche10,tranche11,tranche12,id_region_fk)  "
				+ "VALUES ('"
				+ obj.getTranche1()
				+ "',"
				+ obj.getTranche2()
				+ ","
				+ obj.getTranche3()
				+ ","
				+ obj.getTranche4()
				+ ","
				+ obj.getTranche5()
				+ ","
				+ obj.getTranche6()
				+ ","
				+ obj.getTranche7()
				+ ","
				+ obj.getTranche8()
				+ ","
				+ obj.getTranche9()
				+ ","
				+ obj.getTranche10()
				+ ","
				+ obj.getTranche11()
				+ ","
				+ obj.getTranche12()
				+ ","
				+ obj.getId_region() + ")");
		try {
			this.connect
			.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY)
					.executeUpdate(
							"INSERT INTO arn_frais_port (tranche1,tranche2,tranche3,tranche4,tranche5,tranche6,tranche7,tranche8,tranche9,tranche10,tranche11,tranche12,id_region_fk)  "
									+ "VALUES ('"
									+ obj.getTranche1()
									+ "',"
									+ obj.getTranche2()
									+ ","
									+ obj.getTranche3()
									+ ","
									+ obj.getTranche4()
									+ ","
									+ obj.getTranche5()
									+ ","
									+ obj.getTranche6()
									+ ","
									+ obj.getTranche7()
									+ ","
									+ obj.getTranche8()
									+ ","
									+ obj.getTranche9()
									+ ","
									+ obj.getTranche10()
									+ ","
									+ obj.getTranche11()
									+ ","
									+ obj.getTranche12()
									+ ","
									+ obj.getId_region() + ")");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(FraisPort obj) {
		System.out.println("suppression dans la BDD d'un frais de port");
		System.out
		.println("DELETE FROM arn_frais_port WHERE id_frais_port  = '"
				+ obj.getId_frais_port() + "' ");

		try {
			this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeUpdate(
							"DELETE FROM arn_frais_port WHERE id_frais_port  = '"
									+ obj.getId_frais_port() + "' ");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(FraisPort obj) {
		System.out.println("Modification dans la BDD d'une Tranche");
		System.out.println("UPDATE arn_frais_port SET tranche1 = '"
				+ obj.getTranche1() + "',tranche2 = '" + obj.getTranche2()
				+ "',tranche3 = '" + obj.getTranche3() + "',tranche4 = '"
				+ obj.getTranche4() + "',tranche5 = '" + obj.getTranche5()
				+ "',tranche6 = '" + obj.getTranche6() + "',tranche7 = '"
				+ obj.getTranche7() + "',tranche8 = '" + obj.getTranche8()
				+ "',tranche9 = '" + obj.getTranche9() + "',tranche10 = '"
				+ obj.getTranche10() + "',tranche11 = '" + obj.getTranche11()
				+ "',tranche12 = '" + obj.getTranche12() + "',id_region_fk = '"
				+ obj.getId_region() + "' WHERE id_frais_port=" + obj.getId_frais_port());

		try {
			this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeUpdate(
							"UPDATE arn_frais_port SET tranche1 = '"
									+ obj.getTranche1() + "',tranche2 = '" + obj.getTranche2()
									+ "',tranche3 = '" + obj.getTranche3() + "',tranche4 = '"
									+ obj.getTranche4() + "',tranche5 = '" + obj.getTranche5()
									+ "',tranche6 = '" + obj.getTranche6() + "',tranche7 = '"
									+ obj.getTranche7() + "',tranche8 = '" + obj.getTranche8()
									+ "',tranche9 = '" + obj.getTranche9() + "',tranche10 = '"
									+ obj.getTranche10() + "',tranche11 = '" + obj.getTranche11()
									+ "',tranche12 = '" + obj.getTranche12() + "',id_region_fk = '"
									+ obj.getId_region() + "' WHERE id_frais_port=" + obj.getId_frais_port());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
