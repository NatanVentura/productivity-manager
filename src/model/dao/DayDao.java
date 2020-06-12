package model.dao;

import java.time.LocalDate;
import java.util.List;

import model.entities.Day;

public interface DayDao {
	void create(Day obj);
	void setDone(Day obj, boolean done);
	Day getDay(LocalDate date);
	public List<Day> findAll();
}
