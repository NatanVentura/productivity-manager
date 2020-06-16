package model.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class Day implements Serializable{

	private static final long serialVersionUID = 1L;
	private LocalDate date;
	private boolean allDone;
	private List<Task> taskList;
	public Day(LocalDate date, List<Task> taskList) {
		this.date = date;
		this.taskList = taskList;
		this.allDone = verifyDone();
	}
	public boolean verifyDone() {
		for(Task tk : taskList) {
			if(tk.isDone()) continue;
			else return false;
		}
		return true;
	}
	public LocalDate getDate() {
		return date;
	}
	public boolean isAllDone() {
		return allDone;
	}
	

	
	public List<Task> getTaskList() {
		return taskList;
	}
	public void setTaskList(List<Task> taskList) {
		this.taskList = taskList;
	}
	public void setAllDone(boolean allDone) {
		this.allDone = allDone;
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
