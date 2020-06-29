package model.dao;

import java.time.LocalDate;

import model.entities.Day;

public interface DayDao {
	void create(Day obj);
	void setDone(Day obj);
	Day getDay(LocalDate date);
}
