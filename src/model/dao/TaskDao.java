package model.dao;


import java.time.LocalDate;
import java.util.List;

import model.entities.Task;

public interface TaskDao {
	void create(Task obj);
	void update(Task obj);
	void deleteByID(int id);
	List<Task> findByDate(LocalDate date);
	
	
	
}
