package application.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class ClientController extends AnchorPane {
	
	@FXML private AnchorPane rootPane;
	@FXML private Pane authPane;
	@FXML private Pane interactivePane;
	@FXML private Button authConnectButton;
	@FXML private Button joinQueueButton;
	@FXML private Button exitQueueButton;
	@FXML private RadioButton statusIndicator;
	@FXML private TextField hostAddressText;
	@FXML private TextField usernameText;
	
	public void initialize() {
		// Turn off the radio button so it only acts as status indicator for user
		this.statusIndicator.setDisable(true);		
	}

}
