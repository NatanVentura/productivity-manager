package model.dao;

import java.time.LocalDate;
import java.util.List;

import model.entities.Task;

public interface TasksDao {
	void create(Task obj);
	void setDone(Task obj);
	void setNotDone(Task obj);
	void updateDescription(Task obj);
	void deleteByID(Task obj);
	List<Task> findByDate(LocalDate date);
	List<Task> findByDoneStatus(boolean doneStatus);
	
	
	
}
