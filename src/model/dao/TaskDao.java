package model.dao;


import java.time.LocalDate;
import java.util.List;

import model.entities.Task;

public interface TaskDao {
	void create(Task obj);
	void setDone(Task obj);
	void update(Task obj);
	void deleteByID(int id);
	List<Task> findByDate(LocalDate date);
	List<Task> findByDateAndDone(LocalDate date,boolean doneStatus);
	List<Task> findAll();
	
	
	
}
