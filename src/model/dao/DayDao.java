package model.dao;

import java.time.LocalDate;
import java.util.List;

import model.entities.Day;

public interface DayDao {
	void create(Day obj);
	void toggleDone(Day obj);
	boolean DayExists(LocalDate date);
	public List<Day> findAll();
}
