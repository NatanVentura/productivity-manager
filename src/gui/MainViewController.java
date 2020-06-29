package gui;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class MainViewController implements Initializable{
	
	@FXML
	private MenuItem menuItemAbout;
	
	@FXML
	private MenuItem menuItemStart;

	
	@FXML
	public void aboutOnAction() {
		loadView("/gui/Help.fxml");
	}
	
	@FXML
	public void startOnAction(ActionEvent event) {
		loadView("/gui/TasksView.fxml");
	}



	@Override
	public void initialize(URL uri, ResourceBundle rb) {
	}

	
	 public void loadView(String absoluteName) { 
		 try { 
			 FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName)); 
			 VBox newVBox = loader.load();
	 
	 
			 Scene mainScene = Main.getScene(); VBox mainVBox = (VBox) ((ScrollPane)mainScene.getRoot()).getContent();
			 Node mainMenu = mainVBox.getChildren().get(0);
			 mainVBox.getChildren().clear(); mainVBox.getChildren().add(mainMenu);
			 mainVBox.getChildren().addAll(newVBox.getChildren()); 
			 } catch(Exception e) {
				 throw new RuntimeException(e.getMessage()); 
			 } 
		 }
	 
	 
	

}
