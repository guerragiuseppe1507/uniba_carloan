package layout.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import dataAccess.DAO;

public class InsertDipendenteController {
	

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

	@FXML
	private void initialize(){
		
	}
	
	@FXML
	private void DipendentRegister() {
		DAO db = new DAO();
		db.connetti();
		resultLabel.setText(Boolean.toString(db.dipendent(usernameField.getText(),emailField.getText(), passwordField.getText(),nomeField.getText(),cognomeField.getText(),Integer.parseInt(filialeField.getText()),telefonoField.getText(),residenzaField.getText())));
		//db.disconnetti();
	}
}