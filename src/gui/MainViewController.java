package gui;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;

public class MainViewController implements Initializable{

	@FXML
	private MenuItem menuItemConfig;
	
	@FXML
	private MenuItem menuItemHelp;

	@FXML
	private DatePicker datePicker;
	
	@FXML
	private Button goBtn;
	
	@FXML
	private void goBtnAction() {
		LocalDate localDate = datePicker.getValue();
		System.out.println(localDate);
	}
	
	@Override
	public void initialize(URL uri, ResourceBundle rb) {
	}

}
