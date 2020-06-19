package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Task;

public class FormController implements Initializable{
	
	private Task task;
	
	@FXML
	private Button saveBtn;
	
	@FXML
	private Button cancelBtn;
	
	@FXML
	private TextField descInput;
	
	@FXML
	private Label errorLabel;
	
	public void setTask(Task obj) {
		System.out.println("Here we are " + obj);
		task = obj;
	}
	
	@FXML
	public void cancelBtnOnaction() {
		
	}
	
	@FXML
	public void saveBtnOnaction() {
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeNodes();
	}

	private void initializeNodes() {
		
	}
	
	public void updateFormData() {
		if(task == null) {
			//throw new IllegalArgumentException("Task object cannot be null");
		}
		Constraints.setTextFieldMaxLength(descInput, 30);
		descInput.setText(task.getDescription());
	}
}
