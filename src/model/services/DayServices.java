package model.services;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.impl.TaskDaoJDBC;
import model.entities.Day;
import model.entities.Task;

public class DayServices {
	private Connection conn;
	private TaskDaoJDBC taskDao;
	
	public void createTaskDao(Connection conn) {
		this.conn = conn;
		TaskDaoJDBC taskDao = (TaskDaoJDBC) DaoFactory.createTaskDao(this.conn);
		this.taskDao = taskDao;
	}
	
	public Day newDay(LocalDate date) {
		List<Task> doneTasks = taskDao.findByDateAndDone(date, true);
		List<Task> undoneTasks = taskDao.findByDateAndDone(date, false);
		return new Day(date, doneTasks, undoneTasks);
	}
	
	public void updateDay(Day d) {
		LocalDate date = d.getDate();
		List<Task> doneTasks = taskDao.findByDateAndDone(date, true);
		List<Task> undoneTasks = taskDao.findByDateAndDone(date, false);
		d.setDoneTasks(doneTasks);
		d.setUndoneTasks(undoneTasks);
	}
	
}
