package model.dao;


import java.time.LocalDate;
import java.util.List;

import model.entities.Task;

public interface TaskDao {
	void create(Task obj);
	void toggleDone(Task obj);
	void updateDescription(Task obj, String newDescription);
	void deleteByID(int id);
	public List<Task> findByDate(LocalDate date);
	public List<Task> findByDateAndDone(LocalDate date,boolean doneStatus);
	public List<Task> findAll();
	
	
	
}
