package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import db.DB;
import db.DbException;
import model.dao.DayDao;
import model.entities.Day;

public class DayDaoJDBC implements DayDao{
	
	private Connection conn;

	public DayDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void create(Day obj) {
		PreparedStatement st = null;
		try {
			
			st = conn.prepareStatement(
					"INSERT INTO Days"
					+ "(_date,done)"
					+ "VALUES"
					+ "(?,?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setDate(1,java.sql.Date.valueOf(obj.getDate()));
			st.setInt(2, obj.isAllDone() ? 1 : 0);
			
			int rowsAffected = st.executeUpdate();
			if(rowsAffected == 0) {
				throw new DbException("Unexpected error");
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			DB.closeStatement(st);
		}
		
		
	}

	@Override
	public void toggleDone(Day obj) {
		PreparedStatement st = null;
		try {
			
			st = conn.prepareStatement(
					"UPDATE Days" +
					"SET done = ?" + 
					"WHERE id = ?");
			
			int i = obj.isAllDone() ? 0 : 1;
			obj.setAllDone(!obj.isAllDone());
			st.setInt(1, i);
			st.setDate(2, java.sql.Date.valueOf(obj.getDate()));
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally{
			 
			DB.closeStatement(st);
		}
		
	}

	@Override
	public boolean DayExists(LocalDate date) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			
			st = conn.prepareStatement(
					"SELECT * FROM Tasks " + 
					"WHERE _date = ?");
			
			st.setDate(1, java.sql.Date.valueOf(date));
			rs = st.executeQuery();
			
			return rs.next();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally{
			 
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		return false;
	}

	

}
