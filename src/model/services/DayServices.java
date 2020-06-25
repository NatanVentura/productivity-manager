package model.services;

import java.time.LocalDate;

import model.dao.DaoFactory;
import model.dao.impl.DayDaoJDBC;
import model.entities.Day;

public class DayServices {
	DayDaoJDBC dao = (DayDaoJDBC) DaoFactory.createDayDao();
	public void setDone(Day obj) {
		dao.setDone(obj);
	}
	public Day getDay(LocalDate date) {
		return dao.getDay(date);
	}
	public void create(Day obj) {
		dao.create(obj);
	}
}
