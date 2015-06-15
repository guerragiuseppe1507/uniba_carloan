package layout.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import dataAccess.DAO;

public class InsertManagerController {
	
	@FXML
	private TextField idLabel;
	
	@FXML
	private TextField usernameLabel;
	
	@FXML
	private TextField emailLabel;
	@FXML
	private TextField passwordLabel;
	
	@FXML
	private TextField filialeLabel;
	
	@FXML
	private TextField nomeLabel;
	
	@FXML
	private TextField cognomeLabel;
	
	@FXML
	private TextField telefonoLabel;
	
	@FXML
	private TextField residenzaLabel;
	
	@FXML
	private Label resultLabel;

	@FXML
	private void initialize(){
		
	}
	
	@FXML
	private void ManagerRegister() {
		DAO db = new DAO();
		db.connetti();
		resultLabel.setText(Boolean.toString(db.manager(usernameLabel.getText(),emailLabel.getText(), passwordLabel.getText(),filialeLabel.getText(),nomeLabel.getText(),cognomeLabel.getText(),telefonoLabel.getText(),residenzaLabel.getText())));
		db.disconnetti();
	}
}
