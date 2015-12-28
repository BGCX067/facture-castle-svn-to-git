package Dao;

import java.sql.SQLException;

import Bean.Tva;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;

public class TvaDAO extends DAO<Tva>{

	
	public TvaDAO(Connection conn) {
        super(conn);
	}
	
	public Tva find(int id) {
	    Tva tva = new Tva();                  
	    try {ResultSet result = (ResultSet) this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery(
	                                                                                            "SELECT * FROM arn_tva WHERE id_tva = " + id);
	         if(result.first())tva = new Tva(id, result.getDouble("taux_tva"));
	
	    } catch (SQLException e) {
	            e.printStackTrace();
	    }
	    return tva;
	}
	
	public int findByTaux(String tauxTva) {
		int idTauxTva = 0;                 
	    try {ResultSet result = (ResultSet) this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery(
	                                                                                            "SELECT * FROM arn_tva WHERE taux_tva = '" + tauxTva +"'");
	    if(result.first()){
	    	idTauxTva = result.getInt("id_tva");
        }else{
        	idTauxTva = 0;
        }
	
	    } catch (SQLException e) {
	            e.printStackTrace();
	    }
	    return idTauxTva;
	}
	
	@Override
	public boolean create(Tva obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Tva obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Tva obj) {
		// TODO Auto-generated method stub
		return false;
	}

}
