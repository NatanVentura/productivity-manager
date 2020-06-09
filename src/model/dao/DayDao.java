package model.dao;

import java.time.LocalDate;

import model.entities.Day;
import model.entities.Task;

public interface DayDao {
	void create(Day obj);
	void toggleDone(Day obj);
	boolean DayExists(LocalDate date);
}
