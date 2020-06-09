package model.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class Day implements Serializable{

	private static final long serialVersionUID = 1L;
	private LocalDate date;
	private boolean allDone;
	private List<Task> doneTasks;
	private List<Task> undoneTasks;
	public Day(LocalDate date, List<Task> doneTasks, List<Task> undoneTasks) {
		this.date = date;
		this.allDone = verifyDone();
		this.doneTasks = doneTasks;
		this.undoneTasks = undoneTasks;
	}
	public boolean verifyDone() {
		return (undoneTasks.size() != 0);
	}
	public LocalDate getDate() {
		return date;
	}
	public boolean isAllDone() {
		return allDone;
	}
	
	public List<Task> getDoneTasks() {
		return doneTasks;
	}
	public void setDoneTasks(List<Task> doneTasks) {
		this.doneTasks = doneTasks;
	}
	public List<Task> getUndoneTasks() {
		return undoneTasks;
	}
	public void setUndoneTasks(List<Task> undoneTasks) {
		this.undoneTasks = undoneTasks;
		verifyDone();
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
