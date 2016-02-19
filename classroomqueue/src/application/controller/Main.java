package application.controller;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;


public class Main extends Application {
	
	private static final String APP_NAME = "Classroom Queue";
	
	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = FXMLLoader.load(getClass().getResource("/application/view/HostApp.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/application/view/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle(APP_NAME);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

/**
 * TODO - 
 *		Reorganize package structure
 *		Add functionality to client controller for buttons
 *		Add functionality to host controller for buttons
 * 		Implement client/server classes
 * 
 * 
 */
