package model.entities;

import java.io.Serializable;
import java.time.LocalDate;

public class Task implements Serializable{
	

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String description;
	private boolean done;
	private LocalDate date;
	public Task(int id, String description, int done, LocalDate date) {
		super();
		this.id = id;
		this.description = description;
		this.done = (done != 0);
		this.date = date;
	}
	public Task(String description, int done, LocalDate date) {
		super();
		this.description = description;
		this.done = (done != 0);
		this.date = date;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isDone() {
		return done;
	}
	public void setDone(boolean done) {
		this.done = done;
	}
	public void setDone(int done) {
		this.done = (done!=0);
	}
	public LocalDate getDate() {
		return date;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Task other = (Task) obj;
		if (id != other.id)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Task [id=" + id + ", description=" + description + ", done=" + done + ", date=" + date + "]";
	}
	
	
	
	
}
