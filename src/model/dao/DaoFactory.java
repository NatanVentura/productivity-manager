package model.dao;

import db.DB;
import model.dao.impl.DayDaoJDBC;
import model.dao.impl.TaskDaoJDBC;

public class DaoFactory {
	public static TaskDao createTaskDao() {
		return new TaskDaoJDBC(DB.getConnection());
	}
	public static DayDao createDayDao() {
		return new DayDaoJDBC(DB.getConnection());
	}
}
