package application;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;

import db.DB;
import model.dao.DaoFactory;
import model.dao.impl.TaskDaoJDBC;
import model.entities.Task;

public class Program {

	
	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		Connection conn = DB.getConnection();
		TaskDaoJDBC taskDao = (TaskDaoJDBC) DaoFactory.createTaskDao(conn);
		Task task = new Task("correr", 0, LocalDate.of(2020, Month.APRIL, 11));
		taskDao.create(task);
		taskDao.updateDescription(task, "Paçoca");
		taskDao.findAll();
		DB.closeConnection();
		//System.out.println(taskDao.findByDate(dt));
		
	}

}
