package layout.view;

import java.util.HashMap;

import presentationTier.FrontController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import dataAccess.DAO;

public class InsertManagerController {
	
	@FXML
	private TextField idField;
	
	@FXML
	private TextField id_utenteField;
	
	@FXML
	private TextField id_filialeField;

	@FXML
	private Label resultLabel;

	@FXML
	private void initialize(){
		
	}
	
	@FXML
	private void ManagerRegisterw() {
		DAO db = new DAO();
		db.connetti();
		resultLabel.setText(Boolean.toString(db.registraManagerDiFiliale(Integer.parseInt(idField.getText()),Integer.parseInt(id_utenteField.getText()),Integer.parseInt( id_filialeField.getText()))));
		//db.disconnetti
	}




















}