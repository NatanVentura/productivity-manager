package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.entities.Task;


public class Main extends Application {
	
	private static Scene mainScene;
	
	@Override
	public void start(Stage primaryStage) {
	try {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MainView.fxml"));
		ScrollPane scrollPane = loader.load();
		
		scrollPane.setFitToHeight(true);
		scrollPane.setFitToWidth(true);
		
		mainScene = new Scene(scrollPane);
		System.out.println(mainScene);
		primaryStage.setScene(mainScene);
		primaryStage.setTitle("Productivity Manager");
		primaryStage.show();
	}catch(IOException e){
		e.printStackTrace();
	}
}
	public static Scene getScene() {
		return mainScene;
	}
	
	public static void main(String[] args) {
		launch(args);
		//Connection conn = DB.getConnection();
	}
}
