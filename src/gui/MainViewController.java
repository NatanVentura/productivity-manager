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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Task;
import model.services.TaskServices;

public class MainViewController implements Initializable, DataChangeListener {

	private TaskServices service;

	private LocalDate date;

	private boolean done;

	@FXML
	private ScrollPane rootScrollPane;

	@FXML
	private VBox vBox;

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

	@FXML
	private TableColumn<Task, String> descriptionCol;
	
	@FXML
	private TableColumn<Task, Task> editColumn;
	
	@FXML
	private TableColumn<Task, Task> checkColumn;

	private ObservableList<Task> obsList;

	public void setDao(TaskServices service) {
		this.service = service;
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
		updateTableView();
	}

	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		date = LocalDate.now();
		setDao(new TaskServices());
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
	/*
	 * private void loadView(String absoluteName) { try { FXMLLoader loader = new
	 * FXMLLoader(getClass().getResource(absoluteName)); VBox newVBox =
	 * loader.load();
	 * 
	 * 
	 * Scene mainScene = Main.getScene(); VBox mainVBox = (VBox) ((ScrollPane)
	 * mainScene.getRoot()).getContent();
	 * 
	 * Node mainMenu = mainVBox.getChildren().get(0);
	 * mainVBox.getChildren().clear(); mainVBox.getChildren().add(mainMenu);
	 * mainVBox.getChildren().addAll(newVBox.getChildren()); } catch(Exception e) {
	 * throw new RuntimeException(e.getMessage()); } }
	 */

	public void checkDone() {
		// something
	}

	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Dao was null");
		}
		List<Task> list = service.findByDate(date);
		obsList = FXCollections.observableArrayList(list);
		taskTable.setItems(obsList);
		initEditButtons();
		initCheckBox();
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
		checkColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		checkColumn.setCellFactory(param -> new TableCell<Task, Task>() {
		private final CheckBox checkBox = new CheckBox();
		@Override
		protected void updateItem(Task obj, boolean empty) {
			super.updateItem(obj, empty);
			if (obj == null) {
				setGraphic(null);
				return;
			}
			if (obj.isDone()) {
				checkBox.setSelected(true);
			}
			setGraphic(checkBox);
			
			checkBox.setOnAction(event -> {
				System.out.println(obj);
				obj.setDone(checkBox.isSelected());
				System.out.println(obj);
				service.setDone(obj);
			});
			/*
			button.setOnAction(event -> {
				try {
					createDialogForm(obj, Utils.currentStage(event),"/gui/Form.fxml");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});*/
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
