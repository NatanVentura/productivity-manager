package application;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;

import db.DB;
import model.dao.DaoFactory;
import model.dao.impl.DayDaoJDBC;
import model.dao.impl.TaskDaoJDBC;
import model.entities.Task;

public class Program {

	
	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		Connection conn = DB.getConnection();
		TaskDaoJDBC taskDao = (TaskDaoJDBC) DaoFactory.createTaskDao(conn);
		DayDaoJDBC dayDao = (DayDaoJDBC) DaoFactory.createDayDao(conn);
		LocalDate date = LocalDate.of(2020, Month.JUNE, 16);
		taskDao.create(new Task("desesperar",1,  date));
		taskDao.create(new Task("cu",1, date));
		taskDao.create(new Task("memdraf", 0, date));
		taskDao.create(new Task("correr", 1, date));
		taskDao.findAll();
		System.out.println("------------");
		System.out.println(dayDao.getDay(date).verifyDone());
		DB.closeConnection();
		

		//System.out.println(taskDao.findByDate(dt));
		
		
	}

}
