package layout.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import layout.model.ContextMenu;
import dataAccess.DAO;

public class InsertDipendenteController implements Initializable, ControlledScreen{
	
	ScreensController myController;
	
	@FXML
	StackPane container;
	
	@FXML
	AnchorPane menu;

	@FXML
	private TextField usernameField;
	
	@FXML
	private TextField emailField;
	@FXML
	private TextField passwordField;
	
	@FXML
	private TextField nomeField;
	
	@FXML
	private TextField cognomeField;
	
	@FXML
	private TextField filialeField;
	
	@FXML
	private TextField telefonoField;
	
	@FXML
	private TextField residenzaField;
	
	@FXML
	private Label resultLabel;

	@Override
	public void initialize(URL url, ResourceBundle rb){
		container.setPrefHeight(ScreensFramework.DEFAULT_HEIGHT);
		container.setPrefWidth(ScreensFramework.DEFAULT_WIDTH);
		menu.setPrefHeight(ScreensFramework.DEFAULT_MENU_HEIGHT);
		menu.setPrefWidth(ScreensFramework.DEFAULT_MENU_WIDTH);
	}
	
	public void setScreenParent(ScreensController screenParent){
		myController = screenParent;
		ContextMenu.showContextMenu(menu,myController);
	}
	
	@FXML
	private void DipendentRegister() {
		DAO db = new DAO();
		db.connetti();
		resultLabel.setText(Boolean.toString(db.dipendent(usernameField.getText(),emailField.getText(), passwordField.getText(),nomeField.getText(),cognomeField.getText(),Integer.parseInt(filialeField.getText()),telefonoField.getText(),residenzaField.getText())));
		//db.disconnetti();
	}
}
