package model.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class Day implements Serializable{

	private static final long serialVersionUID = 1L;
	private LocalDate date;
	private boolean isDone;
	private List<Task> tasks;
	public Day(LocalDate date) {
		super();
		this.date = date;
		this.isDone = false;
		this.tasks = retrieveTasks();
		
	}
	private List<Task> retrieveTasks(){
		List<Task> dbTasks = null;
		return dbTasks;
	}
	
	public LocalDate getDate() {
		return date;
	}
	public boolean isDone() {
		return isDone;
	}
	public void setDone(boolean isDone) {
		this.isDone = isDone;
	}
	public List<Task> getTasks() {
		return tasks;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Day other = (Day) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		return true;
	}
	
	
	
	
	
}
