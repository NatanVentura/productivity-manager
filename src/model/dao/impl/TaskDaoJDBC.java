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
import model.dao.DaoFactory;
import model.dao.TaskDao;
import model.entities.Day;
import model.entities.Task;

public class TaskDaoJDBC implements TaskDao {

	private Connection conn;

	public TaskDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void create(Task obj) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			LocalDate dt = obj.getDate();
			st = conn.prepareStatement(
					"INSERT INTO Tasks"
					+ "(description,_date,done) "
					+ "VALUES "
					+ "(?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getDescription());
			st.setDate(2,java.sql.Date.valueOf(dt));
			st.setInt(3, obj.isDone() ? 1 : 0);
			
			int rowsAffected = st.executeUpdate();
			if(rowsAffected > 0) {
				rs = st.getGeneratedKeys();
				while(rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
					
				}
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			 
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		
		
	}



	@Override
	public void update(Task obj) {
		PreparedStatement st = null;
		DayDaoJDBC dayDao = (DayDaoJDBC) DaoFactory.createDayDao();
		
		try {
			LocalDate dt = obj.getDate();
			if(dayDao.getDay(dt) == null){
				dayDao.create(new Day(dt,null));
			}
			st = conn.prepareStatement(
					"UPDATE Tasks " +
					"SET description = ?, " + 
					"_date = ?," +
					"done = ? " +
					"WHERE id = ? ;" 
					);
			
			st.setString(1, obj.getDescription());
			st.setDate(2, java.sql.Date.valueOf(obj.getDate()));
			int i = obj.isDone() ? 1 : 0;
			st.setInt(3,i);
			st.setInt(4, obj.getId());
			st.executeUpdate();
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally{
			 
			DB.closeStatement(st);
		}

	}

	@Override
	public void deleteByID(int id) {
		PreparedStatement st = null;
		try {
			
			st = conn.prepareStatement(
					"DELETE " +
					"FROM Tasks " + 
					"WHERE id = ?");
			
			st.setInt(1, id);
			st.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally{
			 
			DB.closeStatement(st);
		}

	}

	@Override
	public List<Task> findByDate(LocalDate date) {
		// TODO Auto-generated method stub
		List<Task> tasks = new ArrayList<>();
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			
			st = conn.prepareStatement(
					"SELECT * FROM Tasks " + 
					"WHERE _date = ?");
			
			st.setDate(1, java.sql.Date.valueOf(date));
			rs = st.executeQuery();
			
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String description = rs.getString("description");
				LocalDate _date = rs.getDate("_date").toLocalDate();
				int done = rs.getInt("done");
				Task task = new Task(id,description,done,_date);
				tasks.add(task);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally{
			 
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		
		return tasks;
	}


	


}
