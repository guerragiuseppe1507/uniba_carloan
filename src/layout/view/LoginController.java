package layout.view;

import dataAccess.DAO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginController {
	
	@FXML
	private TextField usernameLabel;
	
	@FXML
	private TextField passwordLabel;
	
	@FXML
	private Label resultLabel;
	
	@FXML
	private void initialize(){
		
	}
	
	@FXML
	private void handleLogin() {
		DAO db = new DAO();
		db.connetti();
		resultLabel.setText(Boolean.toString(db.accesso(usernameLabel.getText(), passwordLabel.getText())));
		db.disconnetti();
	}

}
