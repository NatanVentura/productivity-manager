package model.dao;

import java.sql.Connection;


import model.dao.impl.TaskDaoJDBC;

public class DaoFactory {
	public static TaskDao createTaskDao(Connection conn) {
		return new TaskDaoJDBC(conn);
	}
}
