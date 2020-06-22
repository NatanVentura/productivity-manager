package gui;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Task;
import model.services.TaskServices;

public class FormController implements Initializable{
	
	private Task task;
	
	private TaskServices service;
	
	private List<DataChangeListener> listeners = new ArrayList<>();
	
	@FXML
	private Button saveBtn;
	
	@FXML
	private Button cancelBtn;
	
	@FXML
	private TextField descInput;
	
	@FXML
	private Label descError;

	@FXML
	private Label dateError;
	
	@FXML
	private DatePicker dateInput;
	
	public void setTask(Task obj) {
		task = obj;
	}
	
	@FXML
	public void cancelBtnOnaction() {
		
	}
	
	
	@FXML
	public void saveBtnOnaction(ActionEvent event) {
		try {
			boolean sucess = getFormData();
			if(sucess) {
				if(service == null) throw new IllegalArgumentException();
				service.createOrUpdate(task);
				notifyListeners();
				Utils.currentStage(event).close();
			}
		} catch(IllegalArgumentException e) {
			Alerts.showAlert("Illegal Exception", "Error saving", e.getMessage(), AlertType.ERROR);
		} catch(DbException e) {
			Alerts.showAlert("DB Exception", "Error saving", e.getMessage(), AlertType.ERROR);
		}
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeNodes();
	}

	private boolean getFormData() {
		boolean error = false;
		
		TaskServices service = new TaskServices();
		String strDate = dateInput.getEditor().getText();
		System.out.println(strDate);
		LocalDate dt = service.tryToGetDate(strDate);
		service.tryToGetDate(strDate);
		if(dt == null) {
			dateError.setText("Invalid date");
			error = true;
		}
		
		String description = descInput.getText();
		System.out.println(description.length());
		if(description.length() == 0) {
			descError.setText("Description was null");
			error = true;
		}
		
		if(error) {
			return false;
		} else {
			task.setDate(dt);
			task.setDescription(description);
			return true;
		}
	}
	
	public void setService(TaskServices service){
		this.service = service;
	}
	
	public void subscribeListener(DataChangeListener listener){
		listeners.add(listener);
	}
	
	public void notifyListeners() {
		for(DataChangeListener listener : listeners) {
			listener.onDataChanged();
		}
	}
	
	private void initializeNodes() {
		
	}
	
	public void updateFormData() {
		if(task == null) {
			throw new IllegalArgumentException("Task object cannot be null");
		}
		Constraints.setTextFieldMaxLength(descInput, 30);
		descInput.setText(task.getDescription());
		dateInput.setValue(task.getDate());
	}
}
