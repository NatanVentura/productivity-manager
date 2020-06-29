package gui;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.ResourceBundle;

import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.entities.Day;
import model.entities.Task;
import model.services.DayServices;
import model.services.TaskServices;

public class TasksViewController implements Initializable, DataChangeListener {

	private TaskServices taskService;
	
	private DayServices dayService;

	private LocalDate date;

	private Day day;
	
	private TasksViewController _this = this;

	@FXML
	private ScrollPane rootScrollPane;

	@FXML
	private VBox vBox;

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

	@FXML
	private TableColumn<Task, String> descriptionCol;
	
	@FXML
	private TableColumn<Task, Task> editColumn;
	
	@FXML
	private TableColumn<Task, Boolean> checkColumn;
	
	@FXML
	private TableColumn<Task, Task> delColumn;
	

	private ObservableList<Task> obsList;

	public void setTaskDao(TaskServices taskService) {
		this.taskService = taskService;
	}

	@FXML
	private void goBtnAction() {
		try {
			LocalDate dt = datePicker.getConverter().fromString(datePicker.getEditor().getText());
			if (dt == null)
				throw new IllegalArgumentException();
			date = dt;
			loadDate();
			if (errorMsg.getOpacity() == 1)
				errorMsg.setOpacity(0);
		} catch (DateTimeParseException e) {
			errorMsg.setOpacity(1);
		} catch (IllegalArgumentException e) {
			errorMsg.setOpacity(1);
		}

	}

	@FXML
	public void addBtnAction(ActionEvent event) throws IOException {
		Stage parentStage = Utils.currentStage(event);
		Task obj = new Task("", 0, date);
		createDialogForm(obj, parentStage, "/gui/Form.fxml");
	}

	public void loadDate() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String dtStr = date.format(dtf);
		dateTitle.setText(dtStr);
		setDay();
		updateTableView();
	}

	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		date = LocalDate.now();
		setTaskDao(new TaskServices());
		setDayDao(new DayServices());
		loadDate();
		initializeNodes();
	}

	private void initializeNodes() {
		if (descriptionCol != null)
			descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));

		// Stage stage = (Stage) rootScrollPane.getScene().getWindow();
		// taskTable.prefHeightProperty().bind(stage.heightProperty());
		VBox.setVgrow(taskTable, Priority.ALWAYS);
	}
	
	
	public void setDayDao(DayServices dayService) {
		this.dayService = dayService;
	}
	
	public void setDay() {
		day = dayService.getDay(date);
		if(day == null) {
			Day _day = new Day(date, null);
			dayService.create(_day);
			day = dayService.getDay(date);
		}
	}
	
	public void checkDone() {
		boolean prevDone = day.isAllDone();
		day.setAllDone(true);
		for(Task tk : obsList) {
			if(!tk.isDone()) {
				day.setAllDone(false);
				break;
			}
		}
		if(prevDone != day.isAllDone()) {
			dayService.setDone(day);
		}
		if(day.isAllDone()) {
			doneLabel.setText("Congratulation! You done all tasks for this day.");
			doneLabel.setTextFill(Color.web("#008000"));
		} else {
			doneLabel.setText("Oh no! You have undone tasks.");
			doneLabel.setTextFill(Color.web("#ff0000"));
		}
	}

	public void updateTableView() {
		if (taskService == null) {
			throw new IllegalStateException("Dao was null");
		}
		List<Task> list = taskService.findByDate(date);
		obsList = FXCollections.observableArrayList(list);
		taskTable.setItems(obsList);
		initEditButtons();
		initCheckBox();
		initDelButtons();
		checkDone();
	}

	private void initEditButtons() {
		editColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		editColumn.setCellFactory(param -> new TableCell<Task, Task>() {
		private final Button button = new Button("edit");
		@Override
		protected void updateItem(Task obj, boolean empty) {
			super.updateItem(obj, empty);
			if (obj == null) {
				setGraphic(null);
				return;
			}
			setGraphic(button);
			button.setOnAction(event -> {
				try {
					createDialogForm(obj, Utils.currentStage(event),"/gui/Form.fxml");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		}
	});
}	
	private void initCheckBox() {
		checkColumn.setCellValueFactory(new PropertyValueFactory<>("done"));
		checkColumn.setCellFactory(new Callback<TableColumn<Task, Boolean>, TableCell<Task, Boolean>>() {

		    @Override
		    public TableCell<Task, Boolean> call(TableColumn<Task, Boolean> col) {
		    	CheckCell checkCell = new CheckCell(_this);
		    	checkCell.setService(new TaskServices());
		        return checkCell;
		    }
		});
}
	private void initDelButtons() {
		delColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		delColumn.setCellFactory(param -> new TableCell<Task, Task>() {
		private final Button button = new Button("Delete");
		@Override
		protected void updateItem(Task obj, boolean empty) {
			super.updateItem(obj, empty);
			if (obj == null) {
				setGraphic(null);
				return;
			}
			setGraphic(button);
			button.setOnAction(event -> {
				Alert alert = new Alert(AlertType.CONFIRMATION,"Delete task '" + obj.getDescription()+ "' ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
				alert.showAndWait();
				
				if(alert.getResult() == ButtonType.YES) {
					taskService.delete(obj);
					_this.updateTableView();
				}
			});
		}
	});
}

	private void createDialogForm(Task obj, Stage parentStage, String absoluteName) throws IOException {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));

			Pane pane = loader.load();

			FormController controller = loader.getController();
			controller.setTask(obj);
			controller.updateFormData();
			controller.setService(new TaskServices());
			controller.subscribeListener(this);

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Enter task informations");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();

		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	public void onDataChanged() {
		updateTableView();
	}

}
