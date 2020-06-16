package model.services;

import java.time.LocalDate;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.impl.TaskDaoJDBC;
import model.entities.Task;

public class TaskServices {
	TaskDaoJDBC dao = (TaskDaoJDBC) DaoFactory.createTaskDao();
	public List<Task> findByDate(LocalDate date){
		return dao.findByDate(date);
	}
}
