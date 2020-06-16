package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DayDao;
import model.entities.Day;
import model.entities.Task;
import model.dao.DaoFactory;

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
					+ "(_date,AllTasksDone)"
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
	public void setDone(Day obj,boolean done) {
		PreparedStatement st = null;
		try {
			
			st = conn.prepareStatement(
					"UPDATE Days" +
					"SET done = ?" + 
					"WHERE id = ?");
			
			int i = done ? 1 : 0;
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
	public Day getDay(LocalDate date) {
		PreparedStatement st = null;
		ResultSet rs = null;
		TaskDaoJDBC taskDao = (TaskDaoJDBC) DaoFactory.createTaskDao(conn);
		try {
			
			st = conn.prepareStatement(
					"SELECT * FROM Days " + 
					"WHERE _date = ?");
			
			st.setDate(1, java.sql.Date.valueOf(date));
			rs = st.executeQuery();
			
			if(rs.next()) {
				LocalDate _date = rs.getDate("_date").toLocalDate();
				List<Task> taskList = taskDao.findByDate(_date);
				Day day = new Day(_date,taskList);
				return day;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally{
			 
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		return null;
	}

	@Override
	public List<Day> findAll() {
		List<Day> days = new ArrayList<>();
		PreparedStatement st = null;
		ResultSet rs = null;
		TaskDaoJDBC taskDao = (TaskDaoJDBC) DaoFactory.createTaskDao(conn);
		try {
			st = conn.prepareStatement("SELECT * FROM Days");
			rs = st.executeQuery();
			
			
			while(rs.next()) {
				LocalDate _date = rs.getDate("_date").toLocalDate();
				List<Task> taskList = taskDao.findByDate(_date);
				Day day = new Day(_date,taskList);
				days.add(day);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally{
			 
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		
		return days;
	}

	

}
