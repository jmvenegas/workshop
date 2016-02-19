package application.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class HostController extends AnchorPane {
	
	@FXML private AnchorPane rootPane;
	@FXML private Pane authPane;
	@FXML private Pane interactivePane;
	@FXML private Button authConnectButton;
	@FXML private TextField portText;
	@FXML private TextField usernameText;
	@FXML private RadioButton statusIndicator;
	@FXML private Button setTopicButton;
	@FXML private TextField topicText;
	@FXML private ListView participantList;
	
	public void initialize() {
		// Turn off the radio button so it only acts as status indicator for user
		this.statusIndicator.setDisable(true);
	}

}
