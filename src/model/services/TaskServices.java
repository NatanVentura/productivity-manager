package model.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.impl.TaskDaoJDBC;
import model.entities.Task;

public class TaskServices {
	TaskDaoJDBC dao = (TaskDaoJDBC) DaoFactory.createTaskDao();
	public List<Task> findByDate(LocalDate date){
		return dao.findByDate(date);
	}
	
	public void createOrUpdate(Task obj) {
		if(obj.getId() == 0) {
			dao.create(obj);
		} else {
			dao.update(obj);
		}
		
	}
	
	public void delete(Task obj) {
		dao.deleteByID(obj.getId());
	}
	
	public LocalDate tryToGetDate(String strDate){
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		    LocalDate dt = LocalDate.parse(strDate, dtf);
		    if(dt == null) throw new IllegalArgumentException();
		    return dt;
		} catch (Exception e) {
			return null;
		}
	}
}
