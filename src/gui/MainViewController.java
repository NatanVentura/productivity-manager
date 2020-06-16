package gui;

import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;



import application.Main;
import db.DB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import model.dao.impl.DayDaoJDBC;
import model.dao.impl.TaskDaoJDBC;
import model.entities.Task;

public class MainViewController implements Initializable{

	private TaskDaoJDBC dao;
	
	private Connection conn = DB.getConnection();
	
	@FXML
	private MenuItem menuItemConfig;
	
	@FXML
	private MenuItem menuItemHelp;

	@FXML
	private DatePicker datePicker;
	
	@FXML
	private Button goBtn;
	
	@FXML
	private Label errorMsg;
	
	@FXML
	private Label dateTitle;
	
	@FXML
	private Button addBtn;
	
	@FXML
	private TableView<Task> taskTable;
	
	@FXML
	private Label doneLabel;
	
	
	
	private ObservableList<Task> obsList;
	
	public void setDao(TaskDaoJDBC dao) {
		this.dao = dao;
	}
	
	@FXML
	private void goBtnAction() {
		LocalDate date = datePicker.getValue();
		System.out.println(date);
		if(date == null) {
			errorMsg.setOpacity(1);
		} else {
			try {
				loadDate(date);
			} catch(Exception e) {
				System.out.println(e.getMessage());
			}
			
		}
		
	}
	
	public void loadDate(LocalDate dt) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String dtStr = dt.format(dtf);
		dateTitle.setText(dtStr);
		setDao(new TaskDaoJDBC(conn));
		updateTableView(dt);
	}
	
	
	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		loadDate(LocalDate.now());
	}
	
	private void loadView(String absoluteName) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVBox = loader.load();
			
			Scene mainScene = Main.getScene();
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			
			Node mainMenu = mainVBox.getChildren().get(0);
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(newVBox.getChildren());
		} catch(Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public void updateTableView(LocalDate date) {
		if (dao == null) {
			throw new IllegalStateException("Dao was null");
		}
		List<Task> list = dao.findByDate(date);
		obsList = FXCollections.observableArrayList(list);
		taskTable.setItems(obsList);
	}

}
